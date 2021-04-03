/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Client -> Implement Client-Server communication using Socket Programming and UDP as transport layer protocol.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    public Client() throws IOException {
        Scanner scanner = new Scanner(System.in);
        InetAddress ip = InetAddress.getLocalHost();
        byte buf[] = null;
        byte[] receive = new byte[65535];
        DatagramPacket DpReceive = null;
        DatagramSocket ds = new DatagramSocket();
        DatagramSocket dSServer = new DatagramSocket(6000);


        System.out.println("Client scocket created");

        while (true) {

            System.out.print("Message to Server: ");
            String input = scanner.nextLine();
            buf = input.getBytes();
            DatagramPacket sendPkt = new DatagramPacket(
                buf,
                buf.length,
                ip,
                5000
            );
            ds.send(sendPkt);
            
            DpReceive = new DatagramPacket(receive, receive.length);
            dSServer.receive(DpReceive);    
            System.out.println("From server: " + data(receive));
            receive = new byte[65535];

            if (input.equals("Exit"))
                break;
        }

        System.out.println("Client exit...");

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
        Client client = new Client();
    }
}