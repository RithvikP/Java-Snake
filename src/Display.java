import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Display extends JComponent{
	
	private int width;
	private int height;
	private static final Color BACKGROUND=Color.BLACK;
	private static final int BLOCK_SIZE=30;
	
	private static final int SPACER=2;
	private static final Color SNAKE_COLOR = Color.RED;
	
	private static final Color FOOD=Color.YELLOW;
	
	private JFrame frame;
	
	private int rows;
	private int cols;
	
	private List<Location> locs;
	
	private Food food;
	
	private boolean end;
	
	
	private List<Color> colors;
	private List<Location> locs2;
	
	public Display(int c, int r){
		height=(r)*BLOCK_SIZE+r*(SPACER/2)+BLOCK_SIZE-SPACER/2-SPACER;
		width=(c)*BLOCK_SIZE+c*SPACER/2;
		rows=r;
		cols=c;
		init();
		end=false;
		colors=new ArrayList<Color>();
		locs2=new ArrayList<Location>();
	}
	
	private void init(){
		frame = new JFrame("Snake");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);
        
        frame.getContentPane().setBackground(BACKGROUND);
        frame.getContentPane().add(this);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        
        frame.setVisible(true);
        
       
	}
	
	public void paint(Graphics g){
		
		
		if(!end){
			for(int i=0;i<rows;i++){
				for(int j=0;j<cols;j++){
					
					if(locs.contains(new Location(i,j))){
						g.setColor(SNAKE_COLOR);
						g.fillRect(j*BLOCK_SIZE, i*BLOCK_SIZE,BLOCK_SIZE/*-SPACER*/, BLOCK_SIZE/*-SPACER*/);
						
					}
					else{
						g.setColor(BACKGROUND);
						g.fillRect(j*BLOCK_SIZE, i*BLOCK_SIZE,BLOCK_SIZE/*-SPACER*/, BLOCK_SIZE/*-SPACER*/);
					}
				}
			}
			
			
			if(food.isVisible()){
				g.setColor(FOOD);
				g.fillRect(food.getLocation().getCol()*BLOCK_SIZE, 
						food.getLocation().getRow()*BLOCK_SIZE, BLOCK_SIZE-SPACER,
							BLOCK_SIZE-SPACER);
			}
			
		}
		else{
	
				colors.add(new Color((int)
	                    (Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
				while(colors.get(colors.size()-1).equals(Color.BLACK)){
					colors.set(colors.size()-1,new Color((int)
		                    (Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
				}
				
				
				for(int i=0;i<locs2.size()-1;i++){
					g.setColor(colors.get(i));
					g.fillRect(locs.get(i).getCol()*BLOCK_SIZE, 
							locs.get(i).getRow()*BLOCK_SIZE,BLOCK_SIZE/*-SPACER*/, BLOCK_SIZE/*-SPACER*/);
				}
				for(int i=locs2.size();i<locs.size();i++){
					g.setColor(SNAKE_COLOR);
					g.fillRect(locs.get(i).getCol()*BLOCK_SIZE, 
							locs.get(i).getRow()*BLOCK_SIZE,BLOCK_SIZE/*-SPACER*/, BLOCK_SIZE/*-SPACER*/);
				}
				
				
			
			
			
		}
		
		
	}
	
	public void displayPlayer(Sprite p)
	{
		locs=p.getLocs();
	}
	
	
	public void displayFood(Food f){
		food=f;
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	public int getRows(){return rows;}
	public int getCols(){return cols;}
	
	public void endGame(){
		end=true;
		
		
		for(int i=0;i<locs.size();i++){
			locs2.add(locs.get(i));
			repaint();
			try{ Thread.sleep(20); } catch(InterruptedException e){}
		}
	
	}
	
	public boolean isEnd(){
		return end;
	}
	
	public void clear(){
		frame.dispose();
	}
	
}
