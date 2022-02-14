package pt.iscte.dcti.poo.sokoban.starter;

import java.util.List;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Teleporte extends ActivateObject {
	private Teleporte nextPortal;
	

	public Teleporte(Point2D initialPosition) {
		super(initialPosition,"Portal_Verde",1,false,true,false,false,false,false,true);
		nextPortal = null;
	}
	
	@Override
	public void activate(Direction direction, List<AbstractSObject> objects) {
		if(isPossibleMoveToNextPortal(SokobanGame.getInstance().getObjects(getPointNextPortal())))
			moveToNextPorta(SokobanGame.getInstance().getObjects(getPosition()));
		
		
	}

	public void setNextPortal (Teleporte teleporte) {
		nextPortal = teleporte;
	}
	
	
	public Point2D getPointNextPortal() {
		return nextPortal.getPosition();
	}
	
	
	// Verifica os objectos no portal seguinte
	private boolean isPossibleMoveToNextPortal (List<AbstractSObject> objects) {
		for(AbstractSObject obj : objects)
			if( obj.isMovable())
				return false;
		return true;
	}
	
	
	// Teleporta a Empilhadora
	private void moveToNextPorta(List<AbstractSObject> objects) {
		for(AbstractSObject obj : objects)
			if( obj.isMovable())
				((ActivateObject)obj).setPosition(getPointNextPortal());

		
		
	}

}

	