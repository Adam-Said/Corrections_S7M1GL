package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.IEspece;

public class EspeceImpl extends UnicastRemoteObject implements IEspece  {
    
    
	private String nomEspece;
    private int dureeVie;

    

    public EspeceImpl() throws RemoteException {
        this.nomEspece = "Undefined";
        this.dureeVie = 0; 
    }

    public EspeceImpl(int dv, String n) throws RemoteException{
        this.nomEspece = n;
        this.dureeVie = dv;
    }

	public EspeceImpl(String n) throws RemoteException{
		this.nomEspece = n;
		this.dureeVie = 10;
	} 
    
	public String getNomEspece() {
		return nomEspece;
	}
	public void setNomEspece(String nomEspece) {
		this.nomEspece = nomEspece;
	}
	public int getDureeVie() {
		return dureeVie;
	}
	public void setDureeVie(int dureeVie) {
		this.dureeVie = dureeVie;
	}


}
