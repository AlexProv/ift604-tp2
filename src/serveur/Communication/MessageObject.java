package serveur.Communication;

import java.net.Socket;

import serveur.InterpreteurDeRequete;

public class MessageObject implements Runnable{
	
	private Serveur serveur;
	private Socket socket;
	private String message;

	private InterpreteurDeRequete interpreteurRequete;

	public MessageObject(Serveur serveur, Socket socket, String message) 
	{
		this.serveur = serveur;
		this.socket = socket;
		this.message = message;
		interpreteurRequete = new InterpreteurDeRequete();
	}
	
	@Override
	public void run() {
		System.out.println( "Reception du message : " + message + " (du socket " + socket + " )");
		boolean success = interpreteurRequete.ParseCommandPari(message, socket, serveur);
		if(success)
			serveur.EnvoyeAClient(socket, "SetBet|Succes du pari");
		else
			serveur.EnvoyeAClient(socket, "SetBet|Pari soumis trop tard");
	}

}
