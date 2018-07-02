package monga;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JOptionPane;




@SuppressWarnings("serial")
public class Player extends Board implements KeyListener {

	
	int playerX = 50;
	int playerY = 50;
	
	int frozenTurns = PowerUp.turnsToFreeze;
	protected static boolean isFrozen = false;
	

	protected static int distance = 50;
	PowerUp power = new PowerUp();
	
	public Player()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	
	public void movePlayerUp()
	{
		if(PlayerY > 0) 
		{
		
			boolean collides = super.collisionCheck(playerX, playerY-distance);   //Check the coordinate the player is about to step into.
			if(collides != true)			
			{
		

					playerY-=distance;  //If false, update this player's coordinates
					super.setPlayerLocation(playerX, playerY); //Update Board's coordinates
					super.turnCount++; //Add to score
					if(isFrozen != false && frozenTurns <=PowerUp.turnsToFreeze){ 
						frozenTurns--;  //If the player is frozen, deduct from the frozen turns counter. If not frozen do nothing.
					}
										
					if(playerX == super.Star1X && playerY == super.Star1Y)
					{
						power.scoreUp();		//If player meets with star, add to score.
						
					}
					
					else if(playerX == super.Star2X && playerY == super.Star2Y){
						power.freeze();  //If player meets with freeze debuff, set isFrozen boolean to true
						
						
					}
					
					if(frozenTurns < 1){
						power.unfreeze();   //If player has done their time frozen, unfreeze them after set number of turns.
						
					}

			
					
		 }
		}
		
	}
			
						
	/* For other directions */

	public void movePlayerDown()
	{
		
		if(PlayerY < 500)
		{
		
			boolean collides = super.collisionCheck(PlayerX, PlayerY+distance);
			if(collides != true)
			{

		
				playerY+=distance;
				super.setPlayerLocation(playerX, playerY);
				super.turnCount++;
				if(isFrozen != false && frozenTurns <= PowerUp.turnsToFreeze){
					frozenTurns--;
				}
				
			
				if(playerX == super.Star1X && playerY == super.Star1Y)
				{
					power.scoreUp();
					
				}
				
				else if(playerX == super.Star2X && playerY == super.Star2Y){
					power.freeze();
					
					
				}
				
				if(frozenTurns < 1){
					power.unfreeze();
					
				}
			}
			
		}

		



	}
	
	public void movePlayerRight()
	{
		
		if(PlayerX < 500)
		{
			
			boolean collides = super.collisionCheck(PlayerX+distance, PlayerY);
			if(collides != true)
			{

				playerX+=distance;
				super.setPlayerLocation(playerX, playerY);
				super.turnCount++;
				if(isFrozen != false && frozenTurns <=PowerUp.turnsToFreeze){
					frozenTurns--;
				}
				
			
				if(playerX == super.Star1X && playerY == super.Star1Y)
				{
					power.scoreUp();
					
				}
				
				else if(playerX == super.Star2X && playerY == super.Star2Y){
					power.freeze();
					
					
				}
				
				if(frozenTurns < 1){
					power.unfreeze();
					
				}

				
			}}
		}





	public void movePlayerLeft()
	{
		
		 if(PlayerX > 0) 
		 {
		
			boolean collides = super.collisionCheck(PlayerX-distance, PlayerY);
			if(collides != true)
			{

				playerX-=distance;
				super.setPlayerLocation(playerX, playerY);
				super.turnCount++;
				if(isFrozen != false && frozenTurns <=PowerUp.turnsToFreeze){
					frozenTurns--;
				}
				
			
				if(playerX == super.Star1X && playerY == super.Star1Y)
				{
					power.scoreUp();
					
				}
				
				else if(playerX == super.Star2X && playerY == super.Star2Y){
					power.freeze();
					isFrozen = true;
					
				}
				
				if(frozenTurns < 1){
					power.unfreeze();
					isFrozen = false;
				}
			}
		 }



	}
	
	// Allows keyboard arrow input
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			movePlayerUp();
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
		
			movePlayerDown();
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			
			movePlayerRight();
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			
			movePlayerLeft();
		}
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		//do nothing
	}
	@Override
	public void keyTyped(KeyEvent e)
	{
		//do nothing
	}

}


