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
	SYMTAB_GLOBAL_SCOPE=0			/* (not used) - Symbols are visible from the Start to the End of the Program */
	, SYMTAB_FUNCTION_SCOPE			/* Symbols are visible from the start to the end of a function */
	, SYMTAB_BLOCK_SCOPE			/* Symbols are visible within a Codeblock (e.g. Stats) */
};

enum symtab_types {
	SYMTAB_FUNCTION_TYPE=0			/* (not used) - references a function  */
	, SYMTAB_VARIABLE_TYPE			/* references a variable */
	, SYMTAB_LABEL_TYPE			/* references a label */
};

struct symtab
{
	struct symtab_element *scopes[SYMTAB_SCOPE_COUNT];
	unsigned scope_maxSize[SYMTAB_SCOPE_COUNT];
	unsigned scope_curSize[SYMTAB_SCOPE_COUNT];
	
};

struct symtab_element
{
	char *name;
	enum symtab_types type;
};

struct symtab symtab_new();
// struct symtab symtab_clone( struct *symtab );
// struct symtab	symtab_grow_scope( struct *symtab, enum symtab_scopes,  int newSize ); 

int symtab_define_symbol(struct symtab*, char*, enum symtab_types, enum symtab_scopes);


int _symtab_append_to_scope(struct symtab*, enum symtab_scopes, struct symtab_element*);
int _symtab_grow_scope(struct symtab*, enum symtab_scopes, unsigned);

struct symtab_element *symtab_get_symbol(struct symtab, char*);

#endif

