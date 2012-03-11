all: scanner

scanner.c: scanner.lex
	flex -oscanner.c scanner.lex

scanner.o: scanner.c
	gcc -c -ansi -pedantic scanner.c -D_GNU_SOURCE

scanner: scanner.o
	gcc -o scanner scanner.o -lfl

clean:
	rm -f scanner scanner.o scanner.c

