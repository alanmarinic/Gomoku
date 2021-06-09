package inteligenca;

import java.util.List;
import java.util.Random;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;

// Algoritem, prek katerega AI izbira svojo naslednjo potezo
public class Minimax extends Inteligenca {
	// Uvozimo Random, za raznolikost računalnikove igre
	private static final Random RANDOM = new Random();
	
	// Vrednost zmage, več kot vsaka druga ocena pozicije
	private static final int ZMAGA = Integer.MAX_VALUE; 
	// Vrednost neodločene igre	
	private static final int NEODLOC = 0;  
	
	// Meja za izbiro prioritetnih potez
	static int meja = 90000;

	public Minimax () {
		super("naključni minimax");
	}
	
	// Izbere naslednjo potezo
	@Override
	public Koordinati izberiPotezo (Igra igra) {
		List<OcenjenaPoteza> ocenjenePoteze = oceni(igra, 1);
		// Izpiše število in ocene najboljših potez
		System.out.println(ocenjenePoteze.size() + " potez z vrednostjo " + ocenjenePoteze.get(0).ocena);
		// Izpiše trenutno nastavljeno prioritetno mejo
		System.out.println(Integer.toString(meja));
		// Izbere naključno potezo iz seznama najboljših
		int i = RANDOM.nextInt(ocenjenePoteze.size());
		return ocenjenePoteze.get(i).poteza;
	}
	
	// Oceni in izbere najboljšo potezo
	public static List<OcenjenaPoteza> oceni(Igra igra, int globina) {
		// Ustvari dva objekta NajboljseOcenjenePoteze
		NajboljseOcenjenePoteze najboljsePoteze = new NajboljseOcenjenePoteze();
		NajboljseOcenjenePoteze najboljseTrenutne = new NajboljseOcenjenePoteze();
		
		// Seznam vseh možnih potez
		List<Koordinati> moznePoteze = igra.poteze();
		
		// Poskusimo vsako potezo v novi kopiji igre
		for (Koordinati p: moznePoteze) {
			// Ustvari kopijo igre, zato da se igrana igra ne spreminja na zaslonu
			Igra tempIgra = new Igra(igra); 
			tempIgra.odigraj (p);
			int ocena;
			switch (tempIgra.stanje()) {
			case ZMAGA_CRNA:
			// p je zmagovalna poteza
			case ZMAGA_BELA: ocena = ZMAGA; break;
			case NEODLOCENO: ocena = NEODLOC; break;
			// Nekdo je na potezi
			default:
				ocena = OceniPozicijo.oceniPozicijo(tempIgra, igra.naPotezi());	
			}
			// Če ima poteza oceno višjo od meje, potezo direktno pripišemo končnim najboljšim
			if (ocena > meja) {najboljsePoteze.addIfBest(new OcenjenaPoteza(p, ocena));}
			// Sicer poteze dodajamo 'trenutnim' najboljšim, s funkcijo, ki določa dolžino seznama
			// sorazmerno z globino, ter sama vstavlja le najboljše poteze
			najboljseTrenutne.vstavi(new OcenjenaPoteza (p, ocena), globina);
		}
		// Če je obstajala kakšna poteza, z oceno višjo od meje,
		// vrenmo končne najboljše poteze in povečamo mejo,
		// saj bodo vse ocene od tu naprej večje
		if (najboljsePoteze.list().size() != 0) {
			meja += 100000;
			return najboljsePoteze.list();
		}
		// Če imamo nastavljeno globino = 1, potem vrenmo kar 'trenutno' najboljše poteze
		if (globina == 1) {najboljsePoteze = najboljseTrenutne;}
		// Če je globina > 1, se namesto čez vse igre ponovno sprehodimo le čez globina najboljših potez
		else {
			for (OcenjenaPoteza p: najboljseTrenutne.list()) {
				// Za vsako izmed 'trenutno' najboljših potez ustvarimo novo igro v ozadju
				NajboljseOcenjenePoteze sezOcen = new NajboljseOcenjenePoteze();
				Igra tempIgra = new Igra(igra); 
				// Odigramo dobre poteze
				tempIgra.odigraj(p.poteza);
				// Ustvarimo seznam OcenjenihPotez, za katere določimo končno oceno po {@globina} novih potez
				sezOcen = najboljsePoteze3(tempIgra,globina-1, igra.naPotezi());
				// Najvišjo izmed teh ocen pripišemo trenutni potezi p, ki jo bomo po možnosti odigrali
				// Dodamo na seznam končnih najboljših
				najboljsePoteze.addIfBest(new OcenjenaPoteza(p.poteza, sezOcen.maxOcena()));
			}
		}
		// Vrnemo seznam končnih najboljših ocen
		return najboljsePoteze.list();
	}
	
	// Vrne seznam vseh potez, ki imajo največjo vrednost z vidike trenutnega igralca na potezi
	public static NajboljseOcenjenePoteze najboljsePoteze3(Igra igra, int globina, Igralec jaz) {
		// Ustvari dva objekta NajboljseOcenjenePoteze
		NajboljseOcenjenePoteze najboljseTrenutne = new NajboljseOcenjenePoteze();
		NajboljseOcenjenePoteze najboljsePoteze = new NajboljseOcenjenePoteze();
		
		// Seznam vseh možnih potez
		List<Koordinati> moznePoteze = igra.poteze();

		// Poskusimo vsako potezo v novi kopiji igre
		for (Koordinati p: moznePoteze) {
			Igra tempIgra = new Igra(igra); 
			tempIgra.odigraj (p);	
			int ocena;
			switch (tempIgra.stanje()) {
			case ZMAGA_CRNA:
			// P je zmagovalna poteza
			case ZMAGA_BELA: ocena = ZMAGA; break;
			case NEODLOCENO: ocena = NEODLOC; break;
			// Nekdo je na potezi
			default:
				ocena = OceniPozicijo.oceniPozicijo(tempIgra, tempIgra.naPotezi().nasprotnik());
			}
			// Če je na potezi nasprotnik, negiramo oceno
			if (jaz == tempIgra.naPotezi()) {ocena = -ocena;}
			najboljseTrenutne.vstavi(new OcenjenaPoteza (p, ocena), globina);
		}
		// Če je globina = 1, potem vrenmo 'trenutno' najboljše poteze
		if (globina == 1) {return najboljseTrenutne;}
		// Če je globina > 1, se namesto čez vse igre ponovno sprehodimo le čez globina najboljših potez
		else {
			for (OcenjenaPoteza p: najboljseTrenutne.list()) {
				Igra tempIgra = new Igra(igra); 
				tempIgra.odigraj(p.poteza);
				najboljsePoteze.dodajSeznam(najboljsePoteze3(tempIgra, globina-1, jaz));
			}
			// Vrnemo seznam najboljših potez
			return najboljsePoteze;
		}
	}
}
