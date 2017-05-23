
public class Location {
	
	private int row;
	private int col;
	
	public static final int NORTH=0;
	public static final int SOUTH=180;
	public static final int EAST=90;
	public static final int WEST=270;
	
	public Location(int r,int c){
		row=r;
		col=c;
	}
	
	public int getRow(){ return row; }
	public int getCol(){ return col; }
	
	public void setRow(int r){ row=r; }
	public void setCol(int c){ col=c; }
	
	public boolean equals(Object obj){
		if(row==((Location)obj).getRow()&&col==((Location)obj).getCol())
			return true;
		
		return false;
	}
	
	public String toString(){
		return row+" "+col;
	}
}
