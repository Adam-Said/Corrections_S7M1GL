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
import inglog.Dictionary.OrderedDictionary;

public class OrderedDictionaryTest {
    OrderedDictionary dic;

    @BeforeEach
    void setUp() {
        dic = new OrderedDictionary();
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
        int size = 5;
        for (int i=0; i<size; i++) {
            dic.put(i,i);
        }
        assertEquals(size, dic.size());
    }
}
