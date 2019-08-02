#include "calc.h"
#include "stdio.h"

extend int num_calc;

int main() {
	printf("num_calc: %d\n", num_calc);
	int res = power(4, -2);
	if (res == POWER_ERR) {
		return -1;
	}
	return 0;
}
