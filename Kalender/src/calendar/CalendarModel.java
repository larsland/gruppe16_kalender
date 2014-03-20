package calendar;

import gui.EventBox;
import gui.MainPanel;

import java.awt.Color;
import java.io.SerializablePermission;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class CalendarModel extends DefaultTableModel {
	/**
	 * 
	 * 
	 * 
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
	private static int selectedAppId = 0;
	private Timestamp _monday = new Timestamp(monday.getYear() - 1900, monday.getMonthOfYear() - 1, monday.getDayOfMonth(), 0, 0, 0, 0);
	private Timestamp _sunday = new Timestamp(sunday.getYear() - 1900, sunday.getMonthOfYear() - 1 , sunday.getDayOfMonth(), 23, 0, 0, 0);
	private ArrayList<User> otherPersons = new ArrayList<User>();
	private static String selectedDate;
	private static int selectedY;

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
		

		// Fjern alle objekter i JPanel Cell
		for (int i = 0; i < getRowCount(); i++) {
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
				CalendarModel.getListModel().addElement("Avtalen starter kl: " + rs.getString("Starttid").substring(11, 16));
				CalendarModel.getListModel().addElement("Avtalen slutter kl: " + rs.getString("Sluttid").substring(11, 16));
				CalendarModel.getListModel().addElement("Dato: " + dato[2].substring(0, 2) + "/" + dato[1]);
				CalendarModel.getListModel().addElement("Sted: " + sted);
				CalendarModel.getListModel().addElement(" ");
				CalendarModel.getListModel().addElement("Beskrivelse:");
				CalendarModel.getListModel().addElement(rs.getString("Beskrivelse"));
				if (rs.getString("Opprettet_av").equals(username)) {
					CalendarModel.getListModel().addElement("1");
					MainPanel.setCreatorButtons(appId);
					CalendarModel.getListModel().addElement("1");
					
					//set endre button and if slett.click, delete avtale
				}
				else {
					CalendarModel.getListModel().addElement("0");
				}

				ArrayList<Object> label = new ArrayList<Object>();
				label.add(rs.getString("Opprettet_av"));
				label.add(1);
				CalendarModel.getParticipantsModel().addElement(label);
			}
			while (rs2.next()) {
				ArrayList<Object> label = new ArrayList<Object>();
				String tempUsername = rs2.getString("brukernavn");
				int tempStatus = rs2.getInt("Godkjenning");		
				label.add(tempUsername);
				label.add(tempStatus);
				if (tempUsername.equals(username)) {
					CalendarModel.getListModel().addElement(tempStatus + "");
					MainPanel.setStatusChangeButtons(appId, tempStatus + "");
				}
				CalendarModel.getParticipantsModel().addElement(label);

			}
			if (CalendarModel.getListModel().size() < 8) {
				CalendarModel.getListModel().addElement(" ");
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
