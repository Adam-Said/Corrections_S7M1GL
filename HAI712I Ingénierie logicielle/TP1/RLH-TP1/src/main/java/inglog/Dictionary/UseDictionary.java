// Robin L'Huillier - 05/10/2022

package inglog.Dictionary;

import java.lang.Math;

public class UseDictionary {
    public static void write(String str) {
        System.out.println(str);
    }
    
    public static void line() {
        write("------------------------------");
    }

    public static void main(String[] args) throws Exception {
        IDictionary dic;
        
        // Ordered Dictionary
        line();
        write("Test de Ordered Dictionary");
        line();
        dic = new OrderedDictionary();
        write("IsEmpty: " + dic.isEmpty());
        write("Put 5 values...");
        for (int i=0; i<5; i++) dic.put(i,i);
        write("IsEmpty: " + dic.isEmpty());
        write("Size: " + dic.size());
        write("ContainsKey(3): " + dic.containsKey(3));
        write("ContainsKey(6): " + dic.containsKey(6));
        write("Get(3): " + dic.get(3));
        try {
            write("Get(6):");
            dic.get(6);
        } catch (Exception e) {
            write(e.toString());
        }
        write("ToString: " + dic.toString());

        // Fast Dictionary
        line();
        write("Test de Fast Dictionary");
        line();
        dic = new FastDictionary();
        write("IsEmpty: " + dic.isEmpty());
        write("Put 50000 values...");
        int maximum = 50000;
        for (int i=0; i<maximum; i++) {
            int rand1 = (int)(Math.random()*maximum);
            int rand2 = (int)(Math.random()*maximum);
            dic.put(rand1, rand2);
        }
        write("IsEmpty: " + dic.isEmpty());
        write("Size: " + dic.size());
        write("ContainsKey(3): " + dic.containsKey(3));
        write("ContainsKey(6): " + dic.containsKey(6));
        try {
            write("Get(6):");
            dic.get(6);
        } catch (Exception e) {
            write(e.toString());
        }

        // Sorted Dictionary
        line();
        write("Test de Sorted Dictionary");
        line();
        dic = new SortedDictionary();
        write("IsEmpty: " + dic.isEmpty());
        write("Put 100 values...");
        maximum = 10;
        for (int i=0; i<maximum; i++) {
            int rand1 = (int)(Math.random()*maximum);
            int rand2 = (int)(Math.random()*maximum);
            dic.put(rand1, rand2);
        }
        write("IsEmpty: " + dic.isEmpty());
        write("Size: " + dic.size());
        write("ContainsKey(3): " + dic.containsKey(3));
        write("ContainsKey(6): " + dic.containsKey(6));
        try {
            write("Get(6):");
            dic.get(6);
        } catch (Exception e) {
            write(e.toString());
        }
        write("ToString: " + dic.toString());

        // Comparaison de temps
        long start, end;
        int entries = 50000;
        int step = (int)(entries/1000);
        line();
        write("Comparaison de temps");
        line();
        write("Ajout de 50.000 entrées puis recherche de 1.000 éléments");
        line();
        dic = new OrderedDictionary();
        start = System.currentTimeMillis();
        for (int i=0; i<entries; i++) dic.put(i,i);
        end = System.currentTimeMillis();
        write("Ordered Dictionary");
        write("50.000 entrées : " + (end - start) + "ms");
        for (int i=0; i<entries; i+=step) {
            try {
                dic.get(i);
            } catch (Exception e) {
                write(e.toString());       
            }
        }
        start = System.currentTimeMillis();
        write("1.000 recherche : " + (start - end) + "ms");
        line();
        dic = new FastDictionary();
        start = System.currentTimeMillis();
        for (int i=0; i<entries; i++) dic.put(i,i);
        end = System.currentTimeMillis();
        write("Fast Dictionary");
        write("50.000 entrées : " + (end - start) + "ms");
        for (int i=0; i<entries; i+=step) dic.get(i);
        start = System.currentTimeMillis();
        write("1.000 recherche : " + (start - end) + "ms");
        line();
        dic = new SortedDictionary();
        start = System.currentTimeMillis();
        for (int i=0; i<entries; i++) dic.put(i,i);
        end = System.currentTimeMillis();
        write("Ordered Dictionary");
        write("50.000 entrées : " + (end - start) + "ms");
        for (int i=0; i<entries; i+=step) dic.get(i);
        start = System.currentTimeMillis();
        write("1.000 recherche : " + (start - end) + "ms");
    }
}
