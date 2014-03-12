package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import calendar.CalendarModel;
import calendar.App;
import calendar.Database;

public class EventBox extends JButton implements ActionListener {
	private int appId;
	private Database db = new Database();
	private String username = null;
	private ImageIcon acceptedIcon = new ImageIcon("img/success-icon.png");
	private ImageIcon pendingIcon = new ImageIcon("img/alert-icon.png");
	private ImageIcon rejectedIcon = new ImageIcon("img/close-icon.png");
	private Color color = Color.GREEN;
	
	public EventBox(String s, int appId, String username) throws SQLException {
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
		CalendarModel.getListModel().removeAllElements();
		CalendarModel.getParticipantsModel().removeAllElements();
		ResultSet rs;
		ResultSet rs2;
		try {
			rs = db.getAppointmentInfo(appId);
			rs2 = db.getParticipantsInAppointment(appId);
			while (rs.next()) {
				CalendarModel.getListModel().addElement("Start: " + rs.getString("Starttid"));
				CalendarModel.getListModel().addElement("Slutt: " + rs.getString("Sluttid"));
				CalendarModel.getListModel().addElement("Beskrivelse: " + rs.getString("Beskrivelse"));
				
				if (rs.getString("Opprettet_av").equals(this.username)) {
				}


				ArrayList<Object> label = new ArrayList<Object>();
				label.add(rs.getString("Opprettet_av"));
				label.add(1);
				CalendarModel.getParticipantsModel().addElement(label);
			}
			while (rs2.next()) {
				ArrayList<Object> label = new ArrayList<Object>();
				label.add(rs2.getString("brukernavn"));
				label.add(rs2.getInt("Godkjenning"));
				
				CalendarModel.getParticipantsModel().addElement(label);
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

}