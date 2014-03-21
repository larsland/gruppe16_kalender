package calendar;

import gui.EventBox;
import gui.MainPanel;
import java.awt.Color;
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
	
	private static String username = null;
	private static Database db = new Database();
	
	// Get the first day of this week
	private LocalDate now = new LocalDate();
	private LocalDate monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
	private LocalDate sunday = now.withDayOfWeek(DateTimeConstants.SUNDAY);

	private static DefaultListModel listModel = new EventModel();
	private static DefaultListModel participantsModel = new DefaultListModel();
	private static JPanel appointment = new JPanel();
	private static int selectedAppId = 0;
	
	// Create timestamp of first day of this week
	private Timestamp _monday = new Timestamp(monday.getYear() - 1900, monday.getMonthOfYear() - 1, monday.getDayOfMonth(), 0, 0, 0, 0);
	private Timestamp _sunday = new Timestamp(sunday.getYear() - 1900, sunday.getMonthOfYear() - 1 , sunday.getDayOfMonth(), 23, 0, 0, 0);

	private ArrayList<User> otherPersons = new ArrayList<User>();
	private static String selectedDate;
	private static int selectedY;

	public CalendarModel(String username) throws SQLException {

		//Initialize the JTable
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
	
	public Timestamp get_monday() {
		return _monday;
	}

	/**
	 * Returns a formatted string of a day 
	 */
	public String getDateString(int day){
		if (day == 1) {
			return _monday.getDate() + "/" + (_monday.getMonth() + 1);
		}
		Timestamp tmpDate = new Timestamp(_monday.getYear(), _monday.getMonth(), _monday.getDate(), 0, 0, 0, 0);
		tmpDate.setDate(tmpDate.getDate() + (day - 1));
		return (tmpDate.getDate()) + "/" + (tmpDate.getMonth() + 1);
	}
	
	/**
	 * Retrieves all the appointments for a given week and inserts them into the calendar.
	 */
	public void setThisWeeksAppointments(int weekNumber) throws SQLException{
		clear();

		if (weekNumber > 0) {
			_monday.setDate(_monday.getDate() + 7);
			_sunday.setDate(_sunday.getDate() + 7);

		} else if (weekNumber < 0) {
			_monday.setDate(_monday.getDate() - 7);
			_sunday.setDate(_sunday.getDate() - 7);
		}
		
		// Retrieve appointments
		ResultSet rs = db.getCreatedAppointments(username, _monday, _sunday);
		insertIntoCalendar(rs,username, Color.green);
		ResultSet rs2 = db.getInvitedAppointments(username, _monday, _sunday);
		insertIntoCalendar(rs2,username, Color.green);

		Color[] personColors = {Color.red, Color.blue, Color.cyan, Color.magenta, Color.yellow, Color.pink, Color.orange};

		// Add appointments for other users
		if (otherPersons.size() > 0) {
			for (int i = 0; i < otherPersons.size(); i++) {
				
				// Do not add appointments for logged in user
				if(otherPersons.get(i).equals(this.getUsername())){continue;}

				try {
					addOtherPersonsAppointments(otherPersons.get(i).getUsername(), personColors[i]);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		this.fireTableDataChanged();
		
	}

	public void insertIntoCalendar(ResultSet rs, String username, Color color) throws SQLException{
		while (rs.next()) {
			int day = rs.getTimestamp("Starttid").getDay();
			if (day == 0) {
				day = 7;
			}
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
		
		// Removes all the elements in the calendar table
		for (int i = 0; i < getRowCount(); i++) {
			for (int j = 1; j < getColumnCount();j++) {
				((JPanel) this.getValueAt(i, j)).removeAll();
			}
		}

		this.fireTableDataChanged();
	}

	
	public void addOtherPersonsAppointments(String u, Color color) throws SQLException {

		// Insert other persons appointments
		ResultSet rs = db.getCreatedAppointments(u, _monday, _sunday);
		insertIntoCalendar(rs,u, color);
		ResultSet rs2 = db.getInvitedAppointments(u, _monday, _sunday);
		insertIntoCalendar(rs2,u, color);

		// Update table
		this.fireTableDataChanged();

	}
	
	/**
	 * Fill information about the appointment in the side panel. 
	 */
	public static void fillSidePanel(int appId){
		getListModel().removeAllElements();
		getParticipantsModel().removeAllElements();
		ResultSet rs;
		ResultSet rs2;
		selectedAppId = appId;
		try {
			rs = db.getAppointmentInfo(appId);
			rs2 = db.getParticipantsInAppointment(appId);
			if (rs.next()) {
				String[] dato = rs.getTimestamp("Dato").toString().split("-");
				String sted = rs.getString("avtale_sted");
				if (sted == null) {
					sted = rs.getString("Sted");
				}
				MainPanel.clearButtons();
				
				// Add elements to side panel's model
				getListModel().addElement("Avtalen starter kl: " + rs.getString("Starttid").substring(11, 16));
				getListModel().addElement("Avtalen slutter kl: " + rs.getString("Sluttid").substring(11, 16));
				getListModel().addElement("Dato: " + dato[2].substring(0, 2) + "/" + dato[1]);
				getListModel().addElement("Sted: " + sted);
				getListModel().addElement(" ");
				getListModel().addElement("Beskrivelse:");
				getListModel().addElement(rs.getString("Beskrivelse"));

				if (rs.getString("Opprettet_av").equals(username)) {
					getListModel().addElement("1");
					MainPanel.setCreatorButtons(appId);
					getListModel().addElement("1");
				}
				else {
					getListModel().addElement("0");
				}

				ArrayList<Object> label = new ArrayList<Object>();
				label.add(rs.getString("Opprettet_av"));
				label.add(1);
				getParticipantsModel().addElement(label);
			}
			while (rs2.next()) {
				ArrayList<Object> label = new ArrayList<Object>();
				String tempUsername = rs2.getString("brukernavn");
				int tempStatus = rs2.getInt("Godkjenning");		
				label.add(tempUsername);
				label.add(tempStatus);
				if (tempUsername.equals(username)) {
					getListModel().addElement(tempStatus + "");
					MainPanel.setStatusChangeButtons(appId, tempStatus + "");
				}
				getParticipantsModel().addElement(label);

			}
			if (getListModel().size() < 8) {
				getListModel().addElement(" ");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void fillSidePanelFromCell(int y, String date) {
		selectedAppId = -1;
		selectedY = y;
		selectedDate = date;
		
		int start = (y + 7);
		if (start < 9) {
			CalendarModel.getListModel().addElement("Kl: 0" + start + ":00");
			CalendarModel.getListModel().addElement("Kl: 0" + (start + 1) + ":00");
		}
		else if (start < 10) {
			CalendarModel.getListModel().addElement("Kl: 09:00");
			CalendarModel.getListModel().addElement("Kl: 10:00");
		}
		else if (start == 24) {
			CalendarModel.getListModel().addElement("Kl: 24:00");
			CalendarModel.getListModel().addElement("Kl: 01:00");
		}
		else {
			CalendarModel.getListModel().addElement("Kl: " + start + ":00");
			CalendarModel.getListModel().addElement("Kl: " + (start + 1) + ":00");
		}
		CalendarModel.getListModel().addElement("Dato: " + date);
	}
	
	public static int getSelectedY() {
		return selectedY;
	}
	
	public static String getSelectedDate() {
		return selectedDate;
	}


	public void setStatus(int appID, int status) {
		try {
			db.setStatusForAppointment(username, appID, status);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAttendence(int appId) {
		try {
			db.removeAttendence(username, appId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAppointment(int appId) {
		try {
			db.deleteAppointment(appId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isCellEditable(int row, int coloumn){
		return true;
	}

	public void setOtherPersons(ArrayList<User> selectedPersons) {
		this.otherPersons = selectedPersons;
	}

	public static int getSelectedAppId() {
		return selectedAppId;
	}


}
