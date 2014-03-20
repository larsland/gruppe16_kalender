package calendar;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

public class EventModel extends DefaultListModel{
	
	private String desc;
	private String date;
	private Object room;
	private String location;
	private Timestamp start;
	private Timestamp end;
	private ArrayList<User> participants;
	private User creator;
	private int id;
	private Date sqlDate;
	private String otherPlace;
	
	public EventModel(){
		
	}
	
	public EventModel(int id, String desc, Date date, Timestamp start, Timestamp end, ArrayList<User> participants, User creator, Room room, String otherPlace){
		setId(id);
		setDesc(desc);
		setSqlDate(date);
		setStart(start);
		setEnd(end);
		setParticipants(participants);
		setCreator(creator);
		setRoom(room);
		setOtherPlace(otherPlace);
	}
	
	public EventModel(String location, String desc, String date, Timestamp start, Timestamp end, ArrayList<User> participants) {		
		setLocation(location);
		setDate(date);
		setDesc(desc);
		setStart(start);
		setEnd(end);
		setParticipants(participants);
	}
	
	public EventModel(Object room, String desc, String date, Timestamp start, Timestamp end, ArrayList<User> participants) {		
		setRoom(room);
		setDate(date);
		setDesc(desc);
		setStart(start);
		setEnd(end);
		setParticipants(participants);
	}
	
	public void setOtherPlace(String otherPlace) {
		this.otherPlace = otherPlace;
	}
	
	public void setSqlDate(Date sqlDate) {
		this.sqlDate = sqlDate;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Date getSqlDate() {
		return sqlDate;
	}
	public int getId() {
		return id;
	}
	public String getOtherPlace() {
		return otherPlace;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}				
	
	public Timestamp getStart() {
		return start;
	}
	public void setStart(Timestamp start) {
		this.start = start;
	}
	
	public Timestamp getEnd() {
		return end;
	}
	public void setEnd(Timestamp end) {
		this.end = end;
	}
	
	public ArrayList<User> getParticipants() {
		return participants;
	}
	public void setParticipants(ArrayList<User> participants) {
		this.participants = participants;
	}
	
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public String getRoom() {
		return room.toString();
	}

	public void setRoom(Object room) {
		this.room = room;
	}
	
	
	

}