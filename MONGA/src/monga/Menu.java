package monga;

import java.awt.CardLayout;

import java.awt.EventQueue;
import java.io.*;
import java.lang.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorListener;


public class Menu {

	/**
	 * Monga Login Manager, Admin Dashboard & GUI programmed by Alex Fonseka 2017
	 */
			
	ArrayList<String> users = new ArrayList<String>();  //Contains user names
	ArrayList<String> secret = new ArrayList<String>(); //Contains user passwords
	ArrayList<String> rights = new ArrayList<String>(); //Contains user rights (User/Superuser)
	
	//All ArrayLists share common elementID. 
		
	private JPasswordField passwordField;
	private JTextField usernameField;
	private JLabel usernameStatus = new JLabel("");
	private JLabel passwordStatus = new JLabel("");
	private JLabel loginStatus = new JLabel("");
	private JLabel tooltip = new JLabel("");
	protected static JFrame frame = new JFrame("Monga");
	private JPanel panelCont = new JPanel();			//Global GUI Components
	private JPanel login = new JPanel();
	private JPanel gameMenu = new JPanel();
	private JPanel highScore = new JPanel();
	private JPanel adminDashboard = new JPanel();
	private JPanel gridSelector = new JPanel();
	private CardLayout cl = new CardLayout();
	
	private String loggedInUser;
	private boolean isAnAdmin;		
	private int selectedGrid = 1;
	
	private Formatter formatter;
	private Scanner scanner;
	
		
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Menu() {
		
		/* Intialize all panels and add to CardLayout. 
		 * Set Resolution 500x500
		 * Set Layout to Absolute.  
		*/
		
		
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		panelCont.setLayout(cl);							
		panelCont.add(login , "1");
		panelCont.add(gameMenu, "2");
		panelCont.add(highScore, "3");
		panelCont.add(adminDashboard, "4");
		panelCont.add(gridSelector, "5");
		AncestorListener arg0 = null;
		panelCont.addAncestorListener(arg0);
		cl.show(panelCont, "1");
		frame.add(panelCont);
		login.setLayout(null);
		gameMenu.setLayout(null);
		highScore.setLayout(null);
		adminDashboard.setLayout(null);
		gridSelector.setLayout(null);
		
		try {
			readFromDisk();     //Read from txt and populate ArrayList for read and manipulation
		}
		catch (Exception e) { 
			System.out.println("users.txt is missing. Creating users.txt");  // If txt missing, create new empty txt
			writeToDisk();
		}
		displayLogin();
		
	}
	
