package logika;

import java.util.Arrays;

// Objekt, ki predstavlja eno vrsto na plošči.
// Vrsta na plošči je predstavljena z dvema tabelama doložine 5.
public class Vrsta {	
	// To sta tabeli x in y koordinat.
	public int[] x;
	public int[] y;
	
	public Vrsta(int[] x, int y[]) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Vrsta [x=" + Arrays.toString(x) + ", y=" + Arrays.toString(y) + "]";
	}
}
