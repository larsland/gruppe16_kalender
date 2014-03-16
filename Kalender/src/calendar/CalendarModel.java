package calendar;

import gui.EventBox;
import gui.MainPanel;

import java.awt.Color;
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
	private ArrayList<String> otherPersons = new ArrayList<String>();

	public static DefaultListModel getListModel() {
		return listModel;
	}
	public static DefaultListModel getParticipantsModel() {
		return participantsModel;
	}

	public static JPanel getAppointment() {
		return appointment;
	}

	public static String getUsername() {
		return username;
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
		insertIntoCalendar(rs,username, Color.green);
		ResultSet rs2 = db.getInvitedAppointments(username, _monday, _sunday);
		insertIntoCalendar(rs2,username, Color.green);
		Color[] personColors = {Color.red, Color.blue, Color.cyan, Color.magenta, Color.yellow, Color.pink, Color.orange};
		if (otherPersons.size() > 0) {
		for (int i = 0; i < otherPersons.size(); i++) {
			if(otherPersons.get(i).equals(this.getUsername())){continue;}
			try {
				addOtherPersonsAppointments(otherPersons.get(i), personColors[i]);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		}
	}

	public void insertIntoCalendar(ResultSet rs, String username, Color color) throws SQLException{
		while (rs.next()) {
			int day = rs.getTimestamp("Starttid").getDay();
			int hour = rs.getTimestamp("Starttid").getHours();
			insertEvent(rs.getInt("AvtaleID"), hour, day, username, color);
		}
	}

	public void insertEvent(Object value, int time, int day, String username, Color color) throws SQLException{
		time = time - 7;
		if (value instanceof Integer) {
			((JPanel) this.getValueAt(time, day)).add(new EventBox("",(Integer) value, username, color));
		}
		else{
			this.setValueAt(new JPanel(), time, day);
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
	 *
	 */
	public void addOtherPersonsAppointments(String u, Color color) throws SQLException {

			//Sett inn andres avtaler
			ResultSet rs = db.getCreatedAppointments(u, _monday, _sunday);
			insertIntoCalendar(rs,u, color);
			ResultSet rs2 = db.getInvitedAppointments(u, _monday, _sunday);
			insertIntoCalendar(rs2,u, color);

		//Oppdater kalenderen
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

	public void setOtherPersons(ArrayList<String> selectedPersons) {
		this.otherPersons = selectedPersons;
	}



}
