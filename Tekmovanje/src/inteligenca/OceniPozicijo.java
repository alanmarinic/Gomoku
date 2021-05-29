package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Vrsta;

public class OceniPozicijo {

	// Metoda oceniPozicijo za igro TicTacToe
	static int three_counter;
	static int four_counter;
	public static int oceniPozicijo (Igra igra, Igralec jaz) {								       
		int ocena = 0;
		four_counter = 0;
		three_counter = 0;
		for (Vrsta v : Igra.VRSTE) {
			ocena = ocena + oceniVrsto(v, igra, jaz);
		}
		if (three_counter > 1) {ocena += 50;}
		else if (three_counter > 0 && four_counter > 0) {ocena += 50;}
		return ocena;
	}
	
	
	
	public static int oceniVrsto2 (Vrsta v, Igra igra, Igralec jaz) {
		Polje[][] plosca = igra.getPlosca();
		int count_Bela = 0;
		int count_Crna = 0;
		for (int k = 0; k < Igra.Z && (count_Bela == 0 || count_Crna == 0); k++) {
			switch (plosca[v.x[k]][v.y[k]]) {
			case Crna: count_Crna += 1; break;
			case Bela: count_Bela += 1; break;
			case PRAZNO: break;
			}
		}
		if (count_Crna > 0 && count_Bela > 0) { return 0; }
		else if (jaz == Igralec.Crna) { return count_Crna - count_Bela; }
		else { return count_Bela - count_Crna; }
	}

	public static int oceniVrsto ( Vrsta v, Igra igra, Igralec jaz) {
		Polje[][] plosca = igra.getPlosca();
		int countBela = 0;
		int countCrna = 0;
		for (int k = 0; k < Igra.Z; k++) {
			switch (plosca[v.x[k]][v.y[k]]) {
			case Crna: countCrna += 1; break;
			case Bela: countBela += 1; break;
			case PRAZNO: break;
			}
		}
		
		// 4 nasprotnik moras blokerat
		if (jaz == Igralec.Crna && countBela == 4 && countCrna == 1) {return 100000;}
		else if (jaz == Igralec.Bela && countCrna == 4 && countBela == 1) {return 100000;}
		
		// _ O O O O
		else if (jaz == Igralec.Crna && countCrna == 4 && plosca[v.x[0]][v.y[0]] == null) {
			int xSmer = v.x[1] - v.x[0];
			int ySmer = v.y[1] - v.y[0];
			// _ O O O O _
			if (v.x[4] + xSmer <= Igra.N && v.y[4] + ySmer <= Igra.N && plosca[v.x[4] + xSmer][v.y[4] + ySmer] == null) {return 300;}
			// lahko 4 naenkrat take vrste dobi, zato more bit 4x manj od blokadne poteze
			return 75;
		}
		// Isto kot prejsnja samo za bele
		else if (jaz == Igralec.Bela && countBela == 4 && plosca[v.x[0]][v.y[0]] == null) {
			int xSmer = v.x[1] - v.x[0];
			int ySmer = v.y[1] - v.y[0];
			// ce && gleda povrsti je kul?
			if (v.x[4] + xSmer <= Igra.N && v.y[4] + 1 <= Igra.N && plosca[v.x[4] + xSmer][v.y[4] + ySmer] == null) {return 300;}
			// lahko 4 naenkrat take vrste dobi, zato more bit 4x manj od blokadne poteze
			return 75;
		}
		else if (jaz == Igralec.Crna && countCrna == 3 && plosca[v.x[0]][v.y[0]] == null && (plosca[v.x[1]][v.y[1]] == null || plosca[v.x[4]][v.y[4]] == null)) {
			int xSmer = v.x[1] - v.x[0];
			int ySmer = v.y[1] - v.y[0];
			if (v.x[4] + xSmer <= Igra.N && v.y[4] + ySmer <= Igra.N && plosca[v.x[4] + xSmer][v.y[4] + ySmer] == null) {three_counter += 1; return 150;}
			return 50;
		}
		
		else if (jaz == Igralec.Bela && countBela == 3 && plosca[v.x[0]][v.y[0]] == null && (plosca[v.x[1]][v.y[1]] == null || plosca[v.x[4]][v.y[4]] == null)) {
			int xSmer = v.x[1] - v.x[0];
			int ySmer = v.y[1] - v.y[0];
			if (v.x[4] + xSmer <= Igra.N && v.y[4] + 1 <= Igra.N && plosca[v.x[4] + xSmer][v.y[4] + ySmer] == null) {three_counter += 1; return 150;}
			return 50;
		}
		
		else if (jaz == Igralec.Crna && countCrna == 4 && countBela == 0) {four_counter += 1; return 40;}
		else if (jaz == Igralec.Bela && countBela == 4 && countCrna == 0) {four_counter += 1; return 40;}
		
		else if (jaz == Igralec.Crna && countCrna == 1 && countBela >= 3) {return 50;}
		else if (jaz == Igralec.Crna && countCrna == 1 && countBela >= 2) {return 25;}
		
		else if (jaz == Igralec.Bela && countCrna >= 3 && countBela == 1) {return 50;}
		else if (jaz == Igralec.Bela && countCrna >= 2 && countBela == 1) {return 25;}
		
		else if (countCrna > 0 && countBela > 0) {return 0;}
		else if (jaz == Igralec.Crna) { return countCrna - countBela; }
		else { return countBela - countCrna; }
	}

}


