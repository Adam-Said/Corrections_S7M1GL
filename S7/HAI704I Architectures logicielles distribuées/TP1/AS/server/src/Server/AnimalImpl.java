package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.IAnimal;
import common.IDossierSuivi;

public class AnimalImpl extends UnicastRemoteObject implements IAnimal {

    private String nom;
    private String nomMaitre;
    private EspeceImpl espece;
    private String race;
	private DossierSuiviImpl dossierSuivi;

    public void setNom(String nom) {
		this.nom = nom;
	}

	public void setNomMaitre(String maitre) {
		this.nomMaitre = maitre;
	}

	public void setEspece(EspeceImpl espece) {
		this.espece = espece;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getNomAnimal() {
		return nom;
	}

	public String getNomMaitre() {
		return nomMaitre;
	}

	public EspeceImpl getEspece() {
		return espece;
	}

	public String getRace() {
		return race;
	}

	public AnimalImpl() throws RemoteException{
        this.nom = "Nom";
        this.nomMaitre="Maitre";
        this.espece= new EspeceImpl();
        this.race="Race";
		this.dossierSuivi = new DossierSuiviImpl();
    }

    public AnimalImpl(String n, String m, EspeceImpl e, String r) throws RemoteException{
		this.nom = n;
		this.nomMaitre = m;
		this.espece = e;
		this.race = r;
		this.dossierSuivi = new DossierSuiviImpl();
    }

	public AnimalImpl(String n, String m, EspeceImpl e, String r, DossierSuiviImpl d) throws RemoteException{
		this.nom = n;
		this.nomMaitre = m;
		this.espece = e;
		this.race = r;
		this.dossierSuivi = d;
    }

	public String getString() throws RemoteException{
		return getNomAnimal() + " - " + getNomMaitre() + "\n" + getEspece().getNomEspece() + " " + getRace() + " " + getDossier().getEtat();
	} 
	
	@Override	
    public String toString(){
        try {
			return getString();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "Erreur récupération état animal";
    }


	public IDossierSuivi getDossier() throws RemoteException {
		return dossierSuivi;
	}

	public void setDossier(DossierSuiviImpl dossier){
		this.dossierSuivi = dossier;
	}



    
}
