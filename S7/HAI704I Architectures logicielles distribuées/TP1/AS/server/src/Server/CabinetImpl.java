package Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import common.IAnimal;
import common.ICabinet;
import common.IClient;
import common.IDossierSuivi;
import common.IEspece;

public class CabinetImpl extends UnicastRemoteObject implements ICabinet {

    private ArrayList<IAnimal> animals;
    private ArrayList<IClient> clients;

    protected CabinetImpl() throws RemoteException {
        this.animals = new ArrayList<IAnimal>();
        this.clients = new ArrayList<IClient>();
    }

   @Override
    public ArrayList<IAnimal> getCabinet() throws RemoteException{
        return this.animals;
    } 

    @Override 
    public void addAnimal(IAnimal animal) throws RemoteException {
        this.animals.add(animal);

        if(animals.size() >= 100){
            for (IClient client : this.clients) {
                client.alertClient("Plus de 100 animaux dans le cabinet !\n");
            }
        } 
    }

    @Override
    public void addAnimal(String nomAnimal, String nomMaitre, IEspece esp, String race, IDossierSuivi doss)
            throws RemoteException {
        EspeceImpl espece = new EspeceImpl();
        espece.setDureeVie(esp.getDureeVie());
        espece.setNomEspece(esp.getNomEspece());
        DossierSuiviImpl dossier = new DossierSuiviImpl();
        dossier.setEtat(doss.getEtat());
        AnimalImpl animal = new AnimalImpl(nomAnimal, nomMaitre, espece, race, dossier);
        
        this.animals.add(animal);

        if(animals.size() >= 100){
            for (IClient client : this.clients) {
                client.alertClient("Plus de 100 animaux dans le cabinet !\n");
            }
        } 

    }

    @Override
    public void addAnimal(String nomAnimal, String nomMaitre, String espece, String race) throws RemoteException {
        EspeceImpl esp = new EspeceImpl(espece);
        DossierSuiviImpl dos = new DossierSuiviImpl("Stable");
        AnimalImpl animal = new AnimalImpl(nomAnimal, nomMaitre, esp, race, dos);
        this.animals.add(animal);

        if(animals.size() >= 100){
            for (IClient client : clients) {
                client.alertClient("Plus de 100 animaux dans le cabinet !\n");
            }
        }  
        
    }

    @Override
    public void addAnimal(String nomAnimal, String nomMaitre, String espece, String race, String dossier) throws RemoteException {
        EspeceImpl esp = new EspeceImpl(espece);
        DossierSuiviImpl dos = new DossierSuiviImpl(dossier);
        AnimalImpl animal = new AnimalImpl(nomAnimal, nomMaitre, esp, race, dos);
        this.animals.add(animal);

        if(animals.size() >= 100){
            for (IClient client : clients) {
                client.alertClient("Plus de 100 animaux dans le cabinet !\n");
            }
        }  
        
    }

    @Override
    public void addAnimal(String nomAnimal, String nomMaitre, IEspece espece, String race, String dossier) throws RemoteException {
        EspeceImpl esp = new EspeceImpl();
        esp.setDureeVie(espece.getDureeVie());
        esp.setNomEspece(espece.getNomEspece());
        DossierSuiviImpl dos = new DossierSuiviImpl(dossier);
        AnimalImpl animal = new AnimalImpl(nomAnimal, nomMaitre, esp, race, dos);
        this.animals.add(animal);

        if(animals.size() >= 100){
            for (IClient client : clients) {
                client.alertClient("Plus de 100 animaux dans le cabinet !\n");
            }
        }  
        
    }

    @Override
    public IAnimal searchAnimal(String nomAnimal) throws RemoteException {
        for (int i = 0; i < this.animals.size(); i++) {
            try {
                if (this.animals.get(i).getNomAnimal().equals(nomAnimal)) {
                    return this.animals.get(i);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            
        }
        System.out.println("Animal inexistant dans le cabinet");
        return null;
    }

    @Override
    public void suprClient(IClient client) throws RemoteException {
        for (int i = 0; i < clients.size(); i++) {
            if (client.equals(clients.get(i) )) {
                clients.remove(i);
            }
        }
    }

   @Override
   public void addClient(IClient client) throws RemoteException{
        this.clients.add(client);
   }  

    
}
