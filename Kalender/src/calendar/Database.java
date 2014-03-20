package calendar;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.PreparedStatement;

import org.joda.time.LocalDate;

public class Database {
	/**
	 *
	 *
	 *
	 */
	private String url = "jdbc:mysql://mysql.stud.ntnu.no/andekol_g16_db";
	private String username = "andekol_g16";
	private String pwd = "gruppe16";

	private Connection con = null;
	private Statement stmt;
	private ResultSet rs;

	public Database() {
		try {
			con = DriverManager.getConnection(url,username,pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createUser(String username, String name, String password) throws SQLException {
		stmt = con.createStatement();
		if (!userExists(username)) {
			String query = "INSERT INTO Person (Brukernavn, Navn, passtoken) VALUES ('"
				+ username + "', '" + name + "', '" + BCrypt.hashpw(password, BCrypt.gensalt()) + "');";
			stmt.executeUpdate(query);
		}
	}


	public boolean userExists(String username) throws SQLException{
		stmt = con.createStatement();
		rs = stmt.executeQuery("SELECT Brukernavn FROM Person " +
			"WHERE Brukernavn = '"+username +"' LIMIT 1;");

		if (rs.next()) {
			return true;
		}
		return false;
	}


	/*
	 * Verify login
	 */
	public boolean checkPass(String username, String password) throws SQLException {
		if (userExists(username)) {
			return BCrypt.checkpw(password, getPassword(username));
		}
		return false;
	}

	public String getPassword(String username) throws SQLException{
		stmt = con.createStatement();
		rs = stmt.executeQuery("SELECT passtoken FROM Person WHERE Brukernavn='"+username+"';");
		if (rs.next()) {
			return rs.getString("passtoken");
		}
		return null;
	}

	public void setStatusForAppointment(String username, int appID, int status) throws SQLException{
		stmt = con.createStatement();
		String query = "UPDATE Deltar_på SET Godkjenning = " + status + " WHERE brukernavn = '"+username+"' and AvtaleID = "+appID+";";
		stmt.executeUpdate(query);
	}

	public void removeAttendence(String username, int appID) throws SQLException {
		stmt = con.createStatement();
		String query = "DELETE FROM Deltar_på WHERE brukernavn = '"+username+"' and AvtaleID = "+appID+";";
		stmt.executeUpdate(query);
	}

	/*
	 * Create appointment
	 * TODO: M¬teleder, m¬terom/sted
	 */
	public void createAppointment(Date date, Timestamp starttime, Timestamp endtime, String desc, String creator, ArrayList<String> participants, int romId, String sted) throws SQLException{
		stmt = con.createStatement();
		String query = "INSERT INTO Avtale (Dato, Starttid, Sluttid, Beskrivelse, Opprettet_av, Sted) VALUES "
				+ "(?,?,?,?,?,?)";
		PreparedStatement st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		st.setDate(1, date);
		st.setTimestamp(2, starttime);
		st.setTimestamp(3, endtime);
		st.setString(4, desc);
		st.setString(5, creator);
		st.setString(6, sted);
		st.executeUpdate();

		// Lagre siste id
	    ResultSet keys = st.getGeneratedKeys();
	    keys.next();
	    int key = keys.getInt(1);

	    //Legg til deltakere
	    String query2 = "INSERT INTO Deltar_på VALUES (?,?,?);";
	    st = con.prepareStatement(query2);
	    for (String user : participants) {
	      st.setInt(1, key);
	      st.setString(2, user);
	      st.setInt(3, 0);
	      st.executeUpdate();
	    }

	    //Legg til Møterom
	    	stmt = con.createStatement();
	    	String query3 = "INSERT INTO Avtalested VALUES ("+key+", "+romId+");";
	    	stmt.execute(query3);
	    

	    //Send varsel
	   String query4 = "INSERT INTO Varsel VALUES (?,?,?,CURRENT_TIMESTAMP);";
	   st = con.prepareStatement(query4);
	   for (String user : participants) {
		   st.setInt(1, key);
		   st.setString(2, user);
		   st.setString(3, "Du har blitt invitert til en avtale av " + creator);
		   st.executeUpdate();
	   }


	}

	public ResultSet getAppointmentInfo(int appointmentId) throws SQLException{
		stmt = con.createStatement();
		String query = "SELECT Avtale.AvtaleID, Dato, Starttid, Sluttid, Beskrivelse," +
			"Opprettet_av, Avtale.Sted AS avtale_sted, Møterom.Sted FROM Avtale inner join Avtalested on " +
			"Avtale.AvtaleID = Avtalested.AvtaleID inner join Møterom on " +
			"Avtalested.RomID = Møterom.RomID WHERE Avtale.AvtaleID = " + appointmentId +
			";";
		rs = stmt.executeQuery(query);
		return rs;

	}

	/*
	 * Endre avtale
	 */
	public void updateAppointment(int id, Date date, Timestamp starttime, Timestamp endtime, String desc, String creator, ArrayList<String> participants, ArrayList<String> deletedPersons ,int romId, String otherPlace) throws SQLException{
		String query1 = "UPDATE Avtale SET Dato = ?, Starttid = ?, Sluttid = ?, Beskrivelse = ?, Opprettet_av = ?, Sted = ? WHERE AvtaleID = ?;";
		PreparedStatement st = con.prepareStatement(query1);
		st.setDate(1, date);
		st.setTimestamp(2, starttime);
		st.setTimestamp(3, endtime);
		st.setString(4, desc);
		st.setString(5, creator);
		st.setString(6, otherPlace);
		st.setInt(7, id);
		st.executeUpdate();

		//Slett gamle
		for (String person : deletedPersons) {
			deleteInvitation(id, person);
		}

		//Inviter nye
		 String query2 = "INSERT INTO Deltar_på VALUES (?,?,?);";
		    st = con.prepareStatement(query2);
		    for (String user : participants) {
		      st.setInt(1, id);
		      st.setString(2, user);
		      st.setInt(3, 0);
		      st.executeUpdate();
		   }
		
		String query3 = "UPDATE Avtalested SET RomID = ? WHERE AvtaleID = ?;";
		st = con.prepareStatement(query3);
		st.setInt(1, romId);
		st.setInt(2, id);
		st.executeUpdate();

	}

	/*
	 * Slett avtale
	 */
	public void deleteAppointment(int id) throws SQLException{
		stmt = con.createStatement();
		String query = "DELETE FROM Avtale WHERE AvtaleID = "+id+";";
		stmt.execute(query);

	}

	public void answerInvitation(int id, String username, int accept) throws SQLException{
		stmt = con.createStatement();
		String query = "UPDATE Deltar_på SET Godkjenning = "+accept+" WHERE AvtaleID = "+id+" and Brukernavn = '"+username+"';";
		stmt.execute(query);
	}

	/*
	 * Skjul invitert avtale
	 */
	public void deleteInvitation(int id, String username) throws SQLException{
		stmt = con.createStatement();
		String query = "DELETE FROM Deltar_på WHERE AvtaleID = "+id+" AND Brukernavn = '"+username+"';";
		stmt.execute(query);
	}

	/*
	 *
	 */
	public ResultSet getParticipantsInAppointment(int appointmentId) throws SQLException{
		stmt = con.createStatement();
		String query = "select brukernavn, Godkjenning from Deltar_på inner join Avtale on Deltar_på.`AvtaleID` = Avtale.AvtaleID WHERE Deltar_på.AvtaleID = "+appointmentId+";";
		rs = stmt.executeQuery(query);
		return rs;
	}


	public ArrayList<User> getUserParticipants(int id) throws SQLException{
		ArrayList<User> p = new ArrayList<User>();
		stmt = con.createStatement();
		String query = "select Deltar_på.brukernavn,Navn, Godkjenning from Deltar_på inner join Avtale on Deltar_på.`AvtaleID` = Avtale.AvtaleID "
				+ " inner join Person on Deltar_på.brukernavn = Person.brukernavn WHERE Deltar_på.AvtaleID = "+id+";";
		rs = stmt.executeQuery(query);
		while (rs.next()) {
			p.add(new User(rs.getString("Navn"), rs.getString("brukernavn"), rs.getInt("Godkjenning")));
		}
		return p;
	}


	/*
	 * Avtaler som bruker er invitert til
	 */
	public ResultSet getInvitedAppointments(String username, Timestamp monday, Timestamp sunday) throws SQLException{
		stmt = con.createStatement();
		String query = "select * from Avtale inner join `Deltar_på` on `Deltar_på`.`AvtaleID` = `Avtale`.`AvtaleID` and brukernavn = '"+username+"' and (Starttid between '"+monday+"' and '"+sunday+"');";
		rs = stmt.executeQuery(query);
		return rs;
	}


	public ArrayList<EventModel> getEventModelAppointments(String username, Timestamp monday, Timestamp sunday) throws SQLException{
		stmt = con.createStatement();
		String query = "SELECT * FROM Avtale WHERE Opprettet_av = '"+username+"' AND (Starttid BETWEEN '"+monday+"' AND '"+sunday+"');"; //' AND Starttid >= "+monday+" AND Sluttid <= "+sunday+"
		rs = stmt.executeQuery(query);
		ArrayList<EventModel> events = new ArrayList<EventModel>();
		if (rs.next()) {
			ArrayList<User> participants = getUserParticipants(rs.getInt("AvtaleID"));
			Room room = getBookedRoom(rs.getInt("AvtaleID"));
			User creator = getCreator(rs.getInt("AvtaleID"));
			events.add(new EventModel(rs.getInt("AvtaleID"), rs.getString("Beskrivelse"), rs.getDate("Dato"), rs.getTimestamp("Starttid"), rs.getTimestamp("Sluttid"), participants, creator, room, ""));
		}
		return events;
	}

	public EventModel getEventModelForAppointment(int id) throws SQLException{
		stmt = con.createStatement();
		String query = "SELECT * FROM Avtale WHERE AvtaleID = "+id+";";
		rs = stmt.executeQuery(query);
		if (rs.next()) {
			int id2 = rs.getInt("AvtaleID");
			String desc = rs.getString("Beskrivelse");
			Date date = rs.getDate("Dato");
			Timestamp start = rs.getTimestamp("Starttid");
			Timestamp end = rs.getTimestamp("Sluttid");
			String sted = rs.getString("Sted");
			ArrayList<User> participants = getUserParticipants(id);
			Room room = getBookedRoom(id);
			User creator = getCreator(id);
			return new EventModel(id, desc, date, start, end, participants, creator, room, sted);
		}
		return null;
	}


	/*
	 * Avtaler som bruker har opprettet selv
	 */
	public ResultSet getCreatedAppointments(String username, Timestamp monday, Timestamp sunday) throws SQLException{
		stmt = con.createStatement();
		String query = "SELECT * FROM Avtale WHERE Opprettet_av = '"+username+"' AND (Starttid BETWEEN '"+monday+"' AND '"+sunday+"');"; //' AND Starttid >= "+monday+" AND Sluttid <= "+sunday+"
		rs = stmt.executeQuery(query);
		return rs;
	}

	public User getCreator(int id) throws SQLException{
		stmt = con.createStatement();
		String query="select Opprettet_av, navn FROM Avtale inner join Person on Avtale.Opprettet_av = Person.brukernavn WHERE AvtaleID = "+id+";";
		rs = stmt.executeQuery(query);
		if (rs.next()) {
			return new User(rs.getString("navn"), rs.getString("Opprettet_av"));
		}
		return null;

	}

	public ResultSet getStatusForAppointment(String username, int appID) throws SQLException{
		stmt = con.createStatement();
		String query = "select brukernavn, Godkjenning from Deltar_på WHERE brukernavn = '"+username+"' and AvtaleID = "+appID+";";
		rs = stmt.executeQuery(query);
		return rs;
	}

	public Room getBookedRoom(int id) throws SQLException{
		stmt = con.createStatement();
		String query = "select Møterom.RomID, sted FROM Møterom inner join Avtalested on Møterom.RomID = Avtalested.RomID where AvtaleID = "+id+";";
		rs = stmt.executeQuery(query);
		if (rs.next()) {
			return new Room(rs.getInt("RomID"), rs.getString("sted"),0);
		}
		return null;
	}

	/*
	 * Hent alle brukere i systemet
	 */
	public ArrayList getAllUsers(String username2) throws SQLException{
		stmt = con.createStatement();
		String query = "SELECT Navn, Brukernavn FROM Person WHERE brukernavn != '"+username2+"';";
		rs = stmt.executeQuery(query);
		ArrayList persons = new ArrayList<User>();
		while (rs.next()) {
			persons.add(new User(rs.getString("Navn"), rs.getString("Brukernavn")));
		}
		return persons;

	}

	public ArrayList<Room> getAvailableRooms(Timestamp startTime, Timestamp endTime, int capacity) throws SQLException {
		stmt = con.createStatement();
		ArrayList<Room> AvailableRooms = new ArrayList<Room>();
		String query = "SELECT RomID, Sted, Antall_pers FROM Møterom WHERE RomID NOT IN " +
				"(SELECT RomID FROM Avtalested WHERE AvtaleID IN " +
				"(SELECT AvtaleID FROM Avtale WHERE " +
				"(Starttid >= ? AND Starttid <= ?) OR " +
				"(Sluttid >= ? AND Sluttid <= ?) OR " +
				"(Sluttid > ? AND Starttid < ?))) AND Antall_pers > '" + capacity + "';";
		PreparedStatement st = con.prepareStatement(query);
		st.setTimestamp(1, startTime);
		st.setTimestamp(2, endTime);
		st.setTimestamp(3, startTime);
		st.setTimestamp(4, endTime);
		st.setTimestamp(5, endTime);
		st.setTimestamp(6, startTime);
		rs = st.executeQuery();
		while (rs.next()) {
			AvailableRooms.add(new Room(rs.getInt("RomID"), rs.getString("Sted"), rs.getInt("Antall_pers")));
		}
		return AvailableRooms;
	}


	public ResultSet getNotifications(String username) throws SQLException {
		stmt = con.createStatement();
		String query = "SELECT AvtaleID, Meldingstekst, tid FROM Varsel WHERE" +
				" Brukernavn = '" + username + "';";
		return stmt.executeQuery(query);
	}

	public void sendNotificationToAll (int avtaleID, String brukernavn, String meld) throws SQLException {
		stmt = con.createStatement();
		String getInvitedQuery = "SELECT Brukernavn FROM Deltar_på WHERE AvtaleID = '" + avtaleID + "';";
		rs = stmt.executeQuery(getInvitedQuery);
		while (rs.next()) {
			String tempUsername = rs.getString("Brukernavn");
			if (!tempUsername.equals(brukernavn)) {
				String query = "INSERT INTO Varsel(AvtaleID, Brukernavn, Meldingstekst) VALUES('" +
						avtaleID + "', '" + tempUsername + "', '" + meld + "');";
				stmt.execute(query);
			}
		}
	}
	
	public void sendAlarmToOne(int avtaleID, String username, String meld, Timestamp ts) throws SQLException {
		stmt = con.createStatement();
		String query = "INSERT INTO Varsel(AvtaleID, Brukernavn, Meldingstekst, tid) VALUES('" +
				avtaleID + "', '" + username + "', '" + meld + "', '" + ts + "');";
		stmt.execute(query);
	}
	
	public void sendNotificationToOne(int avtaleID, String username, String meld) throws SQLException {
		stmt = con.createStatement();
		String query = "INSERT INTO Varsel(AvtaleID, Brukernavn, Meldingstekst) VALUES('" +
				avtaleID + "', '" + username + "', '" + meld + "');";
		stmt.execute(query);
	}

	public void removeNotification(int avtaleID, String username, String meld) throws SQLException {
		stmt = con.createStatement();
		String query = "DELETE FROM Varsel WHERE AvtaleID = '" + avtaleID +
				"' AND Brukernavn = '" + username + "' AND Meldingstekst = '" + meld + "';";
		stmt.executeUpdate(query);
	}

	public ArrayList getAvailableRoomsOnBookedAppointment(Timestamp startTime,
			Timestamp endTime, Integer capacity, int id) throws SQLException {
		stmt = con.createStatement();
		ArrayList<Room> AvailableRooms = new ArrayList<Room>();
		String query = "SELECT RomID, Sted, Antall_pers FROM Møterom WHERE RomID NOT IN " +
				"(SELECT RomID FROM Avtalested WHERE AvtaleID IN " +
				"(SELECT AvtaleID FROM Avtale WHERE AvtaleID != ? AND (" +
				"(Starttid >= ? AND Starttid <= ?) OR " +
				"(Sluttid >= ? AND Sluttid <= ?) OR " +
				"(Sluttid > ? AND Starttid < ?)))) AND Antall_pers > '" + capacity + "';";
		PreparedStatement st = con.prepareStatement(query);
		st.setInt(1, id);
		st.setTimestamp(2, startTime);
		st.setTimestamp(3, endTime);
		st.setTimestamp(4, startTime);
		st.setTimestamp(5, endTime);
		st.setTimestamp(6, endTime);
		st.setTimestamp(7, startTime);
		rs = st.executeQuery();
		while (rs.next()) {
			AvailableRooms.add(new Room(rs.getInt("RomID"), rs.getString("Sted"), rs.getInt("Antall_pers")));
		}
		return AvailableRooms;
	}


}