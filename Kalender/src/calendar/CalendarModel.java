package calendar;

import gui.EventBox;
import gui.MainPanel;

import java.io.SerializablePermission;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class CalendarModel extends DefaultTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String username = null;
	private static Database db = new Database();
	private LocalDate now = new LocalDate();
	private LocalDate monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
	private LocalDate sunday = now.withDayOfWeek(DateTimeConstants.SUNDAY);
	private static DefaultListModel listModel = new EventModel();
	private static DefaultListModel participantsModel = new DefaultListModel();
	private static JPanel appointment = new JPanel();
	private Timestamp _monday = new Timestamp(monday.getYear() - 1900, monday.getMonthOfYear() - 1, monday.getDayOfMonth(), 0, 0, 0, 0);
	private Timestamp _sunday = new Timestamp(sunday.getYear() - 1900, sunday.getMonthOfYear() - 1 , sunday.getDayOfMonth(), 23, 0, 0, 0);
	
	public static DefaultListModel getListModel() {
		return listModel;
	}
	public static DefaultListModel getParticipantsModel() {
		return participantsModel;
	}
	
	public static JPanel getAppointment() {
		return appointment;
	}
	
	
	public String getDateString(int day){
		if (day == 1) {
			return _monday.getDate() + "/" + (_monday.getMonth() + 1);
		}
		Timestamp tmpDate = new Timestamp(_monday.getYear(), _monday.getMonth(), _monday.getDate(), 0, 0, 0, 0);
		tmpDate.setDate(tmpDate.getDate() + (day - 1));
		return (tmpDate.getDate()) + "/" + (tmpDate.getMonth() + 1);
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
		insertIntoCalendar(rs,false);
		ResultSet rs2 = db.getInvitedAppointments(username, _monday, _sunday);
		insertIntoCalendar(rs2,false);
	}
	
	public void insertIntoCalendar(ResultSet rs, boolean otherPerson) throws SQLException{
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
		if (value instanceof Integer) {
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
		else{
			this.setValueAt(new JPanel(), time, 3);

		}
	}public void setThursday(Object value, int time) throws SQLException{
		time = time - 7;
		if (!(value instanceof JPanel)) {
			((JPanel) this.getValueAt(time, 4)).add(new EventBox("",(Integer) value, username));
		}
		else{
			this.setValueAt(new JPanel(), time, 4);

		}
	}public void setFriday(Object value, int time) throws SQLException{
		time = time - 7;
		if (!(value instanceof JPanel)) {
			((JPanel) this.getValueAt(time, 5)).add(new EventBox("",(Integer) value, username));
		}else{
			this.setValueAt(new JPanel(), time, 4);

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
		
		// Fjern alle objekter i JPanel Cell
		for (int i = 1; i < getRowCount(); i++) {
			for (int j = 1; j < getColumnCount();j++) {
				((JPanel) this.getValueAt(i, j)).removeAll();
			}
		}
		
		// Oppdater tabell
		this.fireTableDataChanged();
	}
	
	/*
	 * TODO: Finne ut om bruker selv deltar pŒ avtalen
	 * TODO: Sette farge
	 * TODO: Beholde avtalen i kalenderen nŒr man blar mellom uker
	 */
	public void addOtherPersonsAppointments(User u) throws SQLException {
		ResultSet rs = db.getCreatedAppointments(u.getUsername(), _monday, _sunday);
		insertIntoCalendar(rs,true);
		ResultSet rs2 = db.getInvitedAppointments(u.getUsername(), _monday, _sunday);
		insertIntoCalendar(rs2,true);
		this.fireTableDataChanged();
		
	}
	
	public static void fillSidePanel(int appId){
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
				
				if (rs.getString("Opprettet_av").equals(username)) {
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
	
	@Override
	public boolean isCellEditable(int row, int coloumn){
		return true;
	}
	


}
