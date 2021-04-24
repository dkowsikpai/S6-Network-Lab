/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Server -> Implement a Concurrent Time Server application using UDP to execute the program at remote server. Client sends a time request to the server, server sends its system time back to the client. Client displays the result.
 */

import java.io.IOException; // Exception handling
import java.net.DatagramPacket; // UDP packet
import java.net.DatagramSocket; // UDP Socket
import java.net.InetAddress; // Getting Host IP address

import java.time.format.DateTimeFormatter; // Format System Time
import java.time.LocalDateTime; // Get System Time

public class Server {

    // Constructor
    public Server(Integer port) throws IOException {
        // Initialization
        InetAddress ip = InetAddress.getLocalHost();
        byte buf[] = null;
        byte[] receive = new byte[65535];
        DatagramPacket DpReceive = null;
        DatagramSocket ds = new DatagramSocket(port); // Listening to port

        // Socket created
        System.out.println("Server scocket created");

        while (true) {
            DpReceive = new DatagramPacket(receive, receive.length);  // Waiting for data to receive
            ds.receive(DpReceive);    // On data receive

            String req = data(receive).toString(); // Request
            String resp = ""; // Response to client
            if (req.equals("date")) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  // Get Date
                LocalDateTime now = LocalDateTime.now();
                resp = dtf.format(now);  
                System.out.println("Client requested for date - " + resp);  
            } else if (req.equals("time")) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  // Get Time
                LocalDateTime now = LocalDateTime.now();
                resp = dtf.format(now);
                System.out.println("Client requested for time - " + resp);
            } else if (req.equals("terminate")) { // Termination
                System.out.println("Client requested for termination");
                System.out.println("Server disconnected");
                break;
            } 
            

            // Send REsponse back to client
            String input = resp;
            buf = input.getBytes(); // making String to bytes
            DatagramPacket sendPkt = new DatagramPacket( // preparing data
                buf,
                buf.length,
                ip,
                6000
            );
            ds.send(sendPkt); // sending data

            receive = new byte[65535]; // re-init
           
        }
    }

    // Fuction to build string from bytes
    public static StringBuilder data(byte[] a) {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }

    // main
    public static void main(String[] argv) throws IOException {
        Server server = new Server(5000);
    }
}