package Magasin;

public class ProduitSolde extends Produit {

	public ProduitSolde(String nom, float prix) {
		super(nom, prix);
	}
	
	public double prixLocation() {
		return super.prixLocation() / 2;
	}
	
}
