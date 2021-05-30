/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Server -> Develop concurrent file server which will provide the file requested by client if it exists. If not, the server sends an appropriate message to the client. Server should also send its process ID (PID) to clients for display along with file or the message.
 */
import java.io.*; // IO class
import java.net.*; // For Socket class
import java.util.*;
import java.lang.ProcessHandle;

// Public class for server
public class Server {

    // Initializing private variables
    private Socket socket;
    private ServerSocket server;

    private DataInputStream receive;
    private DataOutputStream send;

    private Integer count = 0;
    

    // Constructor / Method
    public Server(int port) {
        try {
            server = new ServerSocket(port); // server starting in a port specified
            System.out.println("Server socket created");

            while (true) {
                socket = server.accept(); // Waiting for clients
                count++;
                System.out.println("Client" + count.toString() + " Connected");

                // System.out.println("Client accepted");

                // Getting data from data socket stream -> from client
                receive = new DataInputStream(
                            new BufferedInputStream(
                                socket.getInputStream()
                            )
                        );

                send = new DataOutputStream(socket.getOutputStream()); // sending stream to client

                

                Thread handler = new ClientHandler(socket, receive, send, count);
                handler.start();

                // close connection
                // System.out.println("Server Exit ... ");
                
            }

        } catch (IOException e) {
            // IO exception
            System.out.println(e);
                // receive.close();
                // send.close();
                // input.close();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(5000);
    }
}

class ClientHandler extends Thread {

    final Socket socket;
    final DataInputStream receive;
    final DataOutputStream send;
    final Integer id;

    public ClientHandler(Socket s, DataInputStream rec, DataOutputStream sed, Integer count) {
        this.socket = s;
        this.receive = rec;
        this.send = sed;
        this.id = count;
    }

    @Override
    public void run() {

        try {
            String req = receive.readUTF(); // get fname

            StringTokenizer st = new StringTokenizer(req, "#"); // request + file name
            req = st.nextToken();
            String fname = st.nextToken();       

            File f = new File("send/"+fname); // get the file from send folder
            if (f.exists()) {
                send.writeUTF("exists"); // if exists alert client
                long pid = ProcessHandle.current().pid(); // send server pid
                send.writeUTF(String.valueOf(pid)); 
                System.out.println("Requested file " + fname + " of size " + f.length() + " bytes"); // send file size
                send.writeUTF(String.valueOf(f.length()));
                InputStream file = new FileInputStream(f); // Set input file stream
                sendFile(file, send); //send file
            } else {
                System.out.println("Requested file " + fname + " doesn't exists"); // if doesn't exists alert client
                send.writeUTF("no");
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
        

        try {
            socket.close();
            receive.close();
            send.close();
            // input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendFile(InputStream file, OutputStream out) {
        try {
            byte[] buffer = new byte[1000]; // set buffer size
            while (file.available()>0) // if buffer available 
            out.write(buffer, 0, file.read(buffer)); // send the file as bytes in buffer
        } catch (IOException e) { System.err.println(e); }
    }
}
