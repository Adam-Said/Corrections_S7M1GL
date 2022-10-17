package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import common.IAnimal;
import common.ICabinet;
import common.IClient;

public class Client extends UnicastRemoteObject implements IClient {
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private Registry registry;
    private ICabinet cabinet;


    protected Client() throws RemoteException {
        registry = null;
        cabinet = null;
    }

    @Override
    public void startClient() throws RemoteException {
        System.setProperty("java.security.policy", "./security.policy");
        SecurityManager securityManager = new SecurityManager();
        System.setSecurityManager(securityManager);

        try {
            this.registry = LocateRegistry.getRegistry();
            this.cabinet = (ICabinet) registry.lookup("cabinet");
            this.cabinet.addClient(this);
            System.out.println("Client connecté.\n");
        } catch (RemoteException e) {
            System.err.println("Problème lors de la connexion au server");
            e.printStackTrace();
        }
        catch (NotBoundException e){
            System.err.println("Problème lors de la connexion au server");
            e.printStackTrace();
        } 
        
    }

    public void addChien(String nom, String maitre, String race, String dos) throws RemoteException {
        Chien esp = new Chien();
        this.cabinet.addAnimal(nom, maitre, esp, race, dos);
        System.out.println(nom+" ajouté avec succés !");
        
    }

    @Override
    public void addAnimal(String nom, String maitre, String esp, String race) throws RemoteException {
        this.cabinet.addAnimal(nom, maitre, esp, race);
        System.out.println(nom+" ajouté avec succés !");
        
    }

    @Override
    public void addAnimal(String nom, String maitre, String esp, String race, String dos) throws RemoteException {
        this.cabinet.addAnimal(nom, maitre, esp, race, dos);
        System.out.println(nom+" ajouté avec succés !");
        
    }

    @Override
    public void setEtatAnimal(String nom, String etat) throws RemoteException {
        try{
            IAnimal animal = cabinet.searchAnimal(nom);
            animal.getDossier().setEtat(etat);
            System.out.println("Etat de "+ nom + " modifié avec succés !");
        }
        catch (Exception e){
            System.err.println("Animal non trouvé");
        } 
        
    }

    @Override
    public void exitClient() throws RemoteException {
        try {
            cabinet.suprClient(this);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Impossible d'accéder au cabinet !");

        } 
    }

    @Override
    public void displayCabinet() throws RemoteException {
        if(cabinet != null){
            System.out.println("Animaux du cabinet :\n");
            for (IAnimal animal : cabinet.getCabinet()) {
                System.out.println("- "+animal.getString());

            }
        }        
    }
    
    @Override
    public String outputCabinet() throws RemoteException {
    	String animalString = null;
        if(cabinet != null){
            System.out.println("Animaux du cabinet :\n");
            for (IAnimal animal : cabinet.getCabinet()) {
                animalString += ("- " + animal.getNomAnimal() + " / " + animal.getNomMaitre() + " / " + animal.getEspece().getNomEspece() + " / " + animal.getRace() + " / " + animal.getDossier().getEtat() + "\n");
            }
        }
		return animalString;        
    }

    @Override
    public IAnimal displayAnimal(String name) throws RemoteException {
        return this.cabinet.searchAnimal(name);
    }

   @Override
   public void alertClient(String msg) throws RemoteException{
        System.out.println(ANSI_RED + msg + ANSI_RESET);
   } 
   
   public String getAlertGui() throws RemoteException{
	   String msgFull = "Plus de 100 animaux dans le cabinet !\n";
	   int cpt = 0;
	   for (IAnimal animal : cabinet.getCabinet()) {
		   cpt = cpt + 1;
	   }
	   if(cpt >= 100) {
			return msgFull;
	   }
	   return "";
   }
    
}
