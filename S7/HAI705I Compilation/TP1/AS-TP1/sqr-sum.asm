# Square and sum function

.data
	input: .asciiz "Entrez un entier \n"
	res: .asciiz "Le résultat est "
	newline: .asciiz "\n"
	
.text
main:
	li $v0, 4
	la $a0, input
	syscall
	li $v0, 5
	syscall
	move $s1, $v0
	li $v0, 4
	la $a0, input
	syscall
	li $v0, 5
	syscall
	move $s2, $v0
	move $a0, $s1
	move $a1, $s2
	jal sum
	move $a0, $v0
	jal disp
	move $a0, $s1
	jal sqr
	move $a0, $v0
	jal disp
	j end

sum:
	add $v0, $a0, $a1
	jr $ra
	
sqr:
	mul $v0, $a0, $a0
	jr $ra
	
disp:
	move $a1, $a0
	la $a0, res
	li $v0, 4
	syscall
	li $v0, 1
	move $a0, $a1
	syscall
	li $v0, 4
	la $a0, newline
	syscall
	jr $ra
	
end: