import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * The Class AusgabeInDatei.
 */
public class AusgabeInDatei {
	
	/**
	 * Die Methode erstellt eine neue Datei und schreib einen String - 'information' rein.
	 *
	 * @param dateiname the dateiname
	 * @param information the information
	 */
	public void neueDateiErstellen(String dateiname, String information){
		Writer buffer = null;
    	try{
    		buffer = new FileWriter(dateiname);
    		buffer.write(information);
    		buffer.close();
    	}
    	catch(IOException e){
    		System.err.println( "Konnte Datei nicht erstellen" );
    	}
	}
	
	/**
	 * Die Methode ergaenzt die Datei mit 'information'.
	 *
	 * @param dateiname the dateiname
	 * @param information the information
	 */
	public void dateiErgaenzen(String dateiname, String information){
		Writer buffer = null;
    	try{
    		buffer = new FileWriter(dateiname, true);
    		buffer.write(information);
    		buffer.close();
    	}
    	catch(IOException e){
    		System.err.println( "Konnte Datei nicht erstellen" );
    	}
	}
	
	/**
	 * Vor jedem Teststart wird in der Datei 'core.csv' die allgemeine Information ueber
	 * den Testeingaben gespeichert:
	 * Durchlaufnummer, Grenzgewicht des Rucksackes und die Anzahl von Eingabeelementen.
	 *
	 * @param durchlauf the durchlauf
	 * @param grenzgewicht the grenzgewicht
	 * @param anzahlVonGegenstaenden the anzahl von gegenstaenden
	 */
	public void printCharakteristik(int durchlauf, double grenzgewicht, int anzahlVonGegenstaenden){
		String dateiname = "eingabe.csv";
		String information = "Durchlauf Nr.: " + durchlauf + ";Grenzgewicht: " + grenzgewicht + ";ElementenAnzahl: " + anzahlVonGegenstaenden;
		dateiErgaenzen(dateiname, information);
		String dateiname1 = "loesung.csv";
		String information1 = "Durchlauf Nr.: " + durchlauf + ";Grenzgewicht: " + grenzgewicht + ";ElementenAnzahl: " + anzahlVonGegenstaenden;
		dateiErgaenzen(dateiname1, information1);
	}
	
	public void printTestErgebnisse(double anzahlVonGegenstaenden, double coreBreite, double coreObjekten, double zeit, double paretoAnzahl){
		String dateiname = "test.txt";
		String information = anzahlVonGegenstaenden + "\t" + coreBreite + "\t" + coreObjekten + "\t" + zeit + "\t" + paretoAnzahl + "\n";
		dateiErgaenzen(dateiname, information);
	}
	
	/**
	 * Die Methode speichert in der Datei - 'core.csv' die Information ueber jedem Gegenstand.
	 *
	 * @param listeVonGegenstaenden the liste von gegenstaenden
	 */
	public void printAlleGegenstaende(ArrayList<Gegenstand> listeVonGegenstaenden){
    	Writer buffer = null;
    	try{
    		buffer = new FileWriter("eingabe.csv", true);
    		buffer.write("\nId;Gewicht;Nutzen;Nutzendichte");
    		for (Gegenstand i : listeVonGegenstaenden){
    			buffer.write("\n" + i.getId() + ";" + i.getGewicht() + ";" + i.getNutzen() + ";" + i.getNutzendichte());
    		}
    		buffer.write("\n\n");
    		buffer.close();
    	}
    	catch(IOException e){
    		System.err.println( "Konnte Datei nicht erstellen" );
    	}
    }    
    
	/**
	 * Die Methode speichert in der Datei - 'core.csv' Information ueber die Break-Loesung.
	 *
	 * @param sack the sack
	 * @param bLoesung the b loesung
	 */
	public void printBreakLoesung(Rucksack sack, BreakLoesung bLoesung){
    	String dateiname = "loesung.csv";
    	String information = "\nBreak-Loesung:;" + sack.getInhalt().toString() +
    			"\nGewicht der Break-Loesung:;" + sack.getAktuellesGewicht() +
    			"\nNutzen der Break-Loesung:;" + sack.getAktuellerNutzen() +
    			"\nGewichtskapazitaet:;" + sack.getGewichtskapazitaet() +
    			"\nBreak-Elemetnt (ID):;" + bLoesung.getBreakObjekt();
    	dateiErgaenzen(dateiname, information);
    }
    
    /**
     * Die Methode speichert in der Datei - 'core.csv' Information ueber
     * den fraktionelle Nutzen und die Nutzendichte des die Break-Elementes.
     *
     * @param bLoesung the b loesung
     */
    public void printFraktionelleLoesung(BreakLoesung bLoesung){
    	String dateiname = "loesung.csv";
    	String information = "\nSteigung des Break-Strahls: ;" + bLoesung.getStrahlSteigung() +
    			"\nFrak. Nutzen: ;" + bLoesung.getFraktionelleNutzen();
    	dateiErgaenzen(dateiname, information);
    }
    
