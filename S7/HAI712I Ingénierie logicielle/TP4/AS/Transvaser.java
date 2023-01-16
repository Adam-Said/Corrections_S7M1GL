package V2;

public class Transvaser extends Commande {
    
    private Bidon source, dest;

    public Bidon getSource() {
        return source;
    }

    public void setSource(Bidon source) {
        this.source = source;
    }

    public Bidon getDest() {
        return dest;
    }

    public void setDest(Bidon dest) {
        this.dest = dest;
    }

    public Transvaser(Bidon source, Bidon dest) {
        this.source = source;
        this.dest = dest;
    }

    @Override
    public void execute() {
        volDeplace = source.transvaser(dest);
    }

    @Override
    public void undo() {
        source.setVolume(source.getVolume() + volDeplace);
        dest.setVolume(dest.getVolume() - volDeplace);
    }
}
