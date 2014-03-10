package calendar;

import gui.LogInPanel;
import gui.MainPanel;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class App {
	
	private Database db = new Database();
	private static String username = null;
	private static JFrame loginFrame;
	private static JFrame mainFrame;


	public static void main(String[] args) throws SQLException{
		loginFrame = new LogInPanel();
		loginFrame.setVisible(true);
	}
	
	public void validateUser(String username, String password) throws SQLException{
		if (db.userExists(username, password)) {
			mainFrame = new MainPanel(username);
			this.username = username;
			loginFrame.setVisible(false);
			loginFrame.remove(loginFrame);
			mainFrame.setVisible(true);
		}
		else{
			JOptionPane.showMessageDialog(loginFrame, "Feil brukernavn eller passord!");
		}
	}
}
