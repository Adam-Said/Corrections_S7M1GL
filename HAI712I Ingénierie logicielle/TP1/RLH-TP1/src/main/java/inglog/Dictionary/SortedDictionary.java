// Robin L'Huillier - 05/10/2022

package inglog.Dictionary;

import java.lang.Comparable;

public class SortedDictionary extends AbstractDictionary {

    public SortedDictionary() {
        this.keys = new Object[0];
        this.values = new Object[0];
    }

    private int compareKeys(Object key1, Object key2) {
        if (key1.getClass().isInstance(key2)
            && Comparable.class.isAssignableFrom(key1.getClass())
            && Comparable.class.isAssignableFrom(key2.getClass())
        ) {
            @SuppressWarnings("unchecked")
            Comparable<Object> compKey1 = (Comparable<Object>)key1;
            @SuppressWarnings("unchecked")
            Comparable<Object> compKey2 = (Comparable<Object>)key2;
            return compKey1.compareTo(compKey2);
        }
        return -1;
    }

    private int potentialIndex(Object key) {
        if (this.size() == 0) return 0;
        if (this.size() == 1) {
            if (this.compareKeys(key, this.keys[0]) <= 0) return 0;
            return 1;
        }
        int start = 0;
        int end = this.size()-1;
        int mid = (int)((end-start)/2);
        while (end - mid > 0 && mid - start > 0) {
            int comp = this.compareKeys(key, this.keys[mid]);
            if (comp == 0) return mid;
            if (comp < 0) end = mid;
            if (comp > 0) start = mid;
            mid = (end-start)/2 + start;
        }
        if (this.compareKeys(key, this.keys[mid]) > 0) mid++;
        if (this.compareKeys(key, this.keys[mid]) > 0) return mid+1;
        return mid;
    }

    protected int indexOf(Object key) {
        int potential = this.potentialIndex(key);
        if (potential < this.size() && this.compareKeys(key, this.keys[potential]) == 0) {
            return potential;
        }

        return -1;
    }

    protected int newIndexOf(Object key) {
        int newSize = this.size() + 1;
        int newPos = this.potentialIndex(key);
        Object[] newKeys = new Object[newSize];
        Object[] newValues = new Object[newSize];

        int offset = 0;
        for (int i=0; i<this.size(); i++) {
            if (i == newPos) offset = 1;
            newKeys[i+offset] = this.keys[i];
            newValues[i+offset] = this.values[i];
        }
        
        this.keys = newKeys;
        this.values = newValues;
        return newPos;
    }
}