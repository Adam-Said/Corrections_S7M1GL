# Robin L'Huillier 22/09/2022
# demander un entier Ã  l'utilisateur, afficher de 1 à n

.data
	demandeEntier: .asciiz "Entrez un entier\n"
	nouvelleLigne: .asciiz "\n"
.text
main:
	li $v0, 4
	la $a0, demandeEntier
	syscall
	li $v0, 5
	syscall
	move $t1, $v0
	li $t0, 1
boucle_affichage:
	bgt $t0, $t1, end
	li $v0, 1
	move $a0, $t0
	syscall		
	li $v0, 4
	la $a0, nouvelleLigne
	syscall	
	addi $t0, $t0, 1
	ble $t0, $t1, boucle_affichage
end:
