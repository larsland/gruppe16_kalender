package calendar;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.toedter.calendar.JDateChooser;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class EventModel extends DefaultListModel{
	
	private String desc;
	private String date;
	private Object room;
	private String location;
	private Timestamp start;
	private Timestamp end;
	private ArrayList<String> participants;
	private User creator = new User();
	
	public EventModel(){
		
	}
	
	public EventModel(String location, String desc, String date, Timestamp start, Timestamp end, ArrayList<String> participants) {		
		setLocation(location);
		setDate(date);
		setDesc(desc);
		setStart(start);
		setEnd(end);
		setParticipants(participants);
	}
	
	public EventModel(Object room, String desc, String date, Timestamp start, Timestamp end, ArrayList<String> participants) {		
		setRoom(room);
		setDate(date);
		setDesc(desc);
		setStart(start);
		setEnd(end);
		setParticipants(participants);
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
	
	public ArrayList<String> getParticipants() {
		return participants;
	}
	public void setParticipants(ArrayList<String> participants) {
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