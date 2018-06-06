package hr.fer.zemris.java.custom.scripting.tests;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;


public class Test4 {
    private String FILE = "fibionacci.smscr";
    @Test
    public void test() {
        
        String documentBody = "";
        try {
            documentBody = new String(Files.readAllBytes(Paths.get("src/main/resources/" + FILE)), StandardCharsets.UTF_8);
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(-1);
        }
         
        Map<String,String> parameters = new HashMap<String, String>();
        Map<String,String> persistentParameters = new HashMap<String, String>();
        List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
        // create engine and execute it
        new SmartScriptEngine(
        new SmartScriptParser(documentBody).getDocumentNode(),
        new RequestContext(System.err, parameters, persistentParameters, cookies)
        ).execute();
    }
}
