package common;

import java.util.UUID;

public class ParisPersonne {
	private String equipe;
	private double mise;
	private UUID uuid;
	
	public ParisPersonne(String equipe, double mise, UUID uuid){
		this.setEquipe(equipe);
		this.setMise(mise);
	}

	/**
	 * @return the equipe
	 */
	public String getEquipe() {
		return equipe;
	}

	/**
	 * @param equipe the equipe to set
	 */
	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}

	/**
	 * @return the mise
	 */
	public double getMise() {
		return mise;
	}

	/**
	 * @param mise the mise to set
	 */
	public void setMise(double mise) {
		this.mise = mise;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
}
