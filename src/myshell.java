import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*TODO
Process threads on &
File IO for < and myshell
 */

public class myshell {
    static List<String> inArgs;
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
        boolean inputFlag=false;
        boolean outputFlag=false;

        //Buffer utilized for handling user input before splitting to individual arguments
        String inBuff;

        Scanner in = new Scanner(System.in);
        while (true){
            System.out.print(env[0]+"> ");
            inBuff=in.nextLine();
            inArgs = Arrays.asList(inBuff.split(" "));

            if (inArgs.contains("&")){
                //Logic for thread handling
            } else if (inArgs.contains("<")){
                int i = inArgs.indexOf("<");
                try(BufferedReader br = new BufferedReader((new FileReader(inArgs.get(i + 1))))){
                    String line=br.readLine();
                    System.out.print(inArgs.get(i+1));
                    String[] lineBuffer = line.split(" ");
                    Collections.addAll(inArgs, lineBuffer);
                } catch (FileNotFoundException e) {
                    System.exit(2);
                } catch (IOException e) {
                    System.exit(3);
                }
                inputFlag=true;
            } else if (inArgs.contains(">")) {
                int i = inArgs.indexOf(">");
                try {
                    File newFile = new File(inArgs.get(i + 1));
                    newFile.delete();
                    newFile.createNewFile();
                } catch (IOException e) {
                    System.exit(4);
                }
                outputFlag=true;
            }
            process(outputFlag, inputFlag, in);
        }
    }

    static void process(boolean output, boolean input, Scanner in){
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
                System.out.println(env[0]);
                break;
            case "environ":
                System.out.println(env[0]+" "+env[1]+" "+env[2]);
                break;
            case "echo":
                for (String s:inArgs) {
                    System.out.print(s+" ");
                }
                System.out.println();
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
