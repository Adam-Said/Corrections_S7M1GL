package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IEspece extends Remote{
    
    String getNomEspece() throws RemoteException;
    int getDureeVie() throws RemoteException;
} 