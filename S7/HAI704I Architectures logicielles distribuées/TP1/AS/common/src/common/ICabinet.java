package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ICabinet extends Remote {
    ArrayList<IAnimal> getCabinet() throws RemoteException;
    void addAnimal(String nomAnimal, String nomMaitre, IEspece espece, String race, IDossierSuivi doss) throws RemoteException;
    void addAnimal(String nomAnimal, String nomMaitre, String espece, String race) throws RemoteException;
    void addAnimal(String nomAnimal, String nomMaitre, String espece, String race, String dossier) throws RemoteException;
    void addAnimal(String nomAnimal, String nomMaitre, IEspece espece, String race, String dossier) throws RemoteException;
    void addAnimal(IAnimal animal) throws RemoteException;
    IAnimal searchAnimal(String nomAnimal) throws RemoteException;
    void addClient(IClient client) throws RemoteException;
    void suprClient(IClient client) throws RemoteException;
}
