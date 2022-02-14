package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class AbstractSObject implements ImageTile{

	private Point2D position;
	private String imageName;
	private int layer;
	protected boolean movable;
	private boolean transposable;
	private boolean consumable;
	private boolean objective;
	private boolean special;
	private boolean breakable;
	private boolean absorvable;

	public AbstractSObject(Point2D initialPosition) {
		position = initialPosition;
		imageName = "Abstrato";
		layer = 1;
		movable = false;
		transposable = false;
		consumable = false;
		objective = false;
		breakable = false;
		special = false;
		absorvable = false;
	}

	public AbstractSObject(Point2D initialPosition,String imageName,int layer,boolean movable, boolean transposable, boolean consumable,boolean absorvable, boolean objective, boolean breakable,boolean special) {
		position = initialPosition;
		this.imageName= imageName;
		this.layer = layer;
		this.movable = movable;
		this.transposable = transposable;
		this.consumable = consumable;
		this.objective = objective;
		this.breakable = breakable;
		this.special = special;
		this.absorvable = absorvable;
	}


	// Implements from ImageTile
	@Override
	public String getName() {
		return imageName;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return layer;
	}

	public boolean isMovable() {
		return movable;
	}


	public boolean isTransposable() {
		return transposable;
	}

	public boolean isConsumable() {
		return consumable;
	}

	public boolean isObjective() {
		return objective;
	}

	public boolean isBreakable() {
		return breakable;
	}

	public boolean isSpecial() {
		return special;
	}

	public boolean isAbsorvable() {
		return absorvable;
	}


	// Outros Metodos
	public void setPosition(Point2D position) {
		this.position = position;
	}

	public void setImageName(String newImageName) {
		imageName = newImageName;
	}

}
