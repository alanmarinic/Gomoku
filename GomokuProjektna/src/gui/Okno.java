package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import inteligenca.Alphabeta;
import inteligenca.Minimax;
import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.VrstaIgralca;
import splosno.KdoIgra;
import splosno.Vodja;


/**
 * Glavno okno aplikacije hrani trenutno stanje igre in nadzoruje potek
 * igre.
 * 
 * @author AS
 *
 */
@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {
	/**
	 * JPanel, v katerega igramo
	 */
	private Platno platno;

	
	//Statusna vrstica v spodnjem delu okna
	private JLabel status;
	
	// Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;

	private JMenuItem izgled;
	private JMenuItem mala;
	private JMenuItem velika;
	private JMenuItem minimax;
	private JMenuItem alfabeta;	
	private JMenuItem hitro;	
	private JMenuItem srednje;	
	private JMenuItem pocasi;	
	/**
	 * Ustvari novo glavno okno in prični igrati igro.
	 */
	public Okno() {
		
		this.setTitle("Gomoku");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
	
		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		
		JMenu igra_menu = new JMenu("Nova igra");
		menu_bar.add(igra_menu);

		igraClovekRacunalnik = dodajMenuItem(igra_menu, "Človek – računalnik");
		igraRacunalnikClovek = dodajMenuItem(igra_menu, "Računalnik – človek");
		igraClovekClovek = dodajMenuItem(igra_menu, "Človek – človek");
		igraRacunalnikRacunalnik = dodajMenuItem(igra_menu, "Računalnik – računalnik");
		
		
		//lastnosti
		JMenu lastnosti_menu = new JMenu("Lastnosti");
		menu_bar.add(lastnosti_menu);
		
		JMenu velikost = new JMenu("Velikost");
		lastnosti_menu.add(velikost);

		JMenu algoritem = new JMenu("Algoritem računalnika");
		lastnosti_menu.add(algoritem);
		
		JMenu odzivnost = new JMenu("Odzivnost računalnika");
		lastnosti_menu.add(odzivnost);
		
		izgled = dodajMenuItem(lastnosti_menu, "Izgled");
		// sub menu items za velikost
		mala = dodajMenuItem(velikost, "15 x 15");
		velika = dodajMenuItem(velikost, "19 x 19");
		minimax = dodajMenuItem(algoritem, "Minimax");
		alfabeta = dodajMenuItem(algoritem, "AlfaBeta");
		hitro = dodajMenuItem(odzivnost, "Hitro");
		srednje = dodajMenuItem(odzivnost, "Srednje");
		pocasi = dodajMenuItem(odzivnost, "Počasi");
		
		// igralno polje-platno
		platno = new Platno();

		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		//polje_layout.anchor = GridBagConstraints.CENTER;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(platno, polje_layout);
		// statusna vrstica za sporočila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		status.setText("Izberite igro!");
		
	}
	
	public JMenuItem dodajMenuItem(JMenu menu, String naslov) {
		JMenuItem menuitem = new JMenuItem(naslov);
		menu.add(menuitem);
		menuitem.addActionListener(this);
		return menuitem;
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == igraClovekRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.Crna, VrstaIgralca.C);
			Vodja.vrstaIgralca.put(Igralec.Bela, VrstaIgralca.R);
			String ime = JOptionPane.showInputDialog(this, "Vnesi ime");
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.Crna, new KdoIgra("Človek")); 
			Vodja.kdoIgra.put(Igralec.Bela, Vodja.racunalnikovaInteligenca);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraRacunalnikClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.Crna, VrstaIgralca.R);
			Vodja.vrstaIgralca.put(Igralec.Bela, VrstaIgralca.C);
			String ime = JOptionPane.showInputDialog(this, "Vnesi ime");
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.Crna, Vodja.racunalnikovaInteligenca);
			Vodja.kdoIgra.put(Igralec.Bela, new KdoIgra("Človek"));
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraClovekClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.Crna, VrstaIgralca.C);
			Vodja.vrstaIgralca.put(Igralec.Bela, VrstaIgralca.C);
			JTextField ime_1 = new JTextField();
			JTextField ime_2 = new JTextField();
			JComponent[] polja = {
					new JLabel("Vnesite ime1"), ime_1,
					new JLabel("Vnesite ime2"), ime_2,
			};
			int izbira = JOptionPane.showConfirmDialog(this, polja, "Input", JOptionPane.OK_CANCEL_OPTION);
			// if (izbira == JOptionPane.OK_OPTION) {}
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.Crna, new KdoIgra("Človek")); 
			Vodja.kdoIgra.put(Igralec.Bela, new KdoIgra("Človek"));
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraRacunalnikRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.Crna, VrstaIgralca.R);
			Vodja.vrstaIgralca.put(Igralec.Bela, VrstaIgralca.R);
			Vodja.kdoIgra = new EnumMap<Igralec,KdoIgra>(Igralec.class);
			Vodja.kdoIgra.put(Igralec.Crna, Vodja.racunalnikovaInteligenca);
			Vodja.kdoIgra.put(Igralec.Bela, Vodja.racunalnikovaInteligenca);
			Vodja.igramoNovoIgro();
		}
		//lastnosti
		else if (e.getSource() == mala) {
			Igra.N = 15;
			Vodja.igra = null;
			osveziGUI();
		}
		else if (e.getSource() == velika) {
			Igra.N = 19;
			Vodja.igra = null;
			osveziGUI();
		}
		else if (e.getSource() == minimax) {
			Vodja.racunalnikovaInteligenca = new Minimax();
			osveziGUI();// osvezi GUI rabmo?
		}
		else if (e.getSource() == alfabeta) {
			Vodja.racunalnikovaInteligenca = new Alphabeta(5);
			osveziGUI();
		}
		else if (e.getSource() == hitro) {
			Vodja.odzivnost = 0;
		}
		
		else if (e.getSource() == srednje) {
			Vodja.odzivnost = 1;
		}
		
		else if (e.getSource() == pocasi) {
			Vodja.odzivnost = 2;
		}
			
			
	}
	
	public void osveziGUI() {
		if (Vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(Vodja.igra.stanje()) {
			case NEODLOCENO: status.setText("Neodločeno!"); break;
			case V_TEKU: 
				status.setText("Na potezi je " + Vodja.igra.naPotezi() + 
						" - " + Vodja.kdoIgra.get(Vodja.igra.naPotezi()).ime()); 
				break;
			case ZMAGA_CRNA: 
				status.setText("Zmagal je Črni - " + 
						Vodja.kdoIgra.get(Vodja.igra.naPotezi().nasprotnik()).ime());
				
				break;
			case ZMAGA_BELA: 
				status.setText("Zmagal je Beli - " + 
						Vodja.kdoIgra.get(Vodja.igra.naPotezi().nasprotnik()).ime());
				break;
			}
		}
		platno.repaint();
	}
	

}
