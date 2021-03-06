import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Jonas Schill
 *
 */
public class State {
	private ArrayList<ArrayList<Integer>> board;
	
	public State(){
	board = new ArrayList<ArrayList<Integer>>();
	setUp();
	}
	
	public void setUp(){
		for(int i = 0; i < 5; i++){
			board.add(new ArrayList<Integer>());
			for(int j = 0; j < 5; j++){
				board.get(i).add(0);
			}
		}
		spawnCell();
		spawnCell();
	}
	
	public void spawnCell(){
		int[] cord = getRandomEmptyCord();
		if(Math.random() < 0.9){
			board.get(cord[0]).set(cord[1], 2);
		} else {
			board.get(cord[0]).set(cord[1], 4);
		}
	}
	
	public int[] getRandomEmptyCord(){
		ArrayList<int[]> emptyCords = new ArrayList<int[]>();
		int[] tempCord;
		Random randomGenerator = new Random();
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				if(board.get(i).get(j) == 0){
					tempCord = new int[2];
					tempCord[0] = i;
					tempCord[1] = j;
					emptyCords.add(tempCord);
				}
			}
		}
		if(emptyCords.size() == 0){
			return null;
		}
		return emptyCords.get(randomGenerator.nextInt(emptyCords.size()));
	}
	
	public void moveRight(){
		condenseRight();
		addRight();
		spawnCell();
	}
	
	public void condenseRight(){
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				if(board.get(i).get(j) == 0){
					board.get(i).remove(j);
					board.get(i).add(0, 0);
				}
			}
		}
	}
	
	public void addRight(){
		for(int i = 0; i < 5; i++){
			for(int j = 4; j > 0; j--){
				if(board.get(i).get(j) == board.get(i).get(j-1) && board.get(i).get(j) != 0){
					board.get(i).set(j, board.get(i).get(j)*2);
					board.get(i).remove(j-1);
					board.get(i).add(0, 0);
				}
			}
		}
	}
	
	public void moveLeft(){
		condenseLeft();
		addLeft();
		spawnCell();
	}
	
	public void condenseLeft(){
		for(int i = 0; i < 5; i++){
			board.get(i).removeAll(Collections.singleton(0));
			int tempSize = board.get(i).size();
			for(int j = tempSize; j < 5; j++){
				board.get(i).add(j, 0);
			}
		}
	}
	
	public void addLeft(){
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 4; j++){
				if(board.get(i).get(j) == board.get(i).get(j+1) && board.get(i).get(j) != 0){
					board.get(i).set(j, board.get(i).get(j)*2);
					board.get(i).set(j+1, 0);
					condenseLeft();
				}
			}
		}
	}
	
	public void moveDown(){
		condenseDown();
		addDown();
		spawnCell();
	}
	
	public void condenseDown(){
		ArrayList<Integer> tempCollum;
		for(int i = 0; i < 5; i++){
			tempCollum = new ArrayList<Integer>();
			for(int j = 0; j < 5; j++){
				tempCollum.add(board.get(j).get(i));
			}
			for(int j = 0; j < 5; j++){
				if(tempCollum.get(j) == 0){
					tempCollum.remove(j);
					tempCollum.add(0, 0);
				}
			}
			for(int j = 0; j < 5; j++){
				board.get(j).set(i, tempCollum.get(j));
			}
		}
	}
	
	public void addDown(){
		ArrayList<Integer> tempCollum;
		for(int i = 0; i < 5; i++){
			tempCollum = new ArrayList<Integer>();
			for(int j = 0; j < 5; j++){
				tempCollum.add(board.get(j).get(i));
			}
			for(int j = 4; j > 0; j--){
				if(tempCollum.get(j) == tempCollum.get(j-1) && tempCollum.get(j) != 0){
					tempCollum.set(j, tempCollum.get(j)*2);
					tempCollum.remove(j-1);
					tempCollum.add(0, 0);
				}
			}
			for(int j = 0; j < 5; j++){
				board.get(j).set(i, tempCollum.get(j));
			}
		}
	}
	
	public void moveUp(){
		condenseUp();
		addUp();
		spawnCell();
	}
	
	public void condenseUp(){
		ArrayList<Integer> tempCollum;
		for(int i = 0; i < 5; i++){
			tempCollum = new ArrayList<Integer>();
			for(int j = 0; j < 5; j++){
				tempCollum.add(board.get(j).get(i));
			}
			tempCollum.removeAll(Collections.singleton(0));
			int tempSize = tempCollum.size();
			for(int j = tempSize; j < 5; j++){
				tempCollum.add(j, 0);
			}
			for(int j = 0; j < 5; j++){
				board.get(j).set(i, tempCollum.get(j));
			}
		}
	}
	
	public void addUp(){
		ArrayList<Integer> tempCollum;
		for(int i = 0; i < 5; i++){
			tempCollum = new ArrayList<Integer>();
			for(int j = 0; j < 5; j++){
				tempCollum.add(board.get(j).get(i));
			}
			for(int j = 0; j < 4; j++){
				if(tempCollum.get(j) == tempCollum.get(j+1) && tempCollum.get(j) != 0){
					tempCollum.set(j, tempCollum.get(j)*2);
					tempCollum.set(j+1, 0);
					condenseUp();
				}
			}
			for(int j = 0; j < 5; j++){
				board.get(j).set(i, tempCollum.get(j));
			}
		}
	}
	
	public ArrayList<ArrayList<Integer>> getBoard(){
		return board;
	}
	
	public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            moveLeft();
        }

        if (key == KeyEvent.VK_RIGHT) {
            moveRight();
        }

        if (key == KeyEvent.VK_UP) {
            moveUp();
        }

        if (key == KeyEvent.VK_DOWN) {
            moveDown();
        }
    }

}
