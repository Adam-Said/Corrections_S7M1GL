#include <stdio.h>
#include <sys/types.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/ipc.h>
#include <sys/sem.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>

#ifndef   CONNEXION_COMPOSANT_H
#define   CONNEXION_COMPOSANT_H
#include "connexion-composant.h"
#endif

/*
connexion-composant.h contient deux structures importantes pour la suite:
struct voisin {
    int descripteur;
    struct sockaddr_in sockaddr_in;
    int numero;
};

struct configuration {
    int numeroComposant;
    fd_set set;
    int nombreVoisins;
    int maxDescripteur;
    struct voisin *voisins;
};
*/

#define MAX_STOCKAGE 100

struct partage {
    int horloge;
    int demandeur;
    int heureDemande;
    int nombreVoisins;
    int numeroComposant;
    pthread_mutex_t lock;
    struct voisin *voisins;
    fd_set *set;
    int maxSet;
    int repAttendues;
    int *differe;
    int nbTravaux;
};

union semun {
    int val ;                   /* cmd = SETVAL */
    struct semid_ds *buf;       /* cmd = IPC STAT ou IPC SET */
    unsigned short *array;      /* cmd = GETALL ou SETALL */
    struct seminfo* __buf;      /* cmd = IPC INFO (sous Linux) */
};

int sitePrioritaire(int n1, int h1, int n2, int h2);
int diffuserDemande(struct voisin voisins[], int taille, int horloge, int numeroComposant, int requete);
void travailAleatoire();
void* threadTravailleur (void* memPartagee);
void* threadDiffuseur (void* memPartagee);
void algorithmeRicartAgrawala(struct configuration *config);


/* Algo multi-thread Ricart-Agrawala
---------------------------------------
Algo global des processus: en boucle, travail hors SC, demander SC, travail en SC, sortir de SC. La durée de travail est aléatoire.
deux threads: un pour travail, un pour s'occuper des envois-réception de messages

Infos partagées: 
---- Dès le début:
n
numero
voisins
---- Mem partagée [pointeur sur struct partagée] (mutex nécessaire):
horloge
demandeur
heureDemande
differe
---- Sémaphore
repAttendues

----
var:
----
n               -- nombre de sites
horloge         -- horloge logique 
numero          -- numero du processus
demandeur       -- vrai si veut SC
heureDemande    -- explicite
repAttendues    -- explicite
differe         -- tableau contenant ceux qui ont fait une demande

-----------
événements:
----------- 
-> Demander SC:
    demandeur = Vrai
    horloge++
    heureDemande = horloge
    repAttendues = n-1
    Diffuser demande (numero, horloge)
    attendre repAttendues à 0

-> Libération:
    Pour chaque element dans differe: Envoyer une réponse
    demandeur = Faux

-> Reception (numeroJ, horlogeJ):
    horloge = max(horloge, horlogeJ)
    Si demandeur = Faux ou (numeroJ, horlogeJ) < (numero, heureDemande):
        Envoyer réponse à j
    Sinon
        Ajouter j à differe

-> Reception reponse:
    repAttendues--

*/