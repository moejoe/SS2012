#include "symtab.h"

#define LOGINFO(msg)	printf("%s\n",msg)

int main(void){
	
	/* test creation of new symtable */
	
	struct symtab st = symtab_new();
	
	assert( (struct symtab_element *)st.scopes[SYMTAB_GLOBAL_SCOPE] == NULL);
	assert( st.scopes[SYMTAB_FUNCTION_SCOPE] == NULL);
	assert( st.scopes[SYMTAB_BLOCK_SCOPE] == NULL);
	assert( st.scope_maxSize[SYMTAB_GLOBAL_SCOPE] == 0 );
	assert( st.scope_maxSize[SYMTAB_FUNCTION_SCOPE] == 0 );
	assert( st.scope_maxSize[SYMTAB_BLOCK_SCOPE] == 0 );
	
	LOGINFO("Symbol Table initialized");


	/* test adding symbols */
	assert( symtab_define_symbol( &st, "gvar1", SYMTAB_VARIABLE_TYPE, SYMTAB_GLOBAL_SCOPE ) == 0 );
	assert( symtab_define_symbol( &st, "fvar1", SYMTAB_VARIABLE_TYPE, SYMTAB_FUNCTION_SCOPE ) == 0 );
	assert( symtab_define_symbol( &st, "bvar1", SYMTAB_VARIABLE_TYPE, SYMTAB_BLOCK_SCOPE ) == 0 );
	assert( symtab_define_symbol( &st, "glabel1", SYMTAB_LABEL_TYPE, SYMTAB_GLOBAL_SCOPE ) == 0 );

	assert( strcmp( st.scopes[SYMTAB_GLOBAL_SCOPE][0].name, "gvar1") == 0 ); 
	assert(  st.scopes[SYMTAB_GLOBAL_SCOPE][0].type == SYMTAB_VARIABLE_TYPE );

	assert( strcmp( st.scopes[SYMTAB_GLOBAL_SCOPE][1].name, "glabel1") == 0 );
	assert(  st.scopes[SYMTAB_GLOBAL_SCOPE][1].type == SYMTAB_LABEL_TYPE );

	assert( st.scope_curSize[SYMTAB_GLOBAL_SCOPE] == 2 );
	assert( st.scope_maxSize[SYMTAB_GLOBAL_SCOPE] == SYMTAB_SCOPE_STARTSIZE );

	LOGINFO("Global Scope writeable");

        assert( strcmp( st.scopes[SYMTAB_FUNCTION_SCOPE][0].name, "fvar1") == 0 );
        assert( st.scopes[SYMTAB_FUNCTION_SCOPE][0].type == SYMTAB_VARIABLE_TYPE );
        LOGINFO("Function Scope writeable");

        assert( strcmp( st.scopes[SYMTAB_BLOCK_SCOPE][0].name, "bvar1") == 0 );
        assert( st.scopes[SYMTAB_BLOCK_SCOPE][0].type == SYMTAB_VARIABLE_TYPE );
        LOGINFO("Block Scope writeable");
		


	/* test finding symbols */
	struct symtab_element *gvar1 = symtab_get_symbol(st, "gvar1");
 	assert( gvar1 != NULL );	
	assert( strcmp(gvar1->name, "gvar1" ) == 0 );
	assert( gvar1->type == SYMTAB_VARIABLE_TYPE );

        struct symtab_element *fvar1 = symtab_get_symbol(st, "fvar1");
	assert( fvar1 != NULL );
        assert( strcmp(fvar1->name, "fvar1" ) == 0 );
        assert( fvar1->type == SYMTAB_VARIABLE_TYPE );

        struct symtab_element *bvar1 = symtab_get_symbol(st, "bvar1");
	assert( bvar1 != NULL );
        assert( strcmp(bvar1->name, "bvar1" ) == 0 );
        assert( bvar1->type == SYMTAB_VARIABLE_TYPE );

        struct symtab_element *glabel1 = symtab_get_symbol(st, "glabel1");
        assert( glabel1 != NULL );
	assert( strcmp(glabel1->name, "glabel1" ) == 0 );
        assert( glabel1->type == SYMTAB_LABEL_TYPE );
	
	LOGINFO("Found symbols by name");

	assert( symtab_get_symbol(st, "DoesNotExist") == NULL );
	
	LOGINFO("Non existing Symbols return NULL for symtab_get_symbol");

	/* Test Adding existing symbols */
	
	exit(0);	
}


