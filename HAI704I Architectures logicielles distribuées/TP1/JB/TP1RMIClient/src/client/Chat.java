package client;

import java.rmi.RemoteException;
import commons.Espece;

public class Chat extends Espece{

	private static final long serialVersionUID = 1L;
	
	public Chat() throws RemoteException {
		this.espece = "Chat";
	}
	
}
