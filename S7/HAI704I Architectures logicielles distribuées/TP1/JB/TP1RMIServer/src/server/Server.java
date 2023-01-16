package server;


import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import commons.CabinetVeterinaire;
import commons.Espece;
import commons.SuiviAnimal;

public class Server {
	

	public static void main(String[] args) {
		
		
		
		try {
//			String chemin = new File("").getAbsolutePath();
			System.setProperty("java.security.policy", "file:./security.policy");
			System.setProperty("java.rmi.server.codebase", "file:./TP1RMIClient/bin/client/");
			
			System.setSecurityManager(new SecurityManager());
			
			Registry registry = LocateRegistry.createRegistry(1099);
			
			CabinetVeterinaire cabinet = new CabinetVeterinaireImpl();
			if (registry == null)
				System.err.println("Registry not found on port 1099");
			else {
				registry.rebind("Cabinet", cabinet);
				System.err.println("Server ready");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}

