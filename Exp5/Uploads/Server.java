/**
 * Author: Kowsik Nandagopan D
 * CSE S6, Roll No 31
 * Program: Server -> Implement Simple Mail Transfer Protocol.
 */

import java.io.*; // IO class
import java.net.*; // For Socket class
import java.util.*;

// Server class
public class Server
{

	// Vector to store active clients
	static Vector<ClientHandler> ar = new Vector<>();
	
	// counter for clients
	static int i = 0;

	public static void main(String[] args) throws IOException
	{
		// server is listening on port 1234
		ServerSocket ss = new ServerSocket(1234);
		System.out.println("Server socket created");
		
		Socket s;
		
		// running infinite loop for getting
		// client request
		while (true)
		{
			// Accept the incoming request
			s = ss.accept();

			// Prints client socket details
			System.out.println("New client request received : " + s);
			
			// obtain input and output streams
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			
			System.out.println("Creating a new handler for this client...");

			// Create a new handler object for handling this request.
			ClientHandler mtch = new ClientHandler(s,"client " + i, dis, dos);

			// Create a new Thread with this object.
			Thread t = new Thread(mtch);
			
			System.out.println("Adding this client to active client list");

			// add this client to active clients list
			ar.add(mtch);

			// start the thread.
			t.start();

			// increment i for new client.
			// i is used for naming only, and can be replaced
			// by any naming scheme
			i++;

		}
	}
}

// ClientHandler class
class ClientHandler implements Runnable
{
	Scanner scn = new Scanner(System.in);
	private String email_id;
	final DataInputStream dis;
	final DataOutputStream dos;
	Socket s;
	boolean isloggedin;
	
	// constructor
	public ClientHandler(Socket s, String email_id,
							DataInputStream dis, DataOutputStream dos) {
		this.dis = dis;
		this.dos = dos;
		this.email_id = email_id;
		this.s = s;
		this.isloggedin=true;
	}

	@Override
	public void run() {
		String received;
		
		try {
			dos.writeUTF("Send your email id");
			received = dis.readUTF(); // Receive email id of the sender
			System.out.println(email_id + " is now " + received);
			this.email_id = received; // Setting email id
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true)
		{
			try
			{
				// receive the string
				received = dis.readUTF();
				
				System.out.println(received);
				
				if(received.equals("logout")){
					this.isloggedin=false;
					this.s.close();
					break;
				} else if (received.charAt(0) == '@') {
					Boolean found = false;
					for (ClientHandler mc : Server.ar) {
						if (mc.email_id.equals(received.substring(1)) && mc.isloggedin==true)	{
							dos.writeUTF("Exists");
							found = true;
						}
					}
					if(!found){
						dos.writeUTF("Not Exists");
					}
				} else {	
					// break the string into message and recipient part
					StringTokenizer st = new StringTokenizer(received, "#"); // Sender email will be appended at the end of string. So We need to get that id and msg separately
					String MsgToSend = st.nextToken();
					String recipient = st.nextToken();
	
					// search for the recipient in the connected devices list.
					// ar is the vector storing client of active users
					for (ClientHandler mc : Server.ar) {
						// if the recipient is found, write on its
						// output stream
						if (mc.email_id.equals(recipient) && mc.isloggedin==true) {
							mc.dos.writeUTF(this.email_id+" : "+MsgToSend);
							break;
						}
					}
				}

			} catch (IOException e) {
				
				// e.printStackTrace();
			} 
			
		}
		try
		{
			// closing resources
			this.dis.close();
			this.dos.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
