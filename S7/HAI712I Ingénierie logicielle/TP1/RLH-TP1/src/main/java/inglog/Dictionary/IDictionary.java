// Robin L'Huillier - 05/10/2022

package inglog.Dictionary;

public interface IDictionary {
    Object get(Object key) throws Exception;
    IDictionary put(Object key, Object value);
    boolean isEmpty();
    boolean containsKey(Object key);
    int size();
}