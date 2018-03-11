package hr.fer.zemris.java.hw01;

import java.util.Scanner;

public class UniqueNumbers {

	static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}
	public static void main(String[] args) {
		TreeNode head =  null;
		System.out.print("Unesite broj > ");
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.compareTo("kraj")==0) break;
			try {
				int value = Integer.parseInt(line);
				head = getResult(head, value);
				
			} catch(NumberFormatException exception) {
				System.out.printf("'%s' nije cijeli broj.%n",line);
			}
			System.out.print("Unesite broj > ");
		}
		printResults(head);
		scanner.close();
	}
	/**
	 * Prints the {@link #inorderPrint(TreeNode) inorder} of the tree and 
	 * also {@link #reverseInorderPrint(TreeNode) reverseInorder} of the tree 
	 * to the standard output. 
	 * @param head tree root
	 */
	public static void printResults(TreeNode head) {
		System.out.printf("Ispis od najmanjeg: ");
		inorderPrint(head);
		System.out.printf("%nIspis od najvećeg: ");
		reverseInorderPrint(head);
	}
	/**
	 * Prints the error message if the given value is already present in the tree
	 * with the given root. If it isn't already in tree, it will be addded in
	 *  and it will be printed notice on standard output.  
	 * @param head tree root
	 * @param value
	 * @return tree root
	 */
	public static TreeNode getResult(TreeNode head, int value)
	{
		if(containsValue(head, value)) {
			System.out.println("Broj već postoji. Preskačem.");
		} else {
			head = addNode(head, value);
			System.out.println("Dodano.");
		}
		return head;
	}
	/**
	 * Prints the inorder of the tree with the given root. 
	 * It prints the order on the standard output. 
	 * @param parent
	 */
	public static void inorderPrint(TreeNode parent) {
		if(parent.left != null) {
			inorderPrint(parent.left);
		}
		System.out.print(parent.value + " ");
		if(parent.right != null) {
			inorderPrint(parent.right);
		}
	}
	/**
	 * Prints the reverse inorder of the tree with given root.
	 * It prints the order on the standard output.
	 * @param parent
	 * @see #inorderPrint(TreeNode) inorderPrint
	 */
	public static void reverseInorderPrint(TreeNode parent) {
		if(parent.right != null) {
			reverseInorderPrint(parent.right);
		}
		System.out.print(parent.value + " ");
		if(parent.left != null) {
			reverseInorderPrint(parent.left);
		}
	}
	/**
	 * Adds the new node to the tree if it isn't already inside. 
	 * @param parent tree root
	 * @param value value that we are adding
	 * @return tree root
	 */
	public static TreeNode addNode(TreeNode parent, int value) {
		
		if(parent == null) {
			parent = addLeaf(value);
		}
		
		else if(parent.value > value) {
			if(parent.left == null) {
				parent.left = addLeaf(value);
			}
			else {
				addNode(parent.left, value);
			}
		}
		else if(parent.value < value){
			if(parent.right == null) {
				parent.right = addLeaf(value);
			}
			else {
				addNode(parent.right, value);
			}
		}
		return parent;
	}
	/**
	 * Setting up the leaf. It sets left and right child to null 
	 * and sets up the value of the node.
	 * @param value value of the node
	 * @return created leaf
	 */
	public static TreeNode addLeaf(int value) {
		TreeNode leaf = new TreeNode();
		leaf.right = leaf.left = null;
		leaf.value = value;
		return leaf;
	}
	/**
	 * Returns true if the given value is present in the tree, 
	 * otherwise it returns false.
	 * @param parent tree root
	 * @param value the value that we are trying to find
	 * @return true if it is present, otherwise false
	 */
	public static boolean containsValue(TreeNode parent, int value) {
		if(parent == null) {
			return false;
		}
		if(parent.value < value) {
			return containsValue(parent.right, value);
		}
		else if(parent.value > value) {
			return containsValue(parent.left, value);
		}
		else {
			return true;
		}
	}
	/**
	 * Returns the number of the nodes in the tree. 
	 * @param parent pointer to the tree root
	 * @return number of nodes in the tree
	 */
	public static int treeSize(TreeNode parent) {
		int answer = 0;
		
		if(parent.left  != null) answer = treeSize(parent.left);
		if(parent.right != null) answer += treeSize(parent.right);
		return answer + 1;
	}
	
}
