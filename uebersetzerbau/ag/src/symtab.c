#include "symtab.h"

struct symtab symtab_new(){
	struct symtab tab;
	int i;
	for( i=0; i < SYMTAB_SCOPE_COUNT; i++) {
		tab.scopes[i] = (struct symtab_element*) malloc(sizeof(struct symtab_element) * SYMTAB_SCOPE_STARTSIZE);
		
		assert( tab.scopes[i] != NULL );
	
		tab.scope_maxSize[i] = SYMTAB_SCOPE_STARTSIZE;
		tab.scope_maxSize[i] = 0;
	}
	return tab;
}