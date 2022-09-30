// Author : Robin L'Huillier
// Date : 30/09/2022

#include <netinet/in.h>
#include <stdio.h>
#include <sys/types.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include "tcp-utils.c"

#define MAX_STOCKAGE 100
#define RESETCOLOR "\033[0m"

char getColorFromNum(int num) {
    return (((num%6)+1)+'0');
}

void printInColor(char msg[], int num) {
    printf("\x1B[3%cm[SA%i]: %s%s\n", getColorFromNum(num), num, msg, RESETCOLOR);
}

int stockerMessage(struct paquet stockage[], int* indice, struct paquet msg, int numeroSousAnneau) {
    if ((*indice) == MAX_STOCKAGE) {
        printInColor("Problème, dépassement du stockage maximum de messages", numeroSousAnneau);
        return -1;
    }
    stockage[(*indice)] = msg;
    (*indice)++;
    return 0; 
}

int envoyerMessages(struct paquet stockage[], int* indice, int descripteur, int numeroSousAnneau) {
    if ((*indice) == 0) {
        return 1;
    }
    printInColor("Envoi de l'ensemble des messages stockés", numeroSousAnneau);
    if (send2TCP(descripteur, indice, sizeof(int)) <= 0) {
        printInColor("Problème lors de l'envoi du nombre de message stockés", numeroSousAnneau);
        return -1;
    }
    for (int i=0; i<(*indice); i++) {
        printInColor("Envoi d'un message", numeroSousAnneau);
        if (send2TCP(descripteur, &stockage[i], sizeof(struct paquet)) <= 0) {
            printInColor("Problème lors de l'envoi d'un message", numeroSousAnneau);
            return -1;
        }
    }
    (*indice) = 0;
    printInColor("Stockage de message vidé", numeroSousAnneau);
    return 0;
}

int lancerElection(int numeroSousAnneau, struct paquet stockage[], int* indice) {
    struct paquet msg;
    msg.requete = ELECTION;
    msg.information = numeroSousAnneau;
    msg.information2 = 1;
    stockerMessage(stockage, indice, msg, numeroSousAnneau);
    return 0;
}

int recevoirMessages(
    int dsPrecAnneau,
    int dsNextAnneau,
    int numeroSousAnneau,
    int* jeSuisLeChef,
    int* tailleReseau,
    struct paquet stockage[],
    int* indice
) {
    struct paquet msg;
    int nbReception;
    int numeroProvenance;
    int calculDeTaille;
    char str[100];

    if (recv2TCP(dsPrecAnneau, &nbReception, sizeof(int)) <= 0) {
        printInColor("Problème lors de la réception du nombre de messages à recevoir", numeroSousAnneau);
        return -1;
    }
    for (int i=0; i<nbReception; i++) {
        if (recv2TCP(dsPrecAnneau, &msg, sizeof(struct paquet)) <= 0) {
            printInColor("Problème lors de la réception d'un message du sous-anneau précédent", numeroSousAnneau);
            return -1;
        }
        switch (msg.requete) {
            case ELECTION:
                numeroProvenance = msg.information;
                calculDeTaille = msg.information2;
                sprintf(str, "J'ai reçu un message d'élection du P°%i, le calcul de taille en cours est %i", numeroProvenance, calculDeTaille);
                printInColor(str, numeroSousAnneau);
                if (numeroProvenance < numeroSousAnneau) {
                    printInColor("Je suis un meilleur candidat, je ne renvoie pas", numeroSousAnneau);
                } else if (numeroProvenance == numeroSousAnneau) {
                    printInColor("J'ai reçu mon propre message, je suis donc l'élu, je renvoie la taille du réseau à tous", numeroSousAnneau);
                    (*jeSuisLeChef) = TRUE;
                    (*tailleReseau) = calculDeTaille;
                    msg.information = calculDeTaille;
                    msg.requete = TAILLE_RESEAU;
                    stockerMessage(stockage, indice, msg, numeroSousAnneau);
                } else {
                    printInColor("C'est un meilleur candidat que moi, je renvoie", numeroSousAnneau);
                    msg.information2++;
                    stockerMessage(stockage, indice, msg, numeroSousAnneau);
                }
                break;
            case TAILLE_RESEAU:
                if ((*jeSuisLeChef)) {
                    printInColor("Le message de taille du reseau a bien fait le tour de l'anneau", numeroSousAnneau);
                } else {
                    (*tailleReseau) = msg.information;
                    sprintf(str, "La taille du reseau est: %i", (*tailleReseau));
                    printInColor(str, numeroSousAnneau);
                    stockerMessage(stockage, indice, msg, numeroSousAnneau);
                }
                break;
            default:
                printInColor("Problème: requête non reconnue", numeroSousAnneau);
                break;
        }
    }
    return 0;
}

void autoDetruireAnneau(int dsNextAnneau, int dsPrecAnneau, fd_set* set) {
    if (dsNextAnneau != -1 && close(dsNextAnneau) == -1) {
        printf("Problème lors de la fermeture du descripteur\n");
        afficheErreur();
    }
    if (close(dsPrecAnneau) == -1) {
        printf("Problème lors de la fermeture du descripteur\n");
        afficheErreur();
    }
    FD_CLR(dsNextAnneau, set);
    FD_CLR(dsPrecAnneau, set);
    printf("Auto-destruction de l'anneau enclenchée...\n");
    exit(0);
}

