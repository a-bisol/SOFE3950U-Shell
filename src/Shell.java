import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//Used for background execution

public class Shell implements Runnable{

    private final ArrayList<String> args;

    public Shell(ArrayList<String> inArgs) {
        this.args=inArgs;
    }

    public void run() {
        switch (args.get(0)){
            case "cd":
                System.setProperty("user.dir", args.get(1));
                myshell.env[0]=System.getProperty("user.dir");
                break;
            case "clr":
                //Will only clear screen on terminals that support ANSI Escape code, no output otherwise
                System.out.print("\033[H\033[2J");
                System.out.flush();
                break;
            case "dir":
                if (myshell.outputIndex > 0){
                    try {
                        FileWriter out = new FileWriter(args.get(myshell.outputIndex + 1));
                        out.write(myshell.env[0]);
                        out.close();
                    } catch (IOException e) {
                        System.exit(5);
                    }
                    myshell.outputIndex = 0;
                } else{
                    System.out.println(myshell.env[0]);
                }
                break;
            case "environ":
                if (myshell.outputIndex > 0){
                    try {
                        FileWriter out = new FileWriter(args.get(myshell.outputIndex + 1));
                        out.write(myshell.env[0]+" "+ myshell.env[1]+" "+ myshell.env[2]);
                        out.close();
                    } catch (IOException e) {
                        System.exit(5);
                    }
                    myshell.outputIndex = 0;
                } else{
                    System.out.println(myshell.env[0]+" "+ myshell.env[1]+" "+ myshell.env[2]);
                }
                break;
            case "echo":
                args.remove(0);
                String echoOut = String.join(" ", args);
                if (myshell.outputIndex > 0){
                    try {
                        FileWriter out = new FileWriter(args.get(myshell.outputIndex));
                        out.write(echoOut);
                        out.close();
                    } catch (IOException e) {
                        System.exit(5);
                    }
                    myshell.outputIndex = 0;
                } else{
                    System.out.println(echoOut);
                }
                break;
            case "help":
                try (BufferedReader br = new BufferedReader(new FileReader("README.md"))) {
                    br.lines().forEach(System.out::println);
                } catch (IOException e) {
                    System.exit(6);
                }
                break;
            case "quit":
                System.exit(1);
                break;
            case "myshell":
                try (BufferedReader br = new BufferedReader(new FileReader(args.get(1)))){
                    for(String line; (line = br.readLine()) != null; ){
                        ArrayList<String> newArgs = new ArrayList<>(Arrays.asList(line.split(" ")));
                        myshell.process(newArgs);
                    }
                } catch (IOException e) {
                    System.exit(7);
                }
                break;
            default:
                System.out.println("Please enter a valid command");
        }
        System.out.print(myshell.env[0]+"> ");
    }
}
