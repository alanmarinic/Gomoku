package inteligenca;

import java.util.List;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;

// Algoritem, prek katerega AI izbira svojo naslednjo potezo
public class Alphabeta extends Inteligenca {
	// Vrednost zmage
	private static final int ZMAGA = Integer.MAX_VALUE;
	// Vrednost izgube
	private static final int ZGUBA = -ZMAGA;
	// Vrednost neodločene igre
	private static final int NEODLOC = 0;
	
	// Koliko potez vnaprej pogleda računalnik
	private int globina;
	
	public Alphabeta (int globina) {
		super("alphabeta globina " + globina);
		this.globina = globina;
	}

	// Izbere naslednjo potezo
	@Override
	public Koordinati izberiPotezo (Igra igra) {
		// Na začetku alpha = ZGUBA in beta = ZMAGA
		return alphabetaPoteze(igra, this.globina, ZGUBA, ZMAGA, igra.naPotezi()).poteza;
	}
	
	// Oceni in izbere najboljšo potezo
	public static OcenjenaPoteza alphabetaPoteze(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		// Definiria in glede na to kdo je na vrsti določi začento oceno
		int ocena;
		if (igra.naPotezi() == jaz) {ocena = ZGUBA;} 
		else {ocena = ZMAGA;}
		
		// Seznam vseh možnih potez
		List<Koordinati> moznePoteze = igra.poteze();
		// Možno je, da se ne spremini vrednost kanditata. Zato ne more biti null.
		Koordinati kandidat = moznePoteze.get(0);
		
		// Sprehodi se čez vse možne poteze
		for (Koordinati p: moznePoteze) {
			// Ustvari kopijo igre, zato da se igrana igra ne spreminja na zaslonu
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj (p);
			int ocenap;
			switch (kopijaIgre.stanje()) {
			case ZMAGA_CRNA: ocenap = (jaz == Igralec.Crna ? ZMAGA : ZGUBA); break;
			case ZMAGA_BELA: ocenap = (jaz == Igralec.Bela ? ZMAGA : ZGUBA); break;
			case NEODLOCENO: ocenap = NEODLOC; break;
			default:
				// Nekdo je na potezi
				if (globina == 1) ocenap = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
				else ocenap = alphabetaPoteze (kopijaIgre, globina-1, alpha, beta, jaz).ocena;
			}
			// Maksimiramo oceno
			if (igra.naPotezi() == jaz) {
				// mora biti > namesto >=
				if (ocenap > ocena) {
					ocena = ocenap;
					kandidat = p;
					alpha = Math.max(alpha,ocena);
				}
			}
			// igra.naPotezi() != jaz, torej minimiziramo oceno
			else {
				// mora biti < namesto <=
				if (ocenap < ocena) {
					ocena = ocenap;
					kandidat = p;
					beta = Math.min(beta, ocena);					
				}
			}
			// Izstopimo iz "for loop", saj ostale poteze ne pomagajo
			if (alpha >= beta)
				return new OcenjenaPoteza (kandidat, ocena);
		}
		return new OcenjenaPoteza (kandidat, ocena);
	}
}
