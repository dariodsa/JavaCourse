package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Class {@link TreeWriter} is a demo class which shows an example
 * on the task from the homework.
 * @author dario
 *
 */
public class TreeWriter {
    /**
     * Main method
     * @param args file path
     */
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("I expected only one argument.");
            return;
        }
        
        Path filePath = Paths.get(args[0]);

        String docBody = "";
        try {
            docBody = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(-1);
        }
        
        SmartScriptParser p = new SmartScriptParser(docBody);
        WriterVisitor visitor = new WriterVisitor();
        p.getDocumentNode().accept(visitor);
        
    }
    /**
     * Class {@link WriterVisitor} implements INodeVisitor
     * specifies how should every node be visited.
     * @author dario
     *
     */
    public static class WriterVisitor implements INodeVisitor {

        @Override
        public void visitTextNode(TextNode node) {
            
            System.out.println(node.getText());
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            
            for(int i=0; i < node.numberOfChildren(); ++i) {
               node.getChild(i).accept(this);
            }
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            for(Element element : node.getElements()) {
                System.out.println(element.asText());
            }
            
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            
            for(int i=0; i < node.numberOfChildren(); ++i) {
                node.getChild(i).accept(this);
             }
        }
       
    }
}
