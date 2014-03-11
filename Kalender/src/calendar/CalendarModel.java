package calendar;

import gui.EventBox;

import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

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
	Timestamp _monday = new Timestamp(monday.getYear() - 1900, monday.getMonthOfYear() - 1, monday.getDayOfMonth(), 0, 0, 0, 0);
	Timestamp _sunday = new Timestamp(sunday.getYear() - 1900, sunday.getMonthOfYear() - 1 , sunday.getDayOfMonth(), 23, 0, 0, 0);

	public CalendarModel(String username) throws SQLException {
		super(new Object[][] {
				{"07:00", null, null, null, null, null, null, null},
				{"08:00", null, null, null, null, null, null, null},
				{"09:00", null, null, null, null, null, null, null},
				{"10:00", null, null, null, null, null, null, null},
				{"11:00", null, null, null, null, null, null, null},
				{"12:00", null, null, null, null, null, null, null},
				{"13:00", null, null, null, null, null, null, null},
				{"14:00", null, null, null, null, null, null, null},
				{"15:00", null, null, null, null, null, null, null},
				{"16:00", null, null, null, null, null, null, null},
				{"17:00", null, null, null, null, null, null, null},
				{"18:00", null, null, null, null, null, null, null},
				{"19:00", null, null, null, null, null, null, null},
				{"20:00", null, null, null, null, null, null, null},
				{"21:00", null, null, null, null, null, null, null},
				{"22:00", null, null, null, null, null, null, null},
				{"23:00", null, null, null, null, null, null, null},
				{"00:00", null, null, null, null, null, null, null}
			},
			new String[] {
				"", "Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "L\u00F8rdag", "S\u00F8ndag"
			});
		this.username = username;
		clear();
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
		Object current = this.getValueAt(time, 1);
		if (current != null) {
			this.setValueAt(current +"-"+ value, time, 1);
		}
		else{
			this.setValueAt(value, time, 1);
		}
	}public void setTuesday(Object value, int time){
		time = time - 7;
		this.setValueAt(value, time, 2);
	}public void setWednesday(Object value, int time){
		time = time - 7;
		this.setValueAt(value, time, 3);
	}public void setThursday(Object value, int time){
		time = time - 7;
		this.setValueAt(value, time, 4);
	}public void setFriday(Object value, int time){
		time = time - 7;
		this.setValueAt(value, time, 5);
	}public void setSaturday(Object value, int time){
		time = time - 7;
		this.setValueAt(value, time, 6);
	}public void setSunday(Object value, int time){
		time = time - 7;
		this.setValueAt(value, time, 7);
	}
	public void clear() throws SQLException{
		for (int i = 7; i <= 24; i++) {
			setMonday(null, i);
			setTuesday(null, i);
			setWednesday(null, i);
			setThursday(null,i);
			setFriday(null, i);
			setSaturday(null, i);
			setSunday(null, i);
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int coloumn){
		return false;
	}



}
