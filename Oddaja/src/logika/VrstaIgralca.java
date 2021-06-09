package logika;

public enum VrstaIgralca {
	R, C; 

	// Določa vrsto igralca
	@Override
	public String toString() {
		switch (this) {
			case C: return "človek";
			case R: return "računalnik";
			default: assert false; return "";
		}
	}
}
