# Author : Robin L'Huillier
# Date : 30/09/2022

import os, time
from subprocess import Popen, PIPE

def line():
    print("---------------------------------");

def lineUp(str):
    line()
    print(str)
    line()

lineUp("Bienvenue dans le programme d'auto-lancement d'anneau")
print("Je compile...")
os.system("mkdir obj; mkdir bin; make clean; make")
time.sleep(1)
lineUp("Compilation terminée")

nbSousAnneaux = int(input("Combien de sous-anneaux doivent être lancés ? "))
portServeur = input("Quel port pour le serveur ? ")

lineUp("Lancement du serveur")
os.system("bin/serveur " + portServeur + " " + str(nbSousAnneaux) + " &")

time.sleep(2)
lineUp("Lancement des sous-anneaux")
for i in range(nbSousAnneaux):
    portSousAnneau = str(int(portServeur) + 10 + i*5)
    print(portSousAnneau)
    os.system("bin/sous-anneau 0.0.0.0 " + portServeur + " " + portSousAnneau + " &")

time.sleep(2)
lineUp("Terminé, destruction des processus récalcitrants")

res = Popen(["ps", "aux"], stdout=PIPE)
res = ''.join(map(chr, (res.communicate())[0]))

killedSousAnneau = False

for r in res.split('\n'):
    if (not killedSousAnneau and "bin/sous-anneau" in r) or "bin/serveur" in r:
        num = r.split(' ')
        for n in num:
            if len(n) > 0 and n[0] in '0123456789':
                os.system("kill " + n)
                break
        if "sous-anneau" in r:
            killedSousAnneau = True
            print("Killed sous-anneau.")
        else:
            print("Killed serveur. (SHOULDN'T HAPPEN)")

time.sleep(2)
lineUp("Programme terminé, au revoir")
