/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Client -> Implement a Concurrent Time Server application using UDP to execute the program at remote server. Client sends a time request to the server, server sends its system time back to the client. Client displays the result.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.util.Scanner;

public final class Client {

    private void process(String req) {
          DatagramSocket datagramSocket = null;
         
          try{
                // Build a DatagramSocket and bind it to any available local port number
                datagramSocket = new DatagramSocket();

                System.out.println("Client socket created");

                // Build a DatagramPacket object to send a request packet to the server (the server is running locally)
                String request = req;
                byte[] buffer = request.getBytes();
                InetAddress serverAddress = InetAddress.getByName("localhost");
                DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length, serverAddress, 5000);

                // Send the packet to the server
                datagramSocket.send(requestPacket);

                // Receive the response packet from the server
                buffer = new byte[256];
                DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(responsePacket);

                // Get the string contained in the received packet
                String received = new String(responsePacket.getData(), 0, responsePacket.getLength());

                // Display the received string
                if (request.equals("GETDate")) System.out.print("Date: ");
                else if (request.equals("GETTime")) System.out.print("Time: ");
                System.out.println(received);
          }
          catch(IOException e){
                e.printStackTrace();
          }
          finally{
                // Close the DatagramSocket
                if(datagramSocket != null)
                      datagramSocket.close();
          }
    }

    public static void main(String[] args){
          new Client().process("GETDate"); // Requesting for date
          new Client().process("GETTime"); // Requesting for time
    }
}