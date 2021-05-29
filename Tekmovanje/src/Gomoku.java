import gui.Okno;
import splosno.Vodja;

// Glavni razred, ki začne program		
public class Gomoku {
	
	public static void main(String[] args) {
		Okno glavno_okno = new Okno();
		glavno_okno.pack();
		glavno_okno.setVisible(true);
		Vodja.okno = glavno_okno;
	}
		
}
