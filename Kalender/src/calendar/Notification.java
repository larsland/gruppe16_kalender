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
				notTime.add(rs.getTimestamp("tid"));
				 if (notTime.get(notTime.size() -1).before(now) ) {
					 notAvtaleID.add(rs.getInt("AvtaleID"));
					 notMessages.add(rs.getString("Meldingstekst"));
				 }

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void removeNoti(int index) {
		try {
			db.removeNotification(notAvtaleID.get(index), username, notMessages.get(index));
			notAvtaleID.remove(index);
			notMessages.remove(index);
			notTime.remove(index);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void sendNotification(int appId, String username, String message) {
		try {
			ResultSet rs = db.getAppointmentInfo(appId);
			if (rs.next()) {
				String starttid = rs.getString("Starttid").substring(11, 16);
				db.sendNotificationToAll(appId, username, message + "kl: " + starttid);
				String tempCreator = rs.getString("Opprettet_av");
				if (!tempCreator.equals(username)) {
					db.sendNotificationToOne(appId, tempCreator, message + "kl: " + starttid);
				}
			}
		} 
		catch (SQLException e) {
		}
	}
}