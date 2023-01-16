package dictionnaire;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FastDictionaryTest {
	FastDictionary fast = null;

	@BeforeAll
	static void init() throws Exception {
		System.out.println("Starting JUnit Test\n");
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Creating fast Dictionary...\n");
		fast = new FastDictionary();
	}

	@Test
	void testMustGrow() {
		fast.put("Clé1", "1");
		fast.put("Clé2", "2");
		fast.put("Clé3", "3");
		assertTrue(fast.mustGrow());
	}

	/*@Test
	void testGrow() {
		fail("Not yet implemented");
	}*/
	
	@Test
	void testSize() throws Exception {
		System.out.println("Test adding element to empty dico\n");
		assertEquals(0, fast.size());
		fast.put("Clé1", "1");
		assertEquals(1, fast.size());
	}
	
	@Test
	void testDicoContainsKey() throws Exception {
		System.out.println("Test if dico contains key\n");
		fast.put("Clé1", "1");
		assertTrue(fast.containsKey("Clé1"));
	}
	
	@Test
	void testDicoGetKey() throws Exception {
		System.out.println("Test if dico can get element\n");
		fast.put("Clé1", "1");
		assertEquals("1", fast.get("Clé1"));
	}
	
	@Test
	void testIndexOf() throws Exception {
		System.out.println("Test if dico can get index of key\n");
		fast.put("Clé1", "1");
		assertEquals(2, fast.indexOf("Clé1"));
	}
	
	@Test
	void testNewIndexOf() throws Exception {
		System.out.println("Test if dico can get new index for next key\n");
		fast.put("Clé1", "1");
		fast.put("Clé2", "2");
		fast.put("Clé3", "3");
		fast.put("Clé4", "4");
		assertEquals(5, fast.newIndexOf("Clé5"));
	}
	


}
