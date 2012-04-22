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

{keyword}[0-9a-zA-Z]+	{printf("ident %s\n",yytext);return T_ID;};
end		{printf("%s\n",yytext);return T_END;};
return	{printf("%s\n",yytext);return T_RETURN;};
goto {printf("%s\n",yytext);return T_GOTO;};
if	{printf("%s\n",yytext);return T_IF;};
then {printf("%s\n",yytext);return T_THEN;};
var {printf("%s\n",yytext);return T_VAR;};
not	{printf("%s\n",yytext);return T_NOT;};
and {printf("%s\n",yytext);return T_AND;};

"=<" {printf("%s\n",yytext);return T_LE;};
{lexeme}		{	printf("%s\n",yytext);return (int) yytext[0];}

{identifier}		{printf("ident %s\n",yytext); return T_ID;}

{decimal}		{yyval=str_to_num(DECIMAL,yytext);printf("num %lu\n", yyval);return T_NUM;};
{hexadecimal}		{yylval=str_to_num(HEXADECIMAL,yytext);printf("num %lu\n", yyval);return T_NUM}:

{whitespace}+		;
.+			{
				printf("ERROR: unrecognized token: %s\n", yytext);
				exit(1);
			}

%%
/*** C-Code section ***/

int str_to_num(int base, char *str){
	/* remove & from DECIMAL numbers */
	if(base == DECIMAL){
			str++;
	}
	return strtoul(str,NULL,base);
}

