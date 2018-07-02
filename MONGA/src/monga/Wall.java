package monga;
@SuppressWarnings("serial")
public class Wall extends Board {


	/*  Wall class written by Alex Fonseka.
	 * Each wall object instantiated has it's own X and Y coordinate as well as a getter and setter method. 
	 * The getter method is used by Board class to retrieve coordinates and paint on the Board. The Board class keeps
	 * track of all instantiated Walls by storing the object in an ArrayList.*/
		

	int xLocation = 0;
	int yLocation = 0;
	

	public Wall(int xLocation, int yLocation)    //Constructor to define the variables of an instance of Wall.
	{
		this.xLocation = xLocation;
		this.yLocation = yLocation;
	}

	
	public int getX()
	{
		
		return xLocation;
	
	}

	public int getY()
	{
		
		return yLocation;
	}
}


