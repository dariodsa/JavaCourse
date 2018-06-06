package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface {@link INodeVisitor} specifies method which 
 * should be implemented by the children class. It should 
 * specifies what should have happened when the {@link TextNode}, 
 * {@link ForLoopNode}, {@link EchoNode} or {@link DocumentNode} is 
 * visited. 
 * @author dario
 *
 */
public interface INodeVisitor {
    
    /**
     * Specifies what will happen when the text node 
     * is visited.
     * @param node text node
     */
    public void visitTextNode(TextNode node);
    
    /**
     * Specifies what will happen when the for node 
     * is visited.
     * @param node forloop node
     */
    public void visitForLoopNode(ForLoopNode node);
    
    /**
     * Specifies what will happen when the echo node 
     * is visited.
     * @param node echo node
     */
    public void visitEchoNode(EchoNode node);
    
    /**
     * Specifies what will happen when the document node 
     * is visited.
     * @param node document node
     */
    public void visitDocumentNode(DocumentNode node);
}
