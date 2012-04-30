#ifndef moe_SYMTAB_H
#define moe_SYMTAB_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#define SYMTAB_SCOPE_STARTSIZE	1024

#define SYMTAB_GROW_SCOPE_SIZE(curSize)	curSize*2

#define SYMTAB_SCOPE_COUNT	3	/* Number of scopes */

enum symtab_scopes {
	ST_GLOBAL_SCOPE=0			/* (not used) - Symbols are visible from the Start to the End of the Program */
	, ST_FUNCTION_SCOPE			/* Symbols are visible from the start to the end of a function */
	, ST_BLOCK_SCOPE			/* Symbols are visible within a Codeblock (e.g. Stats) */
};

enum symtab_types {
	ST_FUNCTION_TYPE=0			/* (not used) - references a function  */
	ST_VARIABLE_TYPE			/* references a variable */
	, ST_LABEL_TPYE			/* references a label */
};

struct symtab
{
	struct st_symtab_element *scopes[SYMTAB_SCOPE_COUNT];
	unsigned scope_maxSize[SYMTAB_SCOPE_COUNT];
	unsigned scope_curSize[SYMTAB_SCOPE_COUNT];
	
};

struct symtab_element
{
	char *name;
	enum symtab_types type;
}

struct symtab symtab_new();
//struct symtab symtab_clone( struct *symtab );
//struct symtab	symtab_grow_scope( struct *symtab, enum symtab_scopes,  int newSize ); 

//int symtab symtab_define_symbol(char *name, enum symtab_types, enum symtab_scopes );
//int symtab symtab_get_symbol	
#endif

