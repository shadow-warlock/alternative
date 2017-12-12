import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by root on 11.12.17.
 */
public class MyReader {
    private BufferedReader reader;
    private char buffer;
    private boolean inputState = true;

    public MyReader(){
        reader = new BufferedReader( new InputStreamReader(System.in));
    }
    public void read() {
        int code = 0;
        try {
            code = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(code == 10) {
            inputState = false;
        }
        buffer = (char) code;
    }
    public char get(){
        return buffer;
    }
    public boolean isInputState(){
        return inputState;
    }
}
