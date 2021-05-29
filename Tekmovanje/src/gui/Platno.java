package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import logika.Igra;
import logika.Polje;
import logika.Vrsta;
import splosno.Koordinati;
import splosno.Vodja;;

// Pripravi in nariše igralno polje
@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener {
	
	public Platno() {
		// Nastavi ozadje
		setBackground(Color.PINK);
		// Spremlja akcije miške
		this.addMouseListener(this);
	}

	// Nastavi začetne dimenzije platna 
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}

	// Relativna širina črte
	private final static double LINE_WIDTH = 0.05;
	
	// Širina enega kvadratka
	private double squareWidth() {
		return Math.min(getWidth(), getHeight()) / Igra.N;
	}
	
	// Relativni prostor okoli krožcev
	private final static double PADDING = 0.18;
	
	// Nariše bel krogec v določeno polje na platnu
	private void paintBela(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		// Premer krogca
		double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING);
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		// Barva krogca
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		// Nariše krogec
		g2.drawOval((int)x, (int)y, (int)d , (int)d);
	}
	
	// Nariše črn krogec v določeno polje na platnu
	private void paintCrna(Graphics2D g2, int i, int j) {
		double w = squareWidth();
		// Premer krogca
		double d = w * (1.0 - LINE_WIDTH - 2.0 * PADDING);
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		// Barva krogca
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		// Nariše krogec
		g2.drawOval((int)x, (int)y, (int)d , (int)d);
	}

	// Nariše igralno ploščo
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		double w = squareWidth();

		// Če imamo zmagovalno 5-terico, njeno ozadje pobarvamo
		Vrsta t = null;
		if (Vodja.igra != null) {t = Vodja.igra.zmagovalnaVrsta();}
		if (t != null) {
			g2.setColor(Color.MAGENTA);
			for (int k = 0; k < Igra.Z; k++) {
				int i = t.x[k];
				int j = t.y[k];
				g2.fillRect((int)(w * i), (int)(w * j), (int)w, (int)w);
			}
		}
		
		// Narišemo črte med posameznimi polji
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke((float) (w * LINE_WIDTH)));
		for (int i = 1; i < Igra.N; i++) {
			// Navpične črte
				// (x, y) prve točke daljice
			g2.drawLine((int)(i * w), (int)(0), 
				// (x, y) zadnje točke daljice
						(int)(i * w), (int)(Igra.N * w));
			// Vodoravne črte
				// (x, y) prve točke daljice
			g2.drawLine((int)(0), (int)(i * w),
				// (x, y) druge točke daljice
						(int)(Igra.N * w), (int)(i * w));
		}
		
		// Nariše krogce na igralno polje
		Polje[][] plosca;;
		if (Vodja.igra != null) {
			plosca = Vodja.igra.getPlosca();
			for (int i = 0; i < Igra.N; i++) {
				for (int j = 0; j < Igra.N; j++) {
					switch(plosca[i][j]) {
					case Bela: paintBela(g2, i, j); break;
					case Crna: paintCrna(g2, i, j); break;
					default: break;
					}
				}
			}
		}	
	}
	
	// Odziv ob kliku z miško - odigra človekovo potezo
	@Override
	public void mouseClicked(MouseEvent e) {
		if (Vodja.clovekNaVrsti) {
			int x = e.getX();
			int y = e.getY();
			int w = (int)(squareWidth());
			int i = x / w ;
			double di = (x % w) / squareWidth() ;
			int j = y / w ;
			double dj = (y % w) / squareWidth() ;
			if (0 <= i && i < Igra.N && 0.5 * LINE_WIDTH < di && di < 1.0 - 0.5 * LINE_WIDTH &&
				0 <= j && j < Igra.N && 0.5 * LINE_WIDTH < dj && dj < 1.0 - 0.5 * LINE_WIDTH) {
				Vodja.igrajClovekovoPotezo (new Koordinati(i, j));
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
