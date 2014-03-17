package calendar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class testclass {
	public static void main(String[] args) throws SQLException {
		Database db = new Database();
		EventModel event = db.getEventModelForAppointment(28);
		System.out.println("Creator: " +event.getCreator());
		System.out.println("Sted: " +event.getRoom());
		System.out.println(event.getParticipants());

		
	}

}
