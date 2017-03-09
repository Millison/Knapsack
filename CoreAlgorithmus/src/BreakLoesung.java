import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// TODO: Auto-generated Javadoc
/**
 * The Class BreakLoesung.
 */
public class BreakLoesung {
	
	private int breakObjekt = 0;
    private double gewichtskapazitaet = 0;
    private double fraktionelleNutzen = 0;
    private double nutzenDerFraktionellenLoesung = 0;
    private double strahlSteigung = 0;
    
    public BreakLoesung(ArrayList<Gegenstand> listeVonGegenstaenden, Rucksack sack){
    	Collections.sort(listeVonGegenstaenden, new AbsteigendSortieren());
    	breakLoesung(listeVonGegenstaenden, sack);
    	fraktionelleLoeusung(listeVonGegenstaenden, sack);
    	vertikaleDistanz(listeVonGegenstaenden);
    	Collections.sort(listeVonGegenstaenden, new AufsteigendSortieren());
    }
    
    /* Die Methoden fuer die Ein- und Ausgabe des Break-Elements, der fraktionelle Loesung,
     * und der Nutzendichte des Break-Elements (entspricht der Steigung des Break-Strahls). */
    public int getBreakObjekt(){
    	return breakObjekt;
    }
    
    public void setBreakObjekt(int bObjekt){
    	breakObjekt = bObjekt;
    }
    
    public double getGewichtskapazitaet(){
    	return gewichtskapazitaet;
    }
    
    public void setGewichtskapazitaet(int gKapazitaet){
    	gewichtskapazitaet = gKapazitaet;
    }
    
    public double getFraktionelleNutzen(){
    	return fraktionelleNutzen;
    }
    
    public void setFraktionelleNutzen(double fNutzen){
    	fraktionelleNutzen = fNutzen;
    }
    
    public void setNutzenDerFraktionellenLoesung(Rucksack sack){
    	nutzenDerFraktionellenLoesung = sack.getAktuellerNutzen() + fraktionelleNutzen;
    }
    
    public double getNutzenDerFraktionellenLoesung(){
    	return nutzenDerFraktionellenLoesung;
    }
    
    public double getStrahlSteigung(){
    	return strahlSteigung;
    }
    
    public void setStrahlSteigung(double sSteigung){
    	strahlSteigung = sSteigung;
    }
    
    /**
     * Die Methode verwendet Greedy Algorithmus fuer die Bestimmung der Break-Loesung.
     * Dabei wird auch das Break-Element bestimmt und gespeichert.
     *
     * @param listeVonGegenstaenden the liste von gegenstaenden
     * @param sack the sack
     */
    public void breakLoesung(ArrayList<Gegenstand> listeVonGegenstaenden, Rucksack sack){
    	for (Gegenstand i : listeVonGegenstaenden){
            if ( (sack.getAktuellesGewicht() + i.getGewicht()) <= sack.getGrenzgewicht()){
            	sack.setAktuellesGewicht(sack.getAktuellesGewicht() + i.getGewicht());
            	sack.setAktuellenNutzen(sack.getAktuellerNutzen() + i.getNutzen());
                i.setZustand(1);
            }
            else{
                i.setZustand(-1);
                breakObjekt = i.getId();
                strahlSteigung = i.getNutzendichte();
                break;	// wenn Break-Element gefunden ist, abbrechen
            }
        }
        gewichtskapazitaet = sack.getGrenzgewicht() - sack.getAktuellesGewicht();
        sack.setInhalt(listeVonGegenstaenden);
    }
    
    /**
     * Die Methode berechnet aufgrund der Restkapazitaet des Rucksacks eine fraktionelle Loesung =
     * Bruchteil des Break-Elements, die im Rucksack noch passen koennte.
     *
     * @param listeVonGegenstaenden the liste von gegenstaenden
     */
    public void fraktionelleLoeusung(ArrayList<Gegenstand> listeVonGegenstaenden, Rucksack sack){
    	
        for (Gegenstand i : listeVonGegenstaenden){
            if (i.getZustand() == -1){
                fraktionelleNutzen = gewichtskapazitaet * i.getNutzen()/i.getGewicht(); // ist keine f-Funktion
                break;
            }
        }
    }
    
    /**
     * Die Methode berechnet f�r jeden Gegenstand die vertikale Distanz zu dem Break-Strahl.
     * In Abhaengigkeit davon, ob der Gegenstand ueber oder unter den Break-Strahl ist,
     * wird man anderes berechnet.
     *
     * @param listeVonGegenstaenden the liste von gegenstaenden
     */
    public void vertikaleDistanz(ArrayList<Gegenstand> listeVonGegenstaenden){
        for (Gegenstand i : listeVonGegenstaenden){
        	double distanz = 0;
        	distanz = Math.abs(i.getNutzen() - (strahlSteigung * i.getGewicht()));
        	i.setVertikaleDistanz(distanz);
        }
    }
}

class AbsteigendSortieren implements Comparator<Gegenstand>{
	@Override
	public int compare(Gegenstand a1, Gegenstand a2) {
		if (a1.getNutzendichte() > a2.getNutzendichte()){
			return -1;
		}
		if (a1.getNutzendichte() < a2.getNutzendichte()){
			return 1;
		}
		else{
			return 0;
		}
	}
}

class AufsteigendSortieren implements Comparator<Gegenstand>{
	@Override
	public int compare(Gegenstand a1, Gegenstand a2) {
		if (a1.getVertikaleDistanz() < a2.getVertikaleDistanz()){
			return -1;
		}
		if (a1.getVertikaleDistanz() > a2.getVertikaleDistanz()){
			return 1;
		}
		else{
			return 0;
		}
	}
}
