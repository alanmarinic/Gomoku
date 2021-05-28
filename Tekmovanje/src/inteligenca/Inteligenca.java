
package inteligenca;
import splosno.KdoIgra;
import logika.Igra;
import splosno.Koordinati;

public abstract class Inteligenca extends KdoIgra{
//probimo private cene je public
	public Inteligenca(String ime) {
		super(ime);
	
	}
	
	public abstract Koordinati izberiPotezo(Igra igra);
}
