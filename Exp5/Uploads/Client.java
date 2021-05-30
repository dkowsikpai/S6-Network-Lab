/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Client -> Implement Simple Mail Transfer Protocol.
 */
import java.io.*; // IO class
import java.net.*; // For Socket class
import java.util.*;

public class Client
{
	final static int ServerPort = 1234;

	public static void main(String args[]) throws UnknownHostException, IOException
	{
		Scanner scn = new Scanner(System.in);
		
		// getting localhost ip
		InetAddress ip = InetAddress.getByName("localhost");
		
		// establish the connection
		Socket s = new Socket(ip, ServerPort);
		System.out.println("Client socket created");
		
		// obtaining input and out streams
		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());

		try {
			String emailReq = dis.readUTF(); // Response from server to set up email id
			System.out.print(emailReq+": "); 
			String emailID = scn.nextLine(); // set email id
			dos.writeUTF(emailID); // send your email id to server
		} catch (IOException e) {
			e.printStackTrace();
		}

		// sendMessage thread
		Thread sendMessage = new Thread(new Runnable()
		{
			@Override
			public void run() {
				while (true) {
					String msg, recep;

					try {
						System.out.print("Enter recipient email id: ");
						recep = scn.nextLine(); // get email id
						dos.writeUTF("@"+recep); // Checking whether recepient email id exists or not
						
						msg = scn.nextLine();
						dos.writeUTF(msg+"#"+recep); // Messages to recepient
						
					} catch (IOException e) {
						//TODO: handle exception
					}					
				}
			}
		});
		
		// readMessage thread
		Thread readMessage = new Thread(new Runnable()
		{
			@Override
			public void run() {

				while (true) {
					try {
						// read the message sent to this client
						String msg = dis.readUTF(); // Reading messages
						System.out.println("\n=========================================="); // New Message from server/receipt
						System.out.println("Message: ");
						System.out.println(msg);
						System.out.println("==========================================");
						if (msg.equals("Exists"))System.out.println("Enter your message: "); // If user exists enter message
					} catch (IOException e) {

						// e.printStackTrace();
					}
				}
			}
		});

		sendMessage.start();
		readMessage.start();

	}
}
