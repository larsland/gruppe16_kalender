package gui;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;

import calendar.Database;
import calendar.User;

public class PersonComboBoxModel extends DefaultComboBoxModel {

	private Database db = new Database();
	
	public PersonComboBoxModel() throws SQLException {
		ArrayList<User> users = db.getAllUsers("");
		for (User u : users) {
			addElement(u);
		}
	}

}