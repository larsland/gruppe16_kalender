package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import calendar.CalendarModel;
import calendar.App;
import calendar.Database;

public class EventBox extends JButton implements ActionListener {
	private int appId;
	private Database db = new Database();
	private String username = null;
	private ImageIcon acceptedIcon = new ImageIcon("src/img/success-icon.png");
	private ImageIcon pendingIcon = new ImageIcon("src/img/alert-icon.png");
	private ImageIcon rejectedIcon = new ImageIcon("src/img/close-icon.png");
	
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
	}

}