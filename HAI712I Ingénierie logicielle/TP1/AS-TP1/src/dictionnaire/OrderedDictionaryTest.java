package dictionnaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderedDictionaryTest {
	OrderedDictionary ordered = null;

	@BeforeAll
	static void init() throws Exception {
		System.out.println("Starting JUnit Test\n");
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Creating ordered Dictionary...\n");
		ordered = new OrderedDictionary();
	}

	@Test
	void testAddOneElementToEmptyDico() throws Exception {
		System.out.println("Test adding element to empty dico\n");
		assertEquals(0, ordered.size());
		ordered.put("Clé1", "1");
		assertEquals(1, ordered.size());
	}
	
	@Test
	void testDicoContainsKey() throws Exception {
		System.out.println("Test if dico contains key\n");
		ordered.put("Clé1", "1");
		assertTrue(ordered.containsKey("Clé1"));
	}
	
	@Test
	void testDicoGetKey() throws Exception {
		System.out.println("Test if dico can get element\n");
		ordered.put("Clé1", "1");
		assertEquals("1", ordered.get("Clé1"));
	}
	
	@Test
	void testIndexOf() throws Exception {
		System.out.println("Test if dico can get index of key\n");
		ordered.put("Clé1", "1");
		assertEquals(1, ordered.indexOf("Clé1"));
	}
	
	@Test
	void testNewIndexOf() throws Exception {
		System.out.println("Test if dico can get new index for next key\n");
		ordered.put("Clé1", "1");
		ordered.put("Clé2", "2");
		ordered.put("Clé3", "3");
		ordered.put("Clé4", "4");
		assertEquals(4, ordered.newIndexOf("Clé5"));
	}

}
