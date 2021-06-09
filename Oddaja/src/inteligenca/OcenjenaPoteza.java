package inteligenca;

import splosno.Koordinati;

// OcenjenaPoteza vsebuje potezo in oceno
public class OcenjenaPoteza {
	Koordinati poteza;
	int ocena;
	
	public OcenjenaPoteza (Koordinati poteza, int ocena) {
		this.poteza = poteza;
		this.ocena = ocena;
	}

	// Primerja oceni dveh OcenjenihPotez
	public int compareTo (OcenjenaPoteza op) {
		if (this.ocena < op.ocena) return -1;
		else if (this.ocena > op.ocena) return 1;
		else return 0;
	}
}
