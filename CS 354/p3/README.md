In this assignment, you will be defusing four binary bombs. The idea is simple: each bomb is just an
executable program, which expects five inputs from you, the bomb defusing expert. If you type in the
right values, you successfully defuse the bomb. If not, the bomb explodes! (Don't worry, it just prints
that the bomb explodes; no real harm is done to you or your computer.)
The four bombs are located in your assignment 3 directory
(/p/course/cs354­common/public/spring16.handin/login/p3, substituting login with your own CS login),
named b1 through b4. Your task is to create four files, b1.solutionthrough b4.solution. Each file
contains the five lines of input demanded by its associated bomb. For example, if the solution to b1 was
1, 2, 3, 4, and 5, you would create a file called b1.solution with the following entries:
1
2
3
4
5
You can create this file with a text editor (vim/gedit/nano) on a Linux Machine. If you are using
Windows or Mac, editing locally followed by uploading will fail for b2. Use remote accessing tools like
ssh or putty instead. Make sure your solution file contains five non­empty lines. Remember to press
enter or return after the last line. The bomb will be trapped into an infinite loop if the solution file
contains less than 5 lines. If that happens, press Ctrl­C to break.
The challenge is to figure out the inputs expected by each of the four bombs. To do this, use two tools:
gdband objdump. Both are incredibly useful for this type of reverse engineering work.
Please copy your executable bombs to your own private directory, and work towards finding your
solutions in your own directory. That way, you will have the original executables in your handin
directory if you accidentally overwrite an executable.
To test whether you have figured out a bomb's defusing code correctly, run the bomb with its input file
./b1<b1.solution
This is how we will grade your assignment. It will print the following success message if you have
figured everything out; otherwise it will tell you the bomb exploded. You can also run each bomb
interactively, and type in your guesses, one at a time. This will be useful in defusing each bomb with the
debugger, as described below.
guess 1 (of 5)? guess 2 (of 5)? guess 3 (of 5)? guess 4 (of 5)? guess 5 (of 5)? success!
