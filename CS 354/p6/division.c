/*************************************************
* Author: Drew Eklund (ameklund@wisc.edu)
* Section: 354-002
*************************************************/

#include <stdio.h>
#include <signal.h>

//Global variables
const int MAX_INT = 10;
int counter = 0;

/* Nopes a SIGFPE exception (Exploding Kittens reference) and exits gracefully. */
void n0pe_handler ( int signum ) {
  
  printf("A divide by zero error has occurred.\nNumber of successful divsions: %d\n",  counter);

  exit(0);
}

/* New handler for interrupt signal displays number of successful divisions and exits (gracefully)*/
void new_intr_handler ( int signum ) {
  
  printf("\nNumber of successful divsions: %d\n",  counter);

  exit(0);
}

/* Takes in 2 integer arguments and shows the result and remainder of integer division of the first by the second.*/
int main()
{

  struct sigaction n0pe;
  memset (&n0pe, 0, sizeof(n0pe));

  // Set up struct n0pe
  n0pe.sa_handler = n0pe_handler;
  sigemptyset (&n0pe.sa_mask);
  n0pe.sa_flags = SA_RESTART;

  struct sigaction new_intr;
  memset (&new_intr, 0, sizeof(new_intr));

  // Set up struct new_intr
  new_intr.sa_handler = new_intr_handler;
  sigemptyset (&new_intr.sa_mask);
  new_intr.sa_flags = SA_RESTART;

  sigaction ( SIGFPE, &n0pe, NULL);
  sigaction ( SIGINT, &new_intr, NULL);

  while (1) {

	char str[10];

	printf("Enter first integer: ");
	
	int a = atoi(fgets(str, 10, stdin));

	printf("Enter second integer: ");

	int b = atoi(fgets(str, 10, stdin));

	int c = a / b;

	int r = a % b;

	printf("%d / %d is %d with a remainder of %d\n", a, b, c, r);

	counter++;
  }

  

  return 0;
}
