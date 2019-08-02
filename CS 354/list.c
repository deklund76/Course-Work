//Drew Eklund, CS354-2

#include <stdio.h>
#include <stdlib.h>

#define MAX_INTS 1000

/* A node in the linked list */
struct node {
    int data;
    struct node *next;
};

//functions

struct node* create_list(int intarray[], int len);

struct node* add_item_at_start(struct node *head, int data);

int search_array(int integers[], int numints, int element);

int search_list(struct node *head, int element);

struct node* create_sorted_list(struct node *head);

struct node* add_item_sorted(struct node *head, int data);

int copy_list_to_array(struct node *head, int *array);

void print_list(struct node *head);

void print_array(int integers[], int len);


int main(int argc, char *argv[])
{

    //Check args
    if(argc != 2) {
	printf("Usage: ./list <input_file>\n");
	exit(EXIT_FAILURE);
    }

    //Variables for file input
    FILE *input;
    char buff[40];
    int first_array[MAX_INTS];
    //Number of integers read from the file
    int num_ints;
    num_ints = 0;
    //Pointer to the head of the first linked list
    struct node *first_list;

    /* Open a file for reading */
    input = fopen(argv[1], "r" );
    
    // Check input file
    if(input == NULL) {
	printf("ERROR: Cannot open file for reading!\n");
	exit(EXIT_FAILURE);
    }
    
    /* Read the numbers from the file, into an array */
    while(fgets(buff, 40, (FILE*)input) != NULL) {
	first_array[num_ints] = atoi(buff);
	 num_ints++;
    }


    fclose(input);

    /* Print the array */
    printf("\nARRAY: ");
    print_array(first_array, num_ints);
    printf("\n");

    /* Create a linked list with the integers from the array */
    first_list = create_list(first_array, num_ints);
    
    /* Print the linked list */
    printf("LINKED LIST: ");
    print_list(first_list);


    /* Repeatedly prompt the user for a number to be searched.
    *  Search the array for the number and print out the result as shown in the specs.
    *  Search the linked list for the number and print out the result as shown in the specs.
    *  Stop prompting when the user enters 'q' (just the character q without the single quotes).
    */

    while(buff[0] != 'q') {
	printf("\nEnter an element to be searched in the list and array:");
	
	int element, list_index, array_index;

	fgets(buff, 11, stdin);

	element = atoi(buff);

	array_index = search_array(first_array, num_ints, element);

	if(array_index == -1 && buff[0] != 'q') {
	   printf("\nElement %d not found in the array\n", element);
	}

	else if(buff[0] != 'q') {
	   printf("\nElement %d found in the array at index %d\n", element, array_index);
	}

	list_index = search_list(first_list, element);

	if(list_index == -1 && buff[0] != 'q') {
	   printf("\nElement %d not found in the linked list\n", element);
	}

	else if(buff[0] != 'q') {
	   printf("\nElement %d found in the list at position %d\n", element, list_index);
	}
    }


    /* Create a sorted list(in ascending order) from the unsorted list */
    struct node *sorted_list;
    int sorted_array[MAX_INTS];

    sorted_list = create_sorted_list(first_list);

    /* Print the sorted list */
    printf("\nSORTED LINKED LIST: ");
    print_list(sorted_list);
    printf("\n");

    /* Copy the sorted list to an array with the same sorted order */
    copy_list_to_array(sorted_list, sorted_array);

    /* Print out the sorted array */
    printf("SORTED ARRAY: ");
    print_array(sorted_array, num_ints);
    printf("\n");    

    /* Print the original linked list again */
    printf("ORIGINAL LINKED LIST: ");
    print_list(first_list);
    printf("\n");

    /* Print the original array again */
    printf("ORIGINAL ARRAY: ");
    print_array(first_array, num_ints);
    printf("\n");


    /* Open a file for writing */
    FILE *output;
    output = fopen("sorted_numbers.txt", "w");

    /* Write the sorted array to a file named "sorted_numbers.txt" */
    printf("Writing integers from a sorted array to a file....\n\n");

    int num_printed = 0;
    for(num_printed = 0; num_printed < num_ints; num_printed++) {
	fprintf(output, "%d\n", sorted_array[num_printed]);
    }

    /* Print out the number of integers written to the file */
    printf("Number of integers written to the file = %d\n", num_printed);

    return 0;
}

