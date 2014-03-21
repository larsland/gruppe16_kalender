package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import calendar.CalendarModel;
import calendar.Database;

public class EventBox extends JButton implements ActionListener {
	private int appId;
	private Database db = new Database();
	private String username = null;
	final private ImageIcon acceptedIcon = new ImageIcon(getClass().getResource("/img/tick.png"));
	final private ImageIcon pendingIcon = new ImageIcon(getClass().getResource("/img/spam-2.png"));
	final private ImageIcon rejectedIcon = new ImageIcon(getClass().getResource("/img/delete.png"));
	
	public EventBox(String s, int appId, String username, Color color) throws SQLException {
		super(s);
		this.appId = appId;
		this.username = username;
		
		ResultSet rs = db.getAppointmentInfo(appId);
		ResultSet rs2 = db.getStatusForAppointment(username, appId);
		while (rs.next()) {
			String creator = rs.getString("Opprettet_av");
			if (creator.equals(this.username)) {
				setIcon(acceptedIcon);
			}
		}
		if (rs2.next()) {
			int status = rs2.getInt("Godkjenning");
			if (status == 0) {
				setIcon(pendingIcon);
			}
			else if (status == -1) {
				setIcon(rejectedIcon);
			}
			else{
				setIcon(acceptedIcon);
			}
		}
		
		
		setOpaque(true);
		addActionListener(this);
		setBorderPainted(false);
		setPreferredSize(new Dimension(25,25));
		setBackground(color);
	}
	
	/*
	 *  Trykk på avtale
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int appId = ((EventBox) e.getSource()).appId;
		CalendarModel.fillSidePanel(appId);
		MainPanel.getAddAlarm().setVisible(true);
	}

}