import java.util.ArrayList;
import java.util.List;

public class BrokenAI implements AI {

	private Display d;
	private Sprite player;
	private Food f;

	private List<Location> locations;
	private Location head;
	
	private List<String> moves=new ArrayList<String>();
	
	public BrokenAI(Display display, Sprite sprite, Food food){
		d=display;
		player=sprite;
		f=food;

		
	}
	
	@Override
	public void changeDirection() {
		head=player.getLocs().get(0);
		
		if(head.getRow()==0){
			if(head.getCol()>0){
				player.setBearing(Location.WEST);
				System.out.println("WEST");
			}
			
			else{
				player.setBearing(Location.SOUTH);
				moves.add("SOUTH");
			}
		}
		else if(head.getCol()%2==1){
			if(head.getRow()==d.getRows()-1){
				player.setBearing(Location.EAST);
				moves.add("EAST");
			}
			else{
				player.setBearing(Location.SOUTH);
				moves.add("SOUTH");

			}
		}
		else{
			
			if(head.getRow()==1&&head.getCol()<d.getCols()-1){
				player.setBearing(Location.EAST);
				moves.add("EAST");

			}
			else{
				player.setBearing(Location.NORTH);
				moves.add("NORTH");
			}
				

		}
		
		
	}

	@Override
	public void print() {
		System.out.println(moves.subList(moves.size()-6, moves.size()));
	}

}
