package monga;
import java.awt.Graphics;

import java.util.ArrayList;

public class Chaser {
	String pastDir = "NULL"; // determines the last direction moved  
	String colDet = "NULL"; // determines whether or not the monster hit a wall.

	int x;
	int y;
	int distance = 50;
	int i = 0;
	Graphics gg;
	ArrayList<Wall> walls = new ArrayList<Wall>();
	
	
	public Chaser(int xCo, int yCo)
	{
		this.x = xCo;
		this.y = yCo;

		
		
		
	}

	public String gamePlay(int PlayerX, int PlayerY)
	{
		
		Move( PlayerX, PlayerY);
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String StringX = Integer.toString(this.x);
		String StringY =Integer.toString(this.y);
		String MonPos = StringX + "-" + StringY;
		
				
		return MonPos;
	}
	
	
	public void Move(int PlayerX, int PlayerY){
		
		
		
	
		// If the monster did not run into a wall on its previous turn
		// calculate shortest path to the player character
		// to intercept
		if (colDet == "NULL")
		{
		
			if (PlayerX != this.x || PlayerY != this.y)
			{
				if(PlayerY > this.y)
				{
					moveMonsterDown();
				}
				
				else if(PlayerY < this.y)
				{
					moveMonsterUp();
				}	
				else if(PlayerX > this.x)
				{
					moveMonsterRight();
				}
				
				else if(PlayerX < this.x)
				{
					moveMonsterLeft();
				}	
				
			}
			
			
		}
		// If previous turn Chaser ran into a wall 
		// and monster cannot chaser player on
		// the other axis attempt to back track
		if (colDet != "NULL")
		{
			
			if(colDet == "NW")
			{
				colDet = "NULL";
				if(PlayerX > this.x)
				{
					moveMonsterRight();
				}
				
				else if(PlayerX < this.x)
				{
					moveMonsterLeft();
				}	
				
				else if(PlayerX == this.x)
				{
					moveMonsterDown();
				}
			}
			
			if(colDet == "SW")
			{
				colDet = "NULL";
				if(PlayerX > this.x)
				{
					moveMonsterRight();
				}
				
				else if(PlayerX < this.x)
				{
					moveMonsterLeft();
				}	
				
				else if(PlayerX == this.x)
				{
					moveMonsterUp();
				}
			}
			
			if(colDet == "EW")
			{
				colDet = "NULL";
				if(PlayerY > this.y)
				{
					moveMonsterDown();
				}
				
				else if(PlayerY < this.y)
				{
					moveMonsterUp();
				}
				
				else if(PlayerY == this.y)
				{
					moveMonsterLeft();
				}
			}
			
			if(colDet == "WW")
			{
				colDet = "NULL";
				
				if(PlayerY > this.y)
				{
					
					moveMonsterDown();
				}
				
				else if(PlayerY < this.y)
				{
					
					moveMonsterUp();
				}
				
				else if(PlayerY == this.y)
				{
					
					moveMonsterRight();
				}
			}
		}
	}
	
	


	public void moveMonsterUp(){
		boolean collides = collisionCheck(this.x, this.y - distance);
		if(collides != true)
		{y -=distance;}
		
		else
		{
			
			colDet ="NW";
		}
	}
	
	public void moveMonsterDown(){
		boolean collides = collisionCheck(this.x, this.y + distance);
		if(collides != true)
		{y +=distance;}
		else
		{
			
			colDet ="SW";
		}
	}
	
	public void moveMonsterRight(){
		
		boolean collides = collisionCheck(this.x + distance, this.y );
		if(collides != true)
		{x +=distance;}
		else
		{
			
			colDet ="EW";
		}
		

	}
	
	public void moveMonsterLeft(){
		boolean collides = collisionCheck(this.x - distance, this.y );
		if(collides != true)
		{x -=distance;}
		else
		{
			
			colDet ="WW";
		}


	}

	// Ensures the monster can neither leave the playing field
	// or pass through the map walls
	public boolean collisionCheck(int x, int y)
	{
		
		for (int i =0; i < walls.size(); i++){
			Wall wall = walls.get(i);
			int wallX = wall.getX();
			int wallY = wall.getY();
			
			if(wallX == x && wallY == y)
			{
				return true;
			}

			
		}
		if(x < 50 || x > 500)
		{
			return true;
		}
		else if(y < 50 || y > 500)
		{
			return true;
		}
		return false;
	}

	public void loadFirstMap()
	{
		int difference = 0;
		int brickLayout = 0;
				
		for(int i = 0; i < 4; i++)
		{
		   walls.add(new Wall(150,350-difference));
		   walls.add(new Wall(300,350-difference));
		   walls.add(new Wall(300-brickLayout, 350));
		   walls.add(new Wall(150+brickLayout, 350));
		   walls.add(new Wall(550 + brickLayout, 100));
		   walls.add(new Wall(400, 100 + difference));
		   walls.add(new Wall(100 + difference, 450));
		   walls.add(new Wall(100 + difference, 100));
		   difference += 50;
		   brickLayout -= 50;
	    }
	}
	
	public void loadSecondMap()
	{
		int difference = 0;
		int brickLayout = 0;
		for(int i = 0; i < 4; i++)
		{
			walls.add(new Wall(300 + brickLayout, 100 + difference));
			walls.add(new Wall(50 + difference, 300 + difference));
			walls.add(new Wall(450 + brickLayout, 350 + difference));
		   difference += 50;
		   brickLayout -= 50;
	    }
		brickLayout = 0;
		difference = 0;
		for(int i = 0; i < 6; i++)
		{
			walls.add(new Wall(500 + brickLayout, 100 + difference));
			brickLayout -= 50;
			difference += 50;
		}
	}
	
	public void loadThirdMap()
	{
		int difference = 0;
		int brickLayout = 0;
		for(int i = 0; i < 2; i++)
		{
			walls.add(new Wall(100, 100 + difference));
			walls.add(new Wall(400, 250 + difference));
			difference += 50;
	    }// 100, 350 to 500 
		difference = 0;
		for(int i = 0; i < 5; i++)
		{
			walls.add(new Wall(150 + difference, 100));
			walls.add(new Wall(100 + difference, 250));
			walls.add(new Wall(400 + brickLayout, 400));
			difference += 50;
			brickLayout -= 50;
		}
		difference = 0;
		for(int i = 0; i < 4; i++)
		{
			walls.add(new Wall(100, 350 + difference));
			difference += 50;
		}
	}
	
}
