import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Snake extends KeyAdapter{

	private Sprite player;
	private Food food;
	
	private AI ai;
	
	private static final int ROW=30;
	private static final int COL=50;
	
	private static final int WAIT_LIMIT=90;
	
	private int wait;
	
	private Display d;
	
	private boolean rightPressed,leftPressed,downPressed,upPressed=false;
	
	private boolean doAI;

	
	public static void main(String[] args) {


		new Snake();
	}
	

	public Snake(){
		
		doAI=true;
		
	    d=new Display(COL,ROW);
	    
		d.getFrame().addKeyListener(this);
		
		food=new Food();
		food.setRandomLocation(ROW,COL);
		player = new Sprite(food,d);
		d.displayPlayer(player);
		d.displayFood(food);
		wait=WAIT_LIMIT;	
		
	
	
		ai=new DeterministicAI(d, player, food);

		new Thread(new Runnable(){
			public void run(){

				while(!player.isLost()){

					
					
					d.repaint();
					d.displayPlayer(player);
					player.move();
					
					player.eatFood();
					
					if(doAI)
						ai.changeDirection();
					
					

					try{ Thread.sleep(wait); } catch(InterruptedException e){}
				}
				System.out.println(player.getBearing());
				ai.print();
				d.endGame();
				
			}
		}).start();
		
		
		//Not increasing the speed of the snake
		/*new Thread(new Runnable(){
			public void run(){
				while(true){
					
					if(wait>WAIT_LIMIT) wait--;
					
					try{ Thread.sleep(1000); } catch(InterruptedException e){}
				}
			}
		}).start();*/

		new Thread(new Runnable(){
			public void run(){
				while(true){	
					if(!food.isVisible()){
						food.setRandomLocation(ROW,COL);
						food.setVisible(true);
					}
					
	
					try{ Thread.sleep(15); } catch(InterruptedException e){}
				}
			}
		}).start();

	}


		@Override
		public void keyPressed(KeyEvent e) {

			if(!rightPressed&&e.getKeyCode()==KeyEvent.VK_RIGHT){
				player.setBearing(Location.EAST);
				rightPressed=true;
			}
			else if(!leftPressed&&e.getKeyCode()==KeyEvent.VK_LEFT){
				player.setBearing(Location.WEST);
				leftPressed=true;
			}
			else if(!upPressed&&e.getKeyCode()==KeyEvent.VK_UP){
				player.setBearing(Location.NORTH);
				upPressed=true;
			}
			else if(!downPressed&&e.getKeyCode()==KeyEvent.VK_DOWN){
				player.setBearing(Location.SOUTH);
				downPressed=true;
			}
			try{ Thread.sleep(20); } catch(InterruptedException ie){}

			if(d.isEnd()){
				if(e.getKeyCode()==KeyEvent.VK_SPACE){
					d.clear();
					new Snake();
					
				}
			}
			
			if(e.getKeyCode()==KeyEvent.VK_Z) doAI=!doAI;
		}
		
		@Override
		public void keyReleased(KeyEvent e){
			if(e.getKeyCode()==KeyEvent.VK_RIGHT) rightPressed=false;
			if(e.getKeyCode()==KeyEvent.VK_LEFT) leftPressed=false;
			if(e.getKeyCode()==KeyEvent.VK_UP) upPressed=false;
			if(e.getKeyCode()==KeyEvent.VK_DOWN) downPressed=false;
		}

		

	}
