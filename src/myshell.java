import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*TODO
Search for <, >, and &
Process threads on &
File IO for <, >, and myshell
Figure out cd and clr
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

        //Buffer utilized for handling user input before splitting to individual arguments
        String inBuff;

        Scanner in = new Scanner(System.in);
        while (true){
            System.out.print(env[0]+"> ");
            inBuff=in.nextLine();
            inArgs = Arrays.asList(inBuff.split(" "));

            //Switch statement depending on first input argument
            switch (inArgs.get(0)){
                case "cd":
                    System.out.println("Change working directory");
                    break;
                case "clr":
                    System.out.println("Clear screen");
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
}
