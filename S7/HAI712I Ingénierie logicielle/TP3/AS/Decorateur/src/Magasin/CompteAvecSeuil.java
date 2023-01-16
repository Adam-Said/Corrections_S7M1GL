package Magasin;

public class CompteAvecSeuil extends Compte {

	private int nbLoc = 5;
	public CompteAvecSeuil(Client user) {
		super(user);
	}
	
	public double prixLocation(Produit prod) {
		if(nbLoc == 0) {
			nbLoc = 5;
			return 0;
		}
		else {
			nbLoc -= 1;
			return prod.prixLocation();			
		}
	}

	public int getNbLoc() {
		return nbLoc;
	}

	public void setNbLoc(int nbLoc) {
		this.nbLoc = nbLoc;
	}
	
	

}
