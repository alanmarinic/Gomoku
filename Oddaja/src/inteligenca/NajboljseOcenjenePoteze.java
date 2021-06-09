package inteligenca;

import java.util.LinkedList;
import java.util.List;

// Objekt NajboljseOcenjenePoteze je seznam OcenjenihPotez
public class NajboljseOcenjenePoteze {
	
	private LinkedList<OcenjenaPoteza> buffer;
		
	public NajboljseOcenjenePoteze() {
		this.buffer = new LinkedList<OcenjenaPoteza> ();
	}
	
	// Če je izbrana OcenjenaPoteza najboljša, jo doda v objekt 
	public void addIfBest(OcenjenaPoteza ocenjenaPoteza) {
		// Če je objekt prazen, jo doda
		if (buffer.isEmpty()) buffer.add(ocenjenaPoteza);
		// OcenjenoPotezo primerja s prvo iz objekta
		else {
			OcenjenaPoteza op = buffer.getFirst();
			switch (ocenjenaPoteza.compareTo(op)) {
			// Če je izbrana OcenjenaPoteza boljša, izprazni objekt
			case 1: 
				buffer.clear();  // ocenjenaPoteza > op
			// Če sta potezi enako dobri, jo doda
			case 0: // ali 1
				buffer.add(ocenjenaPoteza); // ocenjenaPoteza >= op
			}		
		}
	}
	
	// Objekt NajboljseOcenjenePoteze spremeni v seznam
	// To storimo da lahko iteriramo po njem
	public List<OcenjenaPoteza> list() {
		return (List<OcenjenaPoteza>) buffer;
	}
	
	// V obstoječ seznam OcenjenihPotez vstavi OcenjenoPotezo
	public void vstavi(OcenjenaPoteza p, int globina) {
		// Če je dolžina seznama manjša od {@globina}, potezo doda
		if (buffer.size() < globina) {buffer.add(p);}
		// Dobi index najslabše poteze
		else {
			int min = 0;
			for (int i = 1; i < buffer.size(); i++) {			
				switch (buffer.get(i).compareTo(buffer.get(min))) {
				case 1:
					// trenutni > najmanjši
					continue;
				case 0:
					// trenutni = najmanjši
					// Če sta oceni enaki, vseeno zamenja potezo, za večjo raznolikost igre
					min = i; 
				case -1:
					// trenutni < najmanjši
					min = i; 
				}
			}
			// Primerja najslabšo potezo seznama s to ki jo vstavlja
			switch (p.compareTo(buffer.get(min))) {
			// Če je že pripadajoča poteza slabša ali enaka, jo zamenja
			case 1:
				buffer.set(min, p);
			case 0:
				buffer.set(min, p);
			case -1:
				//System.out.println("dela case -1");
				//buffer.set(min, p);
				break;
			}
		}
	}
	
	// Združi dva seznama OcenjenihPotez
	public void dodajSeznam(NajboljseOcenjenePoteze list) {
		for(OcenjenaPoteza i: list.buffer) {
			buffer.add(i);
		}
	}
	
	// Iz seznama OcenjenihPotez dobi najvišjo oceno
	public int maxOcena() {
		int max = Integer.MIN_VALUE;
		for (OcenjenaPoteza p: buffer) {
			if (p.ocena > max) {max = p.ocena;}
		}
		return max;
	}	
}