	/* GUI Code fairly self explanatory. Please refer to any attached Strings (eg."Login Successful" or "Incorrect Password" to easily 
	 * identify which segment does what. Exceptions apply, and those are exclusively commented on. */

	
	private void displayLogin() {   //Contains all elements of Login menu
				
		JLabel mongaTitle = new JLabel("Monga Login Manager");
		mongaTitle.setForeground(Color.WHITE);
		mongaTitle.setBounds(164, 57, 211, 46);
		mongaTitle.setFont(new Font("Tahoma", Font.PLAIN, 17));
		login.add(mongaTitle);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(180, 188, 140, 29);
		login.add(passwordField);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(105, 134, 65, 29);
		login.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(105, 191, 65, 22);
		login.add(lblPassword);
		
		JButton btnLogin = new JButton("Login");  //Login button code begins.
		btnLogin.setBounds(203, 243, 89, 23);
		btnLogin.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				/* Get String from user and pass fields. 
				 * Iterate through ArrayList and compare String.
				 */
				
				String userBuffer = usernameField.getText();
				String passBuffer = passwordField.getText();  
				String storedUser = null;
				String storedPass = null;
				
				
								
				for (int i =0; i < users.size(); i++) {
				
				String right = rights.get(i);
				storedUser = users.get(i);
				storedPass = secret.get(i);
				
								
				if (userBuffer.equals(storedUser)) {
					
					if(passBuffer.equals(storedPass)){
						loginStatus.setText("Login successful!");   //Log user in and set Admin boolean appropriately
						usernameStatus.setText("");
						passwordStatus.setText("");
						loggedInUser = userBuffer;
						if(right.equals("Superuser")){
								isAnAdmin = true;
						}
						else{
							isAnAdmin = false;
						}
						gameMenu.revalidate();
						login.revalidate();
						panelCont.revalidate();
						
						displayMainMenu();
						
						break;
					}
					else{
						passwordStatus.setText("Incorrect password");
						usernameStatus.setText("");
						loginStatus.setText("");
						break;
					}
				}
				
				else{
					usernameStatus.setText("User does not exist");
					passwordStatus.setText("");
					loginStatus.setText("");
					
									
			}
			
			}
				
		}
		});
		login.add(btnLogin);  //Add login button to JFrame. Login button code ends.
				
		usernameField = new JTextField();
		usernameField.setBounds(180, 134, 140, 29);
		usernameField.setColumns(10);
		usernameStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		usernameStatus.setBounds(330, 137, 154, 22);
		login.add(usernameField);
		
		
		usernameStatus.setForeground(Color.RED);
		login.add(usernameStatus);
		passwordStatus.setBounds(330, 191, 154, 22);
				
		passwordStatus.setForeground(Color.RED);
		login.add(passwordStatus);
		loginStatus.setBounds(310, 240, 154, 29);
				
		loginStatus.setForeground(Color.GREEN);
		login.add(loginStatus);
		
		JButton btnNewUser = new JButton("New User");    //Register user stuff starts
		btnNewUser.setBounds(203, 277, 89, 23);
		btnNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userBuffer = usernameField.getText();
				String passBuffer = passwordField.getText();
				Boolean alreadyExists = false;
				
				if(userBuffer.equals("") || (passBuffer.equals(""))){
					JOptionPane.showMessageDialog(null, "Login credentials incomplete!");
				}
				
				else{
				
				for(int i=0; i < users.size();i++){
					String storedUser = users.get(i);
					if(storedUser.equals(userBuffer)){
						alreadyExists = true;
						break;
						
					}
					else{
						alreadyExists = false;
					}
				}
				
				if(alreadyExists != true){
					users.add(userBuffer);      //Update loaded ArrayList with new element
					secret.add(passBuffer);
					rights.add("User");
					usernameStatus.setText("");
					passwordStatus.setText("");
					loginStatus.setText("");
					JOptionPane.showMessageDialog(null, "Successfully added user: "+userBuffer);
					showUserList();
					writeToDisk();  //Dump new ArrayList into txt so can be restored if program is terminated
					
				}
				else{
					JOptionPane.showMessageDialog(null, "User already exists!");
				}
				
																
			}
			}
		});
		login.add(btnNewUser); 	//Add New User button to JPanel. New User code ends.
		
					
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("resources/loginBG.jpg"));
		lblBackground.setBounds(0, 0, 500, 500);
		login.add(lblBackground);
	
		
		
	}
	
	private void displayMainMenu(){  //Contains all elements of Main Menu
		
				
		JLabel mainMenuTitle = new JLabel("Monga Main Menu");
		mainMenuTitle.setForeground(Color.WHITE);
		mainMenuTitle.setBounds(185, 65, 350, 46);
		mainMenuTitle.setFont(new Font("Tahoma", Font.PLAIN, 17));
		gameMenu.add(mainMenuTitle);
		
				
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] args = {};
				try{
					Window.main(args);     //Launch Monga in-game window
				}
				catch(Exception e){
					System.out.println("failed to launch");
				}
				Board.currentPlayerName = loggedInUser;  //Let board know the name of the player that has logged in.
				frame.setVisible(false);	//Dispose login/menu window
				frame.dispose();
			}
		});
		
		btnPlay.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	tooltip.setText("Enter game as "+loggedInUser);
		    	tooltip.setFont(new Font("Tahoma", Font.PLAIN, 11));
				tooltip.setBounds(320, 150, 154, 22);
				tooltip.setForeground(Color.WHITE);
				tooltip.setVisible(true);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	tooltip.setVisible(false);
		    }
		});
		btnPlay.setBounds(203, 150, 100, 23);
		btnPlay.setEnabled(true);
		gameMenu.add(btnPlay);
		
		JButton btnHighScores = new JButton("Highscores");
		btnHighScores.setBounds(203, 200, 100, 23);
		btnHighScores.setEnabled(true);
		btnHighScores.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				showHighScores();
			}
		});
		
		btnHighScores.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	tooltip.setText("View highscores");
		    	tooltip.setFont(new Font("Tahoma", Font.PLAIN, 11));
				tooltip.setBounds(320, 200, 154, 22);
				tooltip.setForeground(Color.WHITE);
				tooltip.setVisible(true);
				
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	tooltip.setVisible(false);
		    }
		});
		gameMenu.add(btnHighScores);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		btnExit.setBounds(203, 250, 100, 23);
		btnExit.setEnabled(true);
		gameMenu.add(btnExit);
		
		btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	tooltip.setText("Quit Monga");
		    	tooltip.setFont(new Font("Tahoma", Font.PLAIN, 11));
				tooltip.setBounds(320, 250, 154, 22);
				tooltip.setForeground(Color.WHITE);
				tooltip.setVisible(true);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	tooltip.setVisible(false);
		    }
		});
		
		gameMenu.add(tooltip);
		
		
		JLabel lblSettingsIcon = new JLabel("");
		lblSettingsIcon.setIcon(new ImageIcon("resources/settingsButton.png"));
		lblSettingsIcon.setBounds(20, 404, 35, 35);
		
		if(isAnAdmin != false){
			gameMenu.add(lblSettingsIcon);
		}
		
		
				
		lblSettingsIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showAdminDash();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				
				if(isAnAdmin != false){
					tooltip.setText("Access admin features");
				}
								
		    	tooltip.setFont(new Font("Tahoma", Font.PLAIN, 11));
				tooltip.setBounds(70, 410, 200, 22);
				tooltip.setForeground(Color.WHITE);
				tooltip.setVisible(true);
			
			}
			@Override
			public void mouseExited(MouseEvent e) {
			
				tooltip.setVisible(false);
			
			}
		});
		
		JLabel currentUserInfo = new JLabel("Logged in as "+loggedInUser, SwingConstants.RIGHT);
		currentUserInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		currentUserInfo.setBounds(280, 408, 174, 31);
		currentUserInfo.setForeground(Color.WHITE);
		gameMenu.add(currentUserInfo);
		
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("resources/loginBG.jpg"));
		lblBackground.setBounds(0, 0, 500, 500);
		gameMenu.add(lblBackground);
		
		
		
		cl.show(panelCont, "2");  /* Calling this switches view in the CardLayout. The passed in String referes to the panel number.
		In this case "2" is for the Main Menu. 
		*/
		
	}
	
	private void showHighScores(){  //Contains all elements of High Scores sub menu
		
		JLabel highScoreTitle = new JLabel("Monga Highscores");
		highScoreTitle.setForeground(Color.WHITE);
		highScoreTitle.setBounds(185, 65, 350, 46);
		highScoreTitle.setFont(new Font("Tahoma", Font.PLAIN, 17));
		highScore.add(highScoreTitle);
		
		/*  Nothing in here currently. Theoretically highscores will be read off a txt and displayed here. 
		 * Highscore logging code is missing in Board class. 
		 */
		
		JButton btnExit = new JButton("Back");
		btnExit.setBounds(20, 404, 90, 23);
		btnExit.setEnabled(true);
		btnExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				cl.show(panelCont, "2");
			}
		});
		
		
		highScore.add(btnExit);
	
		JLabel currentUserInfo = new JLabel("Logged in as "+loggedInUser, SwingConstants.RIGHT);
		currentUserInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		currentUserInfo.setBounds(280, 408, 174, 31);
		currentUserInfo.setForeground(Color.WHITE);
		highScore.add(currentUserInfo);	
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("resources/loginBG.jpg"));
		lblBackground.setBounds(0, 0, 500, 500);
		highScore.add(lblBackground);
			
		
		cl.show(panelCont, "3");
	}
	
	private void showAdminDash() {  //Contains all elements of Admin Dash submenu
		
		JLabel adminTitle = new JLabel("Admin Dashboard");
		adminTitle.setFont(new Font("Tahoma", Font.PLAIN, 17));
		adminTitle.setBounds(180, 11, 156, 48);
		adminTitle.setForeground(Color.WHITE);
		adminDashboard.add(adminTitle);
		
				
		JButton btnUsers = new JButton("Users");
		btnUsers.setBounds(38, 75, 89, 23);
		btnUsers.setEnabled(false);
		adminDashboard.add(btnUsers);
				
		
		JButton btnMaps = new JButton("Maps");
		btnMaps.setBounds(143, 75, 89, 23);
		btnMaps.setEnabled(true);
		btnMaps.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				showGridSelector();
			}
		});
		
		adminDashboard.add(btnMaps);
		
				
		
		JButton btnExit = new JButton("Back");
		btnExit.setBounds(20, 404, 90, 23);
		btnExit.setEnabled(true);
		btnExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				cl.show(panelCont, "2");
			}
		});
		adminDashboard.add(btnExit);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> userList = new JList<>( model );
		userList.setToolTipText("Users currently registered");
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userList.setBounds(38, 111, 224, 216);
						
		for(int i =0; i < users.size(); i++){
			String tUser = users.get(i);
			model.addElement(tUser);
		}
		
		JScrollPane scroll = new JScrollPane(userList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); /* NOTE: I Never got this to work.
		If the number of users exceeds a certain limit, any username after that will be placed outside the boundaries of the JList (ie. Will not be visible).
		Cannot scroll down/up because scroll bar doesn't show up. 
		 */
		
		adminDashboard.add(userList);
		
		JButton btnDelete = new JButton("Delete ");
		btnDelete.setBounds(330, 168, 89, 23);
		btnDelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				try{
					
				String selectedUser = null;
				
				selectedUser = userList.getSelectedValue();
							
				for(int i =0; i < users.size(); i++){
					String thisUser = users.get(i);
					if(selectedUser.equals(loggedInUser)){
						JOptionPane.showMessageDialog(null, "You can't delete yourself, fool!");  //No offense.
						break;
					}
										
					else if(selectedUser.equals(thisUser)){
						users.remove(i);
						secret.remove(i);
						rights.remove(i);
						writeToDisk();
						model.removeAllElements();
						for(int j =0; j < users.size(); j++){
							String tUser = users.get(j);
							model.addElement(tUser);
						}
						JOptionPane.showMessageDialog(null, thisUser+" was deleted from the system");
						break;
					}
				}
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null, "No user was selected");
					return;
				}
				
			}
			
		});
		adminDashboard.add(btnDelete);
		
		JButton btnPromote = new JButton("Promote");
		btnPromote.setBounds(330, 214, 89, 23);
		btnPromote.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
			try{
				String selectedUser = null;
				
				selectedUser = userList.getSelectedValue();
							
				for(int i =0; i < users.size(); i++){
					String thisUser = users.get(i);
					String thisRight = rights.get(i);
					if(thisUser.equals(selectedUser)){
						if(thisRight.equals("Superuser")){
							JOptionPane.showMessageDialog(null, thisUser+" is already an admin!");
							break;
						}
						else{
							rights.set(i, "Superuser");
							writeToDisk();
							JOptionPane.showMessageDialog(null, selectedUser+" was promoted to an admin!");
							break;
						}
					}
					if(selectedUser.equals(null)){
						JOptionPane.showMessageDialog(null, "No user was selected");
						break;
					}
				}
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "No user was selected");
				return;
			}
			}
		});
		adminDashboard.add(btnPromote);
		
		
		JLabel currentUserInfo = new JLabel("Logged in as "+loggedInUser, SwingConstants.RIGHT);
		currentUserInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		currentUserInfo.setBounds(280, 408, 174, 31);
		currentUserInfo.setForeground(Color.WHITE);
		adminDashboard.add(currentUserInfo);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("resources/loginBG.jpg"));
		lblBackground.setBounds(0, 0, 500, 500);
		adminDashboard.add(lblBackground);
		
		cl.show(panelCont, "4");
	}
	
	private void showGridSelector(){   //Contains all elements of GridSelector submenu
		
				
		JLabel heading = new JLabel("Admin Dashboard");
		heading.setBounds(180, 11, 156, 48);
		heading.setFont(new Font("Tahoma", Font.PLAIN, 17));
		heading.setForeground(Color.WHITE);
		gridSelector.add(heading);
		
		JLabel currentUserInfo = new JLabel("Logged in as "+loggedInUser, SwingConstants.RIGHT);
		currentUserInfo.setBounds(280, 408, 174, 31);
		currentUserInfo.setForeground(Color.WHITE);
		currentUserInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		gridSelector.add(currentUserInfo);
		
		JButton btnUsers = new JButton("Users");
		btnUsers.setBounds(38, 75, 89, 23);
		btnUsers.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				cl.show(panelCont, "4");
			}
		});
		gridSelector.add(btnUsers);
		
		JButton btnMaps = new JButton("Maps");
		btnMaps.setBounds(143, 75, 89, 23);
		btnMaps.setEnabled(false);
		gridSelector.add(btnMaps);
		
		JLabel gridPreview = new JLabel("");
		gridPreview.setIcon(new ImageIcon("resources/grid1.png"));
		gridPreview.setBounds(91, 116, 300, 250);
		gridSelector.add(gridPreview);
		
		JLabel cycleBackward = new JLabel("<");
		cycleBackward.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (selectedGrid == 1){
					gridPreview.setIcon(new ImageIcon("resources/grid3.png"));
					selectedGrid = 3;
					
				}
				else if (selectedGrid == 3){
					gridPreview.setIcon(new ImageIcon("resources/grid2.png"));
					selectedGrid = 2;
					
				}
				else if (selectedGrid == 2){
					gridPreview.setIcon(new ImageIcon("resources/grid1.png"));
					selectedGrid = 1;
					
				}
				
				
			}
		});
		cycleBackward.setFont(new Font("Tahoma", Font.PLAIN, 40));
		cycleBackward.setBounds(23, 206, 48, 48);
		cycleBackward.setForeground(Color.WHITE);
		gridSelector.add(cycleBackward);
		
		JLabel cycleForward = new JLabel(">");
		cycleForward.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectedGrid == 1){
					gridPreview.setIcon(new ImageIcon("resources/grid2.png"));
					selectedGrid = 2;
					
					
				}
				else if (selectedGrid ==2) {
					gridPreview.setIcon(new ImageIcon("resources/grid3.png"));
					selectedGrid = 3;
					
				}
				else if (selectedGrid == 3) {
					gridPreview.setIcon(new ImageIcon("resources/grid1.png"));
					selectedGrid = 1;
					
				}
			}
		});
		cycleForward.setFont(new Font("Tahoma", Font.PLAIN, 40));
		cycleForward.setBounds(426, 206, 48, 48);
		cycleForward.setForeground(Color.WHITE);
		gridSelector.add(cycleForward);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(196, 389, 89, 23);
		btnConfirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				
				Board.mapID = selectedGrid;    /* Cycling forward and backward changes the selectedGrid variable.
				When clicking confirm, the selectedGrid becomes the mapID variable in the board.
				When board launches, it creates the layout according to the mapID.*/
				
				JOptionPane.showMessageDialog(null, "Confirmed!");
			}
		});
		gridSelector.add(btnConfirm);
		
		JButton btnExit = new JButton("Back");
		btnExit.setBounds(20, 404, 90, 23);
		btnExit.setEnabled(true);
		btnExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				cl.show(panelCont, "2");
			}
		});
		gridSelector.add(btnExit);
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon("resources/loginBG.jpg"));
		lblBackground.setBounds(0, 0, 500, 500);
		gridSelector.add(lblBackground);
				
		cl.show(panelCont, "5");
	}
	

	private void showUserList(){   //Print all users in ArrayList to console. Developer use only.
		for(int i =0; i < users.size(); i++){
			System.out.println(rights.get(i)+" "+users.get(i)+" "+secret.get(i));
			
		}
	}
	
	private void writeToDisk(){   //Dump all elements in ArrayList to txt so can be reloaded later if program is terminated.
		try{
			formatter = new	Formatter("users.txt");
		}
		catch(Exception e){
			System.out.println("File Write Failure");
		}
		for(int i =0; i < users.size(); i++){
			String user = users.get(i);
			String pass = secret.get(i);
			String right = rights.get(i);
			formatter.format("%s%s%s%n", right+" ", user+" ", pass);
		}
		formatter.close();
		
	}
	
	private void readFromDisk(){  //Read from txt and populate arrayList
		try{
			scanner = new Scanner(new File("users.txt"));
		}
		catch(Exception e){
			System.out.println("File Read Failure");
		}
		
	    while(scanner.hasNext()){
	    	String right = scanner.next();
	    	rights.add(right);
	    	String user = scanner.next();
	    	users.add(user);
	    	String pass = scanner.next();
	    	secret.add(pass);
	    }
	    scanner.close();
	}
	

}

/* Alex Fonseka 
 * Team Monga - SEF S1, 17
 */
