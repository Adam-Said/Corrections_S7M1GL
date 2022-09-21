package dictionnaire;


public abstract class AbstractDictionary implements IDictionary {

	protected Object[] keyDic;
	protected Object[] valueDic;
	
	@Override
	public Object get(Object key) throws InexistentException {
		int indice = 0;
		if(this.containsKey(key)) {
			indice = this.indexOf(key);			
		}
		else {
			throw new InexistentException("Clé non existante");
		}
		
		return valueDic[indice-1];
	}

	@Override
	public IDictionary put(Object key, Object value) {
		if(!this.containsKey(key)) {
			int newIndice = this.newIndexOf(key);
			keyDic[newIndice] = key;
			valueDic[newIndice] = value;
		}
		
		return this;
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return !(this.indexOf(key) == -1);
	}

	@Override
	public int size() {
		return this.valueDic.length;
	}

	public abstract int indexOf(Object key);
	
	public abstract int newIndexOf(Object key);
	
}
