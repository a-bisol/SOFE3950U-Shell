import java.io.*;
import java.util.*;

/*TODO
Process threads on &
File IO for > and myshell
 */

public class myshell {
    static int outputIndex=0;
    static ArrayList<String> inArgs;
    static String[] env = new String[3];
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

        Scanner in = new Scanner(System.in);
        while (true){
            System.out.print(env[0]+"> ");
            inBuff=in.nextLine();
            inArgs = new ArrayList<>(Arrays.asList(inBuff.split(" ")));

            if (inArgs.contains("&")){
                //Logic for thread handling
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
            process(in);
        }
    }

    static void process(Scanner in){
        switch (inArgs.get(0)){
            case "cd":
                System.setProperty("user.dir", inArgs.get(1));
                env[0]=System.getProperty("user.dir");
                break;
            case "clr":
                //Will only clear screen on terminals that support ANSI Escape code, no output otherwise
                System.out.print("\033[H\033[2J");
                System.out.flush();
                break;
            case "dir":
                if (outputIndex > 0){
                    try {
                        FileWriter out = new FileWriter(inArgs.get(outputIndex + 1));
                        out.write(env[0]);
                        out.close();
                    } catch (IOException e) {
                        System.exit(4);
                    }
                    outputIndex = 0;
                } else{
                    System.out.println(env[0]);
                }
                break;
            case "environ":
                if (outputIndex > 0){
                    try {
                        FileWriter out = new FileWriter(inArgs.get(outputIndex + 1));
                        out.write(env[0]+" "+env[1]+" "+env[2]);
                        out.close();
                    } catch (IOException e) {
                        System.exit(4);
                    }
                    outputIndex = 0;
                } else{
                    System.out.println(env[0]+" "+env[1]+" "+env[2]);
                }
                break;
            case "echo":
                inArgs.remove(0);
                String echoOut = String.join(" ", inArgs);
                if (outputIndex > 0){
                    try {
                        FileWriter out = new FileWriter(inArgs.get(outputIndex));
                        out.write(echoOut);
                        out.close();
                    } catch (IOException e) {
                        System.exit(4);
                    }
                    outputIndex = 0;
                } else{
                    System.out.println(echoOut);
                }
                break;
            case "help":
                System.out.println("readme file goes here");
                break;
            case "pause":
                in.nextLine();
                break;
            case "quit":
                System.exit(1);
                break;
            case "myshell":
                System.out.println("Process file in");
                break;
            default:
                System.out.println("Please enter a valid command");
        }
    }
}
