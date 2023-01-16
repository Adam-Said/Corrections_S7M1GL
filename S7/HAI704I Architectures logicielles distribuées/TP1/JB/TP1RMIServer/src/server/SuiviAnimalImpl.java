package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import commons.SuiviAnimal;

public class SuiviAnimalImpl extends UnicastRemoteObject implements SuiviAnimal, Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private String nomSuivi;

	protected SuiviAnimalImpl() throws RemoteException {
		super();
	}
	
	public SuiviAnimalImpl(String sa)  throws RemoteException {
		nomSuivi = sa;
	}

	@Override
	public String getSuivi() {
		return nomSuivi;
	}

	@Override
	public void setSuivi(String nouveauSuivi) {
		nomSuivi = nouveauSuivi;
	}
	
}
