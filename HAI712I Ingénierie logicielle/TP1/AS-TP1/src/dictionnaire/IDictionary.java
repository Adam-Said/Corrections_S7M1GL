package dictionnaire;

public interface IDictionary {
	
	public Object get(Object key) throws InexistentException;
	
	public IDictionary put(Object key, Object value);
	
	public boolean isEmpty();
	
	public boolean containsKey(Object key);
	
	public int size();
	
}
