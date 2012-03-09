CC	= /usr/bin/gcc
CFLAGS  =  -pedantic -ansi -Wall -g
LDFLAGS = 
OUTDIR	= ~/abgabe/asma

OBJ	= asma.o 
all: asma.o test

asma.o: asma.s
	$(CC) -c $<

# Generiert ein Assemble template 
template:
	$(CC) -O -S asma_template.c

# Erzeugt die Abgabe
abgabe: 
	mkdir -p $(OUTDIR)
	cp asma.s $(OUTDIR)
	cp Makefile $(OUTDIR)

# VU Test
vutest: abgabe
	/usr/ftp/pub/ublu/test/asma/test



# Meine Tests
asma-test: asma.o
	$(CC) $(CFLAGS) -o asma-test test-main.c $(LDFLAGS)

test: asma-test

clean: 
	rm -rf $(OBJ) asma_template.s asma-test $(OUTDIR)/*.o

.PHONY: clean
