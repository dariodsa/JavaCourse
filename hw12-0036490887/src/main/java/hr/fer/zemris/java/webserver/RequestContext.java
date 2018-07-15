package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class {@link RequestContext} consist of the request which specifies what
 * user's request consist. It allows user to set, view or delete parameters,
 * persistent parameters and temporary parameters. It consist of encoding rules
 * which specifies how should text should be encoded.
 * 
 * @author dario
 *
 */
public class RequestContext {

    /**
     * mime for html
     */
    private static final String HTML = "text/html";

    /**
     * mime for text plain
     */
    private static final String PLAIN = "text/plain";

    /**
     * client's output stream
     */
    private OutputStream outputStream;
    /**
     * charsets encoding
     */
    private Charset charset;

    /**
     * encoding of the characters
     */
    private String encoding = "UTF-8";
    /**
     * status code
     */
    private int statusCode = 200;
    /**
     * status text
     */
    private String statusText = "OK";
    /**
     * mime type
     */
    private String mimeType = "text/html";
    /**
     * map, string to string, parameters
     */
    private Map<String, String> parameters;
    /**
     * map string to string, temporary parameters
     */
    private Map<String, String> temporaryParameters = new HashMap<>();
    /**
     * map string to string, presisstent parameters
     */
    private Map<String, String> persistentParameters;
    /**
     * list of output cookies
     */
    private List<RCCookie> outputCookies;
    /**
     * boolean, if the header is created
     */
    private boolean headerGenerated;
    /**
     * dispatcher
     */
    private IDispatcher dispatcher;

    /**
     * Constructs {@link RequestContext} with the given parameters.
     * 
     * @param outputStream
     *            client output stream
     * @param parameters
     *            parameters
     * @param persistentParameters
     *            persistent parameters
     * @param outputCookies
     *            list of cookies
     */
    public RequestContext(OutputStream outputStream, Map<String, String> parameters,
            Map<String, String> persistentParameters, List<RCCookie> outputCookies) {

        Objects.requireNonNull(outputStream);
        this.outputStream = outputStream;

        if (parameters == null) {
            this.parameters = new HashMap<>();
        } else {
            this.parameters = parameters;
        }

        if (persistentParameters == null) {
            this.persistentParameters = new HashMap<>();
        } else {
            this.persistentParameters = persistentParameters;
        }

        if (outputCookies == null) {
            this.outputCookies = new ArrayList<>();
        } else {
            this.outputCookies = outputCookies;
        }
    }

    /**
     * 
     * @param outputStream
     *            client's output stream
     * @param parameters
     *            parameters
     * @param persistentParameters
     *            persistent parameters
     * @param outputCookies
     *            output cookies
     * @param temporaryParameters
     *            temporary parameters
     * @param dispatcher
     *            dispatcher
     */
    public RequestContext(OutputStream outputStream, Map<String, String> parameters,
            Map<String, String> persistentParameters, List<RCCookie> outputCookies,
            Map<String, String> temporaryParameters, IDispatcher dispatcher) {

        this(outputStream, parameters, persistentParameters, outputCookies);
        this.temporaryParameters = temporaryParameters;
        this.dispatcher = dispatcher;
    }

    /**
     * Returns the current {@link IDispatcher} disatcher.
     * 
     * @return object's dispatcher
     */
    public IDispatcher getDispatcher() {
        return this.dispatcher;
    }

    /**
     * Returns the parameter value under given name.
     * 
     * @param name
     *            parameter name
     * @return parameter value
     */
    public String getParameter(String name) {
        return parameters.get(name);
    }

    /**
     * Returns the set of parameters name.
     * 
     * @return set of parameters name
     */
    public Set<String> getParameterNames() {
        return parameters.keySet();
    }

    /**
     * Returns the persistent parameter value under given name.
     * 
     * @param name
     *            persistent parameter name
     * @return value under given name
     */
    public String getPersistentParameter(String name) {
        return this.persistentParameters.get(name);
    }

    /**
     * Sets the persistent parameter under given name and value.
     * 
     * @param name
     *            parameter name
     * @param value
     *            parameter value
     */
    public void setPersistentParameter(String name, String value) {
        Objects.requireNonNull(name);
        this.persistentParameters.put(name, value);
    }

    /**
     * Removes persistent parameter under given name.
     * 
     * @param name
     *            parameter name
     */
    public void removePersisitentParameter(String name) {
        this.persistentParameters.remove(name);
    }

    /**
     * Returns the value of temporary parameter under given name.
     * 
     * @param name
     *            parameter name
     * @return value under given name
     */
    public String getTemporaryParameter(String name) {
        return this.temporaryParameters.get(name);
    }

    /**
     * Returns set of temporary parameter's name.
     * 
     * @return names of temporary parameters
     */
    public Set<String> getTemporaryParameterNames() {
        return this.temporaryParameters.keySet();
    }

    /**
     * Sets the temporary parameter under given name and value.
     * 
     * @param name
     *            parameter's name
     * @param value
     *            parameter's value
     */
    public void setTemporaryParameter(String name, String value) {
        Objects.requireNonNull(name);
        this.temporaryParameters.put(name, value);
    }

