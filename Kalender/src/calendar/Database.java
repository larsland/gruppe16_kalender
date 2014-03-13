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
	
//private String url = "jdbc:mysql://127.0.0.1/fls";
//private String username = "root";
//private String pwd = "";


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

	/*
	 * Verify login
	 */
	public boolean userExists(String username) throws SQLException{
		stmt = con.createStatement();
		rs = stmt.executeQuery("SELECT Brukernavn FROM Person " +
			"WHERE Brukernavn = '"+username +"' LIMIT 1;");
	
		if (rs.next()) {
			return true;
		}
		return false; 
	}	

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
	
	
	/*
	 * Create appointment
	 * TODO: M¿teleder, m¿terom/sted
	 */
	public void createAppointment(Date date, Timestamp starttime, Timestamp endtime, String desc, String creator, ArrayList<User> participants) throws SQLException{
		stmt = con.createStatement();
		String query = "INSERT INTO Avtale (Dato, Starttid, Sluttid, Beskrivelse, Opprettet_av) VALUES "
				+ "(?,?,?,?,?)";
		PreparedStatement st = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		st.setDate(1, date);
		st.setTimestamp(2, starttime);
		st.setTimestamp(3, endtime);
		st.setString(4, desc);
		st.setString(5, creator);
		st.executeUpdate();
		// Lagre siste id
	    ResultSet keys = st.getGeneratedKeys();    
	    keys.next();  
	    int key = keys.getInt(1);
	    
	    //Legg til deltakere
	    String query2 = "INSERT INTO Deltar_på VALUES (?,?,?);";
	    st = con.prepareStatement(query2);
	    for (User user : participants) {
	      st.setInt(1, key);
	      st.setString(2, user.getUsername());
	      st.setInt(3, 0);
	      st.executeUpdate();
	    }
	}
	
	/*
	 * 
	 */
	public ResultSet getParticipantsInAppointment(int appointmentId) throws SQLException{
		stmt = con.createStatement();
		String query = "select brukernavn, Godkjenning from Deltar_pŒ inner join Avtale on Deltar_pŒ.`AvtaleID` = Avtale.AvtaleID WHERE Deltar_pŒ.AvtaleID = "+appointmentId+";";
		rs = stmt.executeQuery(query);
		return rs;
	}
	
	/*
	 * 
	 */
	public ResultSet getAppointmentInfo(int appointmentId) throws SQLException{
		stmt = con.createStatement();
		String query = "SELECT * FROM Avtale WHERE AvtaleID= '"+ appointmentId+"';";
		rs = stmt.executeQuery(query);
		return rs;
		
	}
	
	/*
	 * Avtaler som bruker er invitert til
	 */
	public ResultSet getInvitedAppointments(String username, Timestamp monday, Timestamp sunday) throws SQLException{
		stmt = con.createStatement();
		String query = "select * from Avtale inner join `Deltar_pŒ` on `Deltar_pŒ`.`AvtaleID` = `Avtale`.`AvtaleID` and brukernavn = '"+username+"' and (Starttid between '"+monday+"' and '"+sunday+"');";
		rs = stmt.executeQuery(query);
		return rs;
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
	
	public ResultSet getStatusForAppointment(String username, int appID) throws SQLException{
		stmt = con.createStatement();
		String query = "select brukernavn, Godkjenning from Deltar_pŒ WHERE brukernavn = '"+username+"' and AvtaleID = "+appID+";";
		rs = stmt.executeQuery(query);
		return rs;
	}
	
	
	/*
	 * Hent alle brukere i systemet
	 */
	public ArrayList getAllUsers() throws SQLException{
		stmt = con.createStatement();
		String query = "SELECT Navn, Brukernavn FROM Person;";
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
	
	public void removeVarsel(int avtaleID, String username) throws SQLException {
		stmt = con.createStatement();
		String query = "DELETE FROM Varsel WHERE AvtaleID = '" + avtaleID + 
				"' AND Brukernavn = '" + username + "';";
		stmt.executeUpdate(query);
	}
	
	
}
