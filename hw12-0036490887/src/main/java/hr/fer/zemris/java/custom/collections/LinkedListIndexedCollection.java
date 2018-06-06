package hr.fer.zemris.java.custom.collections;

/**
 * {@code LinkedListIndexedCollection} is a collection of linked elements, each
 * one has pointer to the left and right element, so insertion and search might
 * took long time.
 * 
 * <p>
 * Insert O(1)
 * </p>
 * <p>
 * Delete O(1)
 * </p>
 * <p>
 * Get O(N)
 * </p>
 * <p>
 * Find O(N)
 * </p>
 * 
 * @author dario
 *
 */
public class LinkedListIndexedCollection extends Collection {

    /**
     * size of a list
     */
    private int size;
    /**
     * head of a list
     */
    private ListNode first;
    /**
     * tail of a list, last element
     */
    private ListNode last;

    /**
     * Construct linked list with head and tail set to null.
     */
    public LinkedListIndexedCollection() {
        this.size = 0;
        this.first = new ListNode();
        this.last = new ListNode();
    }

    /**
     * Construct linked list and stores elements from {@code Collection} other to
     * the linked list.
     * 
     * @param other
     *            collection which elements will be added to the linked list
     * @throws NullPointerException
     *             collection can't be null
     */
    public LinkedListIndexedCollection(Collection other) {

        if (other == null)
            throw new NullPointerException("Collection can't be null");
        addAll(other);
    }

    /**
     * Returns the number of currently stored objects in this collections.
     * 
     * @return size of collection
     */
    public int size() {
        return size;
    }

    /**
     * Adds new object to the end of the linked list.
     * 
     * @throws NullPointerException
     *             value can't be null
     */
    public void add(Object value) {
        if (value == null)
            throw new NullPointerException("Value can't be null.");

        insert(value, size);

    }

    /**
     * Returns the n-th {@code Object} in the array. Complexity is O(N), but it
     * looks from which side is closer, left or right, so it is less then O(N).
     * 
     * @param index
     *            n-th element in the array
     * @return object at the given position
     * @throws IndexOutOfBoundsException
     *             index was not in the array range
     */
    public Object get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(
                    String.format("Index was not in the range [0, %d].%nIndex was %d.", size - 1, index));
        if (index * 2 <= size) {
            for (ListNode node = first; node != null; node = node.getRight()) {
                if (--index == -1) {
                    return node.getValue();
                }
            }
        } else {
            for (ListNode node = last; node != null; node = node.getLeft()) {
                if (++index == size) {
                    return node.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Removes all elements from the linked list. It erase all elements inside and
     * then head and tail.
     */
    public void clear() {
        this.size = 0;
        first = new ListNode();
        last = new ListNode();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.size];

        ListNode node = first;
        int poc = 0;
        while (node != null) {
            array[poc++] = node.value;
            node = node.right;
        }
        return array;
    }

    /**
     * Inserts the new value at the given position. Position can't be less then
     * zero.
     * 
     * @param value
     *            value of an object
     * @param position
     *            position of an object
     * @throws NullPointerException
     *             Value can't be null
     * @throws IllegalArgumentException
     *             Postion can't be less then zero.
     * @throws IllegalArgumentException
     *             Position can't be greater then collection size
     */
    public void insert(Object value, int position) {

        if (value == null)
            throw new NullPointerException("Value can't be null");
        if (position < 0)
            throw new IllegalArgumentException("Postion can't be less then zero.");
        if (position > size)
            throw new IllegalArgumentException("Position can't be greater then collection size");

        if(first == null) first = new ListNode();
        if(last == null) last = new ListNode();
        
        int position2 = position;

        if (this.size == 0 && position == 0) {
            first.value = value;
            last = first;
            this.size++;
            return;
        } else if (position == 0) {
            ListNode node = new ListNode();
            node.value = value;
            node.right = first;
            first.left = node;
            first = node;

            this.size++;
            return;
        }
        ListNode pos = first;
        while (--position > 0) {
            pos = pos.right;
        }

        ListNode val = new ListNode();
        val.value = value;
        val.left = pos;
        val.right = pos.right;
        pos.right = val;

        if (position2 == this.size) {
            last = val;
        }

        this.size++;
    }

    /**
     * Returns the index position in the collection where the value is located. If
     * the value isn't in the collection, function returns -1.
     * 
     * @param value
     *            object which will be searched in a list
     * @return index of positon where value is located
     * @see #Object.equals()
     * @throws NullPointerException
     *             value can't be null
     */
    public int indexOf(Object value) {
        if (value == null)
            throw new NullPointerException("Value can't be null");

        int index = 0;
        for (ListNode node = first; node != null; node = node.getRight()) {
            if (node.getValue().equals(value)) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    /**
     * Returns true only if the collection contains given value, as determined by
     * {@link #equals(Object) equals} method.
     * 
     * @param value
     *            object which will be search in a collection
     * @return true if collection contains object, false otherwise
     */
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    /**
     * Returns true only if the collection contains given value as determined by
     * {@link #equals(Object) equals} method and removes one occurrence of it (in
     * this class it is not specified which one).
     * 
     * @param value
     *            object which will be removed from collection
     * @return true if object was removed, false otherwise
     */
    public boolean remove(Object value) {
        if (contains(value)) {
            remove(indexOf(value));
            return true;
        }
        return false;
    }

    /**
     * Removes the element at the given index location.
     * 
     * @param index
     *            location of an object that will be removed
     * @throws IndexOutOfBoundsException
     *             index is not in valid range
     */
    public void remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(
                    String.format("Index was not in the range [0, %d].%nIndex was %d.", size - 1, index));
        int in = index;
        for (ListNode node = first; node != null; node = node.getRight()) {
            if (index-- == 0) {
                
                if(in == 0) {
                    first = first.right;
                    continue;
                } else if(in == this.size - 1) {
                    last = last.left;
                    continue;
                }
                
                node.getLeft().setRight(node.getRight());
                node.getRight().setLeft(node.getLeft());
                
            }
        }
        this.size--;
        return;
    }

    /**
     * Static class {@link ListNode} encapsulate node which will be added in linked
     * list. Each node has it's left and right node, and it's value. Getters and
     * setter are also supported to the user.
     * 
     * @author dario
     *
     */
    private static class ListNode {
        /**
         * left node
         */
        private ListNode left;
        /**
         * right node
         */
        private ListNode right;
        /**
         * value of the current object
         */
        private Object value;

        /**
         * Returns the left node.
         * 
         * @return left node
         */
        public ListNode getLeft() {
            return left;
        }

        /**
         * Sets the left node.
         * 
         * @param left
         *            left node
         */
        public void setLeft(ListNode left) {
            this.left = left;
        }

        /**
         * Returns right node of a list
         * 
         * @return right node
         */
        public ListNode getRight() {
            return right;
        }

        /**
         * Sets the right node of a list
         * 
         * @param right
         *            right node
         */
        public void setRight(ListNode right) {
            this.right = right;
        }

        /**
         * Returns value of an object
         * 
         * @return value of an object
         */
        public Object getValue() {
            return value;
        }

    }
}
