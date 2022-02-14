package pt.iscte.dcti.poo.sokoban.starter;

import java.util.List;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Buraco extends ActivateObject {

	public Buraco(Point2D initialPosition){
		super(initialPosition,"Buraco",0,false,true,false,true,false,false,false);
	}
	
	@Override
	public void activate(Direction direction, List<AbstractSObject> objects) {
		for(AbstractSObject obj : objects) {
			if(obj.isMovable()) {
				if(((PushableObject)obj).isAbsorved())
					SokobanGame.getInstance().removeObject(obj);
				else {
					((PushableObject)obj).getBlocked();
				}
		}
	}
	}
	
}