    /**
     * Removes temporary parameter under given name.
     * 
     * @param name
     *            parameter's name
     */
    public void removeTemporaryParameter(String name) {
        this.temporaryParameters.remove(name);
    }

    /**
     * Adds given cookie to the list of cookies.
     * 
     * @param cookie
     *            cookie which will be added to list
     */
    public void addRCCookie(RCCookie cookie) {
        if (headerGenerated) {
            throw new RuntimeException("You can' change this property.");
        }
        outputCookies.add(cookie);
    }

    /**
     * Writes given byte of data to the output stream.
     * 
     * @param data
     *            byte data
     * @return {@link RequestContext}
     * @throws IOException
     *             socket error
     */
    public RequestContext write(byte[] data) throws IOException {

        if (!headerGenerated) {
            String adding = "";
            if (mimeType.compareTo(HTML) == 0 || mimeType.compareTo(PLAIN) == 0) {
                adding = "; charset=" + encoding;
            }
            String header = "HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Content-Type: " + mimeType + adding
                    + "\r\n";
            for (RCCookie cookie : outputCookies) {
                header += cookie.toString() + "\r\n";
            }

            header += "\r\n";
            headerGenerated = true;

            outputStream.write(header.getBytes(StandardCharsets.ISO_8859_1));
        }

        outputStream.write(data);
        return this;
    }

    /**
     * It writes given text to the client's output stream.
     * 
     * @param text
     *            text which will be written on it
     * @return {@link RequestContext} on which will data be recorded
     * @throws IOException
     *             output stream
     */
    public RequestContext write(String text) throws IOException {
        charset = Charset.forName(encoding);

        byte[] data = text.getBytes(charset);
        return write(data);
    }

    /**
     * Sets the output stream of the {@link RequestContext}.
     * 
     * @param outputStream
     *            output stream
     */
    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * Sets the charset.
     * 
     * @param charset
     *            {@link Charset} charset
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * Sets the encoding.
     * 
     * @param encoding
     *            String encoding
     */
    public void setEncoding(String encoding) {
        if (headerGenerated) {
            throw new RuntimeException("You can' change this property.");
        }
        this.encoding = encoding;
    }

    /**
     * Sets the status code.
     * 
     * @param statusCode
     *            status code specified in RFC
     */
    public void setStatusCode(int statusCode) {
        if (headerGenerated) {
            throw new RuntimeException("You can' change this property.");
        }
        this.statusCode = statusCode;
    }

    /**
     * Sets the status text of the http header.
     * 
     * @param statusText
     *            status text specified in RFC.
     */
    public void setStatusText(String statusText) {
        if (headerGenerated) {
            throw new RuntimeException("You can' change this property.");
        }
        this.statusText = statusText;
    }

    /**
     * Sets the mime type of the header.
     * 
     * @param mimeType
     *            mime type, specified in RFC.
     */
    public void setMimeType(String mimeType) {
        if (headerGenerated) {
            throw new RuntimeException("You can' change this property.");
        }
        this.mimeType = mimeType;
    }

    /**
     * Class {@link RCCookie} encapsulate cookies which are specifies in the http's
     * rfc. It consists of it's name, value domain, path and max age.
     * 
     * @author dario
     *
     */
    public static class RCCookie {
        /**
         * name property
         */
        private String name;
        /**
         * value property
         */
        private String value;
        /**
         * domain property
         */
        private String domain;
        /**
         * path property
         */
        private String path;
        /**
         * max age property
         */
        private Integer maxAge;

        /**
         * Constructs {@link RCCookie} with the given parameters.
         * 
         * @param name
         *            name of the cookie
         * @param value
         *            cookie's value
         * @param maxAge
         *            max age of the cookie
         * @param domain
         *            domain of the cookie
         * @param path
         *            path on which cookie is valid
         */
        public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
            this.name = name;
            this.value = value;
            this.domain = domain;
            this.maxAge = maxAge;
            this.path = path;
        }

        /**
         * Returns obejcts's name value.
         * 
         * @return name property
         */
        public String getName() {
            return name;
        }

        /**
         * Returns obejcts's value property.
         * 
         * @return value property
         */
        public String getValue() {
            return value;
        }

        /**
         * Returns obejcts's domain value.
         * 
         * @return domain property
         */
        public String getDomain() {
            return domain;
        }

        /**
         * Returns obejcts's path.
         * 
         * @return path property
         */
        public String getPath() {
            return path;
        }

        /**
         * Returns obejcts's max age value.
         * 
         * @return max age property
         */
        public int getMaxAge() {
            return maxAge;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            builder.append("Set-Cookie: ");
            builder.append(String.format("%s=\"%s\"; ", name, value));

            if (domain != null) {
                builder.append(String.format("Domain=%s; ", domain));
            }

            if (path != null) {
                builder.append(String.format("Path=%s; ", path));
            }

            if (maxAge != null) {
                builder.append(String.format("Max-Age=%d; ", maxAge));
            }

            builder.append("HttpOnly");
            return builder.toString();
        }

    }
}
