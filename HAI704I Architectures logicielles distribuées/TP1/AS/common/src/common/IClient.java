package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    
    public void startClient() throws RemoteException;
    public void displayCabinet() throws RemoteException;
    public void addAnimal(String nom, String maitre, String esp, String race) throws RemoteException;
    public void addAnimal(String nom, String maitre, String esp, String race, String dos) throws RemoteException;
    public void setEtatAnimal(String nom, String etat) throws RemoteException;
    public IAnimal displayAnimal(String name) throws RemoteException;
    public void exitClient() throws RemoteException;
    public void alertClient(String msg) throws RemoteException;
	public String outputCabinet() throws RemoteException;
    
}
