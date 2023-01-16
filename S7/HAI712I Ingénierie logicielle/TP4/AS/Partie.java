package V2;

import java.util.ArrayList;

public class Partie {
    
    protected ArrayList<Bidon> bidonsList;
    private int volumeFinal;
    
    public void setBidonsList(ArrayList<Bidon> bidonsList) {
        this.bidonsList = bidonsList;
    }
    public void setVolumeFinal(int volumeFinal) {
        this.volumeFinal = volumeFinal;
    }

    public int getVolumeFinal() {
        return volumeFinal;
    }

    public String getVolumeActuel() {
        String res = "";
        for (Bidon bidon : bidonsList) {
            res += bidon.toString() + "\n";
        }
        return res;
    }

    public Partie(int nbBidons, int[] capacites, int volumeFinal) {
        this.volumeFinal = volumeFinal;
        this.bidonsList = new ArrayList<Bidon>();
        for (int i = 0; i < nbBidons; i++) {
            Bidon bidon = new Bidon(capacites[i], 0);
            bidonsList.add(bidon);
        }
    }

    public void jouer() {
        //Commande 1
        ArrayList<ICommande> history = new ArrayList<>();

        ICommande com1 = new Remplir(bidonsList.get(0));
        com1.execute();
        history.add(com1);
        System.out.println(getVolumeActuel());
        //Commande 2
        ICommande com2 = new Transvaser(bidonsList.get(0), bidonsList.get(1));
        com2.execute();
        history.add(com2);
        System.out.println(getVolumeActuel());
        //Commande 3
        ICommande com3 = new Transvaser(bidonsList.get(0), bidonsList.get(2));
        com3.execute();
        history.add(com3);
        System.out.println(getVolumeActuel());
        //Commande 4
        ICommande com4 = new Vider(bidonsList.get(0));
        com4.execute();
        history.add(com4);
        System.out.println(getVolumeActuel());
        //Commande 5
        ICommande com5 = new Transvaser(bidonsList.get(2), bidonsList.get(0));
        com5.execute();
        history.add(com5);
        System.out.println(getVolumeActuel());
        //Commande 5 UNDO
        com5.undo();
        System.out.println(getVolumeActuel());
        System.out.println("Last command removed");

        //Commande 5 redone
        com5.execute();
        System.out.println(getVolumeActuel());
        System.out.println("Command re-done");
        //Commande 6
        ICommande com6 = new Transvaser(bidonsList.get(1), bidonsList.get(0));
        com6.execute();
        history.add(com6);
        System.out.println(getVolumeActuel());


        if (this.bidonsList.get(0).getVolume() == this.getVolumeFinal()) {
            System.out.println("Bravo, vous avez gagné !");
        } else {
            System.out.println("Dommage, vous avez perdu !");
        }
    }

    
}
