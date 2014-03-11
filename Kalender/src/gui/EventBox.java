package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public EventBox(String s, int appId) {
		super(s);
		this.appId = appId;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CalendarModel.getListModel().removeAllElements();
		ResultSet rs;
		try {
			rs = db.getAppointmentInfo(appId);
			while (rs.next()) {
				CalendarModel.getListModel().addElement("Start: " + rs.getString("Starttid"));
				CalendarModel.getListModel().addElement("Slutt: " + rs.getString("Sluttid"));
				CalendarModel.getListModel().addElement("Beskrivelse: " + rs.getString("Beskrivelse"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}