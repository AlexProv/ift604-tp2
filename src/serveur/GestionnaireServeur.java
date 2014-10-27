package serveur;

import java.io.*;

import common.ListeDesMatchs;
import common.Match;
import common.ParisPersonne;
import serveur.Communication.Serveur;

public class GestionnaireServeur
{
	public static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args)
	{
		System.out.println("Initialisation du serveur");
		PreLaunchSequence();
		int port1 = 9876;
		try
		{
			Serveur serveur1 = new Serveur(port1);
			int choix = 0;
			String donnee = "";
			System.out.println("Voici les options a faire sur le serveur : \n1. Ajouter une partie\n2. Ajouter un but\n3. Ajouter une penalite\n4. Sauvegarder JSON\n5. GetBet");
			while(true)
			{
				if(bufferedReader.ready()){
					choix = Integer.parseInt(bufferedReader.readLine());
					switch(choix){
						case 1:
							System.out.println("Quel est le nom de l'equipe visiteur?");
							donnee = bufferedReader.readLine();
							System.out.println("Quel est le nom de l'equipe receveur?");
							ListeDesMatchs.getListeDesMatchs().ajouterPartie(new Match(ListeDesMatchs.getNextId(), donnee, bufferedReader.readLine()));
							break;
						case 2:
							System.out.println("Ajouter un but pour quel partie (idPartie)?");
							choix = Integer.parseInt(bufferedReader.readLine());
							Match match = ListeDesMatchs.getListeDesMatchs().getMatch(choix);
							System.out.println("Quel est le joueur qui a marque?");
							donnee = bufferedReader.readLine();
							System.out.println("Quel equipe (V ou D)?");
							if(bufferedReader.readLine().equals("V"))
								match.ajouterBut(donnee, match.getEquipeV(), match.getNumPeriode(), match.getTempsPeriodeMillSeconde());
							else
								match.ajouterBut(donnee, match.getEquipeD(), match.getNumPeriode(), match.getTempsPeriodeMillSeconde());
							break;
						case 3:
							System.out.println("Ajouter une penalite pour quel partie (numero)?");
							choix = Integer.parseInt(bufferedReader.readLine());
							match = ListeDesMatchs.getListeDesMatchs().getMatch(choix);
							System.out.println("Combien de minutes?");
							choix = Integer.parseInt(bufferedReader.readLine());
							System.out.println("Quel equipe (V ou R)?");
							if(bufferedReader.readLine().equals("V"))
								match.ajouterPenalite(match.getEquipeV(), match.getNumPeriode(), match.getTempsPeriodeMillSeconde(), choix);
							else
								match.ajouterPenalite(match.getEquipeD(), match.getNumPeriode(), match.getTempsPeriodeMillSeconde(), choix);
							break;
						case 4:
							sauvegarderFichier();
							break;
						case 5:
							for(Match match1 : ListeDesMatchs.getListeDesMatchs().getAllMatchs()){
								for(ParisPersonne pp : match1.getParis().getParis()){
									System.out.println(pp.getEquipe() + " " + pp.getMise());
								}
							}
							
							break;
					}
				}
			}
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
			ioe.printStackTrace();
		}		
	}
	
	
	
	private static void PreLaunchSequence()
	{
		File file = new File("");
		String path = file.getAbsolutePath();
		ListeDesMatchs listMatch = new ListeDesMatchs();
		
		try
		{
			String bdtext = lireFichier(path + "/bd.json");
			if(bdtext != null){
				listMatch = ListeDesMatchs.JsonToListDesMatchs(bdtext);
				ListeDesMatchs.setListeDesMatchs(listMatch);
			}
			else{
				listMatch.ajouterPartie(new Match(ListeDesMatchs.getNextId(), "Montreal", "Boston"));
				listMatch.ajouterPartie(new Match(ListeDesMatchs.getNextId(), "Washington", "Ottawa"));
				ListeDesMatchs.setListeDesMatchs(listMatch);
			}
			ListeDesMatchs.getListeDesMatchs().startClocks();
			
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			listMatch = ListeDesMatchs.getListeDesMatchs();
		}
	}
	
	private static void sauvegarderFichier() throws FileNotFoundException, UnsupportedEncodingException{
		File file = new File("");
		String path = file.getAbsolutePath() + "/bd.json";
		String data = ListeDesMatchs.getListeDesMatchs().ToJson();
		PrintWriter writer = new PrintWriter(path, "UTF-8");
		writer.print(data);
		writer.close();
	}
	
	private static String lireFichier(String nomFichier) throws IOException
	{
	   String contenu = null;
	   File fichier = new File(nomFichier);
	   if(!fichier.exists())
		   return null;
	   FileReader reader = new FileReader(fichier);
       char[] chars = new char[(int) fichier.length()];
       reader.read(chars);
       contenu = new String(chars);
       reader.close();
	   return contenu;
	}
}
