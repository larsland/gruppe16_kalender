package calendar;

public class User {
	private String username;
	private String name;
	private int going;
	
	public User(String name, String username, int going){
		this.name = name;
		this.username = username;
		this.going = going;
	}

	public User(String name, String username) {
		this.name = name;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
	
	public String getName() {
		return name;
	}
	
	public int getGoing() {
		return going;
	}

	public void setGoing(int going) {
		this.going = going;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}

}
