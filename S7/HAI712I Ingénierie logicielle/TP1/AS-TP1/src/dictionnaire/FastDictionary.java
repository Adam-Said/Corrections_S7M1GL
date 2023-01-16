package dictionnaire;

public class FastDictionary extends AbstractDictionary {

	public FastDictionary() {
		keyDic = new Object[4];
		valueDic = new Object[4];
	}
	
	@Override
	public int indexOf(Object key) {
		int indiceCur = (key.hashCode() % keyDic.length);
		if(indiceCur < 0) indiceCur += keyDic.length;
		for (int i = indiceCur; i < keyDic.length ; i++) {
			if(keyDic[i] == null) {
				return -1;
			}
			if(keyDic[i].equals(key)) {
				return i+1;
			}
		}
		return -1;
	}

	@Override
	public int newIndexOf(Object key) {
		if (mustGrow()) grow();
		int hash = (key.hashCode() % keyDic.length);
		if(hash < 0) hash += keyDic.length;
		for (int i = hash; i < keyDic.length ; i++) {
			if(keyDic[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public int size() {
		int res = 0;
		for(Object elt : keyDic) {
			if(elt != null) res++;
		}
		return res;
	}
	
	public boolean mustGrow() {
		return ((float)size() / (float)keyDic.length >= 0.75);
	}
	
	public IDictionary grow() {
		int newSize = keyDic.length * 2;
		Object[] tmpKey = new Object[newSize];
		Object[] tmpValue = new Object[newSize];
		
		for(int i = 0; i < keyDic.length ; i++) {
			if(this.keyDic[i] == null) continue;
			Object key = this.keyDic[i];
			int newIndex = key.hashCode() % newSize;
			if(newIndex < 0) newIndex += newSize;
			while(tmpKey[newIndex] != null) {
				newIndex++;
				if(newIndex >= newSize) newIndex = 0;
			}
			
			tmpKey[newIndex] = this.keyDic[i];
			tmpValue[newIndex] = this.valueDic[i];
		}

		this.keyDic = tmpKey;
		this.valueDic = tmpValue;
		
		return this;
	}

}
