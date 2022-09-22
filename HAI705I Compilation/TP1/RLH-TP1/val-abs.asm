# Robin L'Huillier 22/09/2022
# valeur absolue d'un entier saisi

.data
	demande: .asciiz "Saisissez un entier\n"
	absolue: .asciiz "Valeur absolue: "

.text
main: 	
	li $v0, 4
	la $a0, demande
	syscall
	li $v0, 5
	syscall
	move $t1, $v0
	bgez $t1, affiche_res
	neg $t1, $t1
	
affiche_res:	
	li $v0, 4
	la $a0, absolue
	syscall
	li $v0, 1
	move $a0, $t1
	syscall
	
