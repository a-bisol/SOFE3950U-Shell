import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class myshell {
    //Global variables used for main and process methods
    static int outputIndex=0;
    static ArrayList<String> inArgs;
    static String[] env = new String[3];
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args){
        /* Writes System properties to the env array
        * env[0] represents the current working directory
        * env[1] represents the home directory
        * env[2] represents the version of Java being used
        * */
        env[0]=System.getProperty("user.dir");
        env[1]=System.getProperty("user.home");
        env[2]=System.getProperty("java.version");

        //Buffer utilized for handling user input before splitting to individual arguments
        String inBuff;

        //Flag utilized for handling if user input will be run through a separate thread
        boolean threadFlag = false;

        while (true){
            //Display prompt if a thread hasn't been called recently (prevents thread output from layering on top of main output)
            if (!threadFlag){
                System.out.print(env[0]+"> ");
            }
            inBuff=in.nextLine();
            inArgs = new ArrayList<>(Arrays.asList(inBuff.split(" ")));
            threadFlag = false;

            //Creates a new thread and passes arguments to it
            if (inArgs.contains("&")){
                threadFlag = true;
                inArgs.remove("&");
                Thread t = new Thread(new Shell(inArgs));
                t.start();
            }
            //Reads first line of given file and uses it for command arguments in place of "< filename"
            else if (inArgs.contains("<")){
                int i = inArgs.indexOf("<");
                try(BufferedReader br = new BufferedReader((new FileReader(inArgs.get(i + 1))))){
                    inArgs.remove(i+1);
                    inArgs.remove(i);
                    Collections.addAll(inArgs, br.readLine().split(" "));
                } catch (FileNotFoundException e) {
                    System.exit(2);
                } catch (IOException e) {
                    System.exit(3);
                }
            }
            //Deletes the file if it exists, then creates a new blank file
            else if (inArgs.contains(">")) {
                int i = inArgs.indexOf(">");
                try {
                    File newFile = new File(inArgs.get(i + 1));
                    newFile.delete();
                    newFile.createNewFile();
                } catch (IOException e) {
                    System.exit(4);
                }
                //outputIndex is used to keep track of where the filename is in the arguments for the FileWriter later
                outputIndex=i;
            }
            if (!threadFlag){
                process(inArgs);
            }
        }
    }

    static void process(ArrayList<String> args) {
        switch (args.get(0)){
            case "cd":
                //allows to change current directory to specified one; If no directory is provided, prints current one
                System.setProperty("user.dir", args.get(1));
                env[0]=System.getProperty("user.dir");
                break;
            case "clr":
                //Will only clear screen on terminals that support ANSI Escape code, no output otherwise
                System.out.print("\033[H\033[2J");
                System.out.flush();
                break;
            case "dir":
                String[] pathnames;
                File dir;
                if (args.size()==1){
                    dir = new File(env[0]);
                } else{
                    dir = new File(args.get(1));
                }
                pathnames = dir.list();
                if (outputIndex > 0){
                    try {
                        FileWriter out = new FileWriter(args.get(outputIndex + 1));
                        for (String pathname : pathnames){
                            out.write(pathname);
                        }
                        out.close();
                    } catch (IOException e) {
                        System.exit(5);
                    }
                    outputIndex = 0;
                } else{
                    for (String pathname : pathnames){
                        System.out.println(pathname);
                    }
                }
                break;
            case "environ":
                //prints the user's working directory, home directory, and the running java version
                if (outputIndex > 0){
                    try {
                        FileWriter out = new FileWriter(args.get(outputIndex + 1));
                        out.write(env[0]+" "+env[1]+" "+env[2]);
                        out.close();
                    } catch (IOException e) {
                        System.exit(5);
                    }
                    outputIndex = 0;
                } else{
                    System.out.println(env[0]+" "+env[1]+" "+env[2]);
                }
                break;
            case "echo":
                //Displays the comment entered
                args.remove(0);
                String echoOut = String.join(" ", args);
                if (outputIndex > 0){
                    try {
                        FileWriter out = new FileWriter(args.get(outputIndex));
                        out.write(echoOut);
                        out.close();
                    } catch (IOException e) {
                        System.exit(5);
                    }
                    outputIndex = 0;
                } else{
                    System.out.println(echoOut);
                }
                break;
            case "help":
                //displays the user manual for the shell
                try (BufferedReader br = new BufferedReader(new FileReader("README.md"))) {
                    br.lines().forEach(System.out::println);
                } catch (IOException e) {
                    System.exit(6);
                }
                break;
            case "pause":
                //Pauses shell operations till "Enter" is pressed
                in.nextLine();
                break;
            case "quit":
                //quits the shell
                System.exit(1);
                break;
            case "myshell":
                //Prints out the path for the shell executable
                try (BufferedReader br = new BufferedReader(new FileReader(args.get(1)))){
                    for(String line; (line = br.readLine()) != null; ){
                        ArrayList<String> newArgs = new ArrayList<>(Arrays.asList(line.split(" ")));
                        process(newArgs);
                    }
                } catch (IOException e) {
                    System.exit(7);
                }
                break;
            default:
                System.out.println("Please enter a valid command");
        }
    }
}

/* Exit codes
1 - Regular quit command
2 - File not found for file input
3 - Error reading file for input
4 - Error deleting/creating file for output
5 - Error writing to file
6 - Error reading README file
7 - Error reading myshell input file
 */
