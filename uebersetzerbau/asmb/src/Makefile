CC	= /usr/bin/gcc
CFLAGS  =  -pedantic -ansi -Wall -g
LDFLAGS = 
OUTDIR	= ~/abgabe/asmb

OBJ	= asmb.o 
all: asmb.o test

asmb.o: asmb.s
	$(CC) -c $<

# Erzeugt die Abgabe
abgabe: 
	mkdir -p $(OUTDIR)
	cp asmb.s $(OUTDIR)
	cp Makefile.abgabe $(OUTDIR)/Makefile

# VU Test
vutest: abgabe
	/usr/ftp/pub/ublu/test/asmb/test

# Meine Tests
asmb-test: asmb.o
	$(CC) $(CFLAGS) -o asmb-test test-main.c asmb.o $(LDFLAGS)

test: asmb-test

clean: 
	rm -rf $(OBJ) asmb-test $(OUTDIR)/*.o

.PHONY: clean
