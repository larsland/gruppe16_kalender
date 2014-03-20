package calendar;

import gui.AddEvent;
import gui.LogInPanel;
import gui.MainPanel;
import gui.RegisterPane;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class App implements ActionListener {

	private Database db = new Database();
	public static String username = null;
	private static JFrame loginFrame;
	private static JFrame registerFrame;
	private static JFrame mainFrame;
	private static JFrame newAppointmentFrame;
	
	public static String getUsername() {
		return username;
	}


	public static void main(String[] args) throws SQLException{
		loginFrame = new LogInPanel();
		loginFrame.setVisible(true);
	}

	public void validateUser(String username, String password) throws SQLException, LineUnavailableException, UnsupportedAudioFileException, IOException{
		if (db.checkPass(username, password)) {
			this.username = username;
			loginFrame.setVisible(false);
			loginFrame.remove(loginFrame);
			
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					try {
						mainFrame = new MainPanel(getUsername());
						mainFrame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

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
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					newAppointmentFrame = new AddEvent(getUsername());
					newAppointmentFrame.setVisible(true);
					newAppointmentFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}


	public void createApp() {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					newAppointmentFrame = new AddEvent(getUsername());
					newAppointmentFrame.setVisible(true);
					newAppointmentFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}


}
