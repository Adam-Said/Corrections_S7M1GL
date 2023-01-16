package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import commons.Animal;
import commons.Espece;
import commons.SuiviAnimal;
import server.*;

public class AnimalImpl extends UnicastRemoteObject implements Animal{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nom;
	private String nomMaitre;
	private Espece espece;
	private String race;
	private String suivi;
	

	
	public AnimalImpl()  throws RemoteException {
	}

	public AnimalImpl(String nomA, String nomMaitreA, String especeA, String raceA, String sa)  throws RemoteException {
		this.nom = nomA;
		this.nomMaitre= nomMaitreA;
		this.espece = new Espece(especeA);
		this.race = raceA;
		this.suivi = sa;
}
	
	
	public AnimalImpl(String nomA, String nomMaitreA, Espece especeA, String raceA, String sa)  throws RemoteException {
			this.nom = nomA;
			this.nomMaitre= nomMaitreA;
			this.espece = especeA;
			this.race = raceA;
			this.suivi = sa;
	}
		
		@Override
		public Animal getAnimal() throws RemoteException {
			return this;
		}
		

		@Override
		public String getNom() throws RemoteException {
			return nom;
		}
		
		@Override
		public String getSuivi() throws RemoteException {
			return suivi;
		}
		
		@Override
		public void afficherNomComplet() throws RemoteException {
			System.out.println(getNomComplet());
		}
		
		@Override
		public String getNomComplet() throws RemoteException {
			return "name: "+nom+", species name: "+espece.getNomEspece()+", average lifespan: "+Integer.toString(espece.getEsperanceDeVie())+", race: "+race+", owned by: "+nomMaitre;
		}

		@Override
		public String getNomEspece() throws RemoteException {
			return espece.getNomEspece();
		}

		@Override
		public String getMaitre() throws RemoteException {
			return nomMaitre;
		}

		

	

}