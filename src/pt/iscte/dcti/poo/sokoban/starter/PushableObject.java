package pt.iscte.dcti.poo.sokoban.starter;

import java.util.List;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class PushableObject extends ActivateObject {

	private boolean absorved;
	private boolean blocked;

	public PushableObject(Point2D initialPosition,String imageName,int layer,boolean absorvable,boolean objective,boolean absorved) {
		super(initialPosition,imageName,layer,true,false,false,absorvable,objective,false,false);
		this.absorved = absorved;
		this.blocked = false;
	}


	@Override
	public void activate(Direction direction, List<AbstractSObject> objects) {
		setPosition(getPosition().plus(direction.asVector()));	
		objects = SokobanGame.getInstance().getObjects(getPosition());
		if(haveObject(objects))
				activateObject(direction,objects);

			
	}


	
	// Activa os objectos absorventes
	private void activateObject(Direction direction,List<AbstractSObject> objects) {
		for(AbstractSObject obj: objects) 
			if(obj.isAbsorvable() || obj.isSpecial())	
				((ActivateObject)obj).activate(direction, objects);
	}


	// Verifica se existe objectos absorventes
	private boolean haveObject(List<AbstractSObject> objects) {

		for(AbstractSObject obj: objects) 
			if(obj.isAbsorvable() || obj.isSpecial())	
				return true;
		return false;

	}

	public void getBlocked() {
		if(!isAbsorved())
		blocked = true;

	}
	
	public boolean isBlocked() {
		return blocked;
	}
	
	public boolean isAbsorved() {
		return absorved;
	}
}
