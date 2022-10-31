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
#include <stdio.h>

#ifndef   CONNEXION_COMPOSANT_H
#define   CONNEXION_COMPOSANT_H
#include "connexion-composant.h"
#endif

#ifndef   RICART_AGRAWALA_H
#define   RICART_AGRAWALA_H
#include "ricart-agrawala.h"
#endif

#ifndef   TCP_UTILS_H
#define   TCP_UTILS_H
#include "tcp-utils.h"
#endif

#define GREVE_GENERALE 1968

int sitePrioritaire(int n1, int h1, int n2, int h2) {
    if (h1 < h2 || (h1 == h2 && n1 < n2))
        return TRUE;
    return FALSE;
}

int diffuserDemande(struct voisin voisins[], int taille, int horloge, int numeroComposant, int requete) {
    struct paquet msg;
    msg.information = horloge;
    msg.information2 = numeroComposant;
    msg.requete = requete;
    int marcheCorrectement = TRUE;
    for (int i=0; i<taille; i++) {
        if (send2TCP(voisins[i].descripteur, &msg, sizeof(struct paquet)) <= 0) {
            printInColor("[T] Problème lors de l'envoi d'une demande à mon voisin", numeroComposant);
            marcheCorrectement = FALSE;
        }
    }
    return marcheCorrectement;
}

void travailAleatoire() {
    time_t t;
    srand((unsigned) time(&t));
    float r = 100+(rand()%100);
    sleep(0.01*r);
}

void* threadTravailleur (void* memPartagee) {
    struct partage *mem = (struct partage*) memPartagee;
    printInColor("[T] Je fonctionne", mem->numeroComposant);

    struct sembuf op;
    op.sem_num = 0;
    op.sem_op = mem->nombreVoisins;
    op.sem_flg = 0;
    struct paquet msg;
    char str[100];

    union semun valinit;
    int ret;

    while(TRUE) {
        travailAleatoire();
        printInColor("[T] Je demande la section critique", mem->numeroComposant);
        if (pthread_mutex_lock(&mem->lock) != 0) {
            printInColor("[T] Problème lors de l'obtention du mutex", mem->numeroComposant);
            afficheErreur();
        }
        mem->horloge++;
        int horloge = mem->horloge;
        mem->heureDemande = horloge;
        mem->demandeur = TRUE;        
        if (mem->nbTravaux <= 0) break;
        if (pthread_mutex_unlock(&mem->lock) != 0) {
            printInColor("[T] Problème lors du rendu du mutex", mem->numeroComposant);
            afficheErreur();
        }

        op.sem_op = mem->nombreVoisins;
        if (semop(mem->repAttendues, &op, 1) < 0) {
            printInColor("[T] Problème lors de la mise de réponse attendues au nombre de voisins", mem->numeroComposant);
            afficheErreur();
        }
        
        if ((ret = semctl(mem->repAttendues, 0, GETVAL, valinit)) == -1){
            perror("[T] erreur initialisation sem : ");
        } 
        printf("[T] Valeurs des sémaphores après remplissage [ "); 
        printf("%d ]\n", ret);

        if (diffuserDemande(mem->voisins, mem->nombreVoisins, mem->horloge, mem->numeroComposant, DEMANDE) == FALSE) {
            if (pthread_mutex_lock(&mem->lock) != 0) {
                printInColor("[T] Problème lors de l'obtention du mutex", mem->numeroComposant);
                afficheErreur();
            }   
            mem->nbTravaux = 0;
            if (pthread_mutex_unlock(&mem->lock) != 0) {
                printInColor("[T] Problème lors du rendu du mutex", mem->numeroComposant);
                afficheErreur();
            }
            break;
        }
        printInColor("[T] J'attends les réponses de tout le monde", mem->numeroComposant);

        op.sem_op = 0;
        if (semop(mem->repAttendues, &op, 1) < 0) {
            printInColor("[T] Problème lors de l'attente que les réponses attendues soient vide", mem->numeroComposant);
            afficheErreur();
        }

        if ((ret = semctl(mem->repAttendues, 0, GETVAL, valinit)) == -1){
            perror("[T] erreur initialisation sem : ");
        } 
        printf("[T] Valeurs des sémaphores après attente vide [ "); 
        printf("%d ]\n", ret);

        printInColor("[T] Je rentre en section critique", mem->numeroComposant);
        travailAleatoire();
        printInColor("[T] Je sors de section critique, je vide", mem->numeroComposant);

        if (pthread_mutex_lock(&mem->lock) != 0) {
            printInColor("[T] Problème lors de l'obtention du mutex", mem->numeroComposant);
            afficheErreur();
        }
        mem->nbTravaux--;
        if (mem->nbTravaux <= 0) break;
        for (int i=0; i<mem->nombreVoisins+1; i++) {
            if (mem->differe[i] == TRUE) {
                printf("differe: %i\n", i);
                msg.requete = REPONSE;
                msg.information2 = mem->numeroComposant;
                if (send2TCP(mem->voisins[i].descripteur, &msg, sizeof(struct paquet)) <= 0) {
                    sprintf(str, "[T] Problème lors de l'envoi d'une réponse différée à %i (ds: %i)", mem->voisins[i].numero, mem->voisins[i].descripteur);
                    printInColor(str, mem->numeroComposant);
                }
            }
        }
        mem->demandeur = FALSE;
        if (pthread_mutex_unlock(&mem->lock) != 0) {
            printInColor("[T] Problème lors du rendu du mutex", mem->numeroComposant);
            afficheErreur();
        }
    }

    printInColor("[T] Grève générale", mem->numeroComposant);
    pthread_exit(NULL);
}

