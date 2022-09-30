# Tell if input is pair or not

.data
	pair: .asciiz "L'entier est pair\n"
	impair: .asciiz "L'entier est impair\n"


.text
main:	li $v0, 5
	syscall
	move $t1,$v0
	

boucle: 
	beqz $t1, test_pair
	bltz $t1, test_impair
	addi $t1, $t1, -2
	j boucle
		
test_pair:
	la $a0, pair
	j display
	
test_impair:
	la $a0, impair
	
display:
	li $v0, 4
	syscall
