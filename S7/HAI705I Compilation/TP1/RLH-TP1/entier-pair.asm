# Robin L'Huillier 22/09/2022
# demander un entier à l'utilisateur, dire s'il est pair ou non

.data
	demandeEntier: .asciiz "Entrez un entier\n"
	pair: .asciiz "L'entier est pair\n"
	impair: .asciiz "L'entier est impair\n"
	
.text
main:
	li $v0, 4
	la $a0, demandeEntier
	syscall
	li $v0, 5
	syscall
	move $t1, $v0
boucle_decremente:
	beqz $t1, entier_pair
	bltz $t1, entier_impair
	addi $t1, $t1, -2
	j boucle_decremente	
entier_pair:
	la $a0, pair
	j afficher
entier_impair:
	la $a0, impair
afficher:
	li $v0, 4
	syscall
