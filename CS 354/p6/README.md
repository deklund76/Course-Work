3. Exception Handling
This section has 2 parts, you’ll be writing a program for each part. These programs use specific
software interrupts, requiring you to set up the capture of the interrupts to be handled, and also
provide the handling code. You’ll be submitting two C files (intdate.cand division.c)
and a completed p6questionsfile for this section.
3.1 First Program: A Periodic Alarm
3.1.1 Periodic Alarm: Step 1
Write a C program called intdate.cthat is composed of two parts. One part is the main
function, which does absolutely nothing in an infinite loop. Before entering the infinite loop, the
main function will set up what is to happen when an alarm goes off 3 seconds later. When the
alarm goes off, this causes a SIGALRMinterrupt to signal the program. This interrupt is to be
caught by the program, and handled by the second part of the program, an interrupt handler. This
handler function will print out the current time, in the same format as the Unix date command,
re­arm the alarm to go off three seconds later, and then return back to the main function (which
continues its infinite loop doing nothing).
Since the main function is to keep running forever, the main()function will contain an infinite
loop such as
while(1){
}
Before executing the infinite loop, the main()function needs to set up the alarm. Once it starts
executing the infinite loop it does nothing useful. For this partially complete program, output will
look something like
%./intdate
Datewillbeprintedevery3seconds.
Enter^Ctoendtheprogram:
currenttimeisTueMar 413:15:212014
currenttimeisTueMar 413:15:242014
currenttimeisTueMar 413:15:272014
^C
Notice that to stop the program from running, you type in a Control+c. Remember for the next
part of this assignment: typing Control+c sends a software interrupt (called SIGINT) to the
running program.
You will use library functions to help you. It is important to check the return values of these
functions, such that your program detects error conditions and acts in response to those errors.
Refer the man pages of following functions that you will use:
● time()and ctime()are library calls to help your handler obtain and print the time in
the correct format.
● alarm()activates the SIGALRMinterrupt to occur in a specified number of seconds.
● sigaction()( DO NOT use signal()) sets up what happens when the specific type
of interrupt (specified as the first parameter) causes a software interrupt. You are
particularly interested in setting the sa_handler field of the structure that
sigaction()needs; it specifies the handler function to run upon an interrupt.
NOTE: Initialize the sigaction struct via memset to be clear before you use it.
structsigactionact;
memset(&act,0,sizeof(act));
3.1.2 Periodic Alarm: Step 2
Once the periodic printing of the time is working, make an addition(s) to the program so that it
does something different other than exiting after the first time a Control+c is typed. This
extension to program only exits after the user types Control+c 5 times. Set up a SIGINThandler
(using sigaction()to set up the call back function). The SIGINThandler either prints how
many more times Control+c must be typed before exiting, or it prints that it caught the 5th one
and it calls exit().
Output of the program looked like this on Feb 26:
Datewillbeprintedevery3seconds.
Enter^C5timestoendtheprogram:
^C
Control+ccaught.4morebeforeprogramisended.
currenttimeisThuFeb2615:15:512015
^C
Control+ccaught.3morebeforeprogramisended.
^C
Control+ccaught.2morebeforeprogramisended.
^C
Control+ccaught.1morebeforeprogramisended.
currenttimeisThuFeb2615:15:542015
^C
FinalControl+ccaught.Exiting.
The alarm interrupt handler will need to re­arm the alarm each time it is called. Since both
main() and the alarm handler need to know/use the number of seconds in order to arm the
alarm, make this value a global variable. Interrupt handlers are not invoked by another function
within the program, so they cannot receive parameters from another function in the program.
You will also need a global variable to keep track of the number of times a Control+c has been
typed. It is only used by the SIGINThandler, but it needs to exist (single instantiation) the entire
time that the program runs.
3.1.3 Periodic Alarm: Step 3
1.Once you are through with this program, copy /p/course/cs354­common/public/bin/demo.c to
your current working folder. Compile the program using the following option:
gccdemo.c­odemo­Wall­m32­std=gnu99
2. Run the program using:
./demo
3. Observe the result. It should be something like this:
Enter^Ctoendtheprogram:
4. Now Press "Control+c" to quit the program. Open the file demo.c and go through the code.
Notice the printf statement after the infinite loop.
printf("Thisshouldnotbeprinted\n");
Because the program is stuck in an infinite loop, the above printf statement is not executed. That
is, the program does not go forward.
6. Now copy the file /p/course/cs354­common/public/bin/p6questionsto your folder
and answer the question in the file.
7. Once you are done answering the question, copy this file (i.e. p6questions) along with
intdate.cin your handin folder for submission of the first part of this assignment.
VERY IMPORTANT:Use the following command to compile your code:
%gcc­ointdateintdate.c­Wall­m32­std=gnu99
NOTE: Even if you have a personal computer with a C compiler, you will not be able to work
on your own computer, as the setup and handling of the variety of interrupts is different on
different platforms. Work on the CSL machines to do this assignment!
3.2 Divide by zero exception handling
Write a simple program that loops (forever) to:
● prompt for and read in one integer value (followed by the newline)
● prompt for and read in a second integer value (followed by the newline)
● calculate the quotient and remainder of doing the integer division operation: int1 / int2,
printing these results, and keeping track of how many division operations were
successfully completed.
A Control+c will cause this program to stop running.
Use fgets()to read each line of input. Then, use atoi()to translate that C string to an
integer.
Users tend to type in bad inputs occasionally. For ease of programming, mostly ignore error
checking on the input. If the user enters in a bad integer value, don't check for a bad integer
value, and don't worry about it. Just use whatever value atoi()returns. If you still don't know
what this value is, look it up in the atoi()man page!
The count of the number of completed divisions needs to be a global variable, as it will be
needed by the second program enhancement (described below).
Call the source code for this program division.c.
A sample run of the program might appear as
Enterfirstinteger:12
Entersecondinteger:2
12/2is6witharemainderof0
Enterfirstinteger:100
Entersecondinteger:­7
100/­7is­14witharemainderof2
Enterfirstinteger:10
Entersecondinteger:20
10/20is0witharemainderof10
Enterfirstinteger:ab17
Entersecondinteger:3
0/3is0witharemainderof0
Enterfirstinteger:^C
Please note the behavior of the program for a non­numeric input ‘ab17’.Handle similar inputs
in the same way.
Once this program is working, enhance it in two ways.
Try your program on a divide by 0 operation. What happens?
The hardware traps when this unrecoverable arithmetic error occurs, and the program crashes,
because it did not (catch and) handle the SIGFPEsignal.
To make this situation a little bit better, set up a handler that will be called if the program
receives the SIGFPEsignal. In the signal handler you write, print a message stating that a divide
by 0 operation was attempted, print the number of successfully completed division operations,
and then exit the program (gracefully, instead of crashing). Below is a sample output of how
such a program will behave.
Enterfirstinteger:1
Entersecondinteger:0
Adividebyzeroerrorhasoccurred.
Numberofsuccessfuldivisions:0
You needed to interrupt this program to cause it to stop running. The second enhancement to this
program captures the Control+c just like the intdate program did, but on the first Control+c
interrupt, the handler prints the number of successfully completed division operations, and then
exits the program (gracefully).
Set up and add a handler for the SIGINTsignal. The handler is to print a little message stating
the number of completed operations, and then exit the program (gracefully).
Here is the sample output for division.c that shows the graceful exit of the program in case of a
SIGINT interrupt.
Enterfirstinteger:1
Entersecondinteger:2
1/2is0witharemainderof1
Enterfirstinteger:3
Entersecondinteger:4
3/4is0witharemainderof3
Enterfirstinteger:^C
Numberofsuccessfuldivisions:2
You’ll be turning in division.cfor this part of the assignment.
