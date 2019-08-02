4. Part 1 ­ running pin (40 points)
ALERT: The understanding about caches developed while working on part 1 would be
necessary to work on part 2. SO, COMPLETE PART 1 BEFORE WORKING ON PART 2
OF THE ASSIGNMENT.
For this part, you will use a program called pin that produces cache performance statistics, given
cache parameters and an executable.
pin runs the executable to internally produce a series of address traces. These are the ordered set
of addresses that a program generates as it runs. They represent the addresses read from or
written to as the program runs. Each address may represent a read for the instruction fetch, or a
read or write to a data variable stored in memory.
The address traces are then used internally by a cache simulator. The cache simulator is a
program that acts as if it is a cache, and for each trace, does a look­up to determine if that address
causes a cache hit or a cache miss. The simulator tallies the hits/misses, and when it has
completed simulation of all the traces, it prints these statistics for you.
To run a simulation, use a command line similar to
$>/p/course/cs354­common/public/cache/pin­t
/p/course/cs354­common/public/cache/source/tools/Memory/obj­ia32/allca
che.so­is16384­ia1­ib64­ds16384­da1­db64­­yourexe
In this command, you would need to replace "yourexe" with the name of your executable file.
This command line is present at the top in file p4questions for you to copy from, in case you
don’t want to type it manually. 6 of these command line arguments specify cache parameters to
pin. The simulator presumes separate I­cache and D­cache. The I­cache holds only the machine
code instructions, as read when doing an instruction fetch. The D­cache holds all other data read
or written while a program runs.
For the I­cache, specify
­ia 1
This causes the set associativity of the cache to be 1, or direct mapped. This is the only value we
will use in this part.
­is N
Substitute a power of 2 for N. This sets the capacity for this cache. For all our simulations in this
part, use a 16KB (16384) size.
­ib N
Substitute a power of 2 for N. N is the number of bytes per block. Use a block size of 64bytes for
I­cache for all simulations.
For the D­cache, specify
­da 1
This causes the set associativity of the cache to be 1, or direct mapped. This is the only value we
will use in this part.
­ds N
Substitute a power of 2 for N. This sets the capacity for this cache. For all our simulations in this
part, use a 16KB (16384) size.
­db N
Substitute a power of 2 for N. N is the number of bytes per block. You will be changing this
parameter in this part of assignment.
As you work your way through this part, you will be answering questions that are in the file
p4questions.This file will be turned in.
Step One: 1­dimensional array code
Write a very small C program called cache1D.c that sets each element of an array of 100,000
integers to the value of its index. The statement that sets a single array element will be something
like
arr[i] = i;
The resulting executable program will be used with pin to generate statistics about cache usage
for your analysis. To make the analysis easier, the C program is required to
● Declare the array as a global variable, so the declaration will be outside of main() (and
prior to main() within the source code file). This requirement will cause the array to be
within the global data segment, and not on the stack.
● Place a forloop inside main() to set each element of the array. One array element is set
during each iteration of the for loop.
Compile your program with
gcc­ocache1Dcache1D.c­Wall­m32­std=gnu99
Step Two: 1­dimensional array code analysis
Run the 3 simulations needed to answer the questions in the p4questionsfile. Then, answer
the questions.
Step Three: 2­dimensional arrays, two ways
Repeat what you did for the 1 dimensional array with two programs that set elements of a
2­dimensional array. Many students will not have used 2­dimensional arrays in C before; the
K&R book, section 5.7­5.9, starting on page 110 will be a good reference for declaring and using
2D arrays.
First, write a C program called cache2Drows.c that sets each element of a 3000 row by 500
column array of integers to the sum of the row index and the column index. The statement that
sets an element will be something like
arr2d[row][col]=row+col;
Use a set of nested forloops, where the inner loop works its way through the elements of
single row of the array, and the outer loop iterates through the rows.
Run the cache analysis requested in the p4questions file.
Second, write another C program called cache2Dcols.c that does the same thing as
cache2Drows.c did, but in a dif erent order. This program has the inner loop work its way
through the elements of a single column of the array, and the outer loop iterates through
the columns. If you truly understand your code, it should be close to trivial to copy
cache2Drows.c and modify it to become cache2Dcols.c.
Last step will be to figure out, understand, and explain why these 2 programs (that accomplish
exactly the same thing) result in different cache performance.
5. Part 2 ­ developing csim (60 points)
5.1 Reference trace files
The cachelab/part2/tracesdirectory contains a collection of reference trace files that we
will use to evaluate the correctness of the cache simulator you write in this part. The trace files
are generated by a Linux program called valgrind. For example, typing
$>valgrind­­log­fd=1­­tool=lackey­v­­trace­mem=yesls­l
on the command line runs the executable program “ls ­l”, captures a trace of each of its
memory accesses in the order they occur, and prints them on stdout. Valgrind memory traces
have the following form:
I0400d7d4,8
M0421c7f0,4
L04f6b868,8
S7ff0005c8,8
Each line denotes one or two memory accesses. The format of each line is
[space]operation[space]address,size
The operation field denotes the type of memory access: “I” denotes an instruction load, “L” a
data load, “S” a data store, and “M” a data modify (i.e., a data load followed by a data store).
You should consider only memory accesses to data (L/S/M) and ignore instruction fetch (I) while
working on your simulator. The address field specifies a 64­bit hexadecimal memory address.
The size field specifies the number of bytes accessed by the operation. In this course we have
dealt with 32­bit computers. Only for part 2 of this assignment, we use 64­bit computer traces ­
all the addresses for accessing memory are 64­bit addresses.
NOTE: You will NOT have to generate any traces on your own. You need to work only with the
traces provided to you.
5.2 Writing the cache simulator csim
You will write a cache simulator in csim.c that takes a valgrind memory trace as input, simulates
the hit/miss/eviction behavior of a cache memory on this trace, and outputs the total number of
hits, misses and evictions.
We have provided you with the binary executable of a reference cache simulator, called
csim­ref, that simulates the behavior of a cache with arbitrary size and associativity on a
valgrind trace file. It uses the LRU (least­recently used) replacement policy when choosing
which cache line to evict. You can use csim­ref to compare your implementation.
The reference simulator takes the following command­line arguments:
Usage:./csim­ref[­hv]­s<s>­E<E>­b<b>­t<tracefile>
­h:Optional help flag that prints usage info
­v:Optional verbose flag that displays trace info
­s<s>:Number of set index bits (S=2^sis the number of sets)
­E<E>:Associativity (number of cache lines per set)
­b<b>:Number of block bits (B=2^bis the block size)
­t<tracefile>:Name of the valgrind trace to replay
The command­line arguments are based on the notation (s, E, and b) from pages 597 and 598 of
the CS:APP2e textbook.
For example, enter the following command:
$>./csim­ref­s4­E1­b4­ttraces/yi.trace
hits:4misses:5evictions:3
The same example in verbosemode:
$>./csim­ref­s4­E1­b4­ttraces/yi.trace­v
M20,1misshit
L22,1hit
S18,1hit
L110,1misseviction
L210,1misseviction
M12,1missevictionhit
hits:4misses:5evictions:3
You can use this verbose output from csim­ref while debugging your code (csim.c). The
“hit”/”miss”/”eviction” in the verbose output above indicates if that particular memory access
(L/S/M) specified by the trace file led to hit/miss/eviction in cache.
We have provided you with skeleton code in file csim.c. Your job is to complete the
implementation so that it outputs the correct number of hits, misses and evictions. You NEED
NOT support the verbose output (using the ­v option) as mentioned above.
Once you have made changes to file csim.c and want to test your implementation, do the
following:
$>makeclean
$>make
$>./csim­s4­E1­b4­ttraces/yi.trace
hits:4misses:5evictions:3
The SAMPLE OUTPUTshould look like as shown below:
hits:4misses:5evictions:3
NOTE: The number of hits, misses and evictions will vary with different cache configurations
and different traces. However, the output should be single line similar to what is shown above.
YOU SHOULD NOT PRINT ANYTHING EXTRA IN THE OUTPUT.
Your simulator must work correctly for arbitrary values of s,E,andb.
Some important points regarding the implementation in csim.c:
You should carefully read the skeleton code in csim.c. Starting from main() function, the flow is
described below in brief:
● Parse the command line arguments. This is already done for you.
● void initCache() ­ This function should allocate the data structures to hold
information about the sets and cache lines using malloc depending on the values of
parameters S (S = 2^s) and E. You need to complete this function.
● void replayTrace(char* trace_fn) This function parses the input trace file.
This part is already done for you. It should call the accessData()function. You need
to complete the missing code in this function.
● voidaccessData(mem_addr_taddr)This function is the core of implementation
which should use the data structures that were allocated in initCache() function to
model the cache hits, misses and evictions. You need to complete this function. The most
crucial thing is to update the global variables hit_count, miss_count,
eviction_count inside this function appropriately. You should implement
Least­Recently­Used (LRU) cache replacement policy.
● void freeCache() This function should free up any memory you allocated using
malloc()in initCache() function. This is crucial to avoid memory leaks in the
code. You need to complete this function.
● printSummary(hit_count, miss_count, eviction_count) This function
prints the statistics in the desired format. This is already implemented for you.
You MUST READ COMMENTS in the file csim.cwhich provide additional details.
