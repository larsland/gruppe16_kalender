package calendar;

public class Room {
	
	private int roomID;
	private String location;
	private int capacity;
	
	public Room(int roomID, String location, int capacity) {
		this.roomID = roomID;
		this.location = location;
		this.capacity = capacity;
	}
	

	public int getRoomID() {
		return roomID;
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public String toString() {
		return location + ' ' + '(' + capacity + ')';
	}

}
