import java.util.ArrayList;
import java.util.List;

public class Sprite {
	
	List<Location> loc;
	public static final int MOVEMENT=1;
	
	private int bearing;
	
	private Food food;
	private Display d;
	
	private boolean isGrowing;
	private int growCount;
	private static final int GROW_LIM=5;
	
	public Sprite(Food f, Display d){
		loc=new ArrayList<Location>();
		loc.add(new Location(5,3));
		loc.add(0,new Location(5,4));
		loc.add(0,new Location(5,5));
	    loc.add(0,new Location(5,6));
	    loc.add(0,new Location(5,7));
		loc.add(0,new Location(5,8));
		loc.add(0,new Location(5,9));
	    loc.add(0,new Location(5,10));
	    
	    food=f;
	    
	    this.d=d;
	    
	    isGrowing=false;
	    
	    growCount=0;
	   
		bearing=Location.EAST;
		
		/*for(int i=0;i<100;i++){
			add();
		}
		*/
		
		
	}
	
	
	public void setBearing(int b){
		new Thread(new Runnable(){
            public void run(){
				if(Math.abs(bearing-b)==180){
				
				}
				else{
					bearing=b;
				}
				
				try{ Thread.sleep(150);} catch(InterruptedException e){}
            }
        }).start();
		
	}
	
	public void move(){
		
		//System.out.println(loc);
		
		for(int i=loc.size()-1;i>0;i--){
			loc.set(i,new Location(loc.get(i-1).getRow(),loc.get(i-1).getCol()));
		}
		
		if(bearing==Location.EAST) loc.get(0).setCol(loc.get(0).getCol()+MOVEMENT);
		if(bearing==Location.WEST) loc.get(0).setCol(loc.get(0).getCol()-MOVEMENT);
		if(bearing==Location.NORTH) loc.get(0).setRow(loc.get(0).getRow()-MOVEMENT);
		if(bearing==Location.SOUTH) loc.get(0).setRow(loc.get(0).getRow()+MOVEMENT);
		
		if(isGrowing) add();
		
		
	}
	
	public boolean isLost(){
		for(int i=0;i<loc.size();i++){
			for(int j=0;j<loc.size();j++){
				if(i!=j){
					if(loc.get(i).equals(loc.get(j))){
						/*System.out.println(loc.get(i));
						System.out.println(loc.get(j));
						
						System.out.println(i);
						System.out.println(j);*/
						
						return true;
					}
						
				}
			}
			
			if(loc.get(i).getCol()<0||loc.get(i).getRow()<0)
				return true;
			
			if(loc.get(i).getCol()>d.getCols()-1||loc.get(i).getRow()>d.getRows()-1)
				return true;
		}
		
		
		
		return false;
	}
	
	public void eatFood(){
		if(loc.contains(food.getLocation())){
			//add();
			food.setVisible(false);
			isGrowing=true;
		}
	}
	
	public void add(){
		
		if(growCount<GROW_LIM){
		//for(int i=0;i<7;i++){
			if(loc.get(loc.size()-1).getCol()==loc.get(loc.size()-2).getCol()){
				if(isValid(new Location(loc.get(loc.size()-1).getRow()+1,loc.get(loc.size()-1).getCol())))
					loc.add(new Location(loc.get(loc.size()-1).getRow()+1,loc.get(loc.size()-1).getCol()));
				
				else if(isValid(new Location(loc.get(loc.size()-1).getRow()-1,loc.get(loc.size()-1).getCol())))
					loc.add(new Location(loc.get(loc.size()-1).getRow()-1,loc.get(loc.size()-1).getCol()));
				
				else if(isValid(new Location(loc.get(loc.size()-1).getRow(),loc.get(loc.size()-1).getCol()+1)))
					loc.add(new Location(loc.get(loc.size()-1).getRow(),loc.get(loc.size()-1).getCol()+1));
				
				else if(isValid(new Location(loc.get(loc.size()-1).getRow(),loc.get(loc.size()-1).getCol()-1)))
					loc.add(new Location(loc.get(loc.size()-1).getRow(),loc.get(loc.size()-1).getCol()-1));
			}
			else{
				if(isValid(new Location(loc.get(loc.size()-1).getRow(),loc.get(loc.size()-1).getCol()+1)))
					loc.add(new Location(loc.get(loc.size()-1).getRow(),loc.get(loc.size()-1).getCol()+1));
				
				else if(isValid(new Location(loc.get(loc.size()-1).getRow(),loc.get(loc.size()-1).getCol()-1)))
					loc.add(new Location(loc.get(loc.size()-1).getRow(),loc.get(loc.size()-1).getCol()-1));
				
				else if(isValid(new Location(loc.get(loc.size()-1).getRow()+1,loc.get(loc.size()-1).getCol())))
					loc.add(new Location(loc.get(loc.size()-1).getRow()+1,loc.get(loc.size()-1).getCol()));
				
				else if(isValid(new Location(loc.get(loc.size()-1).getRow()-1,loc.get(loc.size()-1).getCol())))
					loc.add(new Location(loc.get(loc.size()-1).getRow()-1,loc.get(loc.size()-1).getCol()));
			}
			growCount++;
		}
		else{
			growCount=0;
			isGrowing=false;
			//System.out.println(loc.size());
		}
	}
	
	public boolean isValid(Location l){
		
		if(!loc.contains(l)){
			if(l.getCol()<d.getCols()&&l.getCol()>=0){
				if(l.getRow()<d.getRows()&&l.getRow()>=0){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public List<Location> getLocs()
	{
		return loc;
	}
}
