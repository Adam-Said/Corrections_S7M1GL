package client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;


public class MainClient {
    
    public static void main(String[] args) throws RemoteException{
        
        Client client = new Client();
        
        try {    
            System.out.println("Démarrage du client...\n ");
            System.out.println("Récupération du cabinet...\n ");
            client.startClient();

            System.out.println("Système prêt !\n ");

            Scanner reader = new Scanner(System.in);
            int choice = -1;
            while (choice < 5) {
                System.out.println("Que voulez-vous faire ?\n1. Voir le cabinet\n2. Ajouter un animal\n3. Ajouter n animaux\n4. Ajouter un chien\n5. Quitter\n ");
                choice = reader.nextInt();

                switch(choice){
   
                    case 1: 
                        client.displayCabinet();
                        System.out.println("\n");
                        break;
                
                    case 2:
                        System.out.println("Ajout d'un animal:\n");
                        Scanner input = new Scanner(System.in);
                        System.out.println("Nom de l'animal: ");
                        String name = input.nextLine();
                        System.out.println("\nNom du maitre: ");
                        String maitre = input.nextLine();
                        System.out.println("\nEspece: ");
                        String espece = input.nextLine();
                        System.out.println("\nRace: ");
                        String race = input.nextLine();
                        System.out.println("\nEtat de sante: ");
                        String etat = input.nextLine();
                        client.addAnimal(name, maitre, espece, race, etat);
                        System.out.println("\nNouvel animal: \n"+client.displayAnimal(name).getString());
                    break;
                    
                    case 3:
                        System.out.println("Ajout de plusieurs animaux:\n");
                        System.out.println("Combien voulez-vous créer d'animaux ? \n");
                        int n = reader.nextInt();
                        System.out.println("Ajout de "+n+" animaux...\n ");
                        for (int i = 0; i < n; i++) {
                            client.addAnimal( String.valueOf(i), "maitre "+String.valueOf(i), "TEST", "Doge", "Tranquille");
                        }
                        break;
                    
                    case 4:
                        System.out.println("Ajout d'un chien':\n");
                        input = new Scanner(System.in);
                        System.out.println("Nom du chien: ");
                         name = input.nextLine();
                        System.out.println("\nNom du maitre: ");
                         maitre = input.nextLine();
                        System.out.println("\nRace du chien: ");
                         race = input.nextLine();
                        System.out.println("\nEtat de sante: ");
                         etat = input.nextLine();
                        client.addChien(name, maitre, race, etat);
                        System.out.println("\nNouvel animal: \n"+client.displayAnimal(name).getString());
                    break;
                    default:
                        System.out.println("Au revoir...");
                        client.exitClient();
                        System.exit(0);
                        break;

                    
                    }
                    System.out.println("appuyez sur une touche pour continuer");
                    System.in.read();
                
            }
            reader.close();
 
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
