package V2;

public class Vider extends Commande {
    
    private Bidon bidon;

    public Bidon getBidon() {
        return bidon;
    }

    public void setBidon(Bidon bidon) {
        this.bidon = bidon;
    }

    public Vider(Bidon bidon) {
        this.bidon = bidon;
    }

    @Override
    public void execute() {
        volDeplace = bidon.getVolume();
        bidon.vider();
    }

    @Override
    public void undo() {
        bidon.setVolume(volDeplace);
    }
    
}
