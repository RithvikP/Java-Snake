
public class Food {
	private Location loc;
	private boolean visible;
	
	public Food(Location l){
		loc=l;
		visible=true;
	}
	
	public Food(){
		loc=null;
		visible=true;
	}
	
	public Location getLocation(){
		return loc;
	}
	
	public boolean isVisible(){ return visible; }
	
	public void setVisible(boolean v){ visible=v; }
	
	public void setLocation(Location l){
		loc=l;
	}
	
	public void setRandomLocation(int r, int c){
		loc=new Location((int)(Math.random()*r),(int)(Math.random()*c));
	}
}
