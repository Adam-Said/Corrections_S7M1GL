package stockage;

import java.util.ArrayList;

public class Directory extends StorageElement {
	
	private ArrayList<StorageElement> elements;
	
	public void ls() {
		for(StorageElement se : elements) {
			System.out.println(se.getNom() +" : " + se.getSize());
		}
	}
	
	public ArrayList<StorageElement> find(String nom) {
		ArrayList<StorageElement> results = new ArrayList<>();
		
		for(StorageElement se : elements) {
			if(se.getNom().contains(nom)) results.add(se);
		}
		return results;
	}
	
	public ArrayList<StorageElement> findR(String nom) {
		ArrayList<StorageElement> results = new ArrayList<>();
		
		for(StorageElement se : elements) {
			if(se.getNom().contains(nom)) results.add(se);
			if(se instanceof Directory) {
				results.addAll(((Directory)se).findR(nom));
			}
		}
		return results;
	}
	
	public void addElement(StorageElement se) {
		this.elements.add(se);
		this.setSize(getSize() + se.getSize());
	}
	
	public Directory(String nom, Directory parent) {
		super(nom, parent, 0);
		this.elements = new ArrayList<StorageElement>();
	}
}
