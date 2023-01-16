package Magasin;

public class ForfaitReduction extends Forfait {

	public ForfaitReduction(Compte compte) {
		super(compte);
	}
	
	public double prixLocation(Produit prod) {
		return (super.prixLocation(prod) * 0.90);
	}

}
