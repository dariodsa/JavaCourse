package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.exec.functions.SmartScriptFunctions;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class {@link SmartScriptEngine} should have document node and {@link RequestContext} in 
 * the constructor. It uses document node for {@link #execute()} method.
 * It executes the document whose parsed tree in obtains.
 * @author dario
 *
 */
public class SmartScriptEngine {
    
    /**
     * document node
     */
    private DocumentNode documentNode;
    
    /**
     * request context
     */
    private RequestContext requestContext;
    
    /**
     * multistack
     */
    private ObjectMultistack multistack = new ObjectMultistack();
    
    /**
     * visitor of the nodes
     */
    private INodeVisitor visitor = new INodeVisitor() {
        
        @Override
        public void visitTextNode(TextNode node) {
            try {
                requestContext.write(node.getText());
            } catch (IOException e) {
               throw new RuntimeException("Output stream error.");
            }
        }
        
        @Override
        public void visitForLoopNode(ForLoopNode node) {
            
            ElementVariable variable = node.getVariable();
            
            Element start = node.getStartExpression();
            Element step = node.getStepExpression();
            Element end = node.getEndExpression();
            
            String variableName = variable.getName();
            multistack.push(variableName, new ValueWrapper(
                                            Integer.parseInt(start.asText())
                                            )
            );
            
            ValueWrapper endValue = new ValueWrapper(Integer.parseInt(end.asText()));
            
            while(multistack.peek(variableName).numCompare(endValue.getValue()) <= 0) {
                
                int len = node.numberOfChildren();
                
                for(int i=0;i<len; ++i) {
                    node.getChild(i).accept(visitor);
                    //System.out.println("i " + multistack.peek(variableName).getValue() + " " +step.getComponent());
                }
                multistack.peek(variableName).add(step.getComponent());
            }
            multistack.pop(variableName);
        }
        
        @Override
        public void visitEchoNode(EchoNode node) {
            
            Stack<ValueWrapper> stack = new Stack<>();
            for(Element element : node.getElements()) {
                //System.out.println(element.getClass().getCanonicalName());
                if(element instanceof ElementString) {
                    stack.push(new ValueWrapper(element.asText()));
                } else if(element instanceof ElementConstantInteger) {
                    stack.push(new ValueWrapper(((ElementConstantInteger) element).getValue()));
                } else if(element instanceof ElementConstantDouble) {
                    stack.push(new ValueWrapper(((ElementConstantDouble) element).getValue()));
                } else if(element instanceof ElementOperator) {
                    
                    ValueWrapper first = stack.pop();
                    ValueWrapper second = stack.pop();
                    
                    switch (((ElementOperator) element).getSymbol()) {
                    case "+":
                        first.add(second.getValue());
                        stack.push(first);
                        break;
                    case "-":
                        first.subtract(second.getValue());
                        stack.push(first);
                        break;
                    case "*":
                        first.multiply(second.getValue());
                        stack.push(first);
                        break;
                    case "/":
                        first.divide(second.getValue());
                        stack.push(first);
                        break;
                    default:
                        throw new RuntimeException(("Erron, unknown operator:"+ ((ElementOperator) element).getSymbol()+"."));
                        
                    }
                } else if(element instanceof ElementFunction) {
                   SmartScriptFunctions.apply(element.asText(), stack, requestContext);
                } else if(element instanceof ElementVariable) {
                    ValueWrapper value = multistack.peek(((ElementVariable)element).getName());
                    stack.push(value.clone());
                }
            }
            
            Stack<ValueWrapper> stackReverse = new Stack<>();
            while(!stack.isEmpty()) {
                stackReverse.add(stack.pop());
            }
            while(!stackReverse.isEmpty()) {
                
                try {
                    String name = stackReverse.pop().getValue().toString();
                    
                    requestContext.write(name);
                } catch (IOException e) {
                    throw new RuntimeException("Problem with output stream.");
                }
            }
            
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            for(int i = 0;i<node.numberOfChildren(); ++i) {
                node.getChild(i).accept(visitor);
            }
        }
    };
    
    /**
     * Constructs {@link SmartScriptEngine} with document node and request context.
     * @param documentNode document node, root of the tree
     * @param requestContext request context, on which result will be printed
     */
    public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
        Objects.requireNonNull(documentNode);
        Objects.requireNonNull(requestContext);
        
        this.documentNode = documentNode;
        this.requestContext = requestContext;
    }
    
    /**
     * Method execute visit root of the tree, document node.
     */
    public void execute() {
        documentNode.accept(visitor);
    }
}
