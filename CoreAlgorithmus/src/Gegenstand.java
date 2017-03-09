import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Gegenstand.
 */
public class Gegenstand{

    @Override
	public String toString() {
		return "Gegenstand [id=" + id + ", nutzen=" + nutzen + ", gewicht="
				+ gewicht + ", nutzendichte=" + nutzendichte + ", imRucksack="
				+ zustand + ", vertikaleDistanz=" + vertikaleDistanz
				+ ", paretoIds=" + paretoIds + ", zufall=" + zufall + "]";
	}

	private int id = 0;
    private double nutzen = 0;
    private double gewicht = 0;
    private double nutzendichte = 0;
    private int zustand = 0;
    private double vertikaleDistanz = 0;
    private ArrayList<Integer> paretoIds = new ArrayList<Integer>();

    Random zufall = new Random();
    
    /**
     * Ein leeres Gegenstand erzeugen.
     */
    public Gegenstand(){
    	id = 0;
    	gewicht = 0;
    	nutzen = 0;
    	nutzendichte = 0;
    	zustand = 0;
        paretoIds.clear();
    }
    
    /**
     * Der Konstruktor wird bei der Generierung von Gegenstaende benoetigt.
     * Er erzeugt die Werte für den Gegenstand.
     *
     * @param i ID von dem Gegenstand (int)
     */
    public Gegenstand(int i) {
        id = i+1;
        nutzen = zufall.nextDouble();
        gewicht =  zufall.nextDouble();
        nutzendichte = nutzen/gewicht;
        zustand = 0;
        paretoIds.add(i+1);
    }
    
    public Gegenstand(int genType, int i, double korrelationsgrad){
		if (genType == 1) genUnkorreliert(i);
		else if (genType == 2) genKorelliert(i, korrelationsgrad);
		else if (genType == 3) genGewichtAehnlich(i, korrelationsgrad);
		else if (genType == 4) genNutzenAehnlich(i, korrelationsgrad);
		else if (genType == 5) genNullKorelliert(i);
	}
    
    /**
     * Der Konstruktor wird bei der Werteuebernahme aus der 'eingabe.txt' - Datei gebraucht.
     * Es werden schon fertig generierte Werte für den Gegenstand übernohmen.
     *
     * @param gNummer ID von dem Gegenstand (int)
     * @param gGewicht Gewicht des Gegenstandes (double)
     * @param gNutzen Nutzen des Gegenstandes (double)
     */
    public Gegenstand(double gNummer, double gGewicht, double gNutzen){
    	id = (int)gNummer;
    	gewicht = gGewicht;
    	nutzen = gNutzen;
    	nutzendichte = nutzen/gewicht;
    	zustand = 0;
        paretoIds.add((int)gNummer);
    }
    
    
   public void genUnkorreliert(int i){
		id = i+1;
	    nutzen = zufall.nextDouble();
	    gewicht =  zufall.nextDouble();
	    nutzendichte = nutzen/gewicht;
	    zustand = 0;
	    paretoIds.add(i+1);
	}
	
	public void genKorelliert(int i, double korrelationsgrad){
		id = i+1;
		gewicht = zufall.nextDouble();
	    boolean nichtErzeugt = true;
	    while(nichtErzeugt){
	    	nutzen =  zufall.nextDouble();
	    	if ((nutzen > gewicht - korrelationsgrad) && (nutzen < gewicht + korrelationsgrad)) nichtErzeugt = false;
	    }
	    nutzendichte = nutzen/gewicht;
	    zustand = 0;
	    paretoIds.add(i+1);
	}
	
	public void genGewichtAehnlich(int i, double korrelationsgrad){
		id = i+1;
	    nutzen = zufall.nextDouble();
	    boolean nichtErzeugt = true;
	    while(nichtErzeugt){
	    	gewicht =  1 - korrelationsgrad + zufall.nextDouble();
	    	if (gewicht <=1) nichtErzeugt = false;
	    }
	    nutzendichte = nutzen/gewicht;
	    zustand = 0;
	    paretoIds.add(i+1);
	}
	
	public void genNutzenAehnlich(int i, double korrelationsgrad){
		id = i+1;
		boolean nichtErzeugt = true;
		while(nichtErzeugt){
			nutzen =  1 - korrelationsgrad + zufall.nextDouble();
	    	if (nutzen <=1) nichtErzeugt = false;
	    }
	    gewicht =  zufall.nextDouble();
	    nutzendichte = nutzen/gewicht;
	    zustand = 0;
	    paretoIds.add(i+1);
	}
	
	public void genNullKorelliert(int i){
		id = i+1;
	    nutzen = zufall.nextDouble();
	    gewicht =  nutzen;
	    nutzendichte = nutzen/gewicht;
	    zustand = 0;
	    paretoIds.add(i+1);
	}
    
    /* Die Methodenblock dient fuer die Ein- und Ausgabe den Werten des Gegenstandes. */
    public void setId(int i){
        id = i+1;
    }
    
    public int getId(){
        return id;
    }

    public void setNutzen(double neuenNutzen){
        nutzen = neuenNutzen;
    }

    public double getNutzen(){
        return nutzen;
    }

    public void setGewicht(double neuesGewicht){
        gewicht =  neuesGewicht;
    }

    public double getGewicht(){
        return gewicht;
    }

    public void setNutzendichte(double neueNutzendichte){
        nutzendichte = neueNutzendichte;
    }

    public double getNutzendichte(){
        return nutzendichte;
    }

    public void setZustand(int neuenZustand){
        zustand = neuenZustand;
    }

    public int getZustand(){
        return zustand;
    }
    
    public void setVertikaleDistanz(double neueVertikaleDistanz){
        vertikaleDistanz = neueVertikaleDistanz;
    }

    public double getVertikaleDistanz(){
        return vertikaleDistanz;
    }
    
    public ArrayList<Integer> getParetoIds(){
        return paretoIds;
    }

    public void setParetoIds(List<Integer> idListe){
    	paretoIds.clear();
        paretoIds.addAll(idListe);
    }
    
    public void resetParetoIds(){
    	paretoIds.clear();
    }
}
