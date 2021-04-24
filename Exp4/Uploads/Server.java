/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Server -> Implement a Concurrent Time Server application using UDP to execute the program at remote server. Client sends a time request to the server, server sends its system time back to the client. Client displays the result.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import java.time.format.DateTimeFormatter; // Format System Time
import java.time.LocalDateTime; // Get System Time

public final class Server {

    public static void main(String[] args){
          try{
                UdpServer udpServer = new UdpServer();
                udpServer.start();
          }
          catch(SocketException e){
                e.printStackTrace();
          }
    }
}

final class UdpServer extends Thread {

    private DatagramSocket datagramSocket;
    private int portNumber = 5000;


    public UdpServer() throws SocketException {
          setName("UdpServer");
         
          // create a DatagramSocket instance and bind it to the specified port number
          datagramSocket = new DatagramSocket(portNumber);

          System.out.println("Server socket created");
    }


    public void run(){
          try{
                while (! isInterrupted()) {
                     
                      // Build a DatagramPacket object to receive a packet
                      byte[] buffer = new byte[256];
                      DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
                     
                      // Receive a packet from a client. The method "receive" blocks until a packet arrives
                      datagramSocket.receive(requestPacket);
                     
                      // Get the string contained in the received packet
                      String request = new String(requestPacket.getData(), 0, requestPacket.getLength());
                     
                    if(request.equals("GETDate")){ // Date Response
                        System.out.println("Client requested for date");

                        // Build a DatagramPacket object to send a response packet to the client
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  // Get Date
                        LocalDateTime now = LocalDateTime.now();
                        String response = dtf.format(now); 
                        buffer = response.getBytes();
                        DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length, requestPacket.getAddress(), requestPacket.getPort());
                        
                        // Send the packet to the client
                        datagramSocket.send(responsePacket); 
                        
                        
                    } else if(request.equals("GETTime")){ // Time Response
                        System.out.println("Client requested for time");

                        // Build a DatagramPacket object to send a response packet to the client
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  // Get Date
                        LocalDateTime now = LocalDateTime.now();
                        String response = dtf.format(now); 
                        buffer = response.getBytes();
                        DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length, requestPacket.getAddress(), requestPacket.getPort());
                        
                        // Send the packet to the client
                        datagramSocket.send(responsePacket);
                        
                    } 
                }
          }
          catch(IOException e){
                e.printStackTrace();
          }
          finally{
                // close the DatagramSocket instance before termination
                datagramSocket.close();
          }
    }

}