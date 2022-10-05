package dictionnaire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SortedDictionaryTest {
	SortedDictionary sorted = null;

	@BeforeAll
	static void init() throws Exception {
		System.out.println("Starting JUnit Test\n");
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Creating sorted Dictionary...\n");
		sorted = new SortedDictionary();
	}

	@Test
	void testAddOneElementToEmptyDico() throws Exception {
		System.out.println("Test adding element to empty dico\n");
		assertEquals(1, sorted.size());
		sorted.put("Clé1", "1");
		assertEquals(2, sorted.size());
	}
	
	@Test
	void testDicoContainsKey() throws Exception {
		System.out.println("Test if dico contains key\n");
		sorted.put("Clé1", "1");
		assertTrue(sorted.containsKey("Clé1"));
	}
	
	@Test
	void testDicoGetKey() throws Exception {
		System.out.println("Test if dico can get element\n");
		sorted.put("Clé1", "1");
		assertEquals("1", sorted.get("Clé1"));
	}
	
	@Test
	void testIndexOf() throws Exception {
		System.out.println("Test if dico can get index of key\n");
		sorted.put("Clé1", "1");
		assertEquals(1, sorted.indexOf("Clé1"));
	}
	
	@Test
	void testNewIndexOf() throws Exception {
		System.out.println("Test if dico can get new index for next key\n");
		sorted.put("Clé1", "1");
		sorted.put("Clé2", "2");
		sorted.put("Clé3", "3");
		sorted.put("Clé4", "4");
		assertEquals(4, sorted.newIndexOf("Clé5"));
	}

}
