This is a solution to the 8-puzzle problem (or the 15-puzzle problem), which is a practical part of the online-course on 
algorithms. The program prints out the sequence of board states from the initial to the last one, if it exists; otherwise, it prints the corresponding message. Pass the name of file as a parameter to Solver.class to see what it does. The specified format for the file is:
N - the dimension of a board
Then n lines of n integers go (from 0 to N^2 - 1 without repetition) representing numbered tiles on a board. The blank block's image here is 0.

All files are compiled and run with the following commands:
javac-algs4 <file>
java-algs4 <class> <parameters>

First of all, make sure that you classpathed the algs4.jar package to be able to use some simplified I/O-functions and more.
