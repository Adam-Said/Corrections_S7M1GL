package Server;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;



public class Server {


	public Server() {}

	public static void main(String[] args) {
		System.setProperty("java.security.policy", "./security.policy");
		SecurityManager securityManager = new SecurityManager();
		System.setSecurityManager(securityManager);
		System.setProperty("java.rmi.server.codebase", "file:./client/obj/");
		try{
			CabinetImpl cabinet = new CabinetImpl();
			EspeceImpl espChat = new EspeceImpl(10,"Chat");
			AnimalImpl animal1 = new AnimalImpl("Popoki", "Hubert", espChat, "siamois");

			cabinet.addAnimal(animal1);
			Registry registry = LocateRegistry.createRegistry(1099);

			if(registry == null){
				System.out.println("Registry can not be created");
			}
			else{
				registry.bind("cabinet", cabinet);
				System.err.println("Server ready!");
			} 

		}
		catch (RemoteException e){
			e.printStackTrace();
		} 
		catch (AlreadyBoundException e){
			e.printStackTrace();
		} 
		
	} 

}