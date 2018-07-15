package hr.fer.zemris.java.hw16.trazilica.processor.structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Class {@link Trie} implements {@link Structure} and allows user to have fast
 * adding and "getting" from the structure. For more information please check
 * this link. <a href="https://en.wikipedia.org/wiki/Trie">Trie</a>
 * 
 * @author dario
 *
 */
public class Trie implements Structure {

    /**
     * main node of the structure
     */
    private Node mainNode = new Node('$');
    /**
     * number of elements in the structure
     */
    private int size;

    @Override
    public boolean add(StringPair pair) {
        StringPair res = get(pair);
        if (res != null)
            return false;

        addText(pair);
        ++size;
        return true;
    }

    /**
     * Adds the nodes for adding the new pair to the structure.
     * 
     * @param pair
     *            its text will be added to structure
     */
    private void addText(StringPair pair) {
        String text = pair.getText();

        Node current = mainNode;

        for (int i = 0, len = text.length(); i < len; ++i) {
            char curr = text.charAt(i);
            Node newNode = current.getKid(curr);
            if (newNode != null) {
                current = newNode;
            } else {
                for (int j = i; j < len; ++j) {
                    Node node = new Node(text.charAt(j));
                    current.addKid(node);
                    current = node;
                }
                break;
            }
        }
        current.setDone(pair);
    }

    @Override
    public StringPair get(StringPair pair) {

        String text = pair.getText();

        Node current = mainNode;

        for (int i = 0, len = text.length(); i < len; ++i) {
            Node newNode = current.getKid(text.charAt(i));
            if (newNode == null)
                return null;
            current = newNode;
        }
        return current.getDone();
    }

    @Override
    public int size() {
        return this.size;
    }

    /**
     * Class {@link Node} encapsulate one node from the tree which has list of kids
     * and its character as the identification between all of kids of its parent.
     * 
     * @author dario
     *
     */
    private static class Node {
        /**
         * character
         */
        private char character;
        /**
         * list of kids
         */
        private List<Node> kids;
        /**
         * {@link StringPair} saved in this node
         */
        private StringPair done;

        /**
         * Constructs {@link Node} with the given character as its main attribute.
         * 
         * @param character
         *            char which is used as the id between the kids
         */
        public Node(char character) {
            this.character = character;
            this.kids = new ArrayList<>();
        }

        /**
         * Sets the {@link StringPair} in the final node.
         * 
         * @param pair
         *            {@link StringPair}
         */
        public void setDone(StringPair pair) {
            this.done = pair;
        }

        /**
         * Returns the {@link StringPair} which text corresponds with that node.
         * 
         * @return {@link StringPair} at the given node
         */
        public StringPair getDone() {
            return this.done;
        }

        /**
         * Adds new {@link Node} to the kids list.
         * 
         * @param kid
         *            new kid
         */
        public void addKid(Node kid) {
            kids.add(kid);
        }

        /**
         * Returns the kid with the given character, null if it doesn't exist.
         * 
         * @param ch
         *            character
         * @return kid with the given character or null
         */
        public Node getKid(char ch) {
            for (Node kid : kids) {
                if (kid.character == ch) {
                    return kid;
                }
            }
            return null;
        }
    }

    @Override
    public Iterator<StringPair> iterator() {
        return new Iterator<StringPair>() {

            private Node curr = mainNode;
            private int num = 0;
            private Stack<Node> stack = new Stack<>();
            private List<StringPair> list = new ArrayList<>();

            {
                stack.add(curr);
                while (!stack.isEmpty()) {
                    Node pos = stack.pop();

                    if (pos.getDone() != null) {
                        list.add(pos.getDone());
                    }

                    for (Node kid : pos.kids) {
                        stack.add(kid);
                    }
                }
                Collections.reverse(list);
            }

            @Override
            public boolean hasNext() {

                return num < size;
            }

            @Override
            public StringPair next() {

                return list.get(num++);
            }
        };
    }

}
