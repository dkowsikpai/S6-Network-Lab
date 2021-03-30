import java.io.*;

class Test {
    public static void main(String[] args) {
        DataInputStream in = new DataInputStream(System.in);
        String line = "";
        try {
            line =  in.readLine();
            System.out.println("->" + line);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}