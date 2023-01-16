#absolute.asm

.text
main: 	li $v0 , 5
	syscall
	li $t0, 0
	blt $v0, $t0, neg
	move $a0, $v0
	li $v0, 1
	syscall
	j end
	
neg: 	neg $a0, $v0
	li $v0, 1
	syscall


end: