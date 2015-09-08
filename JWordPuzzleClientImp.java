import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import javax.swing.*;

public class JWordPuzzleClientImp {
	JFrame frame = new JFrame();
	String serverIP;
	int regStatus = 1;
	int loginStatus = 1;
	
	public JWordPuzzleClientImp(String s) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("JWordPuzzle");
		frame.setLocationRelativeTo(null);
		
		serverIP = new String(s);
		
		login();
	}
	
	public void login() {
		final JPanel loginPanel = new JPanel();
		JLabel usrID = new JLabel("User ID:");
		JLabel psswd = new JLabel("Password:");
		final JTextField ID = new JTextField(10);
		final JPasswordField password = new JPasswordField(10);
		JButton register = new JButton("New Account");
		JButton login = new JButton("Login");
		
		// Set Login Board
		loginPanel.setLayout(new GridLayout(3, 2));
		loginPanel.setBorder(BorderFactory.createTitledBorder("Login"));
		loginPanel.add(usrID);
		loginPanel.add(ID);
		loginPanel.add(psswd);
		loginPanel.add(password);
		loginPanel.add(register);
		loginPanel.add(login);
		
		// Add actions for button "New Account"
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().remove(loginPanel);
				register();
			}
			
		});
		
		// Add actions for button "Login"
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane p = new JOptionPane("");
				
				if(ID.getText().equals("")) {
					p = new JOptionPane("User ID could not be empty!", JOptionPane.ERROR_MESSAGE);
				} else if(String.valueOf(password.getPassword()).equals("")) {
					p = new JOptionPane("Password could not be empty!", JOptionPane.ERROR_MESSAGE);
				} else {
					// Start to communicate with the server
					try {
						PuzzleInterface obj = (PuzzleInterface) Naming.lookup("//"+serverIP+"/PuzzleServer");
						if(!obj.isValid(ID.getText())) {
							p = new JOptionPane("User ID is not valid!", JOptionPane.ERROR_MESSAGE);
						} else if (obj.isOnLine(ID.getText())) {
							p = new JOptionPane("User ID has logged in!", JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e1) {
						System.out.println("Puzzle Server error:" + e1.getMessage());
					}
				}
				
				if(p.getMessage().equals("")) {
					loginStatus = 0;
				} else {
					frame.getContentPane().remove(loginPanel);
					frame.getContentPane().add(p);
					frame.pack();
					loginStatus = 1;
				}
			}
		});
		
		frame.getContentPane().add(loginPanel);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void register() {
		final JPanel regPanel = new JPanel();
		JLabel usrID = new JLabel("User ID:");
		JLabel creatPwd = new JLabel("Create Password:");
		JLabel confirmPwd = new JLabel("Confirm Password:");
		final JTextField ID = new JTextField(10);
		final JPasswordField password= new JPasswordField(10);
		final JPasswordField password2= new JPasswordField(10);
		JButton clear = new JButton("Clear");
		JButton register = new JButton("Register");
		
		// Set Register Board
		regPanel.setBorder(BorderFactory.createTitledBorder("Register"));
		regPanel.setLayout(new GridLayout(4, 2));
		regPanel.add(usrID);
		regPanel.add(ID);
		regPanel.add(creatPwd);
		regPanel.add(password);
		regPanel.add(confirmPwd);
		regPanel.add(password2);
		regPanel.add(clear);
		regPanel.add(register);
		JOptionPane p = new JOptionPane();
		// Add actions for button "Clear"
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ID.setText("");
				password.setText("");
				password2.setText("");
			}
			
		});
		
		// Add actions for button "Register"
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane p = new JOptionPane("");
				
				if(ID.getText().equals("")) {
					p = new JOptionPane("User ID could not be empty!", JOptionPane.ERROR_MESSAGE);
				} else if(!checkID(ID.getText())) {
					p = new JOptionPane("User ID Ilegal!", JOptionPane.ERROR_MESSAGE);
				} else if(String.valueOf(password.getPassword()).equals("")) {
					p = new JOptionPane("Password could not be empty!", JOptionPane.ERROR_MESSAGE);
				} else if(!String.valueOf(password.getPassword()).equals(String.valueOf(password2.getPassword()))) {
					p = new JOptionPane("The passwords doesn't match!", JOptionPane.ERROR_MESSAGE);
				}
				
				if(p.getMessage().equals("")) {
					regStatus = 0;
				} else {
					frame.getContentPane().remove(regPanel);
					frame.getContentPane().add(p);
					frame.pack();
					regStatus = 1;
				}
			}
		});
		
		frame.getContentPane().add(regPanel);
		
		frame.pack();
	}
	
	// Check whether one ID to be registered is legal
	public boolean checkID(String ID) {
		if (!Character.isAlphabetic(ID.charAt(0))) {
			return false;
		} else {
			for(int i = 0; i < ID.length(); i++) {
				if(!Character.isAlphabetic(ID.charAt(i)) && !Character.isDigit(ID.charAt(i))) 
					return false;
			}
		}
		return true;
	}
	
	public static void main(String args[]) {
		new JWordPuzzleClientImp("10.0.2.15");
	}
}
