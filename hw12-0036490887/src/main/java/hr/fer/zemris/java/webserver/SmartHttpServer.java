package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.BiConsumer;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class {@link SmartHttpServer} specifies how should http 
 * server should behave. Accepts server configuration file, document 
 * root, workers properties and others. It creates each thread for 
 * each connection which is established.
 * @author dario
 *
 */
public class SmartHttpServer {
    /**
     * address constant
     */
    private static final String ADDRESS = "server.address";
    /**
     * domain name constant
     */
    private static final String DOMAIN_NAME = "server.domainName";
    /**
     * server port constant
     */
    private static final String PORT = "server.port";
    /**
     * workers thread constant
     */
    private static final String WORKER_THREADS = "server.workerThreads";
    /**
     * document root constant
     */
    private static final String DOCUMENT_ROOT = "server.documentRoot";
    /**
     * timeout constant
     */
    private static final String TIMEOUT = "session.timeout";
    /**
     * mime config constant
     */
    private static final String MIME_CONFIG = "server.mimeConfig";
    /**
     * workers config constant
     */
    private static final String WORKERS_CONFIG = "server.workers";
    /**
     * sleep time of the session cleaner
     */
    private static final int SLEEP_TIME = 1000 * 60 * 5;
    /**
     * length of the session id
     */
    public static final int SID_LEN = 20;

    /**
     * address of the server
     */
    private String address;
    /**
     * domain name of the server
     */
    @SuppressWarnings("unused")
    private String domainName;
    /**
     * port of the server
     */
    private int port;
    /**
     * number of workers thread
     */
    private int workerThreads;
    /**
     * session timeout
     */
    private int sessionTimeout;
    /**
     * mime types
     */
    private Map<String, String> mimeTypes = new HashMap<>();
    /**
     * server thread
     */
    private ServerThread serverThread;
    /**
     * thread pool
     */
    private ExecutorService threadPool;
    /**
     * document root, path
     */
    private Path documentRoot;
    /**
     * hash map of the workers map
     */
    private Map<String, IWebWorker> workersMap = new HashMap<>();

    /**
     * map of the session
     */
    private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();

    /**
     * SessionRandom, used in generating sid
     */
    private Random sessionRandom = new Random();
    /**
     * if the server is turned off
     */
    private boolean serverShutDown;
    