void* threadDiffuseur (void* memPartagee) {
    struct partage *mem = (struct partage*) memPartagee;
    printInColor("[D] Je fonctionne", mem->numeroComposant);

    fd_set settmp;
    struct paquet msg;
    struct sembuf op;
    op.sem_num = 0;
    op.sem_op = -1;
    op.sem_flg = 0;
    char str[100];

    struct timeval tv;
    tv.tv_sec = 1;
    tv.tv_usec = 0;

    union semun valinit;
    int ret;
    if ((ret = semctl(mem->repAttendues, 0, GETVAL, valinit)) == -1){
        perror("[D] erreur initialisation sem : ");
    } 
    printf("[D] Valeurs des sémaphores après initialisation [ "); 
    printf("%d ]\n", ret);

    while(TRUE) {
        if (mem->nbTravaux <= 0) {
            diffuserDemande(mem->voisins, mem->nombreVoisins, mem->horloge, mem->numeroComposant, GREVE_GENERALE);
            break;
        }

        settmp = *mem->set;
        if (select(mem->maxSet+1, &settmp, NULL, NULL, &tv) == -1) {
            printInColor("[D] Problème lors du select", mem->numeroComposant);
            afficheErreur();
            continue;
        }

        for (int df=2; df <= mem->maxSet; df++) {
            if (!FD_ISSET(df, &settmp)) {
                continue;
            }   

            if (recv2TCP(df, &msg, sizeof(struct paquet)) <= 0) {
                printInColor("[D] Problème lors de la réception d'un message", mem->numeroComposant);
                afficheErreur();
            }

            if (msg.requete == DEMANDE) {
                int horlogeJ = msg.information;
                int numeroJ = msg.information2;
                if (mem->horloge < horlogeJ) {
                    if (pthread_mutex_lock(&mem->lock) != 0) {
                        printInColor("[D] Problème lors de l'obtention du mutex", mem->numeroComposant);
                        afficheErreur();
                    }
                    mem->horloge = horlogeJ;
                    if (pthread_mutex_unlock(&mem->lock) != 0) {
                        printInColor("[D] Problème lors du rendu du mutex", mem->numeroComposant);
                        afficheErreur();
                    }
                }

                if (mem->demandeur == FALSE || sitePrioritaire(numeroJ, horlogeJ, mem->numeroComposant, mem->heureDemande) == TRUE) {
                    sprintf(str, "demandeur: %i, numJ: %i, hJ: %i, num: %i, h: %i", mem->demandeur, numeroJ, horlogeJ, mem->numeroComposant, mem->heureDemande);
                    printInColor(str, mem->numeroComposant);
                    msg.requete = REPONSE;
                    msg.information2 = mem->numeroComposant;
                    int ds = -1;
                    for (int i=0; i<mem->nombreVoisins; i++)
                        if (mem->voisins[i].numero == numeroJ)
                            ds = mem->voisins[i].descripteur;
                    if (ds == -1) {
                        printInColor("[D] Problème: pas d'équivalence de descripteur pour le message qu'on vient de recevoir", mem->numeroComposant);
                    } else {
                        if (send2TCP(ds, &msg, sizeof(struct paquet)) <= 0) {
                            printInColor("[D] Problème lors de l'envoi d'une réponse", mem->numeroComposant);
                            afficheErreur();
                        }
                        printInColor("[D] J'envoie une réponse", mem->numeroComposant);
                    }
                } else {
                    if (pthread_mutex_lock(&mem->lock) != 0) {
                        printInColor("[D] Problème lors de l'obtention du mutex", mem->numeroComposant);
                        afficheErreur();
                    }
                    int indice = -1;
                    for (int i=0; i<mem->nombreVoisins; i++)
                        if (mem->voisins[i].numero == numeroJ)
                            indice = i;
                    mem->differe[indice] = TRUE;
                    if (pthread_mutex_unlock(&mem->lock) != 0) {
                        printInColor("[D] Problème lors du rendu du mutex", mem->numeroComposant);
                        afficheErreur();
                    }
                }

            } else if (msg.requete == REPONSE) {
                sprintf(str, "[D] Réception d'une réponse venant de %i", msg.information2);
                printInColor(str, mem->numeroComposant);
                if (semop(mem->repAttendues, &op, 1) < 0) {
                    printInColor("[D] Problème lors de la décrémentation des réponses attendues", mem->numeroComposant);
                    afficheErreur();
                }

                if ((ret = semctl(mem->repAttendues, 0, GETVAL, valinit)) == -1){
                    perror("[T] erreur initialisation sem : ");
                } 
                printf("[D] Valeurs des sémaphores après retrait de 1 [ "); 
                printf("%d ]\n", ret);

            } else if (msg.requete == GREVE_GENERALE) {
                printInColor("L'un des processus proclame la grève générale", mem->numeroComposant);
                if (pthread_mutex_lock(&mem->lock) != 0) {
                    printInColor("[D] Problème lors de l'obtention du mutex", mem->numeroComposant);
                    afficheErreur();
                }
                mem->nbTravaux = 0;
                if (pthread_mutex_unlock(&mem->lock) != 0) {
                    printInColor("[D] Problème lors du rendu du mutex", mem->numeroComposant);
                    afficheErreur();
                }
                if ((ret = semctl(mem->repAttendues, 0, GETVAL, valinit)) == -1){
                    perror("[D] erreur initialisation sem : ");
                }
                op.sem_op = -ret;
                if (ret > 0) {
                    if (semop(mem->repAttendues, &op, 1) < 0) {
                        printInColor("[D] Problème lors de la décrémentation des réponses attendues", mem->numeroComposant);
                        afficheErreur();
                    }
                }
                break;
            
            } else {
                printInColor("[D] Requete du message invalide", mem->numeroComposant);
            }
        }
    }

    printInColor("[D] Grève générale", mem->numeroComposant);
    pthread_exit(NULL);
}

