package commons;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Animal extends Remote{
	
	
	String getNom() throws RemoteException;
	String getSuivi() throws RemoteException;
	String getNomEspece() throws RemoteException;
	String getMaitre() throws RemoteException;
	String getNomComplet() throws RemoteException;
	void afficherNomComplet() throws RemoteException;
	Animal getAnimal() throws RemoteException;
	
	
	
	
}
