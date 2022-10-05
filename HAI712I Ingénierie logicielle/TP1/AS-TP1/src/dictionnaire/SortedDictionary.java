package dictionnaire;

public class SortedDictionary extends AbstractDictionary {

	public SortedDictionary() {
		keyDic = new Object[1];
		valueDic = new Object[1];
	}
	
	
	// indexOf Séquentiel
//	@Override
//	public int indexOf(Object key) {
//		int indiceCur = 0;
//		for (Object elt : keyDic) {
//			indiceCur++;
//			if(elt == null) continue;
//			if(elt.equals(key)) {
//				return indiceCur;
//			}
//		}
//		return -1;
//	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int indexOf(Object key) {
		int debut = 0;
		int fin = keyDic.length -1;
		int mid = 0;
		
		Comparable<Object> compKey = (Comparable<Object>)key;
		
		while(fin > debut) {
			mid = (fin + debut) / 2;
			Comparable<Object> compElt = (Comparable<Object>)keyDic[mid];
			if(compKey.compareTo(compElt) == 0) {
				return mid +1;
			}
			else if (compKey.compareTo(compElt) < 0) {
				fin = mid ;
				
			}
			else {
				debut = mid +1;
				
			}
			
		}
		return -1;
		
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public int newIndexOf(Object key) {
		int newSize = keyDic.length + 1;
		int indiceCur = 0;
		int indice = -1;
		Comparable<Object> compKey = (Comparable<Object>)key;
		for(Object elt : keyDic) {
			Comparable<Object> compElt = (Comparable<Object>)elt;
			if(elt == null) {
				indice = indiceCur;
				break;
			}
			int compare = compKey.compareTo(compElt);
			if(compare < 0) {
				indice = indiceCur;
				break;
			}
			indiceCur++;
			
		}
		indice = indiceCur;
		
		
		Object[] tmpKey = new Object[newSize];
		Object[] tmpValue = new Object[newSize];
		
		for(int i = 0; i < indice; i++) {
			tmpKey[i] = this.keyDic[i];
			tmpValue[i] = this.valueDic[i];
		}
		
		for(int i = indice + 1 ; i < newSize -1; i++) {
			tmpKey[i] = this.keyDic[i - 1];
			tmpValue[i] = this.valueDic[i - 1];
		}
		
		this.keyDic = tmpKey;
		this.valueDic = tmpValue;
		
		return indice;
	}

}
