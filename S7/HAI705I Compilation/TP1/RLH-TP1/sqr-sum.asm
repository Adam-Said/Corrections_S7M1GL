# Robin L'Huillier 22/09/2022
# deux fonctions sqr et sum

.data
	saisirEntier: .asciiz "Saisissez un entier\n"
	afficherSum: .asciiz "Le résultat de sum est\n"
	afficherSqr: .asciiz "Le résultat de sqr est\n"
	nouvelleLigne: .asciiz "\n"

.text
main:
	li $v0, 4
	la $a0, saisirEntier
	syscall
	li $v0, 5
	syscall
	move $s1, $v0
	li $v0, 4
	la $a0, saisirEntier
	syscall
	li $v0, 5
	syscall
	move $s2, $v0
	move $a0, $s1
	move $a1, $s2
	jal sum_a0_a1
	move $a0, $v0
	jal afficher_sum
	move $a0, $s1
	jal sqr_a0
	move $a0, $v0
	jal afficher_sqr
	j end
	
sum_a0_a1:
	add $v0, $a0, $a1
	jr $ra

sqr_a0:
	mul $v0, $a0, $a0
	jr $ra	
	
afficher_sum:
	move $a1, $a0
	la $a0, afficherSum
	j afficher_global
afficher_sqr:
	move $a1, $a0
	la $a0, afficherSqr
afficher_global:
	li $v0, 4
	syscall
	li $v0, 1
	move $a0, $a1
	syscall
	li $v0, 4
	la $a0, nouvelleLigne
	syscall
	jr $ra
	
end:
	
