package calendar;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Notification {
	
	private Database db = new Database();
	private ResultSet rs;
	private ArrayList<String> notMessages;
	private ArrayList<Integer> notAvtaleID;
	private ArrayList<Timestamp> notTime;
	private String username;
	
	
	public Notification(String username) {
		this.username = username;
		setValues(username);
	}
	
	public ArrayList<String> getNotMessages() {
		return notMessages;
	}

	public ArrayList<Integer> getNotAvtaleID() {
		return notAvtaleID;
	}


	public ArrayList<Timestamp> getNotTime() {
		return notTime;
	}

	public void setValues(String username) {
		notMessages = new ArrayList<String>();
		notAvtaleID = new ArrayList<Integer>();
		notTime = new ArrayList<Timestamp>();
		Timestamp now = new Timestamp((new Date()).getTime());
		try {
			rs = db.getNotifications(username);
			while (rs.next()) {
				notAvtaleID.add(rs.getInt("AvtaleID"));
				notTime.add(rs.getTimestamp("tid"));
				 if (notTime.get(notTime.size() -1).before(now) ) {
					 notMessages.add(rs.getString("Meldingstekst"));
				 }

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void removeNoti(int index) {
//		db.removeNotification(notAvtaleID.get(index), username);
		notAvtaleID.remove(index);
		notMessages.remove(index);
		notTime.remove(index);
	}
}
