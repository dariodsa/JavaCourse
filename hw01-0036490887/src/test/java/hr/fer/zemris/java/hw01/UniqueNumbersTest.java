package hr.fer.zemris.java.hw01;

import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;
import org.junit.Assert;
import org.junit.Before;

public class UniqueNumbersTest {

	TreeNode glava;

	@Before
	public void init() {
		
		glava = null;
		glava = UniqueNumbers.addNode(glava, 42);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 21);
		glava = UniqueNumbers.addNode(glava, 76);
		glava = UniqueNumbers.addNode(glava, 35);
	}

	@Test
	public void testFirstTestExample() {
		
		Assert.assertEquals(42, glava.value);
		Assert.assertEquals(76, glava.right.value);
		Assert.assertEquals(21, glava.left.value);
		Assert.assertEquals(35, glava.left.right.value);
	}

	@Test
	public void testTreeSize() {
		Assert.assertEquals(4, UniqueNumbers.treeSize(glava));
	}

	@Test
	public void testContains() {
		
		Assert.assertEquals(true, UniqueNumbers.containsValue(glava, 42));
		Assert.assertEquals(true, UniqueNumbers.containsValue(glava, 76));
		Assert.assertEquals(true, UniqueNumbers.containsValue(glava, 21));
		Assert.assertEquals(true, UniqueNumbers.containsValue(glava, 35));
	}

	@Test
	public void testDoesntContains() {
		
		Assert.assertEquals(false, UniqueNumbers.containsValue(glava, 41));
		Assert.assertEquals(false, UniqueNumbers.containsValue(glava, 79));
		Assert.assertEquals(false, UniqueNumbers.containsValue(glava, 19));
		Assert.assertEquals(false, UniqueNumbers.containsValue(glava, 38));

	}

}
