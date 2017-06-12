import java.util.ArrayList;
import java.util.List;

public class DeterministicAI implements AI{
	private Display d;
	private Sprite player;
	private Food f;

	private List<Location> locations;
	private Location head;
	
	private Location foodLoc;
	private int index;

	private List<Integer> bestMoves;

	public DeterministicAI(Display display, Sprite sprite, Food food){
		d=display;
		player=sprite;
		f=food;

		index=0;
	}


	public void changeDirection(){
		locations=player.getLocs();


		head=locations.get(0);
		Location move=calculateOutcomes(head);

		if(move==null){

			if(player.getBearing()==Location.NORTH&&top()){
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
			/*
			Location l=head;
			if(isValid(new Location(l.getRow()-1,l.getCol()))) player.setBearing(Location.NORTH);
			if(isValid(new Location(l.getRow()+1,l.getCol()))) player.setBearing(Location.SOUTH);
			if(isValid(new Location(l.getRow(),l.getCol()+1))) player.setBearing(Location.EAST);
			if(isValid(new Location(l.getRow(),l.getCol()-1))) player.setBearing(Location.WEST);*/
		}
		else if(move.getCol()>head.getCol()) player.setBearing(Location.EAST);
		else if(move.getCol()<head.getCol()) player.setBearing(Location.WEST);
		else if(move.getRow()>head.getRow()) player.setBearing(Location.SOUTH);
		else if(move.getRow()<head.getRow()) player.setBearing(Location.NORTH);
	}

	private Location calculateOutcomes(Location l){

		int bestMove=Integer.MAX_VALUE;
		Location move=null;
		if(isValid(new Location(l.getRow(),l.getCol()+1))&&(move==null||
				distance(new Location(l.getRow(),l.getCol()+1))<bestMove)&&
				notDeadEnd(new Location(l.getRow(),l.getCol()+1))){
			bestMove=distance(new Location(l.getRow(),l.getCol()+1));
			move=new Location(l.getRow(),l.getCol()+1);
		}
		if(isValid(new Location(l.getRow(),l.getCol()-1))&&(move==null||
				((distance(new Location(l.getRow(),l.getCol()-1))<bestMove||
				isSafer(new Location(l.getRow(),l.getCol()-1),move))&&
				notDeadEnd(new Location(l.getRow(),l.getCol()-1))))){
			bestMove=distance(new Location(l.getRow(),l.getCol()-1));
			move=new Location(l.getRow(),l.getCol()-1);
		}
		if(isValid(new Location(l.getRow()+1,l.getCol()))&&(move==null||
				((distance(new Location(l.getRow()+1,l.getCol()))<bestMove||
				isSafer(new Location(l.getRow()+1,l.getCol()),move))&&
				notDeadEnd(new Location(l.getRow()+1,l.getCol()))))){
			bestMove=distance(new Location(l.getRow()+1,l.getCol()));
			move=new Location(l.getRow()+1,l.getCol());
		}
		if(isValid(new Location(l.getRow()-1,l.getCol()))&&(move==null||
				((distance(new Location(l.getRow()-1,l.getCol()))<bestMove||
				isSafer(new Location(l.getRow()-1,l.getCol()),move))&&
				notDeadEnd((new Location(l.getRow()-1,l.getCol())))))){
			bestMove=distance(new Location(l.getRow()-1,l.getCol()));
			move=new Location(l.getRow()-1,l.getCol());
		}


		//System.out.println(move);
		return move;

	}

	private int distance(Location l){

		//Euclidean Distance
		double euclidean = (Math.sqrt(Math.pow((f.getLocation().getRow()-l.getRow()),2)+
				Math.pow((f.getLocation().getCol()-l.getCol()),2)));
		
		//Manhattan Distance
		double manhattan = (Math.abs(l.getCol()-f.getLocation().getCol())+
				Math.abs(l.getRow()-f.getLocation().getRow()));
		
		//Chebyshev Distance
		double chebyshev = (Math.max(Math.abs(l.getCol()-f.getLocation().getCol()),
				Math.abs(l.getRow()-f.getLocation().getRow())));
		
		 
		//return (int)(euclidean*(1-numAround(l))+manhattan*(numAround(l)));
		
		//return (int)(euclidean*(1-numAround(l))+manhattan*(numAround(l))+1.0*chebyshev*(1-numAround(l)));
		
		
		//return (int) ((euclidean/chebyshev)+manhattan);
		//return (int) euclidean;
		return (int)manhattan;
		
		//return (int) euclidean;
		
		//return (int) chebyshev;

	}
	
	private double numAround(Location l){
		List<Location> locs=player.getLocs();
		
		int count=0;
		int total=0;
		
		for(int i=l.getRow()-3;i<l.getRow()+4;i++){
			for(int j=l.getCol()-3;j<l.getCol()+4;j++){
				
				if(isValidBounds(l)){
					total++;
					if(locs.contains(new Location(i,j))) count++;
				}
			}
		}
		
		return (total-1.0*count)/total;
		
	}

	private boolean notDeadEnd(Location l){

		boolean right = player.getLocs().subList(2, player.getLocs().size()).
				contains(new Location(l.getRow(),l.getCol()+1))||
				(l.getRow()<0||l.getRow()>=d.getRows()||l.getCol()<0||l.getCol()>d.getCols()-1);

		boolean left =  player.getLocs().subList(2, player.getLocs().size()).
				contains(new Location(l.getRow(),l.getCol()-1))||
				(l.getRow()<0||l.getRow()>=d.getRows()||l.getCol()<0||l.getCol()>d.getCols()-1);
		boolean top =  player.getLocs().subList(2, player.getLocs().size()).
				contains(new Location(l.getRow()-1,l.getCol()))||
				(l.getRow()<0||l.getRow()>=d.getRows()||l.getCol()<0||l.getCol()>d.getCols()-1);
		boolean bottom =  player.getLocs().subList(2, player.getLocs().size()).
				contains(new Location(l.getRow()+1,l.getCol()))||
				(l.getRow()<0||l.getRow()>=d.getRows()||l.getCol()<0||l.getCol()>d.getCols()-1);

		int count=0;

		if(right) count++;
		if(left) count++;
		if(top) count++;
		if(bottom) count++;

		return count<3;
	}

	private boolean isSafer(Location l1, Location l2){
		boolean right1 = player.getLocs().subList(2, player.getLocs().size()).
				contains(new Location(l1.getRow(),l1.getCol()+1))||
				(l1.getRow()<0||l1.getRow()>=d.getRows()||l1.getCol()<0||l1.getCol()>d.getCols()-1);

		boolean left1 =  player.getLocs().subList(2, player.getLocs().size()).
				contains(new Location(l1.getRow(),l1.getCol()-1))||
				(l1.getRow()<0||l1.getRow()>=d.getRows()||l1.getCol()<0||l1.getCol()>d.getCols()-1);

		boolean top1 =  player.getLocs().subList(2, player.getLocs().size()).
				contains(new Location(l1.getRow()-1,l1.getCol()))||
				(l1.getRow()<0||l1.getRow()>=d.getRows()||l1.getCol()<0||l1.getCol()>d.getCols()-1);
		boolean bottom1 =  player.getLocs().subList(2, player.getLocs().size()).
				contains(new Location(l1.getRow()+1,l1.getCol()))||
				(l1.getRow()<0||l1.getRow()>=d.getRows()||l1.getCol()<0||l1.getCol()>d.getCols()-1);
		
		boolean right2 = player.getLocs().subList(2, player.getLocs().size()).
				contains(new Location(l2.getRow(),l2.getCol()+1))||
				(l2.getRow()<0||l2.getRow()>=d.getRows()||l2.getCol()<0||l2.getCol()>d.getCols()-1);

		boolean left2 =  player.getLocs().subList(2, player.getLocs().size()).
				contains(new Location(l2.getRow(),l2.getCol()-1))||
				(l2.getRow()<0||l2.getRow()>=d.getRows()||l2.getCol()<0||l2.getCol()>d.getCols()-1);

		boolean top2 =  player.getLocs().subList(2, player.getLocs().size()).
				contains(new Location(l2.getRow()-1,l2.getCol()))||
				(l2.getRow()<0||l2.getRow()>=d.getRows()||l2.getCol()<0||l2.getCol()>d.getCols()-1);
		boolean bottom2 =  player.getLocs().subList(2, player.getLocs().size()).
				contains(new Location(l2.getRow()+1,l2.getCol()))||
				(l2.getRow()<0||l2.getRow()>=d.getRows()||l2.getCol()<0||l2.getCol()>d.getCols()-1);

		int count1=0,count2=0;
		
		if(right1) count1++;
		if(left1) count1++;
		if(top1) count1++;
		if(bottom1) count1++;
		
		if(right2) count2++;
		if(left2) count2++;
		if(top2) count2++;
		if(bottom2) count2++;

		return count1<count2;
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
		System.out.println(notDeadEnd(player.getLocs().get(0)));
	}


	/*if(player.getBearing()==Location.NORTH&&top()){
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
}*/
}
