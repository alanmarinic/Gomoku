package logika;

public enum Igralec {
	Bela, Crna;

//x = bela , y = crna	

	 public Igralec nasprotnik() {
         return (this == Bela ? Crna : Bela);
	 }

	 public Polje getPolje() {
		 return (this == Bela ? Polje.Bela : Polje.Crna);
	 }
	 
	 @Override
	 public String toString() {
		 return (this == Bela ? "Bela" : "Crna");
	 }

}

