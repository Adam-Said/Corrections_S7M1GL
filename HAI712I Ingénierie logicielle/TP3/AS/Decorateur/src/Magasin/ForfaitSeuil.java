package Magasin;

public class ForfaitSeuil extends Forfait {

	private int nbLoc = 5;
	public ForfaitSeuil(Compte compte) {
		super(compte);
	}
	
	public double prixLocation(Produit prod) {
		if(nbLoc == 0) {
			nbLoc = 5;
			return 0;
		}
		else {
			nbLoc -= 1;
			return super.prixLocation(prod);		
		}
	}

}
