package monga;

public class PowerUp 
{

	//How long the Player will be Frozen
	protected static int turnsToFreeze = 4;
	protected static int scoreTurn = 1;
	
	//Adds 10 points to "score" which is what turnCount is counting
	public void scoreUp()
	{
	
			if(scoreTurn > 0)
				{
				Board.turnCount += 10;
				
				Board.star1Exsists = false;
				scoreTurn--;
				}
			
	
	}
//freezes the Player by setting the "distance"(by which he moves) to 0
	public void freeze()
	{
	 
		Player.distance = 0;
		Player.isFrozen = true;
		
		
	}
	//unfreezes the player by resetting the "distance" to 50 and setting the boolean to false
	public void unfreeze(){
		Player.distance = 50;
		Player.isFrozen = false;
		Board.star2Exsists = false;
	}
	}


