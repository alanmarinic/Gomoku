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
		if (three_counter > 1) {ocena += 19;}
		else if (three_counter > 0 && four_counter > 0)
		{ocena += 19;}
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

//	public static int oceniPozicijo2 (Igra igra, Igralec jaz) {
//		int ocena = 0;
//		int four_counter = 0;
//		int three_counter = 0;
//		 - - _ - - -       _ - - - _ - - - _ 
//		blokada ++++++++++++++++
//		else if (ti imas 4 pa na obeh straneh nic) {kr kul 4500} +++++++++++++++++++++++++++++
//		else if (ti mas 4 pa sam  ene strani frej) // za zdej ma oceno 4 po standardnem minimax +++++
//		else if (3 pa z obeh nic) {isto k ena gor} // skor, 1 mnj bo, kr 3 rzen ce bo preslab komp +++++
//		else if (2x (3 pa z obeh nic)) {4500} ++++++
//		else if (2x (4)) {4500}
//		
//	}
	public static int oceniVrsto ( Vrsta v, Igra igra, Igralec jaz) {
		Polje[][] plosca = igra.getPlosca();
		int countBela = 0;
		int countCrna = 0;
		for (int k = 0; k < Igra.Z && (countBela == 0 || countCrna == 0); k++) {
			switch (plosca[v.x[k]][v.y[k]]) {
			case Crna: countCrna += 1; break;
			case Bela: countBela += 1; break;
			case PRAZNO: break;
			}
		}
		// 4 nasprotnik moras blokerat
		if (jaz == Igralec.Crna && countBela == 4 && countCrna == 1) {return 100;}
		else if (jaz == Igralec.Bela && countCrna == 4 && countBela == 1) {return 100;}
		
		//else if (jaz == Igralec.Crna && countCrna == 4 && countBela == 0 && plosca[v.x[k] + 1][v.y[k + 1]] == null) {}
		else if (jaz == Igralec.Crna && countCrna == 4 && plosca[v.x[0]][v.y[0]] == null) {
			int xSmer = v.x[1] - v.x[0];
			int ySmer = v.y[1] - v.y[0];
			// ce && gleda povrsti je kul?
			if (v.x[4] + xSmer <= Igra.N && v.y[4] + 1 <= Igra.N && plosca[v.x[4] + xSmer][v.y[4] + ySmer] == null) {return 20;}
			// lahko 4 naenkrat take vrste dobi, zato more bit 4x manj od blokadne poteze
			return 4;
		}
		else if (jaz == Igralec.Bela && countBela == 4 && plosca[v.x[0]][v.y[0]] == null) {
			int xSmer = v.x[1] - v.x[0];
			int ySmer = v.y[1] - v.y[0];
			// ce && gleda povrsti je kul?
			if (v.x[4] + xSmer <= Igra.N && v.y[4] + 1 <= Igra.N && plosca[v.x[4] + xSmer][v.y[4] + ySmer] == null) {return 20;}
			// lahko 4 naenkrat take vrste dobi, zato more bit 4x manj od blokadne poteze
			return 4;
		}
		else if (jaz == Igralec.Crna && countCrna == 3 && plosca[v.x[0]][v.y[0]] == null && (plosca[v.x[1]][v.y[1]] == null || plosca[v.x[4]][v.y[4]] == null)) {
			int xSmer = v.x[1] - v.x[0];
			int ySmer = v.y[1] - v.y[0];
			if (v.x[4] + xSmer <= Igra.N && v.y[4] + 1 <= Igra.N && plosca[v.x[4] + xSmer][v.y[4] + ySmer] == null) {three_counter += 1; return 3;}
			return 3;
		}
		
		else if (jaz == Igralec.Bela && countBela == 3 && plosca[v.x[0]][v.y[0]] == null && (plosca[v.x[1]][v.y[1]] == null || plosca[v.x[4]][v.y[4]] == null)) {
			int xSmer = v.x[1] - v.x[0];
			int ySmer = v.y[1] - v.y[0];
			if (v.x[4] + xSmer <= Igra.N && v.y[4] + 1 <= Igra.N && plosca[v.x[4] + xSmer][v.y[4] + ySmer] == null) {three_counter += 1; return 3;}
			return 3;
		}
		
		else if (jaz == Igralec.Crna && countCrna == 4 && countBela == 0) {four_counter += 1; return 4;}
		else if (jaz == Igralec.Bela && countBela == 4 && countCrna == 0) {four_counter += 1; return 4;}
		
		else if (countCrna > 0 && countBela > 0) {return 0;}
		else if (jaz == Igralec.Crna) { return countCrna - countBela; }
		else { return countBela - countCrna; }
	}
}


