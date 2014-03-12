package calendar;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.toedter.calendar.JDateChooser;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class EventModel extends DefaultListModel{
	
	private String desc;
	private Date date;
	private String start;
	private String end;
	private ArrayList<User> participants;
	private User creator = new User();
	
	public EventModel(){
		
	}
	
	public EventModel(String desc, Date date, String start, String end, User creator) {
		setDate(date);
		setDesc(desc);
		setStart(start);
		setEnd(end);
		setCreator(creator);
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		java.util.Date utlDate = date;
		java.sql.Date sqlDate = new java.sql.Date(utlDate.getTime());
		this.date = sqlDate;
	}				
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
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
	
	

}
