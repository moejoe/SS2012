all: parser

scanner.c: scanner.lex
	flex -oscanner.c scanner.lex

scanner.o: scanner.c parser.h
	gcc -c -ansi -pedantic scanner.c -D_GNU_SOURCE

parser: scanner.o parser.o
	gcc -o parser scanner.o parser.o -lfl

parser.o: parser.c
	gcc -c -ansi -pedantic parser.c

parser.c parser.h: parser.y
	yacc -t -v -d parser.y -o parser.c

clean:
	rm -f parser scanner.o scanner.c parser.h parser.c parser.o parser.output

