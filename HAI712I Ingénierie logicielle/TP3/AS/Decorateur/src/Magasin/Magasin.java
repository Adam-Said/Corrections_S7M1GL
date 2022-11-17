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
		
		//Dupont achete un forfait reduction.
		 cmt = new ForfaitReduction (cmt);
		 System.out.println("reduction lgv : " + cmt.prixLocation(lgv));
		 //Dupont achete en plus un forfait seuil, le seuil est `a 2
		 cmt = new ForfaitSeuil (cmt);
		 System.out.println("Seuil1+Reduction lgv: " + cmt.prixLocation(lgv));
		 System.out.println("Seuil2+Reduction lgv: " + cmt.prixLocation(lgv));
		 System.out.println("Seuil3+Reduction lgv: " + cmt.prixLocation(lgv)); //rend 0
		 //Dupont avec ses 2 forfaits loue un produit sold ́e
		 Produit r4 = new ProduitSolde("RockyIV", (float) 10.0);
		 System.out.println("Seuil1+Reduction+Solde rocky: " + cmt.prixLocation(r4));
		 System.out.println("Seuil2+Reduction+Solde rocky: " + cmt.prixLocation(r4));
		 System.out.println("Seuil3+Reduction+Solde rocky: " + cmt.prixLocation(r4));
		
	}

}
