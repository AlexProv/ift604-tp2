package common;

import com.google.gson.annotations.Expose;


public class But {
	
	@Expose private String pointeur;
	@Expose private String equipe;
	@Expose private int numPeriode;
	@Expose private long tempsPeriodeMs;
	
	public But(String pointeur, String equipe, int numPeriode, long tempsPeriodeMs){
		setPointeur(pointeur);
		setEquipe(equipe);
		setNumPeriode(numPeriode);
		setTempsPeriodeMs(tempsPeriodeMs);
	}
	
	public String getPointeur() {
		return pointeur;
	}

	public void setPointeur(String pointeur) {
		this.pointeur = pointeur;
	}

	public String getEquipe() {
		return equipe;
	}

	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}

	public int getNumPeriode() {
		return numPeriode;
	}

	public void setNumPeriode(int numPeriode) {
		this.numPeriode = numPeriode;
	}

	/**
	 * @return the tempsPeriodeMs
	 */
	public long getTempsPeriodeMs() {
		return tempsPeriodeMs;
	}

	/**
	 * @param tempsPeriodeMs the tempsPeriodeMs to set
	 */
	public void setTempsPeriodeMs(long tempsPeriodeMs) {
		this.tempsPeriodeMs = tempsPeriodeMs;
	}

	public String ToXml()
	{
		return  SerializateurJson.objectToJson(this);
	}
	
	static public But XmlToBut(String s)
	{
		return (But)SerializateurJson.jsonToObject(s, But.class);
	}
	
	
	
	
}
