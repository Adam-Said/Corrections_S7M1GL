package Magasin;

public class Magasin {

	public static void main(String[] args) {
		Produit lgv = new Produit("La grande vadrouille", (float) 10.0);
		Client cl = new Client("Dupont");
		Compte cmt = new Compte(cl);

		Produit solde = new ProduitSolde("Dora l'exploratrice", (float) 3.0);
		System.out.println("Produit solde: " + cmt.prixLocation(solde));
		
		System.out.println("Prix de base : "+ lgv.getPrix());
		
		System.out.println("Prix loc compte normal : "+ cmt.prixLocation(lgv)); // Compte base
		
		Compte cmt2 = new CompteAvecReduction(cl);
		System.out.println("Prix compte avec réduc : " + cmt2.prixLocation(lgv)); // Compte réduc
		
		Compte cmt3 = new CompteAvecSeuil(cl); // Comtpe seuil
		
		for(int i = 0; i < 6; i++) {
			System.out.println("Prix compte avec seuil : " + cmt3.prixLocation(lgv));
		}
		
	}

}
