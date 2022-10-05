// Robin L'Huillier - 05/10/2022

package inglog.Dictionary;

public class FastDictionary extends AbstractDictionary {
    protected int actualSize;

    public FastDictionary() {
        actualSize = 0;
        this.keys = new Object[4];
        this.values = new Object[4];
    }

    public int size() {
        return this.actualSize;
    }

    protected boolean mustGrow() {
        return this.actualSize >= 0.75*(double)this.values.length;
    }

    protected FastDictionary grow() {
        int newSize = this.values.length *2;
        Object[] newKeys = new Object[newSize];
        Object[] newValues = new Object[newSize];

        for (int i=0; i<this.values.length; i++) {
            if (this.keys[i] == null) continue;
            Object key = this.keys[i];
            Object value = this.values[i];
            int newIndex = key.hashCode() % newSize;
            while (newKeys[newIndex] != null) {
                newIndex++;
                if (newIndex >= newSize) {
                    newIndex = 0;
                }
            }
            newKeys[newIndex] = key;
            newValues[newIndex] = value;
        }

        this.keys = newKeys;
        this.values = newValues;

        return this;
    }

    public FastDictionary put(Object key, Object value) {
        super.put(key, value);
        this.actualSize++;
        return this;
    }

    private int potentialIndex(Object key) {
        int index = key.hashCode() % this.values.length;
        while (this.keys[index] != null && !this.keys[index].equals(key)) {
            index++;
            if (index >= this.values.length) index = 0;
        }
        return index;
    }

    protected int indexOf(Object key) {
        int index = this.potentialIndex(key);
        if (this.keys[index] != null && this.keys[index].equals(key)) return index;
        return -1;
    }

    protected int newIndexOf(Object key) {
        if (this.mustGrow()) this.grow();
        return this.potentialIndex(key);
    }

    public String toString() {
        String str = "FastDictionary[" + this.size() + "]\n";
        for (int i=0; i<this.values.length; i++) {
            if (this.keys[i] == null) continue;
            str += this.keys[i] + " : " + this.values[i] + "\n"; 
        }
        return str;
    }
}
