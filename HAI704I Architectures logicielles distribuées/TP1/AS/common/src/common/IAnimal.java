package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAnimal extends Remote{
    
    public String getNomAnimal() throws RemoteException;
    public String getNomMaitre() throws RemoteException;
    public IEspece getEspece() throws RemoteException;
    public String getRace() throws RemoteException;
    public String getString() throws RemoteException;
    public IDossierSuivi getDossier() throws RemoteException;
    

}
