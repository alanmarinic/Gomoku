package splosno;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingWorker;

import gui.Okno;
import inteligenca.Minimax;
//import inteligenca.Alphabeta;
import inteligenca.Inteligenca;
import logika.Igra;
import logika.Igralec;
import logika.VrstaIgralca;

public class Vodja {
	// Ustvarimo nove objekte, ki so potrebni za zagon programa
	public static Map<Igralec, VrstaIgralca> vrstaIgralca;
	public static Map<Igralec,KdoIgra> kdoIgra;
	
	public static Okno okno;
	
	public static Igra igra = null;
	public static boolean clovekNaVrsti = false;
	public static int odzivnost = 1; 
	
	// Zažene novo igro
	public static void igramoNovoIgro () {
		igra = new Igra ();
		igramo ();
	}
	
	// Funkcija poganja in osvežuje igro, dokler se ta ne konča
	public static void igramo () {
		okno.osveziGUI();
		switch (igra.stanje()) {
		case ZMAGA_CRNA:
		case ZMAGA_BELA:
		case NEODLOCENO:
			return; // odhajamo iz metode igramo
		case V_TEKU: 
			Igralec igralec = igra.naPotezi();
			VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
			switch (vrstaNaPotezi) {
			case C: 
				clovekNaVrsti = true;
				break;
			case R:
				igrajRacunalnikovoPotezo();
				break;
			}
		}
	}

	// Določi algoritem računalnika Minimax ali Alpabeta
	public static Inteligenca racunalnikovaInteligenca = new Minimax();
	
	// Odigra računalnikovo potezo
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetnaIgra = igra;
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void> () {
			// V ozadju izbere in odigra računalnikovo potezo
			@Override
			protected Koordinati doInBackground() {
				Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				try {TimeUnit.SECONDS.sleep(odzivnost);} catch (Exception e) {};
				return poteza;
			}
			@Override
			protected void done () {
				Koordinati poteza = null;
				try {poteza = get();} catch (Exception e) {};
				if (igra == zacetnaIgra) {
					igra.odigraj(poteza);
					igramo ();
				}
			}
		};
		worker.execute();
	}
	
	// Odigra človekovo potezo
	public static void igrajClovekovoPotezo(Koordinati poteza) {
		if (igra.odigraj(poteza)) clovekNaVrsti = false;
		igramo ();
	}	
}
