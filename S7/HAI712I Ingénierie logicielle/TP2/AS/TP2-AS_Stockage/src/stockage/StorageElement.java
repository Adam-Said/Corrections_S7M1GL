package stockage;

public abstract class StorageElement {
	
	private String nom;
	private Directory parent;
	private int size;
	
	public int size() {
		return this.size;
	}
	
	public String absolutePath() {
		return this.parent == null ? nom : parent.absolutePath() + "/" + nom;
	}
	
	public void setParent(Directory parent) {
		this.parent = parent;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Directory getParent() {
		return parent;
	}

	public StorageElement(String nom, Directory parent, int size) {
		this.nom = nom;
		this.parent = parent;
		this.size = size;
	}

	public StorageElement(String nom, int size) {
		this.nom = nom;
		this.size = size;
		this.parent = null;
	}
	
	
	
	
	
	
}
