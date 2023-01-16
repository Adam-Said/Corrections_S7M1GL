package client;

import java.rmi.RemoteException;
import commons.Espece;

public class Chien extends Espece{

	private static final long serialVersionUID = 1L;
	
	public Chien() throws RemoteException {
		this.espece = "Chien";
	}
	
}
