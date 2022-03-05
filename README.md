# SOFE3950U Lab Report 2; Project Shell

Kinjal Shah; 100743551

Raza Naqvi; 100754516

Raphaiel Halim; 100700318

Ivan Bisol; 100701735

### Documentation: Readme

Name: Java to make a shell 

Description: 
Java is a widely used object-oriented programming language where the rules and syntax are based on C and C++ language. 
We decided to use Java since it is a much more flexible language and can be used to create a shell in a more efficient manner. Additionally, we are also more experienced in the implementation of different processes and functions in this language, which allows for the smoother completion of the assignment’s requirements

I/O redirection in Linux:
-Redirection is defined as changing the direction of where commands read input to where commands send output. 
-Meta characters are used to represent redirection, for example “>”, “<” are used for redirection into files or pipe (“|”) for programs

I/O redirection in Java:
-Redirection manipulates streams of bytes not characters. 
-Simple static method calls; setIn(InputStream), setOut(PrintStream), setError(PrintStream)
-Redirecting output is useful for when a large amount of output is created on your screen, scrolling past faster than you can read. 
-Redirecting input is useful for testing particular user-input sequence repeatedly


Program Environment: 
Java runs on Java Runtime Environment (JRE). JRE is a software layer that runs on top of an OS (operating system), providing class libraries and other resources that a specific Java program needs to run. It is a set of components necessary to create and run a Java application. For our “myshell.java” we are tracking user’s working directory, users home directory and running java version.


List of implemented commands:
cd <directory>: allows to change the current directory to the specified <directory>. If the <directory> is not present, it simply prints the current directory. 
clr: Clears the screen of all previous commands
dir <directory>: lists the contents of the specified <directory>
environ: lists the environment strings
echo <comment>: Displays the <comment> followed by a new line
help: displays the user manual for the shell
pause: Pauses the operation of the shell until “Enter” is pressed
quit: Quits the shell
myshell: prints out the full path for the shell executable

Error Codes in myshell.java and what they mean:
-Regular quit command
-File not found for file input
-Error reading file for input 
-Error deleting/creating file for output
-Error writing to file
-Error reading README file
-Error reading myshell input file


How the code works:

The main program is encapsulated with a while true loop, which continuously repeats to constantly move forward. Each time the loop runs, the user’s input, which is taken in as a String, is broken down into an arraylist. This makes it easier for the program to manipulate and search for certain characters or words. The input is then put through an if/else tree, which will check for &, >, or < to respectively run as a thread, process output, or process input. Finally, a switch case is run by the process method based on the argument that was first inputted by the user.

Contribution:
Readme file and documentation - Kinjal Shah, Raza Naqvi
Shell coding, thread implementation, code comments - Ivan Bisol
Attempting thread implementation, readme/documentation, and code comments - Raphaiel Halim

