import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Snake extends KeyAdapter{

	private Sprite player;
	private Food food;
	
	private static final int ROW=30;
	private static final int COL=50;
	
	private static final int WAIT_LIMIT=115;
	
	private int wait;
	
	private Display d;
	
	public static void main(String[] args) {


		new Snake();
	}
	

	public Snake(){
	    d=new Display(COL,ROW);

		d.getFrame().addKeyListener(this);
		
		food=new Food();
		food.setRandomLocation(ROW,COL);
		player = new Sprite(food,d);
		d.displayPlayer(player);
		d.displayFood(food);
		wait=WAIT_LIMIT;		

		new Thread(new Runnable(){
			public void run(){

				while(!player.isLost()){

					//System.out.println(player.checkLost());

					d.repaint();
					d.displayPlayer(player);
					player.move();
					
					player.eatFood();

					try{ Thread.sleep(wait); } catch(InterruptedException e){}
				}
				
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

			if(e.getKeyCode()==KeyEvent.VK_RIGHT) player.setBearing(Location.EAST);
			else if(e.getKeyCode()==KeyEvent.VK_LEFT) player.setBearing(Location.WEST);
			else if(e.getKeyCode()==KeyEvent.VK_UP) player.setBearing(Location.NORTH);
			else if(e.getKeyCode()==KeyEvent.VK_DOWN) player.setBearing(Location.SOUTH);
			try{ Thread.sleep(50); } catch(InterruptedException ie){}

			if(d.isEnd()){
				if(e.getKeyCode()==KeyEvent.VK_SPACE){
					d.clear();
					new Snake();
					
				}
			}
		}

		

	}
