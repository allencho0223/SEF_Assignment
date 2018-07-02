package monga;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Window
{

	static JFrame window = new JFrame("MONGA");
	static Board game = new Board();
	static Player player = new Player();

	// When the use clicks play game, it calls main function here
	// Sets JFrame size and represent GUI

	public static void main(String[] args) throws InterruptedException
	{
		
		int resolutionX = 610;
		int resolutionY = 685;
		
		window.setSize(resolutionX, resolutionY);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
		window.add(game);
		window.add(player);
		game.repaint();
	}
	
	protected static void disposeJFrame(){
		window.setVisible(false);
		window.dispose();
	}
}
