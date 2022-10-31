**Disclaimer**

N'est pas inclus ici les fichiers d'interconnexion des composants, ni le serveur permettant de leur passer les adresses.  

Manquent aussi les fichiers *connexion-composant.c* et *tcp-utils.c* qui contiennent des structures et signatures de fonctions utilitaires. J'ai ajouté dans *ricart-agrawala.h* les struct. Les fonctions quant à elles sont des fonctions classiques d'envoi de données, ou d'affichage. 

On suppose que les processus sont déjà connectés par tcp, et connaissent leurs voisins qui sont déjà joints dans un fd_set (voir *struct configuration*).  

Vous trouverez dans le fichier *ricart-agrawala.h* quelques notes sur les variables utilisées et les IPC choisis.  

L'implémentation suit l'algorithme Ricart-Agrawala d'exclusion mutuelle:

-----------
événements:
----------- 
1. Demander SC:  
   -- demandeur = Vrai  
   -- horloge++  
   -- heureDemande = horloge  
   -- repAttendues = n-1  
   -- Diffuser demande (numero, horloge)  
   -- attendre repAttendues à 0  

2. Libération:
   -- Pour chaque element dans differe:   
   -- -- Envoyer une réponse  
   -- demandeur = Faux  

3. Reception (numeroJ, horlogeJ):    
   -- horloge = max(horloge, horlogeJ)  
   -- Si demandeur = Faux ou (numeroJ, horlogeJ) < (numero, heureDemande):  
   -- -- Envoyer réponse à j  
   -- Sinon  
   -- -- Ajouter j à differe  

4. Reception reponse:  
   -- repAttendues--