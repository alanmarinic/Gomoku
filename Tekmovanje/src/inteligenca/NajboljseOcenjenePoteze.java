package inteligenca;

import java.util.LinkedList;
import java.util.List;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;

public class NajboljseOcenjenePoteze {
	
	private LinkedList<OcenjenaPoteza> buffer;
		
	public NajboljseOcenjenePoteze() {
		this.buffer = new LinkedList<OcenjenaPoteza> ();
	}
	
	public void addIfBest(OcenjenaPoteza ocenjenaPoteza) {
		if (buffer.isEmpty()) buffer.add(ocenjenaPoteza);
		else {
			OcenjenaPoteza op = buffer.getFirst();
			switch (ocenjenaPoteza.compareTo(op)) {
			case 1: 
				buffer.clear();  // ocenjenaPoteza > op
			case 0: // ali 1
				buffer.add(ocenjenaPoteza); // ocenjenaPoteza >= op
			}			
		}
	}
	
	public List<OcenjenaPoteza> list() {
		return (List<OcenjenaPoteza>) buffer;
	}
	
	public void vstavi(OcenjenaPoteza p, int globina) {
		if (buffer.size() < globina) {buffer.add(p);}
		else {
			int min = 0;
			for (int i = 1; i < buffer.size(); i++) {			
				switch (buffer.get(i).compareTo(buffer.get(min))) {
				case 1: 
					continue;  // trenutni > najmanjsi
				case 0:
					min = i; // trenutni = najmanjsi
				case -1:
					min = i; // trenutni < najmanjsi
				}
			}
			switch (p.compareTo(buffer.get(min))) {
			case 1:
				//System.out.println("dela case 1");
				buffer.set(min, p);
			case 0:
				//System.out.println("dela case 0");
				buffer.set(min, p);
			case -1:
				//System.out.println("dela case -1");
				//buffer.set(min, p);
				break;
			}
		}
	}
	
	public void dodajSeznam(NajboljseOcenjenePoteze list) {
		for(OcenjenaPoteza i: list.buffer) { // to ne razumem
			buffer.add(i);
		}
	}
	
	public int maxOcena() {
		int max = Integer.MIN_VALUE;
		for (OcenjenaPoteza p: buffer) {
			if (p.ocena > max) {max = p.ocena;}
		}
		return max;
	}
	
}

