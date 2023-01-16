package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDossierSuivi extends Remote{
    
    void setEtat(String e) throws RemoteException;
    String getEtat() throws RemoteException;
}
