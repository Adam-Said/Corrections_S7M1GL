// Author : Robin L'Huillier
// Date : 30/09/2022

#include <netinet/in.h>
#include <stdio.h> 
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <string.h>
#include <errno.h>

#define ADRESSE_ECOUTE 1
#define ATTRIBUTION_NUMERO 2
#define ADRESSE_VOISIN 3
#define ELECTION 4
#define TAILLE_RESEAU 5
#define DESTRUCTION_ANNEAU 6

#define TRUE 1
#define FALSE 0
#define MAX_BUFFER_SIZE 1000

struct paquet {
    int requete;
    int information;
    int information2;
    struct sockaddr_in adresse;
};

void afficheErreur() {
    printf("errno: %d , %s\n", errno, strerror(errno));
}

void afficheLigne() {
    printf("--------------------------------\n");
}

int creerSocket() {
    int option = 1;
    int ds = socket(PF_INET, SOCK_STREAM, 0);
    setsockopt(ds, SOL_SOCKET, SO_REUSEADDR, &option, sizeof(option));
    if (ds == -1){
        perror("Problème creation socket :");
        afficheErreur();
        exit(1); 
    }
    printf("Création de la socket réussie \n");
    return ds;
}

struct sockaddr_in nommerSocket(char* port, int ds) {
    struct sockaddr_in ad;
    ad.sin_family = AF_INET;
    ad.sin_addr.s_addr = INADDR_ANY;
    if(atoi(port) != -1) {
        ad.sin_port = htons((short) atoi(port));
    }
    printf("Port: %hu\n", htons(ad.sin_port));
    int res;
    res = bind(ds, (struct sockaddr*)&ad, sizeof(ad));
    if (res == 0) {
        printf("Socket nommée avec succès\n");
    } else {
        printf("Socket non nommée : %i \n", res);
        afficheErreur();
        exit(1);
    }
    return ad;
}

struct sockaddr_in designerSocket(char* port, char* ip) {
    struct sockaddr_in sock;
    sock.sin_family = AF_INET;
    int resConvertAddr = inet_pton(AF_INET, ip, &(sock.sin_addr));
    if(resConvertAddr == 1) {
        printf("Adresse IP correctement convertie\n");
    } else {
        printf("Problème à la conversion de l'adresse IP\n");
        afficheErreur();
        exit(1);
    }
    sock.sin_port = htons((short)atoi(port));
    return sock;
}

int connecterTCP(int ds, struct sockaddr_in* sock) {
    socklen_t lgAdr = sizeof(struct sockaddr_in);
    int resConnexion = connect(ds, (struct sockaddr*)sock, lgAdr);
    if (resConnexion == 0) {
        printf("Connexion réussie\n");
    } else {
        printf("Connexion impossible\n");
        afficheErreur();
        exit(-1);
    }
    return resConnexion;
}

int listenTCP(int ds, int nbMaxAttente) {
    int resListen = listen(ds, nbMaxAttente);
    if (resListen == -1) {
        printf("Problème lors de l'écoute\n");
        afficheErreur();
        exit(1);    
    } else {
        printf("En écoute\n");
    }
    return resListen;
}

int sendTCP(int sock, void* msg, int sizeMsg) {
    int res;
    int sent = 0;
    while(sent < sizeMsg) {
        res = send(sock, msg+sent, sizeMsg-sent, 0);
        sent += res;
        if (res == -1) {
            printf("Problème lors de l'envoi du message\n");
            afficheErreur();
            return -1;
        }
    }
    return sent;
}

int recvTCP(int sock, void* msg, int sizeMsg) {
    int res;
    int received = 0;
    while(received < sizeMsg) {
        res = recv(sock, msg+received, sizeMsg-received, 0);
        received += res;
        if (res == -1) {
            printf("Problème lors de la réception du message\n");
            afficheErreur();
            return -1;
        } else if (res == 0) {
            return 0;
        }
    }
    return received;
}

int recv2TCP(int sock, void* msg, int sizeMsg) {
    int taille;
    recvTCP(sock, &taille, sizeof(int));
    if (taille > sizeMsg) {
        printf("Problème, buffer trop petit! (taille attendue %i, taille réelle %i)\n", taille, sizeMsg);
        return -1;
    }
    return recvTCP(sock, msg, taille);
}

int send2TCP(int sock, void* msg, int sizeMsg) {
    sendTCP(sock, &sizeMsg, sizeof(int));
    return sendTCP(sock, msg, sizeMsg);
}
