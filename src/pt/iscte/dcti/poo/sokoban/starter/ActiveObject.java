package pt.iscte.dcti.poo.sokoban.starter;

import java.util.List;

import pt.iul.ista.poo.utils.Direction;


public interface ActiveObject {
	
	// Metodo activate - a implementar em objectos moviveis ou consumiveis
	public void activate(Direction direction, List<AbstractSObject> objects);

	
	
	
}
