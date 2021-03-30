/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Server -> Implement Client-Server communication using Socket Programming and TCP as transport layer protocol.
 */
import java.io.*; // IO class
import java.net.*; // For Socket class
import java.util.*;

// Public class for server
public class Server {

    // Initializing private variables
    private Socket socket;
    private ServerSocket server;
    private Scanner input;
    private DataInputStream receive;
    private DataOutputStream send;

    // Constructor / Method
    public Server(int port) {
        try {
            server = new ServerSocket(port); // server starting in a port specified
            System.out.println("Server socket created");
            // System.out.print("Server started\nWaiting for a client ...\n");
            socket = server.accept(); // Waiting for clients
            // System.out.println("Client accepted");

            // Getting data from data socket stream -> from client
            receive = new DataInputStream(
                        new BufferedInputStream(
                            socket.getInputStream()
                        )
                    );

            input = new Scanner(System.in); // from console
            send = new DataOutputStream(socket.getOutputStream()); // sending stream to client

            String data = "";
            while (!data.equals("Exit")) {
                data = receive.readUTF(); // reading from client data
                System.out.println("From Client: " + data);  
                System.out.print("To Client: ");
                data = input.nextLine(); // reads line
                send.writeUTF(data); // writes data
            
            }

            // close connection
            System.out.println("Server Exit ... ");
            socket.close();
            receive.close();
            send.close();
            input.close();

        } catch (IOException e) {
            // IO exception
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(5000);
    }
}