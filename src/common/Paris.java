package common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Paris{
	private List<ParisPersonne> listParisMatch;
	private double totalMise = 0;
	
	public Paris(){
		listParisMatch = new ArrayList<ParisPersonne>();
	}
	
	public void setParis(ParisPersonne parisPersonne){
		listParisMatch.add(parisPersonne);
		totalMise += parisPersonne.getMise();
	}
	
	public List<ParisPersonne> getParis(){
		return listParisMatch;
	}
	
	public String calculerGain(String gagnant, UUID uuid){
		double totalMiseProrata = 0;
		double totalMiseGagnante = 0;
		if(gagnant == "N"){
			for (ParisPersonne parisPersonne : listParisMatch) {
				return "SetBet|Mise de depart:" + parisPersonne.getMise() + "$,Resultat:" + (parisPersonne.getMise() * 0.75) + "$";
			}
		}
		else{
			totalMiseProrata = (0.75 * totalMise);
			for (ParisPersonne parisPersonne : listParisMatch) {
				if(parisPersonne.getUuid() == uuid){
					if(parisPersonne.getEquipe() == gagnant){
						totalMiseGagnante += parisPersonne.getMise();
					}
					else{
						return "SetBet|Mise de depart:" + parisPersonne.getMise() + "$,Resultat:0$";
					}
				}
				else{
					if(parisPersonne.getEquipe() == gagnant){
						totalMiseGagnante += parisPersonne.getMise();
					}
				}
			}
			for (ParisPersonne parisPersonne : listParisMatch) {
				if(parisPersonne.getUuid() == uuid){
					
					double currentMise = parisPersonne.getMise();
					double pourcentageTotalGagnant = currentMise/totalMiseGagnante;
					return "SetBet|Mise de depart:" + parisPersonne.getMise() + "$,Resultat:" + (pourcentageTotalGagnant * totalMiseProrata) + "$";
				}
			}
		}
		return "SetBet|Miss the net";//If UUID is not the same
	}
}
