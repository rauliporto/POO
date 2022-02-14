package pt.iscte.dcti.poo.sokoban.starter;

import java.util.ArrayList;
import java.util.List;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Gelo extends ActivateObject{

	public Gelo(Point2D initialPosition) {
		super(initialPosition,"Gelo",1,false,true,false,false,false,false,true);
	}

	@Override
	public void activate(Direction direction, List<AbstractSObject> objects) {
		List<AbstractSObject> moveObjects = SokobanGame.getInstance().getObjects(getPosition());
		if(canMoveNextPosition(direction))
			for(AbstractSObject obj: moveObjects) 
				if(obj.isMovable())
					((ActivateObject)obj).setPosition(getPosition().plus(direction.asVector()));


		if(haveSpecialOjects(direction)) {
			List<AbstractSObject>specialObjects = getSpecialObjects(direction);
			for(AbstractSObject obj: specialObjects) 
				((ActivateObject)obj).activate(direction,null);
		}

	}


	// Verifica se pode mover o objecto para a proxima posição
	private boolean canMoveNextPosition(Direction direction) {
		List<AbstractSObject> objects = SokobanGame.getInstance().getObjects(getPosition().plus(direction.asVector()));
		for(AbstractSObject obj: objects)
			if(obj.isMovable() || obj.isConsumable() || obj.isBreakable())
				return false;
		return true;
	}

	// Verifica se existem objectos especiais
	private boolean haveSpecialOjects(Direction direction) {
		List<AbstractSObject> objects = SokobanGame.getInstance().getObjects(getPosition().plus(direction.asVector()));
		for(AbstractSObject obj: objects)
			if (obj.isSpecial())
				return true;
		return false;
	}

	private List<AbstractSObject> getSpecialObjects(Direction direction){
		List<AbstractSObject>objects = SokobanGame.getInstance().getObjects(getPosition().plus(direction.asVector()));
		List<AbstractSObject> specialObjects = new ArrayList<AbstractSObject>();
		for(AbstractSObject obj: objects)
			if (obj.isSpecial())
				specialObjects.add(obj);
		return specialObjects;
	}



}
