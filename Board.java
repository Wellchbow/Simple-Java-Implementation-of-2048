import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author Jonas Schill
 *
 */
public class Board extends JPanel{

    private State state;

    public Board() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(new Color(239, 228, 176));
        setDoubleBuffered(true);
        
        state = new State();
    }

    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(rh); 
        
        g2.setFont((new Font("Helvetica", Font.BOLD, 25)));
        ArrayList<ArrayList<Integer>> board = state.getBoard();
        
        for(int i = 0; i < 5; i++){
        	for(int j = 0; j < 5; j++){
        		if(board.get(i).get(j) == 2){
        			g2.setColor(new Color(127, 127, 127));
        		} else if(board.get(i).get(j) == 4){
        			g2.setColor(new Color(136, 0, 21));
        		} else if(board.get(i).get(j) == 8){
        			g2.setColor(new Color(237, 28, 36));
        		} else if(board.get(i).get(j) == 16){
        			g2.setColor(new Color(255, 127, 39));
        		} else if(board.get(i).get(j) == 32){
        			g2.setColor(new Color(255, 242, 0));
        		} else if(board.get(i).get(j) == 64){
        			g2.setColor(new Color(34, 177, 76));
        		} else if(board.get(i).get(j) == 128){
        			g2.setColor(new Color(0, 162, 232));
        		} else if(board.get(i).get(j) == 256){
        			g2.setColor(new Color(63, 72, 204));
        		} else if(board.get(i).get(j) == 512){
        			g2.setColor(new Color(163, 73, 164));
        		} else if(board.get(i).get(j) == 1024){
        			g2.setColor(Color.WHITE);
        		}
        		g2.drawString(board.get(i).get(j).toString(), j*100+100, i*100+100);
        		g2.setColor(Color.BLACK);
        	}
        } 
        Toolkit.getDefaultToolkit().sync();
        g2.dispose();
    }

    private class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            state.keyPressed(e);
            repaint();
            ArrayList<ArrayList<Integer>> board = state.getBoard();
            for(int i = 0; i < 5; i++){
            	for(int j = 0; j < 5; j++){
            		if(board.get(i).get(j) == 2048){
            		        int option = JOptionPane.showConfirmDialog(null, "You Win! Play Again?", "YOU WIN", JOptionPane.YES_NO_OPTION);
            		       	if(option == 0){
            		      		state = new State();
            		       	} else {
            		       		System.exit(0);
            		       	} 
            		}
            	}
            }
            if(state.getRandomEmptyCord() == null){
            	int option = JOptionPane.showConfirmDialog(null, "Yout Lose! Play Again?", "YOU LOSE", JOptionPane.YES_NO_OPTION);
		       	if(option == 0){
		      		state = new State();
		       	} else {
		       		System.exit(0);
		       	}
            }
            repaint();
        }
    }
}
