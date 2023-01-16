# Robin L'Huillier 22/09/2022
# array de 3 entiers, permuter valeurs

.data
	tabEnt: .space 12
	afficheEntiers: .asciiz "Les 3 entiers stockés sont: \n"
	nouvelleLigne: .asciiz "\n"

.text
main:
	li $t1, 1
	li $t0, 0
	sw $t1, tabEnt($t0)
	addi $t1, $t1, 1
	addi $t0, $t0, 4
	sw $t1, tabEnt($t0)
	addi $t1, $t1, 1
	addi $t0, $t0, 4
	sw $t1, tabEnt($t0)
	li $a0, 3
	jal affiche_tableau
	li $t0, 0
	lw $s0, tabEnt($t0)
	addi $t0, $t0, 4
	lw $s1, tabEnt($t0)
	addi $t0, $t0, 4
	lw $s2, tabEnt($t0)
	move $t2, $s0
	move $s0, $s2
	move $s2, $t2
	li $t0, 0
	sw $s0, tabEnt($t0)
	addi $t0, $t0, 4
	sw $s1, tabEnt($t0)
	addi $t0, $t0, 4
	sw $s2, tabEnt($t0)
	li $a0, 3
	jal affiche_tableau
	j end
	

affiche_tableau:
	move $t9, $a0
	li $v0, 4
	la $a0, afficheEntiers
	syscall
	li $t0, 0
while_affiche_tableau:
	beqz $t9, fin_affiche_tableau
	li $v0, 1
	lw $a0, tabEnt($t0)
	syscall
	addi $t0, $t0, 4
	addi $t9, $t9, -1
	li $v0, 4
	la $a0, nouvelleLigne
	syscall
	j while_affiche_tableau	
fin_affiche_tableau:
	jr $ra
	
end:
