/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Client -> Using Wireshark observe data transferred in client server communication using UDP and identify the UDP datagram.
 */


import java.io.IOException;
import java.net.*;
import java.util.*;


public class Client {

    Integer PORT = 6000;

    public Client() throws IOException {
        InetAddress ip = InetAddress.getLocalHost();
        DatagramSocket socket = new DatagramSocket(PORT);

        byte buffer[] = new byte[65535];

        System.out.println("Client Socket created");

        while (true){
            DatagramPacket packet = new DatagramPacket(
                buffer,
                buffer.length
            );
            socket.receive(packet);

            System.out.println(dataParser(buffer));
        }
    }

    public static StringBuilder dataParser(byte[] buffer) {
        if (buffer == null) return null;
        StringBuilder data = new StringBuilder();
        int i = 0;
        while (buffer[i] != 0) {
            data.append((char)buffer[i++]);
        }
        return data;
    }

    public static void main(String[] argv) throws IOException{
        Client client = new Client();
    }
}
