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

  public CheckCombo() {
	  setPreferredSize(new Dimension(170,30));
}


  private ArrayList<User> selectedPersons = new ArrayList<User>();

  public void actionPerformed(ActionEvent e) {
    JComboBox cb = (JComboBox) e.getSource();
    CheckComboStore store = (CheckComboStore) cb.getSelectedItem();
    CheckComboRenderer ccr = (CheckComboRenderer) cb.getRenderer();
    ccr.checkBox.setSelected((store.state = !store.state));
    setSelectedPersons(store.id, store.state);
  }

  public void setSelectedPersons(User username, boolean selected) {
	  if (selected) {
		  selectedPersons.add(username);
	  }
	  else{
		  for (User u : getSelectedPersons()) {
			  if (u.getUsername().equals(username.getUsername())) {
				  selectedPersons.remove(u);
			}
		}
	  }
  }

  public ArrayList<User> getSelectedPersons() {
	return selectedPersons;
}

  public JPanel getContent(String username) throws SQLException {
    ArrayList<User> names = new ArrayList<User>();
    names = db.getAllUsers(username);
    ArrayList<User> stringNames = new ArrayList<User>();
    for (int i = 0; i < names.size(); i++) {
      stringNames.add(names.get(i));
    }

    User[] ids = new User[stringNames.size()];
    Boolean[] values = new Boolean[stringNames.size()];

    for (int i = 0; i < stringNames.size(); i++) {
      ids[i] = stringNames.get(i);
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
  
  public boolean inSelected(String name, ArrayList<User> selected){
	 for (User user : selected) {
		 if (name.equals(user.getName())) {
			 return true;
		}
	}
	 return false;
  }
  
  public JPanel getSelectedContent(ArrayList<User> selected, String username) throws SQLException {
	    ArrayList<User> names = new ArrayList<User>();
	    names = db.getAllUsers(username);
	    ArrayList<User> stringNames = new ArrayList<User>();
	    for (int i = 0; i < names.size(); i++) {
	      stringNames.add(names.get(i));
	    }

	    User[] ids = new User[stringNames.size()];
	    Boolean[] values = new Boolean[stringNames.size()];

	    for (int i = 0; i < stringNames.size(); i++) {
	      ids[i] = stringNames.get(i);
	      if (inSelected(stringNames.get(i).getName(), selected)) {
	    	  values[i] = Boolean.TRUE;
	      }
	      else{
	    	  values[i] = Boolean.FALSE;
	      }

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
    checkBox.setText(store.id.toString());
    checkBox.setSelected(((Boolean) store.state).booleanValue());
    checkBox.setBackground(isSelected ? Color.blue : Color.white);
    checkBox.setForeground(isSelected ? Color.white : Color.black);
    return checkBox;
  }
}

class CheckComboStore {
  User id;
  Boolean state;

  public CheckComboStore(User id, Boolean state) {
    this.id = id;
    this.state = state;
  }
}
