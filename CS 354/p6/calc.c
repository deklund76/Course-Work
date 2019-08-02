#include "calc.h"

int num_err = 0;
static int num_calc = 0;

int power(int base, int exp) {
	num_calc++;
	int res = 1;
	if(base < 0) {
		num_err++;
		return POWER_ERR;
	}
	for (int i = 0; i < exp; i++) {
		res *= base;
	}
	return res;
}

