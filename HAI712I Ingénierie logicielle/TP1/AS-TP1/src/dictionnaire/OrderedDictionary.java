package dictionnaire;

public class OrderedDictionary extends AbstractDictionary {


	
	public OrderedDictionary() {
		keyDic = new Object[0];
		valueDic = new Object[0];
	}
	
	@Override
	public int indexOf(Object key) {
		int indiceCur = 0;
		for (Object elt : keyDic) {
			indiceCur++;
			if(elt.equals(key)) {
				return indiceCur;
			}
		}
		return -1;
	}

	@Override
	public int newIndexOf(Object key) {
		int newSize = this.size() + 1;
		Object[] tmpKey = new Object[newSize];
		Object[] tmpValue = new Object[newSize];
		
		for(int i = 0; i < newSize -1; i++) {
			tmpKey[i] = this.keyDic[i];
			tmpValue[i] = this.valueDic[i];
		}
		
		this.keyDic = tmpKey;
		this.valueDic = tmpValue;
		
		return newSize-1;
	}

}
