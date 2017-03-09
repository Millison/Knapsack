import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc
/**
 * Die Klasse bestimmt die Eingabeart der Testinstanzen. Die Eingaben kann man sowohl
 * im Programm generieren lassen, als auch von einen externen Datei 'eingaben.txt' holen.
 * Der Kostruktor fragt den Benutzer nach Eingabe-Instanzen.
 * Falls der Benutzer die Instanzen aus der Datei hollen will, erfullt es der Konstruktor.
 * Dabei wird der Test nur ein Mal durchgefuehrt.
 * Anderfalls werden die Anzahl von der Gegenstaende und Durchlaufe gespeichert und weiter im 'ProgrammStart' geliefert.
 * 
 */
public class Eingabe {
	
	private int eingabeVerteilung = 0;
	private int anzahlVonGegenstaenden = 0;
	private int anzahlVonDurchlaeufen = 0;
	private int eingabeart = 0;
	private Scanner benutzerEingabe = new Scanner(System.in);
	
	public Eingabe(ArrayList<Gegenstand> listeVonGegenstaenden){
		
		System.out.println("1 - Eingaben im Programm generieren lassen.");
		System.out.println("2 - Eingaben aus externe Datei.");
		eingabeart = benutzerEingabe.nextInt();
		
		if (eingabeart == 1){
			System.out.println("Verteilung der Eingaben (1-5):\n"
					+ "\t1 - Unkorreliert\n"
					+ "\t2 - Korreliert\n"
					+ "\t3 - Gewicht-Ähnlich\n"
					+ "\t4 - Nuzzen-Ähnlich\n"
					+ "\t5 - 0-Korreliert\n ");
			eingabeVerteilung = benutzerEingabe.nextInt();
			if (eingabeVerteilung == 1 || eingabeVerteilung < 1 || eingabeVerteilung > 5){
				eingabeVerteilung = 1; // Falls den Benutzer sich vertippt
				System.out.println("Die Anzahl von Eingabeelementen: ");
		    	anzahlVonGegenstaenden = benutzerEingabe.nextInt();
		    	System.out.println("Die Anzahl von den Testabläufe: ");
				anzahlVonDurchlaeufen = benutzerEingabe.nextInt();
			}
			else {
				System.out.println("Die Anzahl von Eingabeelementen: ");
		    	anzahlVonGegenstaenden = benutzerEingabe.nextInt();
			}
			benutzerEingabe = null;
		}
		else if (eingabeart == 2){
			benutzerEingabe = null;
			
			/* Bei der Eingabe aus der Datei wird der Test nur ein Mal durchgefuehrt.
			 * Fuer weitere Tests sollen die Eingabedaten erneut in die externe Datei generiert werden. */
			anzahlVonDurchlaeufen = 1;
			
			try{
				BufferedReader br = new BufferedReader(new FileReader("eingabe.txt"));
			    
			    String zeile = "";
			    anzahlVonGegenstaenden = Integer.parseInt(zeile = br.readLine());
			    double[] eingabezahl = new double[3];
			    
			    while( (zeile = br.readLine()) != null ){
			    	StringTokenizer st = new StringTokenizer(zeile, " ");
			    	eingabezahl[0] = Double.valueOf(st.nextToken());
			    	eingabezahl[1] = Double.valueOf(st.nextToken());
			    	eingabezahl[2] = Double.valueOf(st.nextToken());
			        Gegenstand  objekt = new Gegenstand(eingabezahl[0], eingabezahl[1], eingabezahl[2]);
		            listeVonGegenstaenden.add(objekt);
			    }
			    br.close();
			}
			catch ( IOException e ){
				System.err.println( "Konnte Datei nicht finden!" );
			}
		}
		else{
			System.out.println("Die Option gibt es nicht!");
		}
	}
	
	public void setEingabeVerteilung(int verteilung){
		eingabeVerteilung = verteilung;
	}
	
	public int getEingabeVerteilung(){
		return eingabeVerteilung;
	}
	
	public void setAnzahlVonGegenstaenden(int anzahl){
		anzahlVonGegenstaenden = anzahl;
	}
	
	public int getAnzahlVonGegenstaenden(){
		return anzahlVonGegenstaenden;
	}
	
	public void setAnzahlVonDurchlaeufe(int anzahl){
		anzahlVonDurchlaeufen = anzahl;
	}
	
	public int getAnzahlVonDurchlaeufen(){
		return anzahlVonDurchlaeufen;
	}
	
	public void setEingabeart(int wahl){
		eingabeart = wahl;
	}
	
	public int getEingabeart(){
		return eingabeart;
	}
	
	public void eingabenGenerieren(ArrayList<Gegenstand> listeVonGegenstaenden){
		/* Fuer jeden Durchlauf werden die Eingaben generiert und in der Liste - 'listeVonGegenstaende' gespeichert  */
		for (int i=0; i < anzahlVonGegenstaenden; i++){
            Gegenstand objekt = new Gegenstand(i);
            listeVonGegenstaenden.add(objekt);
        }
	}
	
	public void korrelierteEingabenGenerieren(ArrayList<Gegenstand> listeVonGegenstaenden, double korrelationsgrad){
		/* Fuer jeden Durchlauf werden die Eingaben generiert und in der Liste - 'listeVonGegenstaende' gespeichert  */
		for (int i=0; i < anzahlVonGegenstaenden; i++){
            Gegenstand objekt = new Gegenstand(eingabeVerteilung, i, korrelationsgrad);
            listeVonGegenstaenden.add(objekt);
        }
	}

}
