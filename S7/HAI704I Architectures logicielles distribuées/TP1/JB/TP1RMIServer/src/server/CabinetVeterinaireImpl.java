package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import commons.Animal;
import commons.CabinetVeterinaire;
import commons.Espece;
import commons.SuiviAnimal;

public class CabinetVeterinaireImpl extends UnicastRemoteObject implements CabinetVeterinaire {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Animal> cabinet ;
	private int size;
	
	
	public CabinetVeterinaireImpl() throws RemoteException {
		this.cabinet = new ArrayList<>();
		this.size = 0;
 }
	
	public ArrayList<Animal> getCabinet() throws RemoteException {
		return cabinet;
	}
	
	@Override
	public void addAnimal(Animal animal) throws RemoteException {
		if(!this.animalExists(animal.getNom(), animal.getMaitre())) {
		cabinet.add(animal);
		size++;
	}
	}

	@Override
	public void addAnimal(String name, String ownerName, String species, String race, String state) throws RemoteException  {
		
		Animal animal = new AnimalImpl(name, ownerName, species, race, state);
		size++;
		cabinet.add(animal);
	}
	
	@Override
	public void addAnimal(String name, String ownerName, Espece species, String race, String state) throws RemoteException  {
		
		Animal animal = new AnimalImpl(name, ownerName, species, race, state);
		if(!this.animalExists(name, ownerName)) {
			size++;
			cabinet.add(animal);
		}
	}

	@Override
	public boolean animalExists(String nom, String owner) throws RemoteException  {
			for (Animal animal : cabinet) {
				if(animal.getNom().equals(nom) && animal.getMaitre().equals(owner)) {
					return true;
				}
			}
		return false;
	}


	@Override
	public int size() throws RemoteException {
		return size;
	}


	

	@Override
	public Animal searchAnimal(String nom) throws RemoteException {
		for (Animal animal : cabinet) {
			if(animal.getNom().equals(nom)) {
				return animal;
			}
		}
	return null;
	}

	
	@Override
	public void removeAnimal(String name, String ownerName) throws RemoteException {

		for (int i = 0; i<size(); i++) {
			if(cabinet.get(i).getNom().equals(name) && cabinet.get(i).getMaitre().equals(ownerName)) {
				cabinet.remove(cabinet.get(i));
				break;
			}
		}

		size--;
	}
	
}