    /**
     * Die Methode ergaenzt die Datei 'core.csv' mit der Information uber Core.
     * Die Gegenstaende, die im Core liegen, werden austeigend nach ihren ID's sortiert.
     *
     * @param coreObjekten the core objekten
     * @param core the core
     */
    public void printCoreObjekte(ArrayList<Gegenstand> coreObjekten, CoreClass core){
    	ArrayList<Integer> coreInhalt = new ArrayList<Integer>();
    	for (Gegenstand i : coreObjekten){
			coreInhalt.add(i.getId());
		}
    	Collections.sort(coreInhalt);
    	
    	String dateiname = "loesung.csv";
    	String information = "\nCorebreite:;" + core.getCoregrenze()*2 + "\nCore-Objekte:;" + coreInhalt.toString();
    	dateiErgaenzen(dateiname, information);
    }    
    
    /**
    * Die Methode schreibt in der Datei 'core.csv' die Gegenstaende, die unter Core ligen
    * und nach Berechnung des Core nicht weiter untersucht sein sollen.
    *
    * @param listeVonGegenstaenden the liste von gegenstaenden
    */
    public void printOutsider(ArrayList<Gegenstand> listeVonGegenstaenden, ArrayList<Gegenstand> coreObjekte){
    	ArrayList<Integer> outsider = new ArrayList<Integer>();
    	for (Gegenstand i : listeVonGegenstaenden){
			if (i.getZustand() == 0 && !coreObjekte.contains(i)){
				outsider.add(i.getId());
			}
		}
    	Collections.sort(outsider);
    	
    	String dateiname = "loesung.csv";
    	String information = "\nObjekte, die unter Core liegen:;" + outsider.toString();
    	dateiErgaenzen(dateiname, information);
    }
    
    /**
     * Die Methode speichert in 'core.csv' der aktuellen Inhalt des Rucksackes.
     *
     * @param sack the sack
     */
    public void printRucksackinhalt(Rucksack sack){
    	String dateiname = "loesung.csv";
    	String information = "\nRucksackinhalt: ;" + sack.getInhalt().toString() +
    			"\nRucksackgewicht: ;" + sack.getAktuellesGewicht() +
    			"\nRucksacknutzen: ;" + sack.getAktuellerNutzen();
    	dateiErgaenzen(dateiname, information);
    }
    
    /**
     * Die Methode schreibt in 'core.csv' alle optimalle Pretomengen,
     * die Nemmhauser/Ullmann-Algorithmus ausrechnet.
     *
     * @param paretoMenge the pareto menge
     */
    public void printParetoMengen(ArrayList<Loesung> paretoOpt){
    	Writer buffer = null;
    	try{
    		buffer = new FileWriter("loesung.csv", true);
//    		buffer.write("Die Anzahl der ParetoOptimal: " + paretoOpt.size() + "\n");
    		
    		Iterator<Loesung> itPareto = paretoOpt.iterator();    		
    		while (itPareto.hasNext()){
    			Loesung element = itPareto.next();
    			double gewicht = element.getGewicht();
    			double nutzen = element.getNutzen();
    			ArrayList<Integer> paretoOptimalID = new ArrayList<Integer>();
    			
    		    // Finden ParetoOptimalID
    			paretoIdsFinden(element,paretoOptimalID);
    			Collections.reverse(paretoOptimalID);
        		
    			buffer.write("\npareto-optimale Lösung: " + paretoOptimalID + ";Gewicht: " + gewicht  + ";Nutzen: " +  nutzen);
        		buffer.flush();
        	}

    		
    		buffer.close();
    	}
    	catch(IOException e){
    		System.err.println( "Konnte Datei nicht erstellen" );
    	}
    }
    
    public ArrayList<Integer> paretoIdsFinden(Loesung objekt, ArrayList<Integer> paretoId ){
	 	
    	if (objekt.getId() != 0){
    		paretoId.add(objekt.id);
    		paretoIdsFinden(objekt.verweisVorherigeLoesung, paretoId);
    		
    		return paretoId;
    	} else {
    		return paretoId;
    	}    	
    }
    
    /**
     * Prints the gesamtzeit.
     *
     * @param zeit the zeit
     */
    public void printGesamtzeit(long zeit){
    	String dateiname = "loesung.csv";
    	String information = "\nDie gesamte Laufzeit:;" + zeit + " Millisek.\n\n";
    	dateiErgaenzen(dateiname, information);
    }
    
    /**
     * Die Methode ergaenzt die Datei 'satistik.csv' mit der wichtigsten Information ueber den Testablauf.
     *
     * @param anzahlVonGegenstaenden the anzahl von gegenstaenden
     * @param coreObjekten the core objekten
     * @param zeit the zeit
     */
    public void printStatistik(int anzahlVonGegenstaenden, double coreBreite, ArrayList<Gegenstand> coreObjekten, long zeit, int paretoAnzahl, int  bElement){
    	String dateiname = "core.csv";
    	String information = "\n" + anzahlVonGegenstaenden + ";" + coreBreite + ";" + coreObjekten.size() + ";" + zeit + ";" + paretoAnzahl + ";" + bElement;
    	dateiErgaenzen(dateiname, information);
    }
}
