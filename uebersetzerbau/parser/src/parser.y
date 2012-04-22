%{
	#include <stdio.h>
	#include <stdlib.h>
%}


%error-verbose

/* Token */

%start	Program
%token	T_END T_RETURN T_GOTO T_IF T_THEN T_VAR T_NOT T_AND
%token	T_NUM T_ID T_LE

%%


Program: 
	Funcdef ";"
	| Program Funcdef ";"
	| \epsilon
	;

Funcdef: 																/* Funktionsdefinition */ 
	T_ID "(" Pars ")" Stats T_END 
	;

/* Allows Parameters to end in a semicolon */
Pars:																		/* Parameterdefinition */  
	ParsS
	| \epsilon
	;

ParsS:
	ParsS T_ID ","
	| T_ID
	| T_ID ","
	;
	
/* Must end in a semicolon! */
Stats:
	Stats Stat ";"
	| Stats LabeldefS Stat ";"
	| Stat ";"
	| LabeldefS Stat ";"
	;
	
/* LabeldefS = { Labeldef }+ */

LabeldefS:															/* Labeldefinition */  
	T_ID ":"
	| LabeldefS T_ID ":" 
	
/* Kontrollieren */	
Stat: 
	return Expr
	| T_GOTO T_ID
	| T_IF Expr T_THEN Stats T_END
	| T_VAR T_ID "=" Expr											/* Variablendefinition */  
	| Lexpr "=" Expr											/* Zuweisung */  
	| Term
	;
	
Lexpr: 
	T_ID																		/* schreibender Variablenzugriff */  
	| "*" Unary														/* schreibT_ENDer Speicherzugriff */
	; 
	
Expr:
	Unary
	| PlusTerm
	| MultTerm
	| AndTerm
	| Term T_LE Term
	| Term "#" Term
	;
	
PlusTerm:
	PlustTerm "*" Term
	| Term
	;
	
MultTerm:
	MultTerm "*" Term
	| Term 
	;
AndTerm:
	AndTerm T_AND Term
	| Term
	;

	
Unary:
	T_NOT Unary
	| "-" Unary
	| "*" Unary														/* lesender Speicherzugriff */
	| Term
	;

Term: 
	"(" Expr ")"
	| T_NUM
	| T_ID																	/* Variablenverwendung */  
	| T_ID "(" Subterm ")" 									/* Funktionsaufruf */  

Suberm: 
	\epsilon
	| Expr ","
	| SubtermS
	| SubtermS ","
	;
SubtermS:
	SubtermS "," Expr
	| Expr
	;
	
%%

extern int yylex();
extern int yylineno;

int yyerror(char *error_text) {
	fprintf(stderr,"Line %i: %s\n",yylineno, error_text);
	exit(2);
}

int main(int argc, char **argv) {
	yydebug=1;
	yyparse();
	return 0;
}
	