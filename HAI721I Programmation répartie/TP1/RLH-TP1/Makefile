########################################
#~ définitions
########################################

BIN=bin/sous-anneau  bin/serveur

SRCS0=sous-anneau.c
SRCS1=serveur.c
SRCS2=tcp-utils.c

default: $(BIN)

########################################
#~ regles pour l'executable
########################################

obj/%.o: %.c
	gcc -Wall -Iinclude -c $< -o $@

bin/sous-anneau: $(SRCS0:%.c=obj/%.o)
	gcc -o $@ $+

bin/serveur: $(SRCS1:%.c=obj/%.o)
	gcc -o $@ $+

clean:
	rm -f $(BIN) obj/*.o *~
