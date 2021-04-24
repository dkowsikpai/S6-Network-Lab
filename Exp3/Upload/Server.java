/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Server -> Implement a multi user chat server using TCP as transport layer protocol.
 */
import java.io.*; // IO class
import java.net.*; // For Socket class
import java.util.*;

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

        String data = "";
        while (!data.equals("Exit")) {
            try {
                data = receive.readUTF(); // reading from client data
                System.out.println("From Client" + id.toString() + ": " + data);  
                String snd = "";
                if (data.equals("Hi, I'm Client "+ id +".")) {
                    snd = "Hello Client "+ id +".";
                } else if (data.equals("May I know who else are chatting with you?")) {
                    snd = String.valueOf(id + 1);
                } else if (data.equals("*Message from Client "+ id +"*")) {
                    snd = "";
                } else if (data.equals("Exit")) {
                    snd = "Exit";
                } else {
                    snd = "Invalid Message";
                }
                System.out.println("To Client" + id.toString() + ": " + snd);
                send.writeUTF(snd); // writes data
            } catch (IOException e) {
                e.printStackTrace();
            }        
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
}
