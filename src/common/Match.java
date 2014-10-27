package common;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.annotations.Expose;

public class Match extends Observable implements Runnable {
	
	@Expose private String equipeV;
	@Expose private String equipeD;
	@Expose private int butV;
	@Expose private int butD;
	@Expose private int numPeriode;
	@Expose private Timer timerPeriode;
	@Expose private Timer timerAlert;
	@Expose private Paris paris;
	
	@Expose private long tempsPeriodeMillSeconde; 
	@Expose private long stopTime;
	
	@Expose private ArrayList<But> listeBut;
	@Expose private ArrayList<Penalite> listePenalite;
	@Expose private int id;
	
	
	public long getTempsPeriodeMillSeconde() {
		return tempsPeriodeMillSeconde;
	}

	public void setTempsPeriodeMillSeconde(long tempsPeriodeMillSeconde) {
		this.tempsPeriodeMillSeconde = tempsPeriodeMillSeconde;
	}

	public Match(int numeroMatch, String equipeVis, String equipeDom){
		equipeV = equipeVis;
		equipeD = equipeDom;
		id = numeroMatch;
		numPeriode = 1;
		butV = 0;
		butD = 0;
		timerPeriode = new Timer();
		listeBut = new ArrayList<But>();
		listePenalite = new ArrayList<Penalite>();
		setParis(new Paris());
		
		
		tempsPeriodeMillSeconde = 0;
		stopTime = System.currentTimeMillis();

		timerPeriode = new Timer();
		PeriodeTimer periodeTimer = new PeriodeTimer(this);
		timerPeriode.scheduleAtFixedRate(periodeTimer, 0,20*60*1000);

		timerAlert = new Timer();
		AlertTimer alertTimer = new AlertTimer(this);
		timerAlert.scheduleAtFixedRate(alertTimer, 2*60*1000,2*60*1000);


	}
	
	public void startClock()
	{
		new Thread(this).start();
	}
	
	public String getEquipeV() {
		return equipeV;
	}

	public void setEquipeV(String equipeV) {
		this.equipeV = equipeV;
	}

	public String getEquipeD() {
		return equipeD;
	}

	public void setEquipeD(String equipeD) {
		this.equipeD = equipeD;
	}

	public int getButV() {
		return butV;
	}

	public void setButV(int butV) {
		this.butV = butV;
	}

	public int getButD() {
		return butD;
	}

	public void setButD(int butD) {
		this.butD = butD;
	}

	public int getNumPeriode() {
		return numPeriode;
	}

	public void setNumPeriode(int numPeriode) {
		this.numPeriode = numPeriode;
	}


	public ArrayList<But> getListeBut() {
		return listeBut;
	}

	public void setListeBut(ArrayList<But> listeBut) {
		this.listeBut = listeBut;
	}

	public ArrayList<Penalite> getListePenalite() {
		return listePenalite;
	}

	public void setListePenalite(ArrayList<Penalite> listePenalite) {
		this.listePenalite = listePenalite;
	}

	/**
	 * @return the paris
	 */
	public Paris getParis() {
		return paris;
	}

	/**
	 * @param paris the paris to set
	 */
	public void setParis(Paris paris) {
		this.paris = paris;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void alert()
	{
		setChanged();
        notifyObservers();
	}
		
	public void ajouterPenalite(String equipe, int periode, long tempsPeriodeMs, int tempsPenalite){
		listePenalite.add(new Penalite(equipe, periode, tempsPeriodeMs, tempsPenalite));
	}
	
	public void ajouterBut(String compteur, String equipe, int periode, long tempsPeriodeMs){
		listeBut.add(new But(compteur, equipe, periode, tempsPeriodeMs));
		if(equipe.equals(equipeD))
			++butD;
		else
			++butV;
	}
	
	public String ToJson()
	{
		return SerializateurJson.objectToJson(this);
	}
	
	static public Match JsonToMatch(String s)
	{
		return (Match)SerializateurJson.jsonToObject(s,Match.class);
	}
	
	@Override
	public void run() {
		while(true)
		{
			if(System.currentTimeMillis() != stopTime )
			{
				tempsPeriodeMillSeconde += System.currentTimeMillis() - stopTime ;
				stopTime = System.currentTimeMillis();
				if(tempsPeriodeMillSeconde >= (1000 * 60 * 60)){
					break;
				}
			}
		}
	}
	

	private class PeriodeTimer extends TimerTask{
		
		public int nbPeriode;
		Match match;
		public PeriodeTimer(Match m)
		{
			match = m;
		}
		
		@Override
		public void run() {
			nbPeriode +=1;
			match.setNumPeriode(nbPeriode);
			if(nbPeriode >= 3)
				this.cancel();
		}
	
	}
	
	private class AlertTimer extends TimerTask{
		
		public int nbAlert;
		Match match;
		public AlertTimer(Match m)
		{
			match = m;
		}
		
		@Override
		public void run() {
			
			nbAlert +=1;
			match.alert();
			if(nbAlert >=10)
				this.cancel();
		}
	
	} 
}



