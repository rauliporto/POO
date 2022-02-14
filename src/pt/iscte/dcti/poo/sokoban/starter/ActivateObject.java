package pt.iscte.dcti.poo.sokoban.starter;

import java.util.List;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class ActivateObject extends AbstractSObject implements ActiveObject{

	
	public ActivateObject(Point2D initialPosition, String imageName, int layer, boolean movable, boolean transposable,
			boolean consumable,boolean absorvable, boolean objective,boolean breakable, boolean special) {
		super(initialPosition, imageName, layer, movable, transposable, consumable, absorvable, objective,breakable,special);
	}
	
	@Override
	public abstract void activate(Direction direction, List<AbstractSObject> objects);

}
