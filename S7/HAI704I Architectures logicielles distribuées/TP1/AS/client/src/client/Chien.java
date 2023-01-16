package client;

import java.io.Serializable;
import common.IEspece;


public class Chien implements IEspece, Serializable  {

    private String nomEspece;
    private int dureeVie;

    public Chien() {
        this.nomEspece = "Chien";
        this.dureeVie = 15; 
    }
        
	public String getNomEspece() {
		return nomEspece;
	}
	public void setNomEspece(String nomEspece) {
		this.nomEspece = nomEspece;
	}
	public int getDureeVie() {
		return dureeVie;
	}
	public void setDureeVie(int dureeVie) {
		this.dureeVie = dureeVie;
	}
}
