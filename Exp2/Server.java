/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Server -> Implement Client-Server communication using Socket Programming and UDP as transport layer protocol.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Server {

    public Server() throws IOException {
        Scanner scanner = new Scanner(System.in);
        InetAddress ip = InetAddress.getLocalHost();
        byte buf[] = null;
        byte[] receive = new byte[65535];
        DatagramPacket DpReceive = null;
        DatagramSocket ds = new DatagramSocket(5000);


        System.out.println("Server scocket created");

        while (true) {

            DpReceive = new DatagramPacket(receive, receive.length);
            ds.receive(DpReceive);    
            System.out.println("From client: " + data(receive));
            receive = new byte[65535];

            System.out.print("Message to Client: ");
            String input = scanner.nextLine();
            buf = input.getBytes();
            DatagramPacket sendPkt = new DatagramPacket(
                buf,
                buf.length,
                ip,
                6000
            );
            ds.send(sendPkt);
            
            if (input.equals("Exit"))
                break;

            
        }

        System.out.println("Server exit...");
    }

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

    public static void main(String[] argv) throws IOException {
        Server server = new Server();
    }
}