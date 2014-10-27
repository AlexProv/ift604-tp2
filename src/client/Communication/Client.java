package client.Communication;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.UUID;

public class Client
{
	private DataOutputStream outStream;
	private Socket socket;
	private String host;
	private int port;
	public UUID uuid;
	
	
	public Client(String host, int port) throws IOException
	{
		this.host = host;
		this.port = port;
		this.uuid = UUID.randomUUID();
	}

	
	public String envoyerRequete(String cmd)
	{
		String reponse = "";
		try
		{
			socket = new Socket(host, port);
			socket.setSoTimeout(10000);//Timeout de 10 secondes
			PrintWriter  printWriter = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			try
			{
				if(cmd.startsWith("SetBet")){
					cmd += "/" + uuid.toString();
				}
				printWriter.println(cmd);
				reponse = inStream.readLine();
			}
			catch (SocketTimeoutException ste)
			{
				reponse = "ste";
				System.out.println("Timeout en attendant la reponse du serveur.");
			}
			socket.close();
		}
		catch(IOException ioe)
		{
			reponse = "ioe";
			System.out.println(ioe);
		}
		
		return reponse;
	}
	
	public void envoyerDonnee(String donnee)
	{
		try
		{
			outStream.writeUTF(donnee);
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
		}
	}
	 
	
}