void algorithmeRicartAgrawala(struct configuration *config) {
    int numeroComposant = config->numeroComposant;
    fd_set set = config->set;
    int nombreVoisins = config->nombreVoisins;
    int maxDescripteur = config->maxDescripteur;
    struct voisin *voisins = config->voisins;
    char str[100];

    afficheLigne();
    printInColor("Initialisation des paramètres de lancement de l'algorithme", numeroComposant);
    afficheLigne();

    // mettre en place le sémaphore de réponse attendues
    sprintf(str, "sem/%i.txt", numeroComposant);
    FILE* semFile = fopen(str, "w");
    fclose(semFile);
    int cleSem = ftok(str, 17);
    int nbSem = 1;
    int idSem=semget(cleSem, nbSem, IPC_CREAT | IPC_EXCL | 0666);
    if(idSem == -1) {
        printInColor("Problème lors de la création du sémaphore", numeroComposant);
        afficheErreur();
    }
    // ushort reponseAttendues[1];
    // reponseAttendues[0] = 0;
    union semun valInit;
    valInit.val = 0;
    if (semctl(idSem, 0, SETVAL, valInit) == -1){
        printInColor("Problème lors de l'initialisation du sémaphore", numeroComposant);
        afficheErreur();
    }
    printInColor("Le sémaphore est en place", numeroComposant);

    // mettre en place les locks et la mém partagée
    struct partage memPartagee;
    pthread_mutex_init(&memPartagee.lock, NULL);
    printInColor("Le mutex est en place", numeroComposant);
    memPartagee.horloge = 0;
    memPartagee.demandeur = FALSE;
    memPartagee.heureDemande = 0;
    memPartagee.voisins = voisins;
    memPartagee.nombreVoisins = nombreVoisins;
    memPartagee.numeroComposant = numeroComposant;
    memPartagee.set = &set;
    memPartagee.maxSet = maxDescripteur;
    memPartagee.repAttendues = idSem;
    memPartagee.nbTravaux = 3;
    int differe[nombreVoisins+1];
    for (int i=0; i<nombreVoisins+1; i++) differe[i] = FALSE;
    memPartagee.differe = differe;
    printInColor("La mémoire partagée est en place", numeroComposant);

    // lancer les deux threads
    pthread_t travailleur;
    pthread_t diffuseur;
    afficheLigne();
    printInColor("Je lance les threads, fin de l'initialisation", numeroComposant);
    afficheLigne();
    if (pthread_create(&travailleur, NULL, threadTravailleur, &memPartagee) != 0){
        printInColor("Impossible de lancer le thread travailleur", numeroComposant);
        afficheErreur();
    }
    if (pthread_create(&diffuseur, NULL, threadDiffuseur, &memPartagee) != 0){
        printInColor("Impossible de lancer le thread diffuseur", numeroComposant);
        afficheErreur();
    }

    // attendre la fin des threads et terminer correctement
    // threads
    if (pthread_join(travailleur, NULL) == -1) {
        printInColor("Problème de terminaison du thread travailleur", numeroComposant);
        afficheErreur();
    }
    if (pthread_join(diffuseur, NULL) == -1) {
        printInColor("Problème de terminaison du thread diffuseur", numeroComposant);
        afficheErreur();
    }
    // mutex
    if (pthread_mutex_destroy(&memPartagee.lock) != 0) {
        printInColor("Problème lors de la destruction du mutex", numeroComposant);
        afficheErreur();
    }
    // semaphore
    if (semctl(idSem, 0, IPC_RMID, NULL)==-1) {
        printInColor("Problème lors de la destruction du sémaphore", numeroComposant);
        afficheErreur();
    }
    sprintf(str, "sem/%i.txt", numeroComposant);
    if (remove(str) != 0) {
        printInColor("Impossible de supprimer le fichier pour clé du sémaphore", numeroComposant);
        afficheErreur();
    }
}