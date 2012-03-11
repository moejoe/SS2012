#include <stdio.h>
#include <stddef.h>

void asmb(unsigned long x[], size_t n);
/* void asmb_callchecking(unsigned long x[], size_t n); */

void asmb_ref(unsigned long x[], size_t n)
{
  unsigned long carry=0;
  unsigned long next_carry;
  long i;
  for (i=n-1; i>=0; i--) {
    next_carry = x[i] <<63;
    x[i] = (x[i] >> 1) | carry;
    carry = next_carry;
  }
}

void pln(unsigned long x[], size_t n)
{
  size_t i;
  printf("0x");
  for (i=n; i>0; i--)
    printf("%016lx",x[i-1]);
}


int test(unsigned long x[], size_t n)
{
  asmb(x,n);
  return 0;
}
  
int main()
{
/*
  unsigned long a[]={~0L,~1L,1L,0L,2L,3L,~2L,
                     0x5555555555555555L,0xAAAAAAAAAAAAAAAAL,
                     0x123456789abcdef,0xfedcba9876543210};
  int f;

  f = test(a+2,0);
  f &= test(a,1);
  f &= test(a+1,1);
  f &= test(a,11);
  f &= test(a+1,9);
  if (!f)
    fprintf(stdout,"\nTest failed.\n");
  else
    fprintf(stdout,"\nTest succeeded.\n");
  return !f;
*/

  unsigned long a[]={0,1};
  test(a,2);
  return 0;
}
