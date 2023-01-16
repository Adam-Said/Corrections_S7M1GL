package Magasin;

public class CompteAvecReduction extends Compte {

	public CompteAvecReduction(Client user) {
		super(user);
	}
	
	public double prixLocation(Produit prod) {
		return prod.prixLocation() - (prod.prixLocation() / 100 ) * 10;
	}

}
