void asma(unsigned long x[]);
void asma_ref(unsigned long x[]);

#include <stdio.h>
#include <string.h>

void asma_ref(unsigned long x[])
{
  unsigned long carry;
  carry = x[1] << 63;
  x[1] = x[1] >> 1;
  x[0] = (x[0] >> 1)|carry;
}

int test(unsigned long x[])
{
  printf("\nInput Stelle 1 Wert:  %016lx\n",x[0]);
  printf("\nInput Stelle 2 Wert:  %016lx\n",x[1]);
  asma(x);
  printf("\nOutput Stelle 1 Wert: %016lx\n",x[0]);
  printf("\nOutput Stelle 2 Wert: %016lx\n",x[1]);
  return 0;
}
  
int main()
{
  unsigned long a[]={~0L,~1L,0L,1L,2L,~0L};
  int f;

  f = test(a);
  f &= test(a+1);
  f &= test(a+2);
  f &= test(a+3);
  f &= test(a+4);
  return 0;
  if (!f)
    fprintf(stdout,"\nTest failed.\n");
  else
    fprintf(stdout,"\nTest succeeded.\n");
  return !f;
}
