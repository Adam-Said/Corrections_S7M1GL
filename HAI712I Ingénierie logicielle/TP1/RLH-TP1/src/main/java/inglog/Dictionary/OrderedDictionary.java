// Robin L'Huillier - 05/10/2022

package inglog.Dictionary;

public class OrderedDictionary extends AbstractDictionary {

    public OrderedDictionary() {
        this.keys = new Object[0];
        this.values = new Object[0];
    }

    protected int indexOf(Object key) {
        for (int i=0; i<this.size(); i++) {
            if (this.keys[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }

    protected int newIndexOf(Object key) {
        int newSize = this.size() + 1;
        Object[] newKeys = new Object[newSize];
        Object[] newValues = new Object[newSize];

        for (int i=0; i<this.size(); i++) {
            newKeys[i] = this.keys[i];
            newValues[i] = this.values[i];
        }
        
        this.keys = newKeys;
        this.values = newValues;
        return newSize-1;
    }
}