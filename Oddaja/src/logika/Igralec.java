package logika;

public enum Igralec {
	// Gomoku -> Križci krožci
	// Bela = X, Črna = O 
	Bela, Crna;

	 public Igralec nasprotnik() {
         return (this == Bela ? Crna : Bela);
	 }

	 // Pogleda ali je na mestu figura belega ali črnega igalca
	 public Polje getPolje() {
		 return (this == Bela ? Polje.Bela : Polje.Crna);
	 }
	 
	 @Override
	 public String toString() {
		 return (this == Bela ? "Bela" : "Crna");
	 }
}
