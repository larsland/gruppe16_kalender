package calendar;

import gui.AddEvent;
import gui.LogInPanel;
import gui.MainPanel;
import gui.RegisterPane;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class App implements ActionListener {

	private Database db = new Database();
	private static String username = null;
	private static JFrame loginFrame;
	private static JFrame registerFrame;
	private static JFrame mainFrame;
	private static JFrame newAppointmentFrame;


	public static void main(String[] args) throws SQLException{
		//loginFrame = new LogInPanel();
		//loginFrame.setVisible(true);
		mainFrame = new MainPanel("andekol");
		mainFrame.setVisible(true);
	}

	public void validateUser(String username, String password) throws SQLException{
		if (db.checkPass(username, password)) {
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

	public void goToRegister() {
		registerFrame = new RegisterPane();
		loginFrame.setVisible(false);
		registerFrame.setVisible(true);
	}

	public void goToLoginn() {
		if (registerFrame != null && registerFrame.isVisible()) {
			registerFrame.setVisible(false);
		}
		if (mainFrame != null && mainFrame.isVisible()) {
			mainFrame.setVisible(false);
		}
		loginFrame = new LogInPanel();
		loginFrame.setVisible(true);
	}

	public void registerUser (String username, String name, String password) throws SQLException {
		if (db.userExists(username)) {
			JOptionPane.showMessageDialog(registerFrame, "Brukernavn opptatt");
		}
		else {
			db.createUser(username, name, password);
			JOptionPane.showMessageDialog(registerFrame, "Bruker opprettet");
			goToLoginn();
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
