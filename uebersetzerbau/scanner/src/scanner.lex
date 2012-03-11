/*** Definition section ***/

/*** Define different kinds of numbers ***/
%{

#define DECIMAL				10
#define HEXADECIMAL		16

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
%}



keyword			end|return|goto|if|then|var|not|and
lexeme			";"|"("|")"|","|":"|"="|"*"|"-"|"+"|"=<"|"#"

identifier		[a-zA-Z_][0-9a-zA-Z_]*
decimal			&[0-9]+
hexadecimal		[0-9][0-9A-Fa-f]*
whitespace		[\t\r\n ]
comment			"(*".*"*)"


/*** Rules section ***/
%%
{comment}		;

{keyword}[0-9a-zA-Z]+	printf("ident %s\n",yytext);
{keyword}		printf("%s\n",yytext);
{lexeme}		printf("%s\n",yytext);
{identifier}		printf("ident %s\n",yytext);
{decimal}		printf("num %lu\n", str_to_num(DECIMAL,yytext));
{hexadecimal}		printf("num %lu\n", str_to_num(HEXADECIMAL,yytext));

{whitespace}+		;
.+			{
				printf("ERROR: unrecognized token: %s\n", yytext);
				exit(1);
			}

%%
/*** C-Code section ***/

int main(void)
{
    /* Call the lexer, then quit. */
    yylex();
    return 0;
}

int str_to_num(int base, char *str){
	/* remove & from DECIMAL numbers */
	if(base == DECIMAL){
			str++;
	}
	return strtoul(str,NULL,base);
}

