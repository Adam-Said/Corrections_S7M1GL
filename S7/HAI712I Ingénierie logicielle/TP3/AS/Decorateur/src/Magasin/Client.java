package Magasin;

public class Client {
	private String nom;
	private Compte compte;
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Compte getCompte() {
		return compte;
	}
	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	public Client(String nom, Compte compte) {
		this.nom = nom;
		this.compte = compte;
	}
	public Client(String nom) {
		this.nom = nom;
		this.compte = null;}
	
	
}