    /**
     * Thread responsible for cleaning the session every {@link #SLEEP_TIME} time.
     */
    private Thread sessionGarbageCollector = new Thread(() -> {
        while (true) {
            synchronized (SmartHttpServer.this) {
                long currentTime = System.currentTimeMillis() / 1000;
                Map<String, SessionMapEntry> sessionsCopy = new HashMap<>(sessions);
                for (Entry<String, SessionMapEntry> entry : sessionsCopy.entrySet()) {
                    if (entry.getValue().validUntil > currentTime) {
                        sessions.remove(entry.getKey());
                    }
                }
            }
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
            }
        }
    });
    

    /**
     * Main method which is responsible for running the server.
     * 
     * @param args
     *            one argument, port
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("I expect only one argument.");
            return;
        }
        SmartHttpServer server = new SmartHttpServer(args[0]);
        server.start();
    }

    /**
     * Constructs http server and gives it configuration file in which it specifies
     * all necessary server properties.
     * 
     * @param configFileName
     *            configuration file
     */
    public SmartHttpServer(String configFileName) {
        Properties properties = new Properties();

        try {
            InputStream inputStream = new FileInputStream(Paths.get(configFileName).toFile());
            properties.load(inputStream);

            address = properties.getProperty(ADDRESS);
            domainName = properties.getProperty(DOMAIN_NAME);
            port = Integer.parseInt(properties.getProperty(PORT));
            workerThreads = Integer.parseInt(properties.getProperty(WORKER_THREADS));
            documentRoot = Paths.get(properties.getProperty(DOCUMENT_ROOT));
            sessionTimeout = Integer.parseInt(properties.getProperty(TIMEOUT));

            Path mimePath = Paths.get(properties.getProperty(MIME_CONFIG));
            Path workersPath = Paths.get(properties.getProperty(WORKERS_CONFIG));

            Properties mimeProperties = new Properties();
            InputStream mimeInputStream = new FileInputStream(mimePath.toFile());
            mimeProperties.load(mimeInputStream);
            mimeProperties.forEach(new BiConsumer<Object, Object>() {

                @Override
                public void accept(Object key, Object value) {
                    mimeTypes.put(key.toString(), value.toString());
                }
            });

            Properties workersProperties = new Properties();
            InputStream workersInputStream = new FileInputStream(workersPath.toFile());
            workersProperties.load(workersInputStream);
            workersProperties.forEach(new BiConsumer<Object, Object>() {

                @SuppressWarnings("deprecation")
                @Override
                public void accept(Object key, Object value) {

                    Class<?> referenceToClass;
                    try {
                        referenceToClass = this.getClass().getClassLoader().loadClass(value.toString());

                        Object newObject = referenceToClass.newInstance();
                        if (workersMap.containsKey(key.toString())) {
                            throw new RuntimeException("Workers map already exsists.");
                        }
                        workersMap.put(key.toString(), (IWebWorker) newObject);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            });

        } catch (IOException e) {
            System.out.println("There was problem with reading the given file.");
        }
    }

    /**
     * Method start server thread and session collector thread.
     */
    protected synchronized void start() {

        this.sessionGarbageCollector.setDaemon(true);
        this.sessionGarbageCollector.start();

        this.threadPool = Executors.newFixedThreadPool(workerThreads, new ThreadFactory() {

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setDaemon(true);
                return thread;
            }
        });
        if (serverThread == null) {
            serverThread = new ServerThread();
            serverThread.start();
        }
    }

    /**
     * Method stops this thread so it exits all other threads because they are
     * daemon.
     */
    protected synchronized void stop() {
        threadPool.shutdown();
        serverShutDown = true;
        System.exit(0);
    }

    /**
     * {@link ServerThread} extends {@link Thread} which specifies how server should
     * be run.
     * 
     * @author dario
     *
     */
    protected class ServerThread extends Thread {
        /**
         * Creates ServerThread and sets it to the daemon thread.
         */
        private ServerThread() {
            // setDaemon(true);
        }

        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    if(serverShutDown) return;
                    Socket client = serverSocket.accept();
                    ClientWorker cw = new ClientWorker(client);
                    threadPool.submit(cw);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Class {@link ClientWorker} implements {@link Runnable} and
     * {@link IDispatcher} and specifies what should every thread responsible for
     * worker should do.
     * 
     * @author dario
     *
     */
    private class ClientWorker implements Runnable, IDispatcher {

        /**
         * client socket
         */
        private Socket csocket;
        /**
         * input stream
         */
        // TODO
        private InputStream inputStream;
        /**
         * output stream
         */
        private OutputStream outputStream;
        /**
         * version of the http
         */
        @SuppressWarnings("unused")
        private String version;
        /**
         * method od the http
         */
        @SuppressWarnings("unused")
        private String method;
        /**
         * host of the http
         */
        @SuppressWarnings("unused")
        private String host;
        /**
         * parameters
         */
        private Map<String, String> params = new HashMap<>();
        /**
         * temopapry parameters
         */
        private Map<String, String> tempParams = new HashMap<>();
        /**
         * persistent parameters
         */
        private Map<String, String> perParams = new HashMap<>();
        /**
         * list of cookies
         */
        private List<RCCookie> outputCookies = new ArrayList<>();
        /**
         * session id
         */
        private String SID;
        /**
         * request context
         */
        private RequestContext context;

        /**
         * Constructs {@link ClientWorker} and specifies the socket from which will be
         * read.
         * 
         * @param csocket
         *            client socket
         */
        public ClientWorker(Socket csocket) {
            this.csocket = csocket;
        }

        @Override
        public void run() {
            try {
                System.out.println("RUN");
                inputStream = csocket.getInputStream();
                outputStream = csocket.getOutputStream();
                List<String> request = readRequestHeader(inputStream);

                String firstLine = request.get(0);
                System.out.println(firstLine);
                // method requested path and version
                String[] splitted = firstLine.split(" ");
                if (splitted.length < 2 || splitted.length > 3) {
                    sendError(outputStream, 400, "Bad request");
                    return;
                }
                String method = splitted[0];
                String requestedPath = splitted[1];
                String version = splitted[2];
                System.out.println(version + "a");
                if (!(version.toUpperCase().compareTo("HTTP/1.0") == 0 || version.toUpperCase().compareTo("HTTP/1.1") == 0)) {
                    sendError(outputStream, 400, "Bad request, wrong HTTP version");
                    return;
                } else if (method.compareTo("GET") != 0) {
                    sendError(outputStream, 400, "Bad request, wrong method name");
                    return;
                }
                System.out.println("set server domain name go through request");
                for (String line : request) {
                    System.out.println(line);
                    if (line.startsWith("Host: ")) {
                        StringBuilder builder = new StringBuilder();
                        String domain = line.toLowerCase().substring("host: ".length());
                        System.out.println(domain);
                        for (int i = 0; i < domain.length(); ++i) {
                            if (domain.charAt(i) == ':')
                                break;
                            builder.append(domain.charAt(i));
                        }
                        // domainName = builder.toString();
                        address = builder.toString();
                        break;
                    }
                }

                checkSession(request);

                String path = "";
                String parameterString = "";

                int index = requestedPath.indexOf('?');
                if (index == -1) {
                    path = requestedPath;
                } else {
                    path = requestedPath.substring(0, index);
                    parameterString = requestedPath.substring(index + 1);
                }

                // System.out.println(parameterString);
                if (parameterString.length() > 0) {
                    parseParameters(parameterString);
                }
                System.out.println("Parsed parameters");

                try {
                    internalDispatchRequest(path, true);
                } catch (Exception e) {
                    throw new RuntimeException(e.getCause());
                }

                Path requestedFile = documentRoot.resolve(Paths.get(path.substring(1))).toAbsolutePath();
                if (!requestedFile.startsWith(documentRoot)) {
                    sendError(outputStream, 404, "File not fonud.");
                    return;
                }
                if (!Files.isRegularFile(requestedFile) || !Files.isReadable(requestedFile)) {
                    sendError(outputStream, 404, "File not fonud.");
                    return;
                }
                if (done) {
                    done = false;
                    return;
                }
                try (InputStream fis = new FileInputStream(requestedFile.toFile())) {
                    String zaglavlje = "HTTP/1.1 200 OK\r\n" + "Content-length: " + Files.size(requestedFile) + "\r\n"
                            + "Content-type: " + getMime(requestedFile.getFileName().toString()) + "\r\n" + "\r\n";

                    outputStream.write(zaglavlje.getBytes(StandardCharsets.US_ASCII));

                    while (true) {
                        byte[] buf = new byte[1024];
                        int len = fis.read(buf);
                        if (len < 1)
                            break;
                        outputStream.write(buf, 0, len);
                    }
                    outputStream.flush();
                    csocket.close();

                }

            } catch (IOException e) {
                throw new RuntimeException("Error with socket input stream.");
            }

        }

        /**
         * Method check session checks header and see if the cookies received from it is
         * in the session map. If it is, it will create new one, save it in the map and
         * send it to user.
         * 
         * @param request
         *            list of string, request
         */
        private void checkSession(List<String> request) {
            synchronized (SmartHttpServer.this) {
                String sidCandidate = null;
                for (String line : request) {
                    if (line.startsWith("Cookie:")) {
                        String cookieLine = line.substring(7).trim();
                        String[] cookies = cookieLine.split(";");
                        for (String cookie : cookies) {
                            if (!cookie.startsWith("sid")) {
                                continue;
                            }
                            sidCandidate = cookie.substring(5, cookie.length() - 1);
                        }
                    }
                }
                System.out.println("SID candidate: " + sidCandidate + " " + sessions.get(sidCandidate));
                if (sidCandidate != null && sessions.get(sidCandidate) != null) {
                    SessionMapEntry session = sessions.get(sidCandidate);
                    long currentTime = System.currentTimeMillis() / 1000;
                    System.out.println(session.host);
                    if (session.validUntil < currentTime || session.host.compareTo(address) != 0) {
                        System.err.println("Cookie is too old.");
                        System.err.println(session.validUntil + " " + currentTime);
                        sessions.remove(sidCandidate);
                    } else {
                        System.out.println("Unutra");
                        perParams = sessions.get(sidCandidate).map;
                        System.out.println("Gotov");
                        return;
                    }
                }
                sidCandidate = generateRandomSID(SID_LEN);

                SessionMapEntry sessionMapEntry = new SessionMapEntry();
                SID = sidCandidate;
                sessionMapEntry.sid = sidCandidate;
                sessionMapEntry.validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
                sessionMapEntry.map = new ConcurrentHashMap<>();
                sessionMapEntry.host = address;
                System.out.println("Host: " + address);
                outputCookies.add(new RCCookie("sid", SID, (int) sessionMapEntry.validUntil, address, "/"));
                // TODO HTTP ONLY
                sessions.put(sidCandidate, sessionMapEntry);
                System.out.println("Broj sesija: " + sessions.size());
                for (String sid : sessions.keySet()) {
                    System.out.println("  " + sid);
                }
                perParams = sessionMapEntry.map;
            }
        }

        /**
         * Generate random session id.
         * 
         * @param len
         *            length of the session id
         * @return generated session id
         */
        public String generateRandomSID(int len) {
            StringBuilder builder = new StringBuilder(len);
            for (int i = 0; i < len; ++i) {
                char newChar = (char) ((int) (sessionRandom.nextInt(26) + 'A'));
                builder.append(newChar);
            }
            return builder.toString();
        }

        /**
         * Returns the mime which is specified in the server properties. It looks the
         * file extension.
         * 
         * @param fileName
         *            name of the file
         * @return mime type for the file
         */
        private String getMime(String fileName) {
            for (String extension : mimeTypes.keySet()) {
                if (fileName.endsWith(extension)) {
                    return mimeTypes.get(extension);
                }
            }
            return "text/plain";
        }

        /**
         * Method parse parameters and sets them to the map.
         * 
         * @param parameterString
         *            parameters
         */
        private void parseParameters(String parameterString) {
            String[] elems = parameterString.split("&");
            for (String elem : elems) {
                String[] values = elem.split("=");
                String name = values[0];
                if (values.length != 2)
                    continue;
                String value = values[1];
                System.out.println(name + " " + value);
                params.put(name, value);
            }
        }

        /**
         * Method send error specifies status code for response, status text and html
         * code for user to be aware of it.
         * 
         * @param outputStream
         *            socket output stream
         * @param statusCode
         *            status code of error
         * @param statusText
         *            status text
         * @throws IOException
         *             socket error
         */
        private void sendError(OutputStream outputStream, int statusCode, String statusText) throws IOException {

            String htmlCode = "<html><body style=background-color:yellow;>" + "<h1>" + statusCode + " " + statusText
                    + "</h1>" + "<img src=http://www.localhost.com:5721/owl.png>" + "</body></html>";
            outputStream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
                    + "Content-Type: text/html;charset=UTF-8\r\n" + "Content-Length: " + htmlCode.length() + "\r\n"
                    + "Connection: close\r\n" + "\r\n" + htmlCode).getBytes(StandardCharsets.US_ASCII));
            outputStream.flush();

        }

        /**
         * Read one request from the input stream. It reads HTTP header.
         * 
         * @param is
         *            intput stream
         * @return byte array of the request
         * @throws IOException
         *             socket error
         */
        private byte[] readRequest(InputStream is) throws IOException {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int state = 0;
            l: while (true) {
                int b = is.read();
                if (b == -1)
                    return null;
                if (b != 13) {
                    bos.write(b);
                }
                switch (state) {
                case 0:
                    if (b == 13) {
                        state = 1;
                    } else if (b == 10)
                        state = 4;
                    break;
                case 1:
                    if (b == 10) {
                        state = 2;
                    } else
                        state = 0;
                    break;
                case 2:
                    if (b == 13) {
                        state = 3;
                    } else
                        state = 0;
                    break;
                case 3:
                    if (b == 10) {
                        break l;
                    } else
                        state = 0;
                    break;
                case 4:
                    if (b == 10) {
                        break l;
                    } else
                        state = 0;
                    break;
                }
            }
            return bos.toByteArray();
        }

        /**
         * Method reads input stream of the socket and returns the list of the headers.
         * 
         * @param inputStream
         *            socket input stream
         * @return list of string, headers
         */
        private List<String> readRequestHeader(InputStream inputStream) {

            String requestHeader;
            try {
                requestHeader = new String(readRequest(inputStream), Charset.defaultCharset());
            } catch (IOException e) {
                throw new RuntimeException("Error with socket input stream.");
            }
            System.out.println(requestHeader);
            List<String> headers = new ArrayList<String>();
            String currentLine = null;
            for (String s : requestHeader.split("\n")) {
                if (s.isEmpty())
                    break;
                char c = s.charAt(0);
                if (c == 9 || c == 32) {
                    currentLine += s;
                } else {
                    if (currentLine != null) {
                        headers.add(currentLine);
                    }
                    currentLine = s;
                }
            }
            if (!currentLine.isEmpty()) {
                headers.add(currentLine);
            }
            return headers;

        }

        @Override
        public void dispatchRequest(String urlPath) throws Exception {
            internalDispatchRequest(urlPath, false);
        }

        /**
         * done variable, specifies was the http request done and sent
         */
        private boolean done = false;
        /**
         * Specifies how should url path be performed. It may be translated to 
         * the workers, or it may be replaced with specific path. It also 
         * check for the direct call and it denies the access if the direct
         * call is performed on admin privileges file.
         * @param urlPath url path
         * @param directCall direct call
         * @throws Exception exception
         */
        public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {

            if (urlPath.startsWith("/private") && directCall) {
                sendError(outputStream, 404, "not found");
            }
            System.out.println("request ");
            String extension = urlPath.substring(urlPath.lastIndexOf('.') + 1);
            if (context == null) {
                context = new RequestContext(outputStream, params, perParams, outputCookies, tempParams, this);
            }
            System.out.println(urlPath + " " + extension);

            if (urlPath.startsWith("/ext/")) {
                String workerName = urlPath.substring(5);
                Class<?> referenceToClass;
                try {
                    referenceToClass = this.getClass().getClassLoader()
                            .loadClass("hr.fer.zemris.java.webserver.workers." + workerName);

                    @SuppressWarnings("deprecation")
                    IWebWorker newObject = (IWebWorker) referenceToClass.newInstance();
                    newObject.processRequest(context);
                    done = true;
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            System.out.println(urlPath);
            if (workersMap.get(urlPath) != null) {
                done = true;
                System.out.println(workersMap.get(urlPath));
                workersMap.get(urlPath).processRequest(context);
            }
            if (urlPath.endsWith(".smscr")) {
                System.out.println(urlPath);
                done = true;
                Path requestedFile = documentRoot.resolve(Paths.get(urlPath.substring(1))).toAbsolutePath();
                String docBody = "";
                try {
                    docBody = new String(Files.readAllBytes(requestedFile), StandardCharsets.UTF_8);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    System.exit(-1);
                }
                new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(), context).execute();
            }
            if (done) {
                outputStream.close();
            }
        }

    }

    /**
     * Private static class {@link SessionMapEntry} encapsulate session which is
     * used in cookies. It contains session id, host, time of valid and map with
     * persistent parameters.
     * 
     * @author dario
     *
     */
    private static class SessionMapEntry {
        /**
         * session id
         */
        @SuppressWarnings("unused")
        String sid;
        /**
         * string host
         */
        String host;
        /**
         * time valid until
         */
        long validUntil;
        /**
         * map of parameters
         */
        Map<String, String> map;
    }

}
