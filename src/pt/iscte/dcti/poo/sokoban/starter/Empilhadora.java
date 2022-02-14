package pt.iscte.dcti.poo.sokoban.starter;

import java.util.List;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Empilhadora extends ActivateObject implements ActiveObject{
	private int moves;
	private int energy;
	private int item;
	
	private int levelMoves;

	public Empilhadora(Point2D initialPosition){
		super(initialPosition,"Empilhadora_U",2,true,false,false,false,false,false,false);
		moves = 0;
		energy = 100;
		item = 0;
		this.levelMoves=0;
	}

	public int getMoves() {
		return moves;
	}

	public int getEnergy() {
		return energy;
	}

	@Override
	public void activate(Direction direction, List<AbstractSObject> objects) {
		Point2D newPosition = getPosition().plus(direction.asVector());
		List<AbstractSObject> objects2 = SokobanGame.getInstance().getObjects(newPosition.plus(direction.asVector()));

		// Escolhe a imagem conforme a direcao
		setImageName("Empilhadora_" + direction.toString().charAt(0));
		if(isOnlyToMove(objects)) 
			moved(newPosition);

		if(haveAbsorveObjects(objects) && !haveMovableObjects(objects)) {
			moved(newPosition);
			lostGame();
		}

		if(haveMovableObjects(objects)) {
			if(!haveBlockedObjects(objects))
				if(isPossibleMoveObject(objects2)) {
					moved(newPosition);
					moveObjects(direction,objects);
				}
		}
		if (haveConsumableObjects(objects)) {
			moved(newPosition);
			consumeObjects(direction,objects);

		}
		if(haveBreakableObjects(objects) && canBreak())	{
			moved(newPosition);
			breakObjects(direction,objects);
		}



		if(haveSpecialObjects(objects)) {
			moved(newPosition);
			activateSpecialObjects(direction,objects);
		}

	}


	// Perde o jogo
	private void lostGame() {
		ImageMatrixGUI.getInstance().update();
		SokobanGame.getInstance().lostGame();
	}
	// Verifica se tem objectos special
	private boolean haveSpecialObjects(List<AbstractSObject> objects) {
		for(AbstractSObject obj : objects)
			if(obj.isSpecial())
				return true;
		return false;
	}	

	// Activa os objetos especiais
	private void activateSpecialObjects(Direction direction,List<AbstractSObject> objects){
		for(AbstractSObject obj : objects)
			if(obj.isSpecial()) {
				((ActivateObject)obj).activate(direction, null);

			}
	}

	// Verifica se tem objectos breakable
	private boolean haveBreakableObjects(List<AbstractSObject> objects) {
		for(AbstractSObject obj : objects)
			if(obj.isBreakable())
				return true;
		return false;
	}	

	// Desparece os objectos breakable
	private void breakObjects(Direction direction,List<AbstractSObject> objects){
		for(AbstractSObject obj : objects)
			if(obj.isBreakable()) {
				((DisappeareObject)obj).activate(direction, objects);

			}
	}

	// Verifica se tem objectos consumiveis
	private boolean haveConsumableObjects(List<AbstractSObject> objects) {
		for(AbstractSObject obj : objects)
			if(obj.isConsumable())
				return true;
		return false;
	}

	//Consome os objectos
	private void consumeObjects(Direction direction,List<AbstractSObject> objects){
		for(AbstractSObject obj : objects)
			if(obj.isConsumable()) {
				setItem(((DisappeareObject)obj).getInvetory());
				((DisappeareObject)obj).activate(direction, objects);
			}
	}

	// Verifica se ha objectos absorventes
	private boolean haveAbsorveObjects(List<AbstractSObject> objects) {
		for(AbstractSObject obj : objects)
			if(obj.isAbsorvable())
				return true;
		return false;
	}

	// verifica se existem objectos bloqueados
	private boolean haveBlockedObjects(List<AbstractSObject> objects) {
		for(AbstractSObject obj : objects)
			if(obj.isMovable())
				if(((PushableObject)obj).isBlocked())
					return true;
		return false;

	}

	// Verifica se existe algum objecto movivel
	private boolean haveMovableObjects(List<AbstractSObject> objects){
		for(AbstractSObject obj : objects)
			if(obj.isMovable())
				return true;
		return false;
	}

	private void moveObjects(Direction direction,List<AbstractSObject> objects){
		for(AbstractSObject obj : objects)
			if(obj.isMovable())
				((PushableObject)obj).activate(direction, objects);
	}

	// funcao auxiliar para verificacao se existe espaco para mover o objecto
	private boolean isPossibleMoveObject(List<AbstractSObject> objects) {	
		for(AbstractSObject obj : objects)
			if(!obj.isTransposable() || obj.isConsumable() ||  obj.isBreakable()) 
				return false;
		return true;
	}

	//Verifica se todos os objectos sao transposto ou seja se podemos mover para o local desses objectos
	private boolean isOnlyToMove(List<AbstractSObject> objects)	{
		for(AbstractSObject obj : objects)
			if(!obj.isTransposable() || obj.isConsumable() ||  obj.isBreakable() || obj.isAbsorvable() || obj.isSpecial()) 
				return false;
		return true;
	}

	// Set item inventory
	private void setItem(int value) {
		if (value == 1) {
			fullEnergy();
		}
		if (value == 2)
			item = value;
	}

	private boolean canBreak() {
		return item == 2 ;
	}

	// Atualiza os movimentos
	private boolean moved(Point2D newPosition) {
		setPosition(newPosition);
		moves++;
		levelMoves++;
		energy--;
		return true;
	}

	// Faz set � energia ao maximo
	private void fullEnergy() {
		energy = 101;	
	}
	
	public int getLvlMoves(){
		return this.levelMoves;
	}
	
	public void resetLvlMoves(){
		this.levelMoves=0;
	}
}



