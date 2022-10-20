package commons;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Espece implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected String espece; 
	protected int esperanceVie;
	
	public Espece() throws RemoteException {
		super();
	}
	
	public Espece(String espece) throws RemoteException{
		this.espece = espece;
		this.esperanceVie = 0;
	}
	
	public Espece(String espece, int esperanceVie) throws RemoteException{
		this.espece = espece ;
		this.esperanceVie = esperanceVie ;
	}


	public String getNomEspece() {
		return this.espece;
	}

	public int getEsperanceDeVie() {
		return esperanceVie;
	}
	
}