int main(int argc, char *argv[]) {

    if (argc != 4){
        printf("utilisation : %s ip_serveur port_serveur port_client\n", argv[0]);
        exit(0);
    }

    char str[100];

    int dsServeur = creerSocket();
    struct sockaddr_in sockServ = designerSocket(argv[2],argv[1]);
    connecterTCP(dsServeur, &sockServ);

    int dsPrecAnneau = creerSocket();
    printf("dsPrecAnneau: %i\n", dsPrecAnneau);
    struct sockaddr_in sockPrec = nommerSocket(argv[3], dsPrecAnneau);
    int nbMaxAttente = 10;
    listenTCP(dsPrecAnneau, nbMaxAttente);

    struct paquet msg;
    msg.requete = ADRESSE_ECOUTE;
    msg.adresse = sockPrec;
    if (send2TCP(dsServeur, &msg, sizeof(struct paquet)) <= 0) {
        printf("Problème lors de l'envoi de l'adresse d'écoute\n");
    }

    if (recv2TCP(dsServeur, &msg, sizeof(struct paquet)) <= 0) {
        printf("Probleme lors de la réception du numéro attribué\n");
        exit(0);
    }
    int numeroSousAnneau = msg.information;
    printInColor("J'ai reçu mon numéro de sous anneau", numeroSousAnneau);

    fd_set set, settmp;
    FD_ZERO(&set);
    FD_SET(dsServeur, &set);
    FD_SET(dsPrecAnneau, &set);
    struct sockaddr_in sockNext;
    socklen_t lgAdr;
    int maxDescripteur = dsServeur;
    if (dsPrecAnneau > dsServeur) maxDescripteur = dsPrecAnneau;
    int dsNextAnneau = -1;

    int connecteAuPrecedent = FALSE;
    int tailleReseau = -1;
    int jeSuisLeChef = FALSE;

    struct paquet stockage[MAX_STOCKAGE];
    int indiceStockage = 0;

    while (TRUE) {
        if (indiceStockage > 0 && dsNextAnneau != -1) {
            if (envoyerMessages(stockage, &indiceStockage, dsNextAnneau, numeroSousAnneau) == -1) {
                autoDetruireAnneau(dsNextAnneau, dsPrecAnneau, &set);
            }
        }

        settmp = set;
        if (select(maxDescripteur+1, &settmp, NULL, NULL, NULL) == -1) {
            printInColor("Problème lors du select", numeroSousAnneau);
            afficheErreur();
            continue;
        }

        for (int df=2; df <= maxDescripteur; df++) {
            if (!FD_ISSET(df, &settmp)) {
                continue;
            }   

            if (df == dsPrecAnneau) {
                if (connecteAuPrecedent) {
                    if (recevoirMessages(dsPrecAnneau, dsNextAnneau, numeroSousAnneau, &jeSuisLeChef, &tailleReseau, stockage, &indiceStockage) == -1) {
                        autoDetruireAnneau(dsNextAnneau, dsPrecAnneau, &set);
                    }
                } else {
                    dsPrecAnneau = accept(dsPrecAnneau, (struct sockaddr*)&sockPrec, &lgAdr);
                    char adresse[INET_ADDRSTRLEN];
                    inet_ntop(AF_INET, &sockPrec.sin_addr, adresse, INET_ADDRSTRLEN);
                    sprintf(str, "Connecté à l'anneau précédent: %s", adresse);
                    printInColor(str, numeroSousAnneau);
                    FD_SET(dsPrecAnneau, &set);
                    if (maxDescripteur < dsPrecAnneau) maxDescripteur = dsPrecAnneau;
                    connecteAuPrecedent = TRUE;
                    continue;
                }
            }

            if (df == dsServeur) {
                struct paquet msg;
                if (recv2TCP(dsServeur, &msg, sizeof(struct paquet)) <= 0) {
                    printInColor("Problème lors de la réception d'un message du serveur", numeroSousAnneau);
                    exit(1);
                }
                printInColor("Deconnexion du serveur", numeroSousAnneau);
                if (close(dsServeur) == -1) {
                    printInColor("Problème lors de la fermeture du descripteur", numeroSousAnneau);
                    afficheErreur();
                }
                FD_CLR(dsServeur, &set);
                switch (msg.requete) {
                    case ADRESSE_VOISIN:
                        sockNext = msg.adresse;
                        char adresse[INET_ADDRSTRLEN];
                        inet_ntop(AF_INET, &sockNext.sin_addr, adresse, INET_ADDRSTRLEN);
                        int port = htons(sockNext.sin_port);
                        sprintf(str, "Tentative de se connecter à l'anneau suivant: %s:%i\n", adresse, port);
                        printInColor(str, numeroSousAnneau);
                        dsNextAnneau = creerSocket();
                        connecterTCP(dsNextAnneau, &sockNext);
                        printInColor("Connexion au prochain sous-anneau réussie", numeroSousAnneau);
                        lancerElection(numeroSousAnneau, stockage, &indiceStockage);
                        break;
                    default:
                        printInColor("Problème: requête non reconnue", numeroSousAnneau);
                        break;
                }
            }
        }
    }
}
