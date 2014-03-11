package calendar;

import gui.AddEvent;
import gui.LogInPanel;
import gui.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class App implements ActionListener {
	
	private Database db = new Database();
	private static String username = null;
	private static JFrame loginFrame;
	private static JFrame mainFrame;
	private static JFrame newAppointmentFrame;


	public static void main(String[] args) throws SQLException{
		//loginFrame = new LogInPanel();
		//loginFrame.setVisible(true);
		mainFrame = new MainPanel("andekol");
		mainFrame.setVisible(true);
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
	
	public static JFrame getMainFrame() {
		return mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			newAppointmentFrame = new AddEvent();
			newAppointmentFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newAppointmentFrame.setVisible(true);
	}
	
	
}
