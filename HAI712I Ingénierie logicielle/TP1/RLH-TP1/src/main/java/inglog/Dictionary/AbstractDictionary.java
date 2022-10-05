// Robin L'Huillier - 05/10/2022

package inglog.Dictionary;

public abstract class AbstractDictionary implements IDictionary {
    protected Object[] keys;
    protected Object[] values;

    public AbstractDictionary() {}

    abstract int indexOf(Object key);

    abstract int newIndexOf(Object key);

    public int size() {
        return this.values.length;
    }

    public boolean containsKey(Object key) {
        return (this.indexOf(key) != -1);
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public Object get(Object key) throws InexistentException {
        if (!this.containsKey(key)) {
            throw new InexistentException("No such key in dictionary: " + key.toString());
        }
        return this.values[this.indexOf(key)];
    }

    public AbstractDictionary put(Object key, Object value) {
        if (this.containsKey(key)) {
            this.values[this.indexOf(key)] = value;
        } else {
            int index = this.newIndexOf(key);
            this.keys[index] = key;
            this.values[index] = value;
        }
        return this;
    }

    public String toString() {
        String str = "AbstractDictionary[" + this.size() + "]\n";
        for (int i=0; i<this.size(); i++) {
            str += this.keys[i] + " : " + this.values[i] + "\n"; 
        }
        return str;
    }
}
