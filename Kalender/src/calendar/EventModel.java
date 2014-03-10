package calendar;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class EventModel {
	
	private String desc;
	private Date date;
	private Timestamp start;
	private Timestamp end;
	private ArrayList<User> participants;
	private User creator = new User();

}
