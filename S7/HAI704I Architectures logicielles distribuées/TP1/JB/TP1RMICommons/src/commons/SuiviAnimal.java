package commons;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SuiviAnimal{
		public String getSuivi() throws RemoteException;
		public void setSuivi(String nom) throws RemoteException;
}
 