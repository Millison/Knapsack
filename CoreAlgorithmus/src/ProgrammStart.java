import java.util.ArrayList;
import java.util.ListIterator;

// TODO: Auto-generated Javadoc
/**
 * Startet das ganzes Programm und erzeugt dabei alle Objekte für die Ein- und Ausgabe.
 * Die Eingaben kann man sowohl im Programm generieren lassen, als auch von einen externen Datei 'eingaben.txt' holen.
 * Format der 'eingaben.txt':
 * Anzahl von Testinstanzen
 * Id Gewicht Nutzen
 *  
 */
public class ProgrammStart {
	
	static ArrayList<Gegenstand> listeVonGegenstaenden = new ArrayList<Gegenstand>();
	static AusgabeInDatei ausgabe = new AusgabeInDatei();
	static double dBreite = 0;
	static double dCoreAnzahl = 0;
	static double dLaufzeit = 0;
	static double dPareto = 0;
	
public static void main(String[] args) {
		
		Eingabe input = new Eingabe(listeVonGegenstaenden);
		
		/*	Bevor die Tests starten, werden zwei Dateien fuer die Ausgabe der Ergebnisse und die Statistik erstellt. */
    	ausgabe.neueDateiErstellen("eingabe.csv", "");
    	ausgabe.neueDateiErstellen("loesung.csv", "");
    	ausgabe.neueDateiErstellen("core.csv", "Anzahl von Eingabeelementen;Core-Breite;Anzahl von Elementen im Core;Laufzeit in Millisek.;Die Anzahl der ParetoOptimal;ID des Break-Elemetes");
    	ausgabe.neueDateiErstellen("test.txt", "Anzahl von Eingabeelementen;Core-Breite;Anzahl von Elementen im Core;Laufzeit in Millisek.;Die Anzahl der ParetoOptimal\n");
    	
    	if (input.getEingabeart() == 1){
    		if (input.getEingabeVerteilung() == 1){
    			double breite = 0;
        		double core = 0;
        		double zeit = 0;
        		double paretoOptimal = 0;
    			for (int durchlaufnummer = 1; durchlaufnummer <= input.getAnzahlVonDurchlaeufen(); durchlaufnummer++){
    				input.eingabenGenerieren(listeVonGegenstaenden);
    				programmAblauf(input.getAnzahlVonGegenstaenden(), durchlaufnummer);
    				breite = breite + dBreite;
    				core = core + dCoreAnzahl;
    				zeit = zeit + dLaufzeit;
    				paretoOptimal = paretoOptimal + dPareto;
    				listeVonGegenstaenden.clear();
    				
    			}
    			dBreite = breite/input.getAnzahlVonDurchlaeufen();
    			dCoreAnzahl = core/input.getAnzahlVonDurchlaeufen();
    			dLaufzeit = zeit/input.getAnzahlVonDurchlaeufen();
    			dPareto = paretoOptimal/input.getAnzahlVonDurchlaeufen();
    			ausgabe.printTestErgebnisse(input.getAnzahlVonGegenstaenden(), dBreite, dCoreAnzahl, dLaufzeit, dPareto);
    		}
    		else if (input.getEingabeVerteilung() > 1 && input.getEingabeVerteilung() < 5){
    			for (double korrelationsgrad = 0.01; korrelationsgrad >= 0.001; korrelationsgrad=korrelationsgrad-0.0015){
    				input.korrelierteEingabenGenerieren(listeVonGegenstaenden, korrelationsgrad);
    				programmAblauf(input.getAnzahlVonGegenstaenden(), 1);
    				ausgabe.printTestErgebnisse(1/korrelationsgrad, dBreite, dCoreAnzahl, dLaufzeit, dPareto);
    				dBreite = 0;
    				dCoreAnzahl = 0;
    				dLaufzeit = 0;
    				dPareto = 0;
    				listeVonGegenstaenden.clear();
    			}
    		}
    		else if (input.getEingabeVerteilung() == 5){
    			input.korrelierteEingabenGenerieren(listeVonGegenstaenden, 0);
				programmAblauf(input.getAnzahlVonGegenstaenden(), 1);
				ausgabe.printTestErgebnisse(0, dBreite, dCoreAnzahl, dLaufzeit, dPareto);
				dBreite = 0;
				dCoreAnzahl = 0;
				dLaufzeit = 0;
				dPareto = 0;
				listeVonGegenstaenden.clear();
    		}
		}
		else if (input.getEingabeart() == 2){
			programmAblauf(input.getAnzahlVonGegenstaenden(), input.getAnzahlVonDurchlaeufen());
			ausgabe.printTestErgebnisse(input.getAnzahlVonGegenstaenden(), dBreite, dCoreAnzahl, dLaufzeit, dPareto);
		}
		System.out.println("Fertig!");
	}
	
	
	/**
	 * Führt den Test aus. Alle Testeingaben werden in einer Datei - 'core.csv' gespeichert.
	 * Format: Durchlaufnummer; Anzahl der Eingabeelemente; Grenzgewicht des Rucksacks
	 * 
	 * Zuerst wird die Break-Lösung nach Greedy-Algorithmus ausgerechnet, dann fraktionelle Lösung und das Core bestimmt.
	 * Die Gegenstände, die im Core liegen werden mit Nemmhauser/Ullmann-Algorithmus weiter untersucht. Die Ergebnise
	 * von Greedy-Algorithmus und Nemmhauser/Ullmann zusammen gelten als optimale Lösung.
	 * 
	 * Die Ergebnise werden in der Datei 'core.csv' gespeichert.
	 * Format: Id; Gewicht; Nutzen; Nutzendichte (Nutzen durch Gewicht)
	 * Break-Loesung; Gewicht der Break-Loesung; Nutzen der Break-Loesung; Gewichtskapazitaet; Break-Elemetnt (Id); fraktionelle Nutzen;
	 * Steigung des Break-Strahls; Corebreite; Core-Objekte; Objekte, die unter Core liegen; Rucksackinhalt; Rucksackgewicht; Rucksacknutzen;
	 * Die gesamte Laufzeit.
	 * 
	 * @param anzahlVonGegenstaenden Anzahl von Gegenständen (int)
	 * @param durchlauf Testnummer (int)
	 */
	public static void programmAblauf(int anzahlVonGegenstaenden, int durchlauf){
    	final long startzeit = System.currentTimeMillis();
        
    	double gesamtGewicht = 0;
    	ListIterator<Gegenstand> itrGegenstand = listeVonGegenstaenden.listIterator();
    	while (itrGegenstand.hasNext()){
    		gesamtGewicht = gesamtGewicht + itrGegenstand.next().getGewicht();
    	}
    	
    	Rucksack sack = new Rucksack(gesamtGewicht, anzahlVonGegenstaenden); // Kostruktoreingabe für den Grenzgewicht
        ausgabe.printCharakteristik(durchlauf, sack.getGrenzgewicht(), anzahlVonGegenstaenden);
        ausgabe.printAlleGegenstaende(listeVonGegenstaenden);
        
        BreakLoesung bLoesung = new BreakLoesung(listeVonGegenstaenden, sack);	// Berechnung der Break-Loesung, des Break-Elementes und fraktionelle Lösung.
        bLoesung.setNutzenDerFraktionellenLoesung(sack);
        ausgabe.printBreakLoesung(sack, bLoesung);
        ausgabe.printFraktionelleLoesung(bLoesung);
        
        CoreClass coreInstanz = new CoreClass(listeVonGegenstaenden, bLoesung, sack);
//        coreInstanz.optimaleCoregrenze(bLoesung, sack);
        
        ArrayList<Gegenstand> coreObjekte = new ArrayList<Gegenstand>(); // Liste von der Gegenstaende, die im Core liegen
        ArrayList<Gegenstand> paretoMengenVonCore = new ArrayList<Gegenstand>();
        
        /* Core wird mehrmals Bestimmt.
         * Als Abbruchbedingugng gilt die Gleichheit der Corebreite aus letzten Iteration
         * und die Corebreite, die erneut ausgerechnet ist. */
        double letzterRucksacknutzen = 0;
        double aktuellerRucksacknutzen = Math.rint( sack.getAktuellerNutzen() * 1000000 ) / 1000000.; //Math.rint( coreInstanz.getCoregrenze() * 1000000 ) / 1000000.;
        double coreBreite = 0;
        int paretoAnzahl = 0;
        
        while (letzterRucksacknutzen < aktuellerRucksacknutzen){	// so lasng der Rucksacknutzen steigt
        	letzterRucksacknutzen = aktuellerRucksacknutzen;
        	coreInstanz.coreBestimmen(listeVonGegenstaenden, sack, coreObjekte);	// Bestimme die Gegenstände, die im Core liegen
        	
        	ausgabe.printCoreObjekte(coreObjekte, coreInstanz);
            ausgabe.printOutsider(listeVonGegenstaenden, coreObjekte);	// Schreibe in 'core.csv' die Gegenstände, die unter Core liegen
            coreBreite = coreInstanz.getCoregrenze();
            
            paretoMengenVonCore.clear();
            NemhauserUllmann paretoMengen = new NemhauserUllmann(coreObjekte, sack.getGewichtskapazitaet()); // Untersuche die Core-Elemente weiter mit dem Nemmhauser/Ullmann - Algorithmus

//            ausgabe.printParetoMengen(NemhauserUllmann.paretoOptimal);	// Schreibe alle optimalle Pretomengen in 'core.csv'
            /* Ergebnis von Greedy-Algorithmus (ohne Core-Gegenstaende) werden mit dem Ergebnis von Nemmhauser/Ullmann zusammengefuegt.
             * Die Loesung gilt als optimale Loesung. Sie wird aber bei jedem Durchlauf immer neu ausgerechnet. */
            coreInstanz.optimalerInhalt(listeVonGegenstaenden, paretoMengen, sack);
            coreInstanz.optimaleCoregrenze(bLoesung, sack);	// Berechne die neue Coregrenze
            aktuellerRucksacknutzen = Math.rint( sack.getAktuellerNutzen() * 1000000 ) / 1000000.;
            
            paretoAnzahl = NemhauserUllmann.paretoOptimal.size();
            NemhauserUllmann.paretoOptimal.clear();
        }
        
        ausgabe.dateiErgaenzen("loesung.csv", "\n; -------- Optimale Loesung -------- ");
        ausgabe.printRucksackinhalt(sack);
        
        /* Nach dem Test wird die verbrauchte Zeit in der 'core.csv' und 'statistic.csv' - Dateien gespeichert.
         * In der 'statistic.csv' werden noch die Anzahl von Eingabe- und Core-Gegenstaende gespeichert. */
        final long endezeit = System.currentTimeMillis();
        ausgabe.printGesamtzeit(endezeit - startzeit);
        ausgabe.printStatistik(anzahlVonGegenstaenden, coreBreite, coreObjekte, endezeit - startzeit, paretoAnzahl, bLoesung.getBreakObjekt());
//        ausgabe.printTestErgebnisse(anzahlVonGegenstaenden, coreBreite, coreObjekte.size(), endezeit - startzeit, paretoAnzahl);
        dBreite = coreBreite;
        dCoreAnzahl = coreObjekte.size();
        dLaufzeit = endezeit - startzeit;
        dPareto = paretoAnzahl;
        sack = null;
        bLoesung = null;
        coreInstanz = null;
    }
	
}
