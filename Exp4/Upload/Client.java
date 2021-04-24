/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Client -> Implement a Concurrent Time Server application using UDP to execute the program at remote server. Client sends a time request to the server, server sends its system time back to the client. Client displays the result.
 */

import java.io.IOException; // Exception handling
import java.net.DatagramPacket; // UDP packet
import java.net.DatagramSocket; // UDP Socket
import java.net.InetAddress; // Getting Host IP address

public class Client {

    // Constructor
    public Client() throws IOException {
        // Variable Initialization
        InetAddress ip = InetAddress.getLocalHost();
        DatagramSocket ds = new DatagramSocket();
        DatagramSocket dSServer = new DatagramSocket(6000);
        String input = "";

        // Client Socket creation
        System.out.println("Client scocket created");

        // Requesting for date
        input = "date";
        sendRequest(ds, ip, input); // Request to server
        receiveResponse(dSServer, "Date"); // Response to server

        // Requesting for time
        input = "time";
        sendRequest(ds, ip, input); // Request to server
        receiveResponse(dSServer, "Time"); // Response to server

        // Requesting for client to terminate
        input = "terminate";
        sendRequest(ds, ip, input); // Request to server    
        System.out.println("Terminate Server");

        // Client Exiting
        System.out.println("Client exit...");

    }

    // Function to send data/request to server
    public void sendRequest(DatagramSocket ds, InetAddress ip, String input) throws IOException {
        byte buf[] = null;
        buf = input.getBytes(); // create bytes of string request
        DatagramPacket sendPkt = new DatagramPacket( // preparing socket
            buf,
            buf.length,
            ip,
            5000
        );
        ds.send(sendPkt); // send data to server
    }

    // Fuction to receive response from server
    public void receiveResponse(DatagramSocket dSServer, String reqStr) throws IOException {
        byte[] receive = new byte[65535]; // Init
        DatagramPacket DpReceive = new DatagramPacket(receive, receive.length);// Receiving data
        dSServer.receive(DpReceive);    // On data receive
        System.out.println(reqStr + ": " + data(receive)); // print
    }

    // Prepare string from bytes
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
        Client client = new Client();
    }
}