package V2;

public abstract class Commande implements ICommande {
    protected int volDeplace;

    public int getVolDeplace() {
        return volDeplace;
    }

    public void setVolDeplace(int volDeplace) {
        this.volDeplace = volDeplace;
    }

    
}
