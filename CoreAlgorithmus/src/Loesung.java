
public class Loesung {
	int id;
	double gewicht;
	double nutzen;
	Loesung verweisVorherigeLoesung; // TODO Verweis auf vorherige Loesung, um die Kombination der Gegenstaende zu finden.
	
	// Leeres Element einfuegen
	public Loesung(int i) {
		// TODO Auto-generated constructor stub
		setGewicht(i);
		setNutzen(i);
		setId(i);
	}
	
	public Loesung(Loesung alteLoesung, Gegenstand element) {
		// TODO Auto-generated constructor stub
		setId(element.getId());
		setGewicht(alteLoesung.getGewicht() + element.getGewicht());
		setNutzen(alteLoesung.getNutzen() + element.getNutzen());   
		verweisVorherigeLoesung = alteLoesung;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setGewicht (double gewicht){
		this.gewicht = gewicht ;
	}
	
	public double getGewicht () {
		return gewicht;
	}
	
	public void setNutzen (double nutzen){
		this.nutzen = nutzen ;
	}
	
	public double getNutzen () {
		return nutzen;
	}

}
