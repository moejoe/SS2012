#include "symtab.h"

struct symtab symtab_new(){
	struct symtab tab;
	int i;
	for( i=0; i < SYMTAB_SCOPE_COUNT; i++) {
//		tab.scopes[i] = (struct symtab_element*)malloc(sizeof(struct symtab_element)*SYMTAB_SCOPE_STARTSIZE);
//		assert( tab.scopes[i] != NULL);
		tab.scopes[i] = NULL;
//		tab.scope_maxSize[i] = SYMTAB_SCOPE_STARTSIZE;
		tab.scope_maxSize[i] = 0;
		tab.scope_curSize[i] = 0;
	}
	return tab;
}
int symtab_define_symbol(struct symtab *tab, char *name, enum symtab_types type, enum symtab_scopes scope ){
	assert( tab != NULL);
	assert( name != NULL);
	assert( scope < SYMTAB_SCOPE_COUNT);
	
	/* see if we must grow the scope table */
	
	struct symtab_element new_symbol;
	new_symbol.name = strdup(name);
	new_symbol.type = type;
	
	assert(_symtab_append_to_scope(tab, scope, &new_symbol) == 0);

	return 0;
}

int  _symtab_append_to_scope(struct symtab *tab, enum symtab_scopes scope, struct symtab_element *element){
        assert( tab != NULL );
        assert( element !=NULL );
	assert( scope < SYMTAB_SCOPE_COUNT );
        if( tab->scope_maxSize[scope] == tab->scope_curSize[scope] ){
		int newSize = tab->scope_maxSize[scope] == 0 ? SYMTAB_SCOPE_STARTSIZE : SYMTAB_GROW_SCOPE_SIZE( tab->scope_maxSize[scope] );
                _symtab_grow_scope(tab, scope, newSize );
        }
	assert( tab->scopes[scope] != NULL );	

	tab->scopes[scope][tab->scope_curSize[scope]++] = *element;
	return 0;
}

int _symtab_grow_scope(struct symtab *tab, enum symtab_scopes scope, unsigned newSize) {
	assert( tab != NULL);
	assert( newSize > 0 );

	struct symtab *old_scope = tab->scopes[scope];

	struct symtab *new_scope = (struct symtab_element*)malloc(sizeof(struct symtab_element)*newSize);
	assert(new_scope != NULL);

	if( tab->scope_curSize[scope] > 0  ){
		assert( old_scope != NULL);
		int i;
		for( i = 0; i <  tab->scope_curSize[scope]; i++ ){
			new_scope[i] = old_scope[i];
		}
		free(old_scope);
	}
	tab->scopes[scope] = new_scope;
	tab->scope_maxSize[scope] = newSize;
	return 0;
}

struct symtab_element *symtab_get_symbol(struct symtab tab, char* name){
	assert( name != NULL);
	
	int i,j;
	for( i = 0; i < SYMTAB_SCOPE_COUNT; i++){
		for( j = 0; j < tab.scope_curSize[i]; j++){
			if( strcmp(tab.scopes[i][j].name, name) == 0 ) 
				return &tab.scopes[i][j];
		}
	}
	return NULL;
}

