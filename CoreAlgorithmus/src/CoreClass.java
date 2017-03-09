import java.util.ArrayList;
import java.util.ListIterator;

// TODO: Auto-generated Javadoc
/**
 * The Class CoreClass.
 */
public class CoreClass {
	
	private double coregrenze = 0;
	
	/**
	 * Der Kostruktor bestimmt die erste Coregrenze.
	 * Die Coregrenze wird weiter im Programmlauf mehrmals neu ausgerechnet.
	 *
	 * @param listeVonGegenstaenden the liste von gegenstaenden
	 */
	public CoreClass(ArrayList<Gegenstand> listeVonGegenstaenden, BreakLoesung bLoesung, Rucksack sack){
//		double erstePositiveDistanz = 0;
//		for (Gegenstand i : listeVonGegenstaenden){
//			if (i.getZustand() == 1){
//				erstePositiveDistanz = i.getVertikaleDistanz();
//			}
//		}
//		coregrenze = bLoesung.getNutzenDerFraktionellenLoesung() - sack.getAktuelleNutzen();
//		if (coregrenze > erstePositiveDistanz && erstePositiveDistanz != 0){
//			coregrenze = erstePositiveDistanz;
//		}
		
		coregrenze = bLoesung.getNutzenDerFraktionellenLoesung() - sack.getAktuellerNutzen();
	}
	
	public void setCoregrenze(double cGrenze){
		coregrenze = cGrenze;
	}
	
	public double getCoregrenze(){
		return coregrenze;
	}
	
	/**
	 * Die Coregrenze wird so lang immer neu ausgerechnet, bis der Rucksack eine optimale Inhalt hat,
	 * bwz. bei mehreren Durlaufen liegen im Core immer die gleiche Elemente.
	 *
	 * @param bLoesung the b loesung
	 * @param sack the sack
	 */
	public void optimaleCoregrenze(BreakLoesung bLoesung, Rucksack sack){		
		coregrenze = bLoesung.getNutzenDerFraktionellenLoesung() - sack.getAktuellerNutzen();
	}
	
	/**
	 * Die Methode bestimmt die Gegenstaende, die im Core liegen.
	 * Fuer die Bestimmung der Core-Grenze werden nach aufsteigender Reihenfolge alle
	 * vertikalen Distanzen aufsummieren, bis sie den fraktionellen Nutzen ueberschreiten.
	 *
	 * @param listeVonGegenstaenden the liste von gegenstaenden
	 * @param sack the sack
	 * @param coreObjekten the core objekten
	 */
	public void coreBestimmen(ArrayList<Gegenstand> listeVonGegenstaenden, Rucksack sack, ArrayList<Gegenstand> coreObjekten){
    	coreObjekten.clear();
    	double sumVertikaleDistanz = 0;
    	for (Gegenstand i : listeVonGegenstaenden){
    		sumVertikaleDistanz = sumVertikaleDistanz + i.getVertikaleDistanz();
    		/* Die Gegenstaende, die gleichzeitig im Core und im Rucksack liegen,
    		 * werden aus dem Rucksack entfernt. Die Rucksack-Eigenschaften werden entsprechend aktualisiert. */
    		if ((sumVertikaleDistanz <= coregrenze) && sack.getInhalt().contains(i.getId())){
                i.setZustand(0);
                sack.gegenstandRausnehmen(i.getId());
                sack.setAktuellesGewicht(sack.getAktuellesGewicht() - i.getGewicht());
                sack.setAktuellenNutzen(sack.getAktuellerNutzen() - i.getNutzen());
                coreObjekten.add(i);
            }
            else if ((sumVertikaleDistanz <= coregrenze) && !sack.getInhalt().contains(i.getId())){
            	coreObjekten.add(i);
            }
            else {
            	continue;
            }
        }
    }
    
    /**
     * Nemmhauser/Ullmann - Algorithmus liefert eine Liste von pareto-optimalen Mengen,
     * die nach Gewicht und Nutzen aufsteigend sortiert ist.
     * Aus der Liste wird die optimale Menge gesucht und zu aktueller Rucksackinhalt addiert.
     *
     * @param listeVonGegenstaenden the liste von gegenstaenden
     * @param paretoObjekte the pareto objekte
     * @param sack the sack
     */    
    public void optimalerInhalt(ArrayList<Gegenstand> listeVonGegenstaenden, NemhauserUllmann paretoMengen, Rucksack sack){

    	ListIterator<Loesung> itrPareto = paretoMengen.paretoOptimal.listIterator();
    	ArrayList<Integer> paretoIds = new ArrayList<Integer>();
		Loesung loesung = new Loesung(0);
		
    	while (itrPareto.hasNext()){
			if (itrPareto.next().gewicht <= sack.getGewichtskapazitaet()){
				loesung = itrPareto.previous();
				itrPareto.next();
			}
			else break;
		}
    	
    	double gewicht = loesung.gewicht;
    	double nutzen = loesung.nutzen;
    	while(loesung.verweisVorherigeLoesung != null){
			if (!paretoIds.contains(loesung.id)){
				paretoIds.add(loesung.id);
			}
			loesung = loesung.verweisVorherigeLoesung;
		}
		sack.setParetoImSack(paretoIds);
		sack.setAktuellesGewicht(sack.getAktuellesGewicht() + gewicht);
		sack.setAktuellenNutzen(sack.getAktuellerNutzen() + nutzen);
		
		for (Gegenstand i: listeVonGegenstaenden){
			if (paretoIds.contains(i.getId())){
				i.setZustand(1);
				continue;
			}
		}
	}

}
