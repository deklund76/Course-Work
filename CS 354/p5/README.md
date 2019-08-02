For this assignment, you will be given the structure for a simple shared library that implements
the memory allocation functions malloc() and free(). Everything is present, except for the
definitions of those two functions, called Mem_Alloc() and Mem_Free() in this library.

Write the code to implement Mem_Alloc() and Mem_Free(). Use a best fitalgorithm when
allocating blocks with Mem_Alloc(). When freeing memory, always coalescewith the adjacent
memory blocks if they are free. list_head is the free list structure as defined and described in
mem.c
