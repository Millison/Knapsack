import java.util.ArrayList;
import java.util.Collections;

// TODO: Auto-generated Javadoc
/**
 * The Class Rucksack.
 */
public class Rucksack {
    
    private double grenzgewicht;
    private double aktuellesRucksackgewicht;
    private double aktuellenRucksacknutzen;
    private double gewichtskapazitaet = 0;
    private ArrayList<Integer> gegenstaendeImRucksack;
    
    /**
     * Der Konstruktor definiert die Eigenschaften von dem Rucksack:
     * 'grenzgewicht' = Groesse des Rucksacks,
     * 'aktuellesRucksackgewicht', 'aktuellenRucksacknutzen' = Füllung des Rucksacks und 
     * 'gegenstaendeImRucksack' = die Nummern von den Gegenstaenden, die im Moment im Rucksack sind.
     *
     * @param gGewicht Grenzgewicht des Rucksackes
     */
    public Rucksack(double gGewicht, int anzahlVonGegenstaenden){
        grenzgewicht = gGewicht/2; // Haelfte des gesamten Gewichtes aller Gegenstaende
        aktuellesRucksackgewicht = 0;
        aktuellenRucksacknutzen = 0;
        gewichtskapazitaet = grenzgewicht - aktuellesRucksackgewicht;
        gegenstaendeImRucksack = new ArrayList<Integer>();
    }
    
    public void setGrenzgewicht(int gGewicht){
        grenzgewicht = gGewicht;
    }

    public double getGrenzgewicht(){
        return grenzgewicht;
    }

    public void setAktuellesGewicht(double aGewicht){
        aktuellesRucksackgewicht = aGewicht;
        gewichtskapazitaet = grenzgewicht - aktuellesRucksackgewicht;
    }

    public double getAktuellesGewicht(){
        return aktuellesRucksackgewicht;
    }

    public void setAktuellenNutzen(double aNutzen){
        aktuellenRucksacknutzen = aNutzen;
    }

    public double getAktuellerNutzen(){
        return aktuellenRucksacknutzen;
    }
    
    public double getGewichtskapazitaet(){
    	return gewichtskapazitaet;
    }
    
    /**
     * Fügt die Gegenstände im Rucksack.
     * Die Gegenstaende, die im Rucksack passen, bzw. im Rucksack sind, haben den Zustand = 1
     *
     * @param listeVonGegenstaenden Liste von den Gegenstände
     */
    public void setInhalt(ArrayList<Gegenstand> listeVonGegenstaenden){
    	gegenstaendeImRucksack.clear();
        for (Gegenstand index: listeVonGegenstaenden){
            if (index.getZustand() == 1){
                gegenstaendeImRucksack.add(index.getId());
            }
        }
        /* Fuer die bessere Anschaulichkeit wird die Menge von den Gegenstaenden
         *  immer nach der Gegenstandsnummer aufsteigend sortiert. */
        Collections.sort(gegenstaendeImRucksack);
    }

    public ArrayList<Integer> getInhalt(){
        return gegenstaendeImRucksack;
    }

    public void gegenstandRausnehmen(int id){
        int index = gegenstaendeImRucksack.indexOf(id);
        if (gegenstaendeImRucksack.contains(id)){
        	gegenstaendeImRucksack.remove(index);
        }
    }
    
    /**
     * Erweitert die aktuelle Rucksackinhalt durch die Liste von den Gegenstaenden,
     * die zur pareto-optimalen Menge gehören.
     *
     * @param paretoElemente Liste von ID's der Gegenstände (int)
     */
    public void setParetoImSack(ArrayList<Integer> paretoElemente){
    	gegenstaendeImRucksack.addAll(paretoElemente);
    	Collections.sort(gegenstaendeImRucksack);
    }
}
