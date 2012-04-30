
#include "symtab.h"

#define LOGINFO(msg)	printf("%s",msg)

int main(void){
	
	/* test creation of new symtable */
	
	struct symtab st = symtab_new();
	
	assert( st != NULL);
	assert( st->scopes[SYMTAB_GLOBAL_SCOPE] != NULL);
	assert( st->scopes[SYMTAB_FUNCTION_SCOPE] != NULL);
	assert( st->scopes[SYMTAB_BLOCK_SCOPE] != NULL);
	assert( st->scope_maxSize[SYMTAB_GLOBAL_SCOPE] == SYMTAB_GROW_SCOPE_SIZE );
	assert( st->scope_maxSize[SYMTAB_FUNCTION_SCOPE] == SYMTAB_GROW_SCOPE_SIZE );
	assert( st->scope_maxSize[SYMTAB_BLOCK_SCOPE] == SYMTAB_GROW_SCOPE_SIZE );
	
	LOGINFO("Symbol Table initialized");
	exit(0);	
}


