package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demo class which runs basic examples 
 * from the homework.
 * @author dario
 *
 */
public class DemoRequestContext {
    /**
     * Main method which runs demo example.
     * @param args not used
     * @throws IOException file can't be opened or read
     */
    public static void main(String[] args) throws IOException {
        demo1("primjer1.txt", "ISO-8859-2");
        demo1("primjer2.txt", "UTF-8");
        demo2("primjer3.txt", "UTF-8");
    }

    /**
     * Demo method1
     * @param filePath file path
     * @param encoding encoding
     * @throws IOException io exception
     */
    private static void demo1(String filePath, String encoding) throws IOException {
        OutputStream os = Files.newOutputStream(Paths.get(filePath));
        RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
                new ArrayList<RequestContext.RCCookie>());
        rc.setEncoding(encoding);
        rc.setMimeType("text/plain");
        rc.setStatusCode(205);
        rc.setStatusText("Idemo dalje");
        // Only at this point will header be created and written...
        rc.write("Čevapčići i Šiščevapčići.");
        os.close();
    }
    /**
     * Demo method2
     * @param filePath file path
     * @param encoding encoding
     * @throws IOException io exception
     */
    private static void demo2(String filePath, String encoding) throws IOException {
        OutputStream os = Files.newOutputStream(Paths.get(filePath));
        RequestContext rc = new RequestContext(os, new HashMap<String, String>(),
        new HashMap<String, String>(),
        new ArrayList<RequestContext.RCCookie>());
        rc.setEncoding(encoding);
        rc.setMimeType("text/plain");
        rc.setStatusCode(205);
        rc.setStatusText("Idemo dalje");
        rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1",
        "/"));
        rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/"));
        // Only at this point will header be created and written...
        rc.write("Čevapčići i Šiščevapčići.");
        os.close();
    }
}