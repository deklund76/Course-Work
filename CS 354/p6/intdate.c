/*************************************************
* Author: Drew Eklund (ameklund@wisc.edu)
* Section: 354-002
*************************************************/

#include <stdio.h>
#include <signal.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>

//Global variables
const int SECONDS = 3;
int counter = 0;

/* New handler for alarm signal prints time and sets new alarm. */
void new_alrm_handler ( int signum ) {
  
  time_t curtime = time(NULL);

  printf("current time is %s", ctime(&curtime));

  alarm(SECONDS);
}

/* New handler for interrupt signal increments until Control+c has been pressed 5 times, then exits. */
void new_intr_handler ( int signum ) {
  
  counter++;

  if(counter == 5) {
	printf("\nFinal Control+c caught. Exiting.\n");
	exit(0);
  }

  else {
	printf("\nControl+c caught. %d more before program is ended.\n", 5 - counter);
  }
}

/* Runs an infinite loop printing the time every 3 seconds. Terminates after Control+c is pressed 5 times.*/
int main()
{

  struct sigaction new_alrm;
  memset (&new_alrm, 0, sizeof(new_alrm));

  // Set up struct new_alrm
  new_alrm.sa_handler = new_alrm_handler;
  sigemptyset (&new_alrm.sa_mask);
  new_alrm.sa_flags = SA_RESTART;

  struct sigaction new_intr;
  memset (&new_intr, 0, sizeof(new_intr));

  // Set up struct new_intr
  new_intr.sa_handler = new_intr_handler;
  sigemptyset (&new_intr.sa_mask);
  new_intr.sa_flags = SA_RESTART;

  sigaction ( SIGALRM, &new_alrm, NULL);
  sigaction ( SIGINT, &new_intr, NULL);

  printf("Date will be printed every 3 seconds.\n");
  
  alarm(SECONDS);

  while (1) {
	
  }

  

  return 0;
}
