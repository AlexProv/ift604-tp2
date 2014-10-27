package serveur.Communication;

import java.net.Socket;

import serveur.InterpreteurDeRequete;

public class Message implements Runnable
{
	private Serveur serveur;
	private Socket socket;
	private String message;

	private InterpreteurDeRequete interpreteurRequete;


	public Message(Serveur serveur, Socket socket, String message) 
	{
		this.serveur = serveur;
		this.socket = socket;
		this.message = message;
		interpreteurRequete = new InterpreteurDeRequete();
	}

	
	public void run()
	{
		try
		{
			System.out.println( "reception du message -- " + message + " -- du socket: " + socket);
			String answer = interpreteurRequete.ParseCommand(message);
			//System.out.println(answer);
			serveur.EnvoyeAClient(socket, answer);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
