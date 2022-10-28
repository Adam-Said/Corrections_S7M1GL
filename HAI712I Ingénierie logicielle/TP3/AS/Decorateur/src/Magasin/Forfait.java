package Magasin;

public class Forfait extends Compte {
	
	private Compte compte;

	public Forfait(Client c) {
		Compte cpte = new Compte(c);
		this.compte = cpte;
	}
	
	public Forfait(Compte c) {
		this.compte = c;
	}
	
	public Client getClient() {
		return this.compte.getUser();
	}
	
	public double prixLocation(Produit p) {
		return compte.prixLocation(p);
	}

}
