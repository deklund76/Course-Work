/*************************************************
* Author: Drew Eklund (ameklund@wisc.edu)
* Section: 354-002
*************************************************/

#include <stdio.h>
#include <signal.h>
#include <string.h>

//Global timer variable
int seconds = 3;

void mysa_handler (int SIGALRM) {
  time_t curtime;

  time(&curtime);

  printf("current time is %s", ctime(&curtime));

  alarm(seconds);
}

int main()
{

  struct sigaction act;
  memset (&act, 0, sizeof(act));

  // Set up struct act
  act.sa_handler = mysa_handler;
  sigemptyset (&act.sa_mask);
  act.sa_flags = SA_RESTART;

  sigaction ( SIGALRM, act, NULL);

  printf("Date will be printed every 3 seconds.");
  
  alarm(seconds);

  while (1) {
	
  }

  

  return 0;
}
