package commons;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface CabinetVeterinaire extends Remote{
	
	public ArrayList<Animal> getCabinet() throws RemoteException;
	public boolean animalExists(String nom, String owner) throws RemoteException;
	public Animal searchAnimal(String nom) throws RemoteException;
	public int size() throws RemoteException;
	
	public void addAnimal(Animal animal) throws RemoteException; 
	void addAnimal(String name, String ownerName, String espece, String race, String suivi) throws RemoteException;
	void addAnimal(String name, String ownerName, Espece espece, String race, String suivi) throws RemoteException;
	void removeAnimal(String name, String ownerName) throws RemoteException;
} 
