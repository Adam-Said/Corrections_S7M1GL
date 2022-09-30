# Robin L'Huillier 22/09/2022
# effectue récursivement la somme des n premiers entiers

.data
	saisirEntier: .asciiz "Saisissez un entier n\n"
	afficherSum: .asciiz "La somme des entiers de 1 à n est : "
	nouvelleLigne: .asciiz "\n"

.text
main:
	li $a1, 4
	li $v0, 4
	la $a0, saisirEntier
	syscall
	li $v0, 5
	syscall
	move $a0, $v0
	jal somme_recursive
	move $a0, $v0
	j affiche
	
somme_recursive:
	addi $sp, $sp, -8 
    	sw $ra, 0($sp)
    	sw $a0, 4($sp)
    	addi $a0, $a0, -1
    	beq $a0, $zero, return_0
	jal somme_recursive
	
fin_recursion:
	lw $ra, 0($sp)
        lw $t0, 4($sp)
        addi $sp, $sp, 8
	add $v0, $v0, $t0
	jr $ra
	
return_0:     
        li $v0,0
        j fin_recursion

affiche:
	move $t1, $a0
	la $a0, afficherSum
	li $v0, 4
	syscall
	li $v0, 1
	move $a0, $t1
	syscall
	li $v0, 4
	la $a0, nouvelleLigne
	syscall

end: