#hello_world.asm

.data
hello: .asciiz "hello world\n"

.text
main: li $v0 , 4
la $a0 , hello
syscall