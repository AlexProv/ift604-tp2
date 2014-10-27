package client;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import client.Communication.Client;
import common.But;
import common.Commands;
import common.ListeDesMatchs;
import common.Match;
import common.Penalite;

public class InitialiseurDeRequete
{
	private Match matchCourant;
	private ListeDesMatchs lm;
	
	private Client client;
	private Timer timerAlert;
	private AlertTimer alertTimer;
	
	public InitialiseurDeRequete(Client c)
	{
		client = c;
		
		timerAlert = new Timer();
	}
	
	public String getListeEquipeRequest()
	{
		return Commands.GET_LIST_MATCH.toString();
	}
	
	public String getMatchRequest(int newMatch, int oldMatch)
	{
		return Commands.GET_EQUIPES_MATCH.toString() + "|" + newMatch + "|" + oldMatch;
	}
	
	public StringBuilder ParseAnswer(String s)
	{
		StringBuilder sb = new StringBuilder();
		String[] answers = s.split("\\|");
		
		if(answers[0].equals(Commands.LIST_MATCH.toString()))
		{
			lm = ListeDesMatchs.JsonToListDesMatchs(answers[1]);
			for(Match match : lm.getAllMatchs()){
				sb.append(match.getId() + ":" + match.getEquipeD() + " vs. " + match.getEquipeV() + System.getProperty("line.separator"));
			}
			//System.out.println("Pour plus de details sur la partie, entrez GetEquipesMatch/(id)!");
			
			if(alertTimer != null)
			{
				alertTimer.cancel();
			}
			return sb;
			
		}
		
		if(answers[0].equals(Commands.EQUIPE_MATCH.toString()))
		{	
			matchCourant = Match.JsonToMatch(answers[1]);
			sb.append("Resume du match no." + matchCourant.getId() + " : " + matchCourant.getEquipeV() + " vs. " + matchCourant.getEquipeD() + System.getProperty("line.separator"));
			sb.append(matchCourant.getButV() + "-" + matchCourant.getButD() + System.getProperty("line.separator"));
			long currentTime = matchCourant.getTempsPeriodeMillSeconde();
			sb.append("Periode " + matchCourant.getNumPeriode() + ", " + setTime(currentTime) + System.getProperty("line.separator"));
			sb.append("Sommaire des buts" + System.getProperty("line.separator"));
			int i = 1;
			for(But but: matchCourant.getListeBut()){
				sb.append(i + ". " + but.getEquipe() + " Marque par : " + but.getPointeur() + ", Periode " + but.getNumPeriode() + " " + setTime(but.getTempsPeriodeMs()) + System.getProperty("line.separator"));
				++i;
			}
			
			System.out.println("Sommaire des penalites");
			i = 1;
			for(Penalite penalite: matchCourant.getListePenalite()){
				if(penalite.getTempsPeriodeFinMs() >= currentTime)
					sb.append(i + ". " + penalite.getEquipePen() + " " + penalite.getTempsPenalite() +" min, Periode " + penalite.getNumPeriode() + " " + setTime(penalite.getTempsPeriodeDebutMs()) + " (En cours)" + System.getProperty("line.separator"));
				else
					sb.append(i + ". " + penalite.getEquipePen() + " " + penalite.getTempsPenalite() +" min, Periode " + penalite.getNumPeriode() + " " + setTime(penalite.getTempsPeriodeDebutMs()) + System.getProperty("line.separator"));
				++i;
			}
			alertTimer = new AlertTimer(matchCourant);
			timerAlert.scheduleAtFixedRate(alertTimer, 30*1000,30*1000);
			return sb;

		}
		if(answers[0].equals(Commands.SET_BET.toString())){
			sb.append(answers[1]);
			return sb;
		}
		return sb;
	}
	
	@SuppressLint("NewApi")
	private String setTime(long ms){
		if(TimeUnit.MILLISECONDS.toMinutes(ms) >= 40)
			return String.format("%02d:%02d", 
			    (TimeUnit.MILLISECONDS.toMinutes(ms) - 40) ,
			    TimeUnit.MILLISECONDS.toSeconds(ms) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));
		else if(TimeUnit.MILLISECONDS.toMinutes(ms) >= 20)
			return String.format("%02d:%02d", 
				    (TimeUnit.MILLISECONDS.toMinutes(ms) - 20) ,
				    TimeUnit.MILLISECONDS.toSeconds(ms) - 
				    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));
		else
			return String.format("%02d:%02d", 
				    TimeUnit.MILLISECONDS.toMinutes(ms) ,
				    TimeUnit.MILLISECONDS.toSeconds(ms) - 
				    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));
	}
	
	
	private class AlertTimer extends TimerTask{
		
		//public int nbAlert;
		Match match;
		public AlertTimer(Match m)
		{
			match = m;
		}
	
/*		public void setMatch(Match m)
		{
			match = m;
		}*/
		
		@Override
		public void run() {
			synchronized (match) {
				if(match.getTempsPeriodeMillSeconde() <= 60 * 1000 * 60)
				{
					String s = client.envoyerRequete(Commands.GET_EQUIPES_MATCH.toString() + "/" + match.getId());
					String[] answers = s.split("\\|");
					if(answers[0].equals(Commands.EQUIPE_MATCH.toString()))
					{
						match = Match.JsonToMatch(answers[1]);
						long currentTime = match.getTempsPeriodeMillSeconde();
						System.out.println("Nouveau chrono au match: Periode " + match.getNumPeriode() + ", " + setTime(currentTime));
						
					}
				}
				else{
					String s = client.envoyerRequete(Commands.GET_RESULT_BET.toString()+ "/" + match.getId() + "/" + client.uuid);
					String[] answers = s.split("\\|");
					if(answers[0].equals(Commands.SET_BET.toString()))
					{
						System.out.println(answers[1]);
					}
				}
			}
		}
	}
}
