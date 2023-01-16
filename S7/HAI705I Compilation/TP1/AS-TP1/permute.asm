# Robin L'Huillier 22/09/2022
# routine qui permute le contenu de deux variables entières de la zone de données

.data
	var1: .space 4
	var2: .space 4
	afficher: .asciiz "Les deux entiers sont\n"
	nouvelleLigne: .asciiz "\n"

.text
main:
	li $t0, 17
	li $t1, 45
	sw $t0, var1
	sw $t1, var2
	jal affiche_donnees
	la $a0, var1
	la $a1, var2
	jal swap
	jal affiche_donnees
	j end

swap:	# prend les deux adresses en argument a0 et a1
	li $t0, 0
	lw $t1, ($a0)
	lw $t2, ($a1)
	move $t0, $t1
	move $t1, $t2
	move $t2, $t0
	sw $t1, ($a0)
	sw $t2, ($a1)	
	jr $ra

affiche_donnees:
	li $v0, 4
	la $a0, afficher
	syscall
	li $v0, 1
	lw $a0, var1
	syscall
	li $v0, 4
	la $a0, nouvelleLigne
	syscall
	li $v0, 1
	lw $a0, var2
	syscall
	li $v0, 4
	la $a0, nouvelleLigne
	syscall
	jr $ra
	
end: