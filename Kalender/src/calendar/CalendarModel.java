package calendar;

import gui.EventBox;
import gui.MainPanel;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class CalendarModel extends DefaultTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username = null;
	private Database db = new Database();
	private LocalDate now = new LocalDate();
	private LocalDate monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
	private LocalDate sunday = now.withDayOfWeek(DateTimeConstants.SUNDAY);
	private static DefaultListModel listModel = new EventModel();
	private static DefaultListModel participantsModel = new DefaultListModel();
	Timestamp _monday = new Timestamp(monday.getYear() - 1900, monday.getMonthOfYear() - 1, monday.getDayOfMonth(), 0, 0, 0, 0);
	Timestamp _sunday = new Timestamp(sunday.getYear() - 1900, sunday.getMonthOfYear() - 1 , sunday.getDayOfMonth(), 23, 0, 0, 0);
	
	public static DefaultListModel getListModel() {
		return listModel;
	}
	public static DefaultListModel getParticipantsModel() {
		return participantsModel;
	}

	public CalendarModel(String username) throws SQLException {
		super(new Object[][] {
				{"07:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"08:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"09:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"10:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"11:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"12:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"13:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"14:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"15:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"16:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"17:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"18:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"19:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"20:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"21:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"22:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"23:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
				{"00:00", new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()},
		},
			new String[] {
				"", "Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "L\u00F8rdag", "S\u00F8ndag"
			});
		this.username = username;
		this.setThisWeeksAppointments(0);
	}
	
	
	public void setThisWeeksAppointments(int weekNumber) throws SQLException{
		clear();
		if (weekNumber > 0) {
			_monday.setDate(_monday.getDate() + 7);
			_sunday.setDate(_sunday.getDate() + 7);
		} else if (weekNumber < 0) {
			_monday.setDate(_monday.getDate() - 7);
			_sunday.setDate(_sunday.getDate() - 7);
		}
		ResultSet rs = db.getCreatedAppointments(username, _monday, _sunday);
		insertIntoCalendar(rs);
		ResultSet rs2 = db.getInvitedAppointments(username, _monday, _sunday);
		insertIntoCalendar(rs2);
	}
	
	public void insertIntoCalendar(ResultSet rs) throws SQLException{
		while (rs.next()) {
			int day = rs.getTimestamp("Starttid").getDay();
			int hour = rs.getTimestamp("Starttid").getHours();
			switch (day) {
			case 1:
				setMonday(rs.getInt("AvtaleID"), hour);
				break;
			case 2:
				setTuesday(rs.getInt("AvtaleID"), hour);
				break;
			case 3:
				setWednesday(rs.getInt("AvtaleID"), hour);
				break;
			case 4:
				setThursday(rs.getInt("AvtaleID"), hour);
				break;
			case 5:
				setFriday(rs.getInt("AvtaleID"), hour);
				break;
			case 6:
				setSaturday(rs.getInt("AvtaleID"), hour);
				break;
			case 7:
				setSunday(rs.getInt("AvtaleID"), hour);
				break;
			default:
				break;
			}
		}
	}
	
	public void setMonday(Object value, int time) throws SQLException{
		time = time - 7;
		GridBagConstraints c = new GridBagConstraints();
		if (!(value instanceof JPanel)) {
			((JPanel) this.getValueAt(time, 1)).add(new EventBox("",(Integer) value, username));
		}
		else{
			this.setValueAt(new JPanel(), time, 1);
		}
	}public void setTuesday(Object value, int time) throws SQLException{
		time = time - 7;
		if (!(value instanceof JPanel)) {
			((JPanel) this.getValueAt(time, 2)).add(new EventBox("",(Integer) value, username));
		}
		else{
			this.setValueAt(new JPanel(), time, 2);

		}
	}public void setWednesday(Object value, int time) throws SQLException{
		time = time - 7;
		if (!(value instanceof JPanel)) {
			((JPanel) this.getValueAt(time, 3)).add(new EventBox("",(Integer) value, username));
		}
	}public void setThursday(Object value, int time) throws SQLException{
		time = time - 7;
		if (!(value instanceof JPanel)) {
			((JPanel) this.getValueAt(time, 4)).add(new EventBox("",(Integer) value, username));
		}
	}public void setFriday(Object value, int time) throws SQLException{
		time = time - 7;
		if (!(value instanceof JPanel)) {
			((JPanel) this.getValueAt(time, 5)).add(new EventBox("",(Integer) value, username));
		}
	}public void setSaturday(Object value, int time) throws SQLException{
		time = time - 7;
		if (!(value instanceof JPanel)) {
			((JPanel) this.getValueAt(time, 6)).add(new EventBox("",(Integer) value, username));
		}
	}public void setSunday(Object value, int time) throws SQLException{
		time = time - 7;
		if (!(value instanceof JPanel)) {
			((JPanel) this.getValueAt(time, 7)).add(new EventBox("",(Integer) value, username));
		}
	}
	public void clear() throws SQLException{
		listModel.removeAllElements();
		for (int i = 7; i <= 24; i++) {
			setMonday(new JPanel(), i);
			setTuesday(new JPanel(), i);
			setWednesday(new JPanel(), i);
			setThursday(new JPanel(),i);
			setFriday(new JPanel(), i);
			setSaturday(new JPanel(), i);
			setSunday(new JPanel(), i);
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int coloumn){
		return true;
	}

}