/* This function takes in an integer array and its size, traverses the array while using the function additem_at_start*struct node *head, int data) to build a list. This function returns the head of the new linked list that was created.*/
struct node* create_list(int intarray[], int len)
{
    struct node *head = NULL;
    int k;
    for(k = 0; k < len; k++) {
	head = add_item_at_start(head, intarray[k]);
    }
    return head;
}

/* This function takes in a pointer to the head of the linked list, adds a new code with the data at the beginning of the list, updates and returns the new head.*/
struct node* add_item_at_start(struct node *head, int data)
{
    struct node *new_node;
    new_node = malloc(sizeof(struct node));
    new_node->data = data;
    new_node->next = head;
    head = new_node;

    return head;
}

/* Searches for the given element in a linked list and returns its position if found, otherwise returns -1*/
int search_list(struct node *head, int element)
{
    struct node *tmp = head;
    int index = 1;

    while(tmp != NULL) {
	if(tmp->data == element) {
	    return index;
	}
        tmp = tmp->next;
	index++;
    }
    return -1;
}

/* This function returns the index of the element if the element is found in the array, otherwise it returns -1*/
int search_array(int integers[], int numints, int element)
{
    int k;
    for(k = 0; k < numints; k++) {
	if(integers[k] == element) {;
	    return k;
	}
    }
    return -1;
}

/* Takes in a pointer to the head to a list and apointer to the start of an array, and returns the number of integers copied to the array.*/
int copy_list_to_array(struct node *head, int *array)
{
    int num_copied;
    num_copied = 0;
    struct node *tmp;
    tmp = head;

    int k;
    for(k = 0; tmp != NULL; k++) {
	array[k] = tmp->data;
	tmp = tmp->next;
	num_copied++;
    }
    return num_copied;
}

/* Takes in as input the head of another list, constructs a new sorted list(ascending order), and returns the head of the sorted list.*/
struct node* create_sorted_list(struct node *head)
{
    struct node *sorted_head = NULL;
    struct node *tmp = head;
    
    while(tmp != NULL) {
	sorted_head = add_item_sorted(sorted_head, tmp->data);
	tmp = tmp->next;
    }
    return sorted_head;
}

/*Adds a node to a linked list in sorted order(ascending), returns the head of the list being added to*/
struct node* add_item_sorted(struct node *sorted_head, int data)
{
    struct node *new_node;
    new_node = malloc(sizeof(struct node));
    new_node->data = data;

    if(sorted_head == NULL || new_node->data < sorted_head->data) {
	new_node->next = sorted_head;
	sorted_head = new_node;
    }
    else {
	struct node *tmp;
	tmp = sorted_head;
	while(tmp != NULL) {
		if(tmp->next == NULL || new_node->data < tmp->next->data) {
			new_node->next = tmp->next;
			tmp->next = new_node;
			break;
		}
		tmp = tmp->next;
	}
    }
    return sorted_head;
}

/*Prints the all elements of a given list*/
void print_list(struct node *head)
{
    if (head == NULL) {
        printf("Linked List is Empty.\n");
    } else {
        struct node *temp = head;
        printf("head->");
        while (temp != NULL) {
            printf("|%d|->", temp->data);
            temp = temp->next;
        }
        printf("NULL\n");
    }
}

/*Prints all elements of a given array*/
void print_array(int integers[], int len)
{
    int i;
    for (i = 0; i < len; i++) {
        printf("| %d ", integers[i]);
    }
    printf("|\n");
}
