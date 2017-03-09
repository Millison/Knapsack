import java.util.ArrayList;
import java.util.ListIterator;

public class NemhauserUllmann {
	public static ArrayList<Integer> anzahlDerparetoOptimal = new ArrayList<Integer>(); // Um zu wissen, wie die Anzahl der paretoOptimalMenge fuer jede Schritt waechst.
	
	static ArrayList<Loesung> tmpParetoOptimal = new ArrayList<Loesung>();
	static ArrayList<Loesung> paretoOptimal = new ArrayList<Loesung>();
	
     public NemhauserUllmann (ArrayList<Gegenstand> gegenstaenden, double gewichtskapazitaet){
    	
    	if (paretoOptimal.isEmpty()) {
    		// Leeres Element erzeugen und einfügen
    		paretoOptimal.add(new Loesung(0));
      	}
    	
    	ListIterator<Gegenstand> itElement = gegenstaenden.listIterator(); 
    	while (itElement.hasNext()) {
    		//Holen das Element aus Gegenstaenden
    		Gegenstand element = itElement.next();
    		tmpParetoOptimal.clear();
    		    		
    		ListIterator<Loesung> itLoesung = paretoOptimal.listIterator();
    		while (itLoesung.hasNext()) {
    			Loesung alteLoesung = itLoesung.next();
//    			tmpParetoOptimal.add(new Loesung(alteLoesung, element));
    			if (alteLoesung.getGewicht() + element.getGewicht() <= gewichtskapazitaet){
    				tmpParetoOptimal.add(new Loesung(alteLoesung, element));
    			}
    			 
    		}
    		    		
    		// Waehrend die beide Liste "paretoOptimal" und "rechtsSeite" zusammenfuehren, entfernen auch die dominierte Mengen. Die Ergebnis speichert in paretoOptimal
    		mergeUndDonimierteMengeEntfernen();
    		
    		// Um zu wissen, wie die paretoOptimalMenge waecht.
    		anzahlDerparetoOptimal.add(paretoOptimal.size());
    		    		
    	}
    	 
     }
     
        
    
     public static void mergeUndDonimierteMengeEntfernen() {
    	 
    	ArrayList<Loesung> zwspeicherParetOpt = new ArrayList<Loesung>(paretoOptimal);
    	ListIterator<Loesung> itL = zwspeicherParetOpt.listIterator();
    	
    	ListIterator<Loesung> itR = tmpParetoOptimal.listIterator();
    	paretoOptimal.clear(); // Loeschen alle alte Loesungen in ParetoOpt    	    	
    	
    	while ( itL.hasNext() || itR.hasNext() ) {
			
    		if ( !itL.hasNext() ) {
        		// Fuegen die gesamte tmpparetoOptimal in Liste ein
    			while ( itR.hasNext() ) {    				
    				paretoOptimal.add(itR.next());
        		}
        	} else if ( !itR.hasNext() ) {
        		// Fuegen die gesamte paretoOptimal in Liste ein
        		while ( itL.hasNext() ) {
        			paretoOptimal.add(itL.next());
        		}
    		} else  {
    			
    			Loesung itLelement = itL.next();
    			Loesung itRelement = itR.next();
    			
    			if (itLelement.getGewicht() > itRelement.getGewicht()  && 
    	    			itLelement.getNutzen() > itRelement.getNutzen() ) {
    	    			paretoOptimal.add(itRelement);
    	    			itL.previous();
    	    	} else if ( itLelement.getGewicht() < itRelement.getGewicht()  && 
    	    				itLelement.getNutzen() < itRelement.getNutzen() ) {
    	    			paretoOptimal.add(itLelement);
    	    			itR.previous();
    	        } else if ( itLelement.getGewicht() < itRelement.getGewicht()  && 
    	    				itLelement.getNutzen() > itRelement.getNutzen() ) {
    	    			itL.previous();
    	    	} else if ( itLelement.getGewicht() > itRelement.getGewicht()  && 
    	    				itLelement.getNutzen() < itRelement.getNutzen() ) {
    	    			itR.previous();
    	    	} else if (	itLelement.getGewicht() == itRelement.getGewicht()  && 
    	    				itLelement.getNutzen() > itRelement.getNutzen() ) {
    	    			itL.previous();
    	    	} else if ( itLelement.getGewicht() == itRelement.getGewicht()  && 
    	    				itLelement.getNutzen() < itRelement.getNutzen() ) {
    	    			itR.previous();
    	    	} else if ( itLelement.getGewicht() < itRelement.getGewicht()  && 
    	    				itLelement.getNutzen() == itRelement.getNutzen() ) {
    	    			itL.previous();
    	    	} else if ( itLelement.getGewicht() > itRelement.getGewicht()  && 
	    				itLelement.getNutzen() == itRelement.getNutzen() ) {
	    				itR.previous();
    	    	} else {
    	    			
    	    			paretoOptimal.add(itRelement);
    	    			itR.previous();
    	    		}    	
    		}
      
    	}
    	
    }
    
//     public ArrayList<Loesung> getParetoOptimal() {
// 		return paretoOptimal;
// 	}
    
     
}
