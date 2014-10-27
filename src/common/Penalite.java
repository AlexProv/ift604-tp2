package common;

import com.google.gson.annotations.Expose;

public class Penalite {
	
	@Expose private int numPeriode;
	@Expose private long tempsPeriodeDebutMs;
	@Expose private long tempsPeriodeFinMs;
	@Expose private String equipePen;
	@Expose private int tempsPenalite;
	
	public Penalite(String equipe, int periode, long tempsPeriodeMs, int tempsPenalite){
		setEquipePen(equipe);
		setNumPeriode(periode);
		setTempsPeriodeDebutMs(tempsPeriodeMs);
		setTempsPeriodeFinMs(tempsPeriodeMs + (tempsPenalite * 60000));
		setTempsPenalite(tempsPenalite);
	}
	
	/**
	 * @return the numPeriode
	 */
	public int getNumPeriode() {
		return numPeriode;
	}

	/**
	 * @param numPeriode the numPeriode to set
	 */
	public void setNumPeriode(int numPeriode) {
		this.numPeriode = numPeriode;
	}

	/**
	 * @return the tempsPeriodeDebutMs
	 */
	public long getTempsPeriodeDebutMs() {
		return tempsPeriodeDebutMs;
	}

	/**
	 * @param tempsPeriodeDebutMs the tempsPeriodeDebutMs to set
	 */
	public void setTempsPeriodeDebutMs(long tempsPeriodeDebutMs) {
		this.tempsPeriodeDebutMs = tempsPeriodeDebutMs;
	}

	/**
	 * @return the tempsPeriodeFinMs
	 */
	public long getTempsPeriodeFinMs() {
		return tempsPeriodeFinMs;
	}

	/**
	 * @param tempsPeriodeFinMs the tempsPeriodeFinMs to set
	 */
	public void setTempsPeriodeFinMs(long tempsPeriodeFinMs) {
		this.tempsPeriodeFinMs = tempsPeriodeFinMs;
	}

	/**
	 * @return the equipePen
	 */
	public String getEquipePen() {
		return equipePen;
	}

	/**
	 * @param equipePen the equipePen to set
	 */
	public void setEquipePen(String equipePen) {
		this.equipePen = equipePen;
	}

	/**
	 * @return the tempsPenalite
	 */
	public int getTempsPenalite() {
		return tempsPenalite;
	}

	/**
	 * @param tempsPenalite the tempsPenalite to set
	 */
	public void setTempsPenalite(int tempsPenalite) {
		this.tempsPenalite = tempsPenalite;
	}

	public String ToJson()
	{
		return SerializateurJson.objectToJson(this);
	}
	
	static public Penalite JsonToBut(String s)
	{
		return (Penalite)SerializateurJson.jsonToObject(s, Penalite.class);
	}

}
