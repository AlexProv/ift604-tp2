package common;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class ListeDesMatchs {
	@Expose private ArrayList<Match> liste;
	private final int nbMatchsMaximum = 10;
	
	static ListeDesMatchs lm;
	

	public static void setListeDesMatchs(ListeDesMatchs l)
	{
		lm = l;
	}
	public static ListeDesMatchs getListeDesMatchs()
	{
		if(lm == null)
		{
			lm = new ListeDesMatchs(1);
			return lm;
		}
		return lm;
	}
	
	private ListeDesMatchs(int i)
	{
		liste = new ArrayList<Match>();
		lm = this;
	}
	
	public ListeDesMatchs(){
		liste = new ArrayList<Match>();
		lm = this;
	}
	
	public int ajouterPartie(Match match){
		if(liste.size() < nbMatchsMaximum)
		{
			liste.add(match);
		}
		else
			System.out.print("Match maximum atteint");
		return match.getId() + 1;
	}
	
	
	public Match getMatch(int noMatch){
		for (Match match : liste) {
			if(match.getId() == noMatch)
				return match;
		}
		return null;
	}
	
	public ArrayList<Match> getAllMatchs(){
		return liste;
	}
	
	public void startClocks()
	{
		for(Match m : liste)
		{
			m.startClock();
		}
	}
	
	public static int getNextId(){
		return lm.liste.size();
	}
	
	public String ToJson()
	{
		return SerializateurJson.objectToJson(this);
	}
	
	static public ListeDesMatchs JsonToListDesMatchs(String s)
	{
		ListeDesMatchs lm = (ListeDesMatchs)SerializateurJson.jsonToObject(s,ListeDesMatchs.class);

		return lm;
	}
}
