/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Client -> Develop concurrent file server which will provide the file requested by client if it exists. If not, the server sends an appropriate message to the client. Server should also send its process ID (PID) to clients for display along with file or the message.
 */

import java.io.*; // IO class
import java.net.*; // For Socket class
import java.util.*;

// Public class for client
public class Client {

    // Initializing private variables
    private Socket socket;
    private DataInputStream receive;
    private DataOutputStream send;

    // Constructor / Method
    public Client(String ip, int port) {
        try {
            socket = new Socket(ip, port); // Socket creation with IP and Port
            System.out.println("Client socket created");
            receive = new DataInputStream( // Data from server input stream
                new BufferedInputStream(
                    socket.getInputStream()
                )
            );
            send = new DataOutputStream(socket.getOutputStream()); // output stream as socket

            Scanner scanner = new Scanner(System.in); // Scanner set up
            System.out.print("Enter file name (with MIME type): "); // Input file name
            String fname = scanner.nextLine();
            send.writeUTF("check#"+fname); // send fname to server

            if (receive.readUTF().equals("exists")){
                FileOutputStream fos = new FileOutputStream("receive/"+fname); // If exists save to received folder
                byte[] buffer = new byte[4096];
                
                System.out.println("PID: "+receive.readUTF()); // Print ServerPID
                int filesize = Integer.parseInt(receive.readUTF()); // file size
                int read = 0;
                int totalRead = 0; // total bytes read
                int remaining = filesize; // remaing file size
                while((read = receive.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                    totalRead += read;
                    remaining -= read;
                    System.out.print(String.valueOf(totalRead*100/filesize) + "% "); //print percentage received
                    // System.out.println(data(buffer));
                    fos.write(buffer, 0, read);
                }
                System.out.println();
                
                fos.close();
            } else {
                System.out.println("Requested file doesn't exists in server."); // if file doesn't exists print message
            }

            
            // close connection
            System.out.println("Client Exit ... ");
            scanner.close(); 
            send.close();
            receive.close();
            socket.close();


        } catch (UnknownHostException e) {
            // This happens when the host address becomes invalid
            System.out.println("Unknown host");
        } catch (ConnectException e) {
            // Connection error happens when the server gets disconnected or no server is listening the port described.
            // Will also triggen when the client is started first
            System.out.println("Connection refused");
        } catch (IOException e) {
            // IO exception
            System.out.println(e);
        } 
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

    public static void main(String[] args) {
        // Runing the function
        Client client = new Client("127.0.0.1", 5000);
    }
}