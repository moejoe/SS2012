CC	= /usr/bin/gcc
#CFLAGS  =  -pedantic  -Wall -g
CFLAG	= -Wall -g
LDFLAGS = 
OUTDIR	= ~/abgabe/ag

all: symtab-test

# Erzeugt die Abgabe
abgabe: 
	mkdir -p $(OUTDIR)
	cp symtab.c symtab.h parser.y scanner.lex $(OUTDIR)
	cp Makefile.abgabe $(OUTDIR)/Makefile

# VU Test
vutest: abgabe
	/usr/ftp/pub/ublu/test/ag/test

# Meine Tests
symtab-test: symtab.o test_symtab.o
	$(CC) $(CFLAGS) -o symtab-test test_symtab.o symtab.o $(LDFLAGS)
	
%.o: %.s
	$(CC) -c $<	

clean: 
	rm -rf *.o symtab-test 

.PHONY: clean
