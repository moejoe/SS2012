CC	= /usr/bin/gcc
CFLAGS  =  -pedantic -ansi -Wall -g
LDFLAGS = 
OUTDIR	= ~/abgabe/ag

all: symtab.o test

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
	$(CC) $(CFLAGS) -o symtab-test symtab.o $(LDFLAGS)
	
%.o: %.s
	$(CC) -c $<	

clean: 
	rm -rf $(OBJ) $(OUTDIR)/*.o

.PHONY: clean
