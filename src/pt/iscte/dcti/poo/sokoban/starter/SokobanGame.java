package pt.iscte.dcti.poo.sokoban.starter;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class SokobanGame implements Observer {

	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	private static SokobanGame INSTANCE;
	private List<AbstractSObject> objects = new ArrayList<AbstractSObject>();
	private List<AbstractSObject> objectives = new ArrayList<AbstractSObject>();
	private Empilhadora player; 
	private String playerName;
	private int level =0;
	
	//Para o Sokoban saber qual o último nível
	private final int finalLevel=3;
	



	private SokobanGame(){	
		player = new Empilhadora(new Point2D (0,0));
		playerName = JOptionPane.showInputDialog( "SokobanGame", "Insira o seu Nome");
		ImageMatrixGUI.getInstance().setName("SokobanGame"); // Define o nome da barra do jogo
		buildMapLevel(level); // Constroi o mapa com uma LIST imageTile que retorna
		updateInfo(); // Atuliza a informa��o
	}


	public static SokobanGame getInstance() {
		if (INSTANCE == null)
			INSTANCE = new SokobanGame();
		return INSTANCE;
	}


	// Construcao do Mapa
	private void buildMapLevel(int level){
		int y= 0;
		List<ImageTile> tiles = new ArrayList<ImageTile>();
		try {
			Scanner loadMap = new Scanner (new File("levels/level"+ level + ".txt"));
			while (loadMap.hasNextLine()) {
				String linha = loadMap.nextLine();
				for(int x = 0; x < WIDTH; x++ ) {
					switch(linha.charAt(x)) {
					case '#': objects.add(new Parede(new Point2D(x,y)));
					break;
					case ' ': objects.add(new Chao(new Point2D(x,y)));
					break;
					case 'O': objects.add(new Buraco(new Point2D(x,y)));
					break;
					case 'C': objects.add(new Caixote(new Point2D(x,y)));
					
					objects.add(new Chao(new Point2D(x,y)));
					break;
					case 'X': objects.add(new Alvo(new Point2D(x,y)));
					objectives.add(objects.get(objects.size()-1));
					break;
					case 'E': player.setPosition(new Point2D(x,y));
					objects.add(player);
					objects.add(new Chao(new Point2D(x,y)));
					break;
					case 'P': objects.add(new PedraGrande(new Point2D(x,y)));
					objects.add(new Chao(new Point2D(x,y)));
					break;
					case 'p': objects.add(new PedraPequena(new Point2D(x,y)));
					objects.add(new Chao(new Point2D(x,y)));
					break;
					case 'b': objects.add(new Bateria(new Point2D(x,y)));
					objects.add(new Chao(new Point2D(x,y)));
					break;
					case 'g': objects.add(new Gelo(new Point2D(x,y)));
					break;
					case '%': objects.add(new ParedeQuebradica(new Point2D(x,y)));
					objects.add(new Chao(new Point2D(x,y)));
					break;
					case 'm': objects.add(new Martelo(new Point2D(x,y)));
					objects.add(new Chao(new Point2D(x,y)));
					break;
					case 't': objects.add(new Teleporte(new Point2D(x,y)));
					objects.add(new Chao(new Point2D(x,y)));
					break;
					
					}
					System.out.print(linha.charAt(x));
				}
				y++;
				System.out.println();
			}
			//Copy do mapa para ImageTile
			for(AbstractSObject obj: objects)
				tiles.add(obj);

			loadMap.close();	
		}		
		catch (FileNotFoundException e){
			System.err.println("Ficheiro n�o encontrado");
		}
		connectPortals();
		ImageMatrixGUI.getInstance().addImages(tiles);
	}


	@Override
	public void update(Observed arg0) {
		int lastKeyPressed = ((ImageMatrixGUI)arg0).keyPressed();
		if (lastKeyPressed == KeyEvent.VK_UP || lastKeyPressed == KeyEvent.VK_DOWN || lastKeyPressed == KeyEvent.VK_LEFT || lastKeyPressed == KeyEvent.VK_RIGHT ) {
			player.activate(Direction.directionFor(lastKeyPressed),getObjects(player.getPosition().plus(Direction.directionFor(lastKeyPressed).asVector()))); 
			updateInfo();
		}
	}	


	// Retorna os objectos no ponto
	public List<AbstractSObject> getObjects(Point2D position) {
		List<AbstractSObject> objectsInPosition = new ArrayList<AbstractSObject>();
		for(AbstractSObject obj : objects)
			if(obj.getPosition().equals(position))
				objectsInPosition.add(obj);
		return objectsInPosition;
	}


	// Verifica se os objectivos est�o completos

	private boolean levelIsCompleted() {
		int count = 0;
		
		for (AbstractSObject obj: objectives)
		for(AbstractSObject obj2: objects)
				if ((obj.getPosition().equals(obj2.getPosition())))
					if(obj2.isObjective() && obj2.isMovable())
						count++;
		if(count == objectives.size())
			return true;
		else 
			return false;
	}


	// Atualiza o jogo
	private void updateInfo() {
		ImageMatrixGUI.getInstance().setStatusMessage("Player: " + playerName + "  Level: " + level +"  Level moves:"+player.getLvlMoves()+"  Total game moves: " + player.getMoves() + "  Energy: " + player.getEnergy());
		ImageMatrixGUI.getInstance().update();
		if(levelIsCompleted()){
			Pontuacao.pontuacaoNivel(this.level, this.player.getLvlMoves());
			if(this.level==this.finalLevel){
				Pontuacao.PontuacaoGeral(this.player.getMoves());			
			}
			else{
			
			setNewLevel();
			}
		}
		else 
			if(player.getEnergy() == 0) 
				lostGame();
	}


	//Limpa o nivel
	private void clearLevel() {
		objects.clear();
		objectives.clear();
		ImageMatrixGUI.getInstance().clearImages();
	}


	// Constru��o de novo nivel	
	private void setNewLevel() {
		System.out.println("Completo");
		JOptionPane.showMessageDialog(null, "Passaste de Nivel","SokobanGame",JOptionPane.QUESTION_MESSAGE);

		clearLevel();
		player.resetLvlMoves();
		buildMapLevel(++level);
		ImageMatrixGUI.getInstance().update();
	}


	// Perdeste o jogo
	public void lostGame() {
		player.resetLvlMoves();
		JOptionPane.showMessageDialog(null, "Perdeste!","SokobanGame",JOptionPane.ERROR_MESSAGE);
		ImageMatrixGUI.getInstance().dispose();
	}

	
	// Remove o objecto 
	public void removeObject(AbstractSObject removeObject) {
				ImageMatrixGUI.getInstance().removeImage((ImageTile)removeObject);
				objects.remove(removeObject);
	}
	
	
	private void connectPortals() {
		List<Teleporte> objectsT = new ArrayList<Teleporte>(); 
		for(AbstractSObject obj: objects)
			if(obj instanceof Teleporte)
				objectsT.add((Teleporte)obj);
		if(objectsT.size()==2) {
			objectsT.get(0).setNextPortal(objectsT.get(1));
			objectsT.get(1).setNextPortal(objectsT.get(0));	
		}
		
	}

}


