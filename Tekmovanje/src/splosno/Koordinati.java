package splosno;

// Razred definira poteze
public class Koordinati {
	// Definiramo x in y komponenti koorinat
	private int x;
	private int y;
	
	public Koordinati(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// Dobi x-koordinato poteze
	public int getX() {
		return x; 
	}

	// Dobi y-koordinato poteze
	public int getY() {
		return y;
	}

	// Izpiše koordinate
	@Override
	public String toString() {
		return "Koordinati [x=" + x + ", y=" + y + "]";
	}
	
	// Preveri ali imata potezi enake koordinate
	@Override 
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || this.getClass() != o.getClass()) return false;
		Koordinati k = (Koordinati) o;
		return this.x == k.x && this.y == k.y;
	}
	
	@Override
	public int hashCode () {
		int x = this.x ; int y = this.y;
		return (x + y) * (x + y + 1) / 2 + y;
	}
}
