package V2;

public class Bidon {
    
    private int capacite;
    private int volume;

    public int getCapacite() {
        return capacite;
    }
    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }
    public int getVolume() {
        return volume;
    }
    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Bidon(int capacite, int volume) {
        this.capacite = capacite;
        this.volume = volume;
    }

    public Bidon(int capacite) {
        this.capacite = capacite;
        this.volume = 0;
    }

    public void remplir() {
        this.setVolume(this.getCapacite());
    }

    public int transvaser(Bidon bidon) {
        int volDeplace = 0;
        if (bidon.getVolume() + this.getVolume() > bidon.getCapacite()) {
            volDeplace = bidon.getCapacite() - bidon.getVolume();
            bidon.setVolume(bidon.getCapacite());
            this.setVolume(this.getVolume() - this.getVolume() + bidon.getVolume());
        } else {
            volDeplace = this.getVolume();
            bidon.setVolume(this.getVolume() + bidon.getVolume());
            this.setVolume(0);
        }
        return volDeplace;
    }

    public void vider() {
        this.setVolume(0);
    }

	@Override
	public String toString() {
		return "Volume: " + String.valueOf(volume) + " (" + (volume * 1.0 /capacite * 1.0)*100 + "%)";
	}

	
}
