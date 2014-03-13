package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import calendar.Database;
import calendar.User;

public class CheckCombo extends JComboBox implements ActionListener {
	Database db = new Database();

	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
		CheckComboStore store = (CheckComboStore) cb.getSelectedItem();
		CheckComboRenderer ccr = (CheckComboRenderer) cb.getRenderer();
		ccr.checkBox.setSelected((store.state = !store.state));
	}

	JPanel getContent() throws SQLException {
		ArrayList<User> names = new ArrayList<User>();
		names = db.getAllUsers();
		ArrayList<String> stringNames = new ArrayList<String>();
		for (int i = 0; i < names.size(); i++) {
			stringNames.add(names.get(i).getName());
		}

		String[] ids = new String[stringNames.size()];
		Boolean[] values = new Boolean[stringNames.size()];

		for (int i = 0; i < stringNames.size(); i++) {
			ids[i] = stringNames.get(i);
			System.out.println(ids[i]);
			values[i] = Boolean.FALSE;

		}

		CheckComboStore[] stores = new CheckComboStore[ids.length];
		for (int j = 0; j < ids.length; j++)
			stores[j] = new CheckComboStore(ids[j], values[j]);
		JComboBox combo = new JComboBox(stores);
		combo.setRenderer(new CheckComboRenderer());
		combo.addActionListener(this);
		JPanel panel = new JPanel();
		panel.add(combo);
		return panel;
	}

	public static void main(String[] args) throws SQLException {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(new CheckCombo().getContent());
		f.setSize(300, 160);
		f.setLocation(200, 200);
		f.setVisible(true);
	}
}

/** adapted from comment section of ListCellRenderer api */
class CheckComboRenderer implements ListCellRenderer {
	JCheckBox checkBox;

	public CheckComboRenderer() {
		checkBox = new JCheckBox();
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		CheckComboStore store = (CheckComboStore) value;
		checkBox.setText(store.id);
		checkBox.setSelected(((Boolean) store.state).booleanValue());
		checkBox.setBackground(isSelected ? Color.blue : Color.white);
		checkBox.setForeground(isSelected ? Color.white : Color.black);
		return checkBox;
	}
}

class CheckComboStore {
	String id;
	Boolean state;

	public CheckComboStore(String id, Boolean state) {
		this.id = id;
		this.state = state;
	}
}