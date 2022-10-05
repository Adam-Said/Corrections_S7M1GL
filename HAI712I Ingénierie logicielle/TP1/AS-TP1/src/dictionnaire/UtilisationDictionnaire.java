package dictionnaire;


public class UtilisationDictionnaire {

	public static void main(String[] args) throws InexistentException {
		
		System.out.println("\n ----- ORDERED DICTIONARY -----\n");
		
		IDictionary dic = new OrderedDictionary();
		
		// Get size of array
		System.out.println("La taille du dictionnaire est de "+ dic.size() + " mots\n");
		// Add word to dictionary
		dic.put("Lavoisier", "Chimiste français");
		
		// Get new size
		System.out.println("La nouvelle taille du dictionnaire est de "+ dic.size() + " mots\n");
		
		// Get value
		Object cle = "Lavoisier";
		System.out.println(cle + ": " + dic.get(cle));
		
		
		dic.put("Godel", "Logicien et mathématicien autrichien");
		cle = "Godel";
		System.out.println(cle + ": " + dic.get(cle));
		
		dic.put(1, "Le chiffre un");
		cle = 1;
		System.out.println(cle + ": " + dic.get(cle));
		
		for(int i = 0; i < 100; i++) {
			dic.put(i, i);
		}
		
		
		cle = "Lavoisier";
		Object res = null;
		long startTime = System.nanoTime();
		res = dic.get(cle);
		long endTime = System.nanoTime();
		System.out.println(cle + ": " + res + " | Récupéré en " + (endTime - startTime) +"nano secondes");
		
		cle = 90;
		startTime = System.nanoTime();
		res = dic.get(cle);
		endTime = System.nanoTime();
		System.out.println(cle + ": " + res + " | Récupéré en " + (endTime - startTime) +"nano secondes");

		System.out.println("\n ----- FAST DICTIONARY -----\n");
		
		IDictionary dicFast = new FastDictionary();
		
		// Get size of array
		System.out.println("La taille du dictionnaire est de "+ dicFast.size() + " mots\n");
		// Add word to dictionary
		dicFast.put("Lavoisier", "Chimiste français");
		
		// Get new size
		System.out.println("La nouvelle taille du dictionnaire est de "+ dicFast.size() + " mots\n");
		
		// Get value
		cle = "Lavoisier";
		System.out.println(cle + ": " + dicFast.get(cle));
		
		
		dicFast.put("Godel", "Logicien et mathématicien autrichien");
		cle = "Godel";
		System.out.println(cle + ": " + dicFast.get(cle));
		
		dicFast.put(1, "Le chiffre un");
		cle = 1;
		System.out.println(cle + ": " + dicFast.get(cle));
		
		for(int i = 0; i < 100; i++) {
			dicFast.put(i, i);
		}
		
		System.out.println("La nouvelle taille du dictionnaire est de "+ dicFast.size() + " mots\n");
		
		cle = "Lavoisier";
		res = null;
		startTime = System.nanoTime();
		res = dicFast.get(cle);
		endTime = System.nanoTime();
		System.out.println(cle + ": " + res + " | Récupéré en " + (endTime - startTime) +"nano secondes");
		
		cle = 90;
		startTime = System.nanoTime();
		res = dicFast.get(cle);
		endTime = System.nanoTime();
		System.out.println(cle + ": " + res + " | Récupéré en " + (endTime - startTime) +"nano secondes");
		
		
		System.out.println("\n ----- SORTED DICTIONARY -----\n");
		
		
		IDictionary dicSorted = new SortedDictionary();
		
		// Get size of array
		System.out.println("La taille du dictionnaire est de "+ dicSorted.size() + " mots\n");
		// Add word to dictionary
		dicSorted.put("Lavoisier", "Chimiste français");
		
		// Get new size
		System.out.println("La nouvelle taille du dictionnaire est de "+ dicSorted.size() + " mots\n");
		
		// Get value
		cle = "Lavoisier";
		System.out.println(cle + ": " + dicSorted.get(cle));
		
		dicSorted.put("Godel", "Logicien et mathématicien autrichien");
		cle = "Godel";
		System.out.println(cle + ": " + dicSorted.get(cle));

		dicSorted.put("ZZZ", "ZZZ");
		cle = "ZZZ";
		System.out.println(cle + ": " + dicSorted.get(cle));
		
		dicSorted.put("ZA", "ZA");
		cle = "ZA";
		System.out.println(cle + ": " + dicSorted.get(cle));
		
		dicSorted.put("AAABBBBBCCCCCCCDDD", "AAAAAAAAAAAAAAA");
		cle = "AAABBBBBCCCCCCCDDD";
		System.out.println(cle + ": " + dicSorted.get(cle));

		
		System.out.println("La nouvelle taille du dictionnaire est de "+ dicSorted.size() + " mots\n");

	}
}
