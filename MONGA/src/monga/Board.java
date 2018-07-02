package monga;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.io.File;

@SuppressWarnings("serial")
public class Board extends JPanel
{
	
	int PlayerX = 50;
	int PlayerY = 50;
	protected static int turnCount = 0;
	ArrayList<Wall> walls = new ArrayList<Wall>();
	int EnemyX = 500;
	int EnemyY = 500;
	int Enemy2X = 250;
	int Enemy2Y = 250;
	int Star1X = 450;
	int Star1Y = 450;
	int Star2X = 150;
	int Star2Y = 150;
	int initX = 0;
	int initY = 0;
	int brickX = 0;
	int brickY = 0;
	protected static int mapID = 1;
	protected static String currentPlayerName = null;
	Chaser monster = new Chaser(EnemyX, EnemyY);
	RandomMonster monster2 = new RandomMonster(Enemy2X, Enemy2Y);
	protected static boolean star1Exsists = true;
	protected static boolean star2Exsists = true;
    JLabel gameScore = new JLabel();
    
    public Board() {
    	this.setLayout(null);
    	this.setSize(new Dimension(650,700));

    }
	
    // Displays objects in JPanel and game score
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		ImageIcon player = new ImageIcon("imgs/newPac.png");
		player.paintIcon(this, g, PlayerX, PlayerY);
		ImageIcon monster = new ImageIcon("imgs/monster.png");
		monster.paintIcon(this, g, EnemyX, EnemyY);
		ImageIcon monster2 = new ImageIcon("imgs/monster2.png");
		monster2.paintIcon(this, g, Enemy2X, Enemy2Y);
		ImageIcon star1 = new ImageIcon("imgs/star1.png");
		ImageIcon star2 = new ImageIcon("imgs/star2.png");
		if (star1Exsists){			
		
			star1.paintIcon(this, g, Star1X, Star1Y);
			player.paintIcon(this, g, PlayerX, PlayerY);
		}
		if (star2Exsists){			
			
			star2.paintIcon(this, g, Star2X, Star2Y);
			player.paintIcon(this, g, PlayerX, PlayerY);
		}
		
		ImageIcon brick = new ImageIcon("imgs/brick.png");
		if(initX == 0)
		{
			for(brickX = 0; brickX < 600; brickX += 50)
			{
				brick.paintIcon(this, g, brickX, initY);
			}
		}
		for(brickY = 50; brickY < 550; brickY += 50)
		{
			for(brickX = 0; brickX < 600; brickX += 50)
			{
				if(brickX == 0 || brickX == 550)
				{
					brick.paintIcon(this, g, brickX, brickY);
				}
			}
		}
		if(brickY == 550)
		{
			for(brickX = 0; brickX < 600; brickX += 50)
			{
				brick.paintIcon(this, g, brickX, brickY);
			}
		}
		
		/* Load the appropriate map based on the Map ID. Menu class modifies the map ID variable when an admin
		 * user changes the grid layout in the Admin Dashboard of Menu.*/
		
		
		
		if(mapID == 1)
		{
			loadFirstMap();
		
		}
		else if(mapID == 2)
		{
			loadSecondMap();
			
		}
		else if(mapID == 3)
		{
			loadThirdMap();
		}
		
		for (int i = 0; i < walls.size(); i++)
		{
	    	/* For every instantiated wall object, get their X and Y coordinates and print the image
	    	 * on the board.
	    	 */
			
			
			Wall wall = walls.get(i);
	    	int wallX = wall.getX();
	    	int wallY = wall.getY();
	    	brick.paintIcon(this, g, wallX, wallY);
	    } 
		

	    gameScore.setBounds(250,600,650,50);
		gameScore.setFont(new Font("Tahoma", Font.PLAIN, 20));
		gameScore.setText("Score: "+turnCount);
		this.add(gameScore);
	    this.revalidate();
	    
	    
	    
   
	}
	
	// Sets player's location and monsters get turn to move
	public void setPlayerLocation(int PlayerX, int PlayerY)
	{
		
		this.PlayerX = PlayerX;
		this.PlayerY = PlayerY; 
		repaint();	
		monsterRandomTurn();
		monsterChaserTurn();
		
		
	}
	
	// Sets Chaser's location
	public void setNPCLocation(int EnemyX, int EnemyY)
	{
		
		this.EnemyX = EnemyX;
		this.EnemyY = EnemyY;
		repaint();
		if (EnemyX == PlayerX && EnemyY == PlayerY)
		{
			
				
			File deathSound = new File("resources/death.wav");
			playSound(deathSound);
			JOptionPane.showMessageDialog(null, "Game Over! Your Score: " + turnCount);
			Window.disposeJFrame();
			
			
			
		}
		
		if(turnCount == 75){
			
			JOptionPane.showMessageDialog(null, "You survived! Your Score: " + turnCount);
			
		}
		
		
	}
	// Sets Random's location
	public void setNPC2Location(int Enemy2X, int Enemy2Y)
	{
		
		this.Enemy2X = Enemy2X;
		this.Enemy2Y = Enemy2Y;
		repaint();
		if (Enemy2X == PlayerX && Enemy2Y == PlayerY)
		{
			
				File deathSound = new File("resources/death.wav");
				playSound(deathSound);
				JOptionPane.showMessageDialog(null, "Game Over! Your Score: " + turnCount);
				Window.disposeJFrame();
				
			
		}
	}
	
	
	public boolean collisionCheck(int x, int y)
	{
		
		/*Accepts the coordinates the player/monster is about to step into and compares against all the X and Y coordinates
		 * of all objects in walls ArrayList. If matched, return true. Else return false and allow movement.
		 * Collision detector method written by Alex Fonseka. Modified by Allen Cho. */
		
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
	
	// Designs map depending on mapID's value
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
	
	public void playSound(File Sound){
			
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
			
		}
		catch (Exception e){
			System.out.println("No sound :(");
		}
	}
	
	public void monsterChaserTurn()
	{
		//Informs the monster where the walls are located in the map
		if(mapID == 1)
		{
			monster.loadFirstMap();
		}
		else if(mapID == 2)
		{
			monster.loadSecondMap();
		}
		else if(mapID == 3)
		{
			monster.loadThirdMap();
		}
		String MonPos = monster.gamePlay(PlayerX, PlayerY);
		
		//Converts the return String into readable X and Y Positions 
		//to update the monster's position 
		String[] Pos = MonPos.split("-");
		String SMonPosX = Pos[0]; 
		String SMonPosY = Pos[1];
		int MonPosX = Integer.parseInt(SMonPosX);
		int MonPosY = Integer.parseInt(SMonPosY);
		setNPCLocation(MonPosX, MonPosY);
	}
	
	public void monsterRandomTurn()
	{
		//Informs the monster where the walls are located in the map
		if(mapID == 1)
		{
			monster2.loadFirstMap();
		}
		else if(mapID == 2)
		{
			monster2.loadSecondMap();
		}
		else if(mapID == 3)
		{
			monster2.loadThirdMap();
		}
		String MonPos = monster2.gamePlay(PlayerX, PlayerY);
		
		//Converts the return String into readable X and Y Positions 
		//to update the monster's position 
		String[] Pos = MonPos.split("-");
		String SMonPosX = Pos[0]; 
		String SMonPosY = Pos[1];
		int MonPosX = Integer.parseInt(SMonPosX);
		int MonPosY = Integer.parseInt(SMonPosY);
		setNPC2Location(MonPosX, MonPosY);
	}
	
}
