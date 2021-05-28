package inteligenca;

import java.util.List;
import java.util.Random;

import logika.Igra;
import logika.Igralec;
//import logika.Igralec;
import splosno.Koordinati;

/*
public class Minimax extends Inteligenca {
	
	private static final int ZMAGA = 100; // vrednost zmage
	private static final int ZGUBA = -ZMAGA;  // vrednost izgube
	private static final int NEODLOC = 0;  // vrednost neodločene igre	
	
	private int globina;
	
	public Minimax (int globina) {
		super("minimax globina " + globina);
		this.globina = globina;
	}
	
	@Override
	public Koordinati izberiPotezo (Igra igra) {
		OcenjenaPoteza najboljsaPoteza = minimax(igra, this.globina, igra.naPotezi());
		return najboljsaPoteza.poteza;	
	}
	
	// vrne najboljso ocenjeno potezo z vidike igralca jaz
	public OcenjenaPoteza minimax(Igra igra, int globina, Igralec jaz) {
		OcenjenaPoteza najboljsaPoteza = null;
		List<Koordinati> moznePoteze = igra.poteze();
		for (Koordinati p: moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj (p);
			int ocena;
			switch (kopijaIgre.stanje()) {
			case ZMAGA_CRNA: ocena = (jaz == Igralec.Crna ? ZMAGA : ZGUBA); break;
			case ZMAGA_BELA: ocena = (jaz == Igralec.Bela ? ZMAGA : ZGUBA); break;
			case NEODLOCENO: ocena = NEODLOC; break;
			default:
				// nekdo je na potezi
				if (globina == 1) ocena = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
				// globina > 1
				else ocena = minimax(kopijaIgre, globina-1, jaz).ocena;	
			}
			if (najboljsaPoteza == null 
					// max, če je p moja poteza
					|| jaz == igra.naPotezi() && ocena > najboljsaPoteza.ocena
					// sicer min 
					|| jaz != igra.naPotezi() && ocena < najboljsaPoteza.ocena)
				najboljsaPoteza = new OcenjenaPoteza (p, ocena);		
		}
		return najboljsaPoteza;
	}
}
*/

public class Minimax extends Inteligenca {
	
	private static final Random RANDOM = new Random();
	
	private static final int ZMAGA = Integer.MAX_VALUE-1000000; // vrednost zmage, več kot vsaka druga ocena pozicije
	private static final int NEODLOC = 0;  // vrednost neodločene igre	
	
	public Minimax () {
		super("naključni minimax");
	}
	
	@Override
	public Koordinati izberiPotezo (Igra igra) {
		List<OcenjenaPoteza> ocenjenePoteze = oceni(igra, 5);
		System.out.println(ocenjenePoteze.size() + " potez z vrednostjo " + ocenjenePoteze.get(0).ocena);
		int i = RANDOM.nextInt(ocenjenePoteze.size());	
		return ocenjenePoteze.get(i).poteza;		
	}

	// vrne seznam vseh potez, ki imajo največjo vrednost z vidike trenutnega igralca na potezi
	public static List<OcenjenaPoteza> najboljsePoteze(Igra igra, int globina) {
		NajboljseOcenjenePoteze najboljsePoteze = new NajboljseOcenjenePoteze();
		List<Koordinati> moznePoteze = igra.poteze();
		for (Koordinati p: moznePoteze) {
			Igra tempIgra = new Igra(igra); 
			tempIgra.odigraj (p);	//poskusimo vsako potezo v novi kopiji igre
			int ocena;
			switch (tempIgra.stanje()) {
			case ZMAGA_CRNA:
			case ZMAGA_BELA: ocena = ZMAGA; break; // p je zmagovalna poteza
			case NEODLOCENO: ocena = NEODLOC; break;
			default: //nekdo je na potezi
				if (globina==1) ocena = OceniPozicijo.oceniPozicijo(tempIgra,igra.naPotezi());
				else ocena = -najboljsePoteze(tempIgra,globina-1).get(RANDOM.nextInt(globina-1)).ocena;
				//else ocena = -najboljsePoteze(tempIgra,globina-1).get(0).ocena; // - ker je drug igralec 
			}
			//najboljsePoteze.addIfBest(new OcenjenaPoteza(p, ocena));
			najboljsePoteze.vstavi(new OcenjenaPoteza(p, ocena), globina);			
		}
		return najboljsePoteze.list();
	}
	
	

	public static List<OcenjenaPoteza> oceni(Igra igra, int globina) {
		NajboljseOcenjenePoteze najboljsePoteze = new NajboljseOcenjenePoteze();
		NajboljseOcenjenePoteze najboljseTrenutne = new NajboljseOcenjenePoteze();
		List<Koordinati> moznePoteze = igra.poteze();
		for (Koordinati p: moznePoteze) {
			Igra tempIgra = new Igra(igra); 
			tempIgra.odigraj (p);	//poskusimo vsako potezo v novi kopiji igre
			int ocena;
			switch (tempIgra.stanje()) {
			case ZMAGA_CRNA:
			case ZMAGA_BELA: ocena = ZMAGA; break; // p je zmagovalna poteza
			case NEODLOCENO: ocena = NEODLOC; break;
			default:
				ocena = OceniPozicijo.oceniPozicijo(tempIgra, igra.naPotezi());
			}
			najboljseTrenutne.vstavi(new OcenjenaPoteza (p, ocena), globina);
		}
		//if (globina == 1) {najboljsePoteze = najboljseTrenutne;}
		
			for (OcenjenaPoteza p: najboljseTrenutne.list()) {
				NajboljseOcenjenePoteze sezOcen = new NajboljseOcenjenePoteze();
				Igra tempIgra = new Igra(igra); 
					tempIgra.odigraj(p.poteza);
					sezOcen = najboljsePoteze3(tempIgra,globina-1, igra.naPotezi());
					najboljsePoteze.addIfBest(new OcenjenaPoteza(p.poteza, sezOcen.maxOcena()));
		
		}
		return najboljsePoteze.list();
	}
	
	public static NajboljseOcenjenePoteze najboljsePoteze3(Igra igra, int globina, Igralec jaz) {
		NajboljseOcenjenePoteze najboljseTrenutne = new NajboljseOcenjenePoteze();
		NajboljseOcenjenePoteze najboljsePoteze = new NajboljseOcenjenePoteze();
		List<Koordinati> moznePoteze = igra.poteze();
		
		for (Koordinati p: moznePoteze) {
			Igra tempIgra = new Igra(igra); 
			tempIgra.odigraj (p);	//poskusimo vsako potezo v novi kopiji igre
			int ocena;
			switch (tempIgra.stanje()) {
			case ZMAGA_CRNA:
			case ZMAGA_BELA: ocena = ZMAGA; break; // p je zmagovalna poteza
			case NEODLOCENO: ocena = NEODLOC; break;
			default:
				ocena = OceniPozicijo.oceniPozicijo(tempIgra, tempIgra.naPotezi().nasprotnik());
			}
			if (jaz == tempIgra.naPotezi()) {ocena = -ocena;}
			najboljseTrenutne.vstavi(new OcenjenaPoteza (p, ocena), globina);
		}
		 if (globina == 1) {return najboljseTrenutne;}
		else {
			for (OcenjenaPoteza p: najboljseTrenutne.list()) {
				Igra tempIgra = new Igra(igra); 
				tempIgra.odigraj(p.poteza);
				najboljsePoteze.dodajSeznam(najboljsePoteze3(tempIgra, globina-1, jaz));
			}
			return najboljsePoteze;
		}
	}

	
}
