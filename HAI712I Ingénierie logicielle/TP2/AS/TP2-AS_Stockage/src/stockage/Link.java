package stockage;

public class Link extends StorageElement {
	
	private String lien;

	public Link(String nom, Directory parent, String lien) {
		super(nom, parent, 0);
		this.lien = lien;
	}

	public String getLien() {
		return lien;
	}

	public void setLien(String lien) {
		this.lien = lien;
	}
	
		
}
