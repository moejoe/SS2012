	.file	"asmb.s"
	.text
.globl asmb
	.type	asmb, @function
asmb:
.LFB2:
	# if n <= 0 : return
	test 	%rsi, %rsi
	jle	exit
	# set r11 a word *behind* x[]
	leaq	(%rdi,%rsi,8), %r11
	# prepare loop register
	movq	%rsi, %rcx
	
	# set carry to 0
	clc
for_loop:
	# set pointer r11 to next x
	leaq	-8(%r11), %r11
	# rotate through carry 	
	rcrq 	(%r11)
	loop	for_loop
exit:
	ret
.LFE2:
	.size	asmb, .-asmb
	.section	.eh_frame,"a",@progbits
.Lframe1:
	.long	.LECIE1-.LSCIE1
.LSCIE1:
	.long	0x0
	.byte	0x1
	.string	"zR"
	.uleb128 0x1
	.sleb128 -8
	.byte	0x10
	.uleb128 0x1
	.byte	0x3
	.byte	0xc
	.uleb128 0x7
	.uleb128 0x8
	.byte	0x90
	.uleb128 0x1
	.align 8
.LECIE1:
.LSFDE1:
	.long	.LEFDE1-.LASFDE1
.LASFDE1:
	.long	.LASFDE1-.Lframe1
	.long	.LFB2
	.long	.LFE2-.LFB2
	.uleb128 0x0
	.align 8
.LEFDE1:
	.ident	"GCC: (Debian 4.3.2-1.1) 4.3.2"
	.section	.note.GNU-stack,"",@progbits
