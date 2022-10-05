// Robin L'Huillier - 05/10/2022

package inglog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.function.ThrowingRunnable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inglog.Dictionary.InexistentException;
import inglog.Dictionary.SortedDictionary;

public class SortedDictionaryTest {
    SortedDictionary dic;

    @BeforeEach
    void setUp() {
        dic = new SortedDictionary();
    }

    @Test
    void testAddOneElementToEmptyDic() {
        String key = "premierElem";
        int value = 177;
        dic.put(key, value);
        assertEquals(1, dic.size());
        assertTrue(dic.containsKey(key));
        try {
            assertEquals(value, dic.get(key));
        } catch (Exception e) {
            assertEquals(true, false);
        }
    }

    @Test
    void testEmpty() {
        assertTrue(dic.isEmpty());
        String key = "premierElem";
        int value = 177;
        dic.put(key, value);
        assertFalse(dic.isEmpty());
    }

    @Test
    void testGetInexistentElement() {
        assertThrows(InexistentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                dic.get("randKey");
            }
        });
    }

    @Test
    void testPutOverValueExistent() {
        String key = "premierElem";
        dic.put(key, 4);
        try {
            assertEquals(4, dic.get(key));
        } catch (Exception e) {
            assertEquals(true, false);
        }
        dic.put(key, 5);
        try {
            assertEquals(5, dic.get(key));
        } catch (Exception e) {
            assertEquals(true, false);
        }
    }

    @Test
    void testPutMultipleValues() {
        int size = 500;
        for (int i=0; i<size; i++) {
            dic.put(i, i);
        }
        assertEquals(size, dic.size());
    }

    @Test
    void testToString() {
        String res = "AbstractDictionary[1]\n4 : 4\n";
        dic.put("4","4");
        assertTrue(res.equals(dic.toString()));
    }

    @Test
    void testPutTwoDifferentTypeOfKeys() {
        String key1 = "key1";
        int key2 = 2;
        dic.put(key1, 1);
        dic.put(key2, 2);
    }
}
