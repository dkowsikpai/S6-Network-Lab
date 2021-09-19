/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Server -> Using Wireshark observe Three Way Handshaking Connection Establishment, 
 * Data Transfer and Three Way Handshaking Connection Termination in client server communication 
 * using TCP.
 */

import java.io.*;
import java.net.*;
import java.util.*;

// Public class for server
public class Server {
    private Socket socket;
    private ServerSocket server;
    private DataOutputStream outStream;

    private int SERVER_PORT = 5000;

    public Server() {
        try {
            server = new ServerSocket(SERVER_PORT);
            System.out.println("Server socket created.");

            socket = server.accept();
            String data = "ABCDE";

            outStream = new DataOutputStream(socket.getOutputStream());

            while(true) {
                outStream.writeUTF(data);
                System.out.println(data);
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    //TODO: handle exception
                }
            }

            // socket.close();
            // outStream.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }
    
    public static void main(String[] args) {
        Server server = new Server();
    }
}