package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.Border;
import calendar.CalendarModel;
import calendar.Database;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AddAlarm extends JPanel {
	private JLabel lblInfo;
	private JButton btnAddAlarm;
	private JSpinner alarmTimer;
	private int minutes, appId;
	
	/**
	 * Initiating CalendarModel, and creating an instance of Database, to use some of it's methods.
	 */
	private Database db = new Database();
	private CalendarModel cm;
	
	/**
	 * Constructor for setting up the GUI for the the Alarm panel
	 * @throws SQLException
	 */
	public AddAlarm() throws SQLException {
		setLayout(null);
		
		lblInfo = new JLabel("Still inn alarm: ");
		lblInfo.setBounds(17, 11, 106, 15);
		add(lblInfo);
		
		//Button for setting the alarm
		btnAddAlarm = new JButton("Ok");
		btnAddAlarm.setFont(new Font("Dialog", Font.BOLD, 9));
		btnAddAlarm.setBounds(174, 5, 50, 30);
		btnAddAlarm.setPreferredSize(new Dimension(100, 30));
		btnAddAlarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				getTime();
			}		
		});
		add(btnAddAlarm);
		
		//JSpinner to set the time in minutes before an appointment
		alarmTimer = new JSpinner();
		alarmTimer.setBounds(119, 5, 50, 30);
		alarmTimer.setPreferredSize(new Dimension(30, 30));
		add(alarmTimer);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		this.setBorder(border);
		this.setVisible(true);
	}
	
	/**
	 * Method called when clicking "btnAddAlarm"
	 * Sends a notification to the user according to the value of the "alarmTImer"
	 */
	public void getTime() {
		
		//Getting the ID for the selected appointment in the calendar view
		appId = cm.getSelectedAppId();
		minutes = (Integer) alarmTimer.getValue();
		Timestamp startTime = null;
		String user = null;
		try {
			//Gets all the info of the selected appointment
			ResultSet rs = db.getAppointmentInfo(appId);
			if (rs.next()) {
				startTime = rs.getTimestamp("Starttid");
				user = rs.getString("Opprettet_av");
			}
			
			@SuppressWarnings("deprecation")
			//Makes a Timestamp with the time of the selected appointment minus the value of "alarmTimer"
			Timestamp ts = new Timestamp(startTime.getYear(), startTime.getMonth(), 
										 startTime.getDate(), startTime.getHours(), 
										 startTime.getMinutes() - minutes, startTime.getSeconds(), 
										 startTime.getNanos());
			
			db.sendAlarmToOne(appId, user, "Møte om" + minutes + "minutter!", ts);
			
			
		} catch (SQLException e) {}
		
	}
	
}