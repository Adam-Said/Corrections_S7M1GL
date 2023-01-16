package Magasin;

public class Produit {
	private String nom;
	private double prix;
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	
	public Produit(String nom, float prix) {
		this.nom = nom;
		this.prix = prix;
	}
	
	public double prixLocation() { // Prix de base en 5%
		return getPrix() * 0.95;
	}
	
	public double prixVente() { // Prix de base en 5%
		return getPrix();
	}
	
}
