package client;

import java.io.*;

import common.Commands;
import client.Communication.Client;

public class GestionnaireClient {

	public static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) {
		System.out.println("Initialisation du client");
		System.out.println("Specifiez l'adresse IP (127.0.0.1 par defaut): ");
		String ip ="";
		int port1 = 9876;

		try
		{
			ip = bufferedReader.readLine();
			if(ip == null || ip == "")
			{
				ip = "127.0.0.1";
			}
			
			Client client = new Client(ip, port1);
			InitialiseurDeRequete idr = new InitialiseurDeRequete(client);
			String requete = "";
			
			System.out.println("Bienvenue sur l'application HockeyLive!");
			System.out.println("Voici la liste des commandes possibles:");
			System.out.println("GetListMatch");
			System.out.println("GetEquipesMatch/idPartie");
			System.out.println("SetBet/idPartie/D ou V/Montant(avec un point si apres decimale)");
			System.out.println("Voici la liste des matchs (ID, Equipe Receveur, Equipe Visiteur):");
			 
			idr.ParseAnswer(client.envoyerRequete(Commands.GET_LIST_MATCH.toString()));
			
			
			while(true){
				if(bufferedReader.ready()){
					requete = bufferedReader.readLine();
					idr.ParseAnswer(client.envoyerRequete(requete));
				}
			}
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
			ioe.printStackTrace();			
		}
		
	}

}
