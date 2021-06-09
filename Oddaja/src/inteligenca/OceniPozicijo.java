package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Vrsta;

public class OceniPozicijo {
	// Število štirih enakih krogcev v eni vrsti
	static int three_counter;
	// Število treh enakih krogcev v eni vrsti
	static int four_counter;

	// Oceni celo igralno ploščo z vidika igralca na potezi
	public static int oceniPozicijo (Igra igra, Igralec jaz) {								       
		// Resetira oceno in counterje
		int ocena = 0;
		four_counter = 0;
		three_counter = 0;
		// Oceno vsake vrste na plošči prišteje skupni oceni
		for (Vrsta v : Igra.VRSTE) {
			ocena = ocena + oceniVrsto(v, igra, jaz);
		}
		if (three_counter > 1) {ocena += 50;}
		else if (three_counter > 0 && four_counter > 0) {ocena += 50;}
		return ocena;
	}

	// Oceni posamezno vrsto z vidika igralca na potezi
	public static int oceniVrsto ( Vrsta v, Igra igra, Igralec jaz) {
		Polje[][] plosca = igra.getPlosca();
		// Število belih krogcev v vrsti
		int countBela = 0;
		// Število črnih krogcev v vrsti 
		int countCrna = 0;
		for (int k = 0; k < Igra.Z; k++) {
			switch (plosca[v.x[k]][v.y[k]]) {
			// Za vsako zasedeno polje prištejemo 1 vstreznemu counterju
			case Crna: countCrna += 1; break;
			case Bela: countBela += 1; break;
			case PRAZNO: break;
			}
		}
// NAČIN OCENJEVANJA:
//  * Ocene oziroma vzorci igranja so razporejeni po padajoči prioriteti
//	* Vsak vzorec je prvo napisan za črnega, potem pa še za belega igralca
		
	// Če obstaja zmagovalna vrsta, ima poteza, ki je privedla do nje absolutno prioriteto,
	// za to je poskrbljeno v algoritmu Minimax/Alphabeta
		
	// Če ima nasprotnik zasedena 4 polja v vrsti, nujno blokiramo
		if (jaz == Igralec.Crna && countBela == 4 && countCrna == 1)
			{return 100000;}
		
	// Enako kot prejšnja samo za bele
		else if (jaz == Igralec.Bela && countCrna == 4 && countBela == 1)
			{return 100000;}

		else if (jaz == Igralec.Crna && countCrna == 4 && plosca[v.x[0]][v.y[0]] == null) {
			int xSmer = v.x[1] - v.x[0];
			int ySmer = v.y[1] - v.y[0];
	// Če imamo dve prosti polji vmes pa 4 zasedena, nam to zagotovi zmago, nasldenjič ko smo na potezi
			if (v.x[4] + xSmer <= Igra.N && v.y[4] + ySmer <= Igra.N && plosca[v.x[4] + xSmer][v.y[4] + ySmer] == null)
				{return 20000;}
			// Lahko naenkrat dobimo 4 take vrste, zato more bit 4x manj od zgornje blokadne poteze
			// Ker je v praksi to zelo neverjetno, je dovolj nekaj manj od 2x toliko 
	// Če lahko zasedemo 4 polja v vrsti in je zadnje prazno, storimo to
			return 200;
		}
		
	// Enako kot prejšnja samo za bele
		else if (jaz == Igralec.Bela && countBela == 4 && plosca[v.x[0]][v.y[0]] == null) {
			int xSmer = v.x[1] - v.x[0];
			int ySmer = v.y[1] - v.y[0];
			if (v.x[4] + xSmer <= Igra.N && v.y[4] + 1 <= Igra.N && plosca[v.x[4] + xSmer][v.y[4] + ySmer] == null)
				{return 20000;}
			return 200;
		}
		
		else if (jaz == Igralec.Crna && countCrna == 3 && plosca[v.x[0]][v.y[0]] == null && (plosca[v.x[1]][v.y[1]] == null || plosca[v.x[4]][v.y[4]] == null)) {
			int xSmer = v.x[1] - v.x[0];
			int ySmer = v.y[1] - v.y[0];
	// Če imamo 3 zasedena in zraven 3 prosta polja
			if (v.x[4] + xSmer <= Igra.N && v.y[4] + ySmer <= Igra.N && plosca[v.x[4] + xSmer][v.y[4] + ySmer] == null)
			// Ker imamo 3 zasedena polja v vrsti, ostala pa prosta 3-counter povečamo za 1
				{three_counter += 1; return 300;}
	// Če imamo 3 zasedena in 2 prosti polji
			return 100;
		}
		
	// Enako kot prejšnja samo za bele
		else if (jaz == Igralec.Bela && countBela == 3 && plosca[v.x[0]][v.y[0]] == null && (plosca[v.x[1]][v.y[1]] == null || plosca[v.x[4]][v.y[4]] == null)) {
			int xSmer = v.x[1] - v.x[0];
			int ySmer = v.y[1] - v.y[0];
			if (v.x[4] + xSmer <= Igra.N && v.y[4] + 1 <= Igra.N && plosca[v.x[4] + xSmer][v.y[4] + ySmer] == null) 
				{three_counter += 1; return 300;}
			return 100;
		}
	
	// Če imamo 4 zasedena in eno prosto polje, malo povišamo oceno in 4-counter povečamo za 1 
		else if (jaz == Igralec.Crna && countCrna == 4 && countBela == 0)
			{four_counter += 1; return 70;}
		
	// Enako kot prejšnja samo za bele
		else if (jaz == Igralec.Bela && countBela == 4 && countCrna == 0)
			{four_counter += 1; return 70;}
		
	// Če ima nasprotnik vsaj 3 zasedena polja, blokiramo preden dopolni vrsto
		else if (jaz == Igralec.Crna && countCrna == 1 && countBela >= 3)
			{return 50;}
		
	// Enako kot prejšnja samo za bele
		else if (jaz == Igralec.Bela && countCrna >= 3 && countBela == 1)
			{return 50;}

	// Če ima nasprotnik 2 zasedeni polji, ga blokiramo, a z manjšo prioriteto kot če jih ima več
		else if (jaz == Igralec.Crna && countCrna == 1 && countBela >= 2)
			{return 25;}
		
	// Enako kot prejšnja samo za bele
		else if (jaz == Igralec.Bela && countCrna >= 2 && countBela == 1)
			{return 25;}
		
	// Ostale poteze ocenjujmo tako da poskusimo dopoolniti čim bolj polne vrste 
		else if (jaz == Igralec.Crna) {return countCrna - countBela;}
		else if (jaz == Igralec.Bela) {return countBela - countCrna;}
		// else if (countCrna > 0 && countBela > 0) {return 0;}
		else { return countBela - countCrna; }
	}
}
