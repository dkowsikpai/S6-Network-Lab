/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Client -> Implement a multi user chat server using TCP as transport layer protocol.
 */

import java.io.*; // IO class
import java.net.*; // For Socket class
import java.util.*;

// Public class for client
public class Client {

    // Initializing private variables
    private Socket socket;
    private Scanner input;
    private DataInputStream receive;
    private DataOutputStream send;

    // Constructor / Method
    public Client(String ip, int port) {
        try {
            socket = new Socket(ip, port); // Socket creation with IP and Port
            System.out.println("Client socket created");
            input = new Scanner(System.in); // Setting data stream from console
            receive = new DataInputStream( // Data from server input stream
                new BufferedInputStream(
                    socket.getInputStream()
                )
            );
            send = new DataOutputStream(socket.getOutputStream()); // output stream as socket

            // data var
            String data = "";
            while (!data.equals("Exit")) { // Run until data says end
                    System.out.print("To Server: ");
                    data = input.nextLine(); // reads line
                    send.writeUTF(data); // writes data
                    data = receive.readUTF(); // data received from server
                    System.out.println("From Server: " + data);
            } 

            
            // close connection
            System.out.println("Client Exit ... ");
            input.close(); 
            send.close();
            receive.close();
            socket.close();


        } catch (UnknownHostException e) {
            // This happens when the host address becomes invalid
            System.out.println("Unknown host");
        } catch (ConnectException e) {
            // Connection error happens when the server gets disconnected or no server is listening the port described.
            // Will also triggen when the client is started first
            System.out.println("Connection refused");
        } catch (IOException e) {
            // IO exception
            System.out.println(e);
        } 
    }

    public static void main(String[] args) {
        // Runing the function
        Client client = new Client("127.0.0.1", 5000);
    }
}