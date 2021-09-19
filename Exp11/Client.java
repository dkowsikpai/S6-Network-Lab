/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Cient -> Using Wireshark observe Three Way Handshaking Connection Establishment, 
 * Data Transfer and Three Way Handshaking Connection Termination in client server communication 
 * using TCP.
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private Socket socket;
    private DataInputStream inStream;

    private int SERVER_PORT = 5000;
    private String SERVER_IP = "127.0.0.1";

    public Client() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Client socket created.");

            inStream = new DataInputStream(socket.getInputStream());

            while(true) {
                String data = inStream.readUTF();
                System.out.println(data);
            }

            // socket.close();
            // inStream.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }
    
    public static void main(String[] args) {
        Client client = new Client();
    }
}