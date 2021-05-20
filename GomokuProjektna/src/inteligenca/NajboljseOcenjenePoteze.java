package inteligenca;

import java.util.LinkedList;
import java.util.List;

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

	public void add(OcenjenaPoteza ocenjenaPoteza) {
		buffer.add(ocenjenaPoteza);
	}
	
	
}
