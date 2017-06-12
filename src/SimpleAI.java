import java.util.ArrayList;
import java.util.List;

public class SimpleAI implements AI{
	private Display d;
	private Sprite player;
	private Food f;
	
	private List<Location> locations;
	private Location head;
	
	public SimpleAI(Display display, Sprite sprite, Food food){
		d=display;
		player=sprite;
		f=food;
	}
	
	public void changeDirection(){
		locations=player.getLocs();
		
		head=locations.get(0);
		
		goTowardsFood();
		
/*		if(player.getBearing()==Location.NORTH&&top()){
			if(!left()) player.setBearing(Location.WEST);
			else if(!right()) player.setBearing(Location.EAST);
		}
		else if(player.getBearing()==Location.SOUTH&&bottom()){
			if(!left()) player.setBearing(Location.WEST);
			else if(!right()) player.setBearing(Location.EAST);
		}
		else if(player.getBearing()==Location.EAST&&right()){
			if(!top()) player.setBearing(Location.NORTH);
			else if(!bottom()) player.setBearing(Location.SOUTH);
		}
		else if(player.getBearing()==Location.WEST&&left()){
			if(!top()) player.setBearing(Location.NORTH);
			else if(!bottom()) player.setBearing(Location.SOUTH);
		}
		*/
		if(player.getBearing()==Location.WEST&&!isValid(new Location(head.getRow(),head.getCol()-1))){
			
				if(isValid(new Location(head.getRow()-1,head.getCol()))) player.setBearing(Location.NORTH);
				else if(isValid(new Location(head.getRow()+1,head.getCol()))) player.setBearing(Location.SOUTH);
			
			
		}
		else if(player.getBearing()==Location.EAST&&!isValid(new Location(head.getRow(),head.getCol()+1))){
			
				if(isValid(new Location(head.getRow()-1,head.getCol()))) player.setBearing(Location.NORTH);
				else if(isValid(new Location(head.getRow()+1,head.getCol()))) player.setBearing(Location.SOUTH);
			
			
		}
		else if(player.getBearing()==Location.NORTH&&!isValid(new Location(head.getRow()-1,head.getCol()))){
			
				
				if(isValid(new Location(head.getRow(),head.getCol()+1))) player.setBearing(Location.EAST);
				else if(isValid(new Location(head.getRow(),head.getCol()-1))) player.setBearing(Location.WEST);
			
			
		}
		else if(player.getBearing()==Location.SOUTH&&!isValid(new Location(head.getRow()+1,head.getCol()))){
			
				
				if(isValid(new Location(head.getRow(),head.getCol()+1))) player.setBearing(Location.EAST);
				else if(isValid(new Location(head.getRow(),head.getCol()-1))) player.setBearing(Location.WEST);
			
			
		}
		
		
			
		
		
	}
	
	private void goTowardsFood(){
		if(f.getLocation().getRow()>head.getRow()) player.setBearing(Location.SOUTH);
		else if(f.getLocation().getRow()<head.getRow()) player.setBearing(Location.NORTH);
		else if(f.getLocation().getCol()>head.getCol()) player.setBearing(Location.EAST);
		else if(f.getLocation().getCol()<head.getCol()) player.setBearing(Location.WEST);
	}
	
	private boolean isValidBounds(Location l){
		if(l.getRow()<0||l.getRow()>=d.getRows()||l.getCol()<0||l.getCol()>d.getCols()-1)
			return false;

		return true;
	}
	
	private boolean isValid(Location l){


		/*if(player.getBearing()==Location.NORTH&&top()){
			return false;
		}
		else if(player.getBearing()==Location.SOUTH&&bottom()){
			return false;
		}
		else if(player.getBearing()==Location.EAST&&right()){
			return false;
		}
		else if(player.getBearing()==Location.WEST&&left()){
			return false;
		}*/

		if(!isValidBounds(l)) return false;


		//if(!player.isValid(l)) return false;

		if(player.getLocs().contains(l)) return false;

		return true;
	}
	
	private boolean top(){
		if(head.getRow()-1<0) return true;
		
		return false;
	}
	
	private boolean bottom(){
		if(head.getRow()+1>=d.getRows()) return true;
		
		return false;
	}
	
	private boolean right(){
		if(head.getCol()+1>=d.getCols()) return true;
		
		return false;
	}
	
	private boolean left(){
		if(head.getCol()-1<0) return true;
		
		return false;
	}
	
	public void print(){
		
	}
	
	
}
