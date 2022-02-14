package pt.iscte.dcti.poo.sokoban.starter;

import java.util.List;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class DisappeareObject extends ActivateObject {

	private int inventory;

	public DisappeareObject(Point2D initialPosition, String imageName, int layer,boolean transposable, boolean consumable,boolean absorvable, boolean objective, boolean breakable,boolean special, int inventory) {
		super(initialPosition, imageName, layer, false,transposable, consumable, absorvable, objective,breakable,special);
		this.inventory = inventory;
	}



	@Override
	public void activate(Direction direction, List<AbstractSObject> objects) {
		for(AbstractSObject obj : objects) {
			if(obj.isConsumable() || obj.isBreakable()) {
				SokobanGame.getInstance().removeObject(obj);
			}
		}
	}
	

	public int getInvetory() {
		return inventory;
	}

}
