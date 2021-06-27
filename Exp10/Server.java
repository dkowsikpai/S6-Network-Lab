/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Server -> Using Wireshark observe data transferred in client server communication using UDP and identify the UDP datagram.
 */


import java.io.IOException;
import java.net.*;
import java.util.concurrent.TimeUnit;


public class Server {

    Integer PORT = 5000;

    public Server() throws IOException {
        InetAddress ip = InetAddress.getLocalHost();
        DatagramSocket socket = new DatagramSocket(PORT);

        byte buffer[] = null;

        System.out.println("Server Socket created");

        while (true){
            String input = "ABCDE";
            System.out.println(input);
            buffer = input.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(
                buffer,
                buffer.length,
                ip,
                6000 // Client Port
            );

            socket.send(sendPacket);

            try {
                Thread.sleep(2000);
            } 
            catch(InterruptedException e) {
                // this part is executed when an exception (in this example InterruptedException) occurs
            }
        }
    }

    public static void main(String[] argv) throws IOException {
        Server server = new Server();
    }
}


