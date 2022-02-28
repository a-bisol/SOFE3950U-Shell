import java.util.ArrayList;

//Used for background execution

public class Shell extends Thread{
    ArrayList<String> args;
    String[] env;
    Shell(ArrayList<String> args, String[] env) {
        this.args=args;
        this.env=env;
    }

    public void run() {

    }
}
