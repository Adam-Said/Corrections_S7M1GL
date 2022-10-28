package Magasin;

public class Compte {
	static int compteur = 0;
	private int id;
	private Client user;
	
	public int getCompteur() {
		return compteur;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Client getUser() {
		return user;
	}
	public void setUser(Client user) {
		this.user = user;
	}
	
	public Compte(Client user) {
		this.id = compteur ++;
		this.user = user;
		user.setCompte(this);
	}

	public Compte() {
		this.id = compteur ++;
		this.user = new Client("");
		user.setCompte(this);
	}
	
	public double prixLocation(Produit produit) {
		return produit.prixLocation();
	}
	
	
}
