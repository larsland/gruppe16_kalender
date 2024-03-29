package gui;
 
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JComboBox;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.JSeparator;
import javax.swing.JTextArea;

import calendar.Database;
import calendar.Emailsys;
import calendar.EventModel;
import calendar.Room;
import calendar.User;

import com.toedter.calendar.JCalendar;

import javax.swing.SpinnerNumberModel;
 
public class AddEvent extends JFrame {
 
    private Database db = new Database();
    private AddExternContacts addExternContacts;     
    
    private JPanel contentPane;
    private JTextArea txtDescription;
    private JTextField txtDate, txtLocation;
    private JComboBox cbRoom;
    private JCalendar calendar;
    private JButton btnConfirmDate, btnAddOtherContacts;
    private JLabel lblLocation, lblHeader;
    private JSpinner startTime, endTime;
    private Timestamp startStamp, endStamp;
    private JSpinner capacity;
    private CheckCombo memberList;
 
    private ArrayList rooms, otherContactsList;
    private String[] init = {""};
    private String username;
     
    public String getUsername() {
        return username;
    }
 
    public AddEvent(String username) throws SQLException {
        this.username = username;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 356, 569);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        initGUI();
    }
 
    /**
     * Method for creating all the swing components, and setting their listeners
     */
    public void initGUI() throws SQLException {
    	
        //----------LABELS--------------------------------------------------
        JLabel lblStartTime = new JLabel("Start tid:");
        lblStartTime.setBounds(12, 58, 70, 15);
        contentPane.add(lblStartTime);
 
        JLabel lblEndTime = new JLabel("Slutt tid:");
        lblEndTime.setBounds(12, 85, 70, 15);
        contentPane.add(lblEndTime);
 
        lblHeader = new JLabel("Legg til eller endre avtale:");
        lblHeader.setFont(new Font("Dialog", Font.BOLD, 16));
        lblHeader.setBounds(60, 12, 268, 19);
        contentPane.add(lblHeader);
 
        JLabel lblDate = new JLabel("Dato:");
        lblDate.setBounds(12, 261, 70, 15);
        contentPane.add(lblDate);
 
        JLabel lblDescription = new JLabel("Beskrivelse:");
        lblDescription.setBounds(12, 302, 100, 15);
        contentPane.add(lblDescription);
 
        JLabel lblCapacity = new JLabel("Kapasitet:");
        lblCapacity.setBounds(12, 402, 84, 15);
        contentPane.add(lblCapacity);
 
        JLabel lblParticipants = new JLabel("Deltagere:");
        lblParticipants.setBounds(12, 456, 84, 15);
        contentPane.add(lblParticipants);
         
        lblLocation = new JLabel("Sted:");
        lblLocation.setBounds(12, 429, 70, 15);
        lblLocation.setVisible(false);
        contentPane.add(lblLocation);
         
        //----------SEPARATORS/BORDERS--------------------------------------------------
        JSeparator separator1 = new JSeparator();
        separator1.setBounds(12, 40, 337, 2);
        contentPane.add(separator1);
         
        JSeparator separator2 = new JSeparator();
        separator2.setBounds(12, 288, 347, 2);
        contentPane.add(separator2);
         
        JSeparator separator3 = new JSeparator();
        separator3.setBounds(12, 388, 347, 2);
        contentPane.add(separator3);
         
        Border border = BorderFactory.createLineBorder(Color.BLACK);
         
        //----------BUTTONS--------------------------------------------------
        JButton btnSave = new JButton("Lagre");
        btnSave.setBounds(72, 493, 93, 25); 
        btnSave.addActionListener(new Save());
        contentPane.add(btnSave);
         
        JButton btnExit = new JButton("Avbryt");
        btnExit.setBounds(194, 493, 93, 25);
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(btnExit);
         
        btnConfirmDate = new JButton("Bekreft dato");
        btnConfirmDate.setFont(new Font("Dialog", Font.BOLD, 11));
        btnConfirmDate.setBounds(219, 80, 123, 25);
        btnConfirmDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtDate.setText(formatDate(calendar.getDate()));
                try {
                    fillRoomList();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        contentPane.add(btnConfirmDate);
         
        btnAddOtherContacts = new JButton("");
        btnAddOtherContacts.setIcon(new ImageIcon(getClass().getResource("/img/envelope.png")));
        btnAddOtherContacts.setBounds(287, 451, 55, 34);
        btnAddOtherContacts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addExternContacts = new AddExternContacts();
                 
            }
        });
        contentPane.add(btnAddOtherContacts);
         
        //----------TEXT FIELDS--------------------------------------------------
        txtDescription = new JTextArea();
        txtDescription.setBounds(123, 301, 218, 75);
        txtDescription.setEditable(true);
        txtDescription.setBorder(border);
        String desc = txtDescription.getText();
        contentPane.add(txtDescription);
         
         
        txtDate = new JTextField();
        txtDate.setBounds(123, 259, 218, 19);
        txtDate.setEditable(false);
        contentPane.add(txtDate);
        txtDate.setColumns(10);
         
        txtLocation = new JTextField();
        txtLocation.setBounds(123, 427, 218, 19);
        txtLocation.setVisible(false);
        contentPane.add(txtLocation);
        txtLocation.setColumns(10);
         
        //----------OTHER COMPONENTS--------------------------------------------------
        cbRoom = new JComboBox(init);
        cbRoom.setToolTipText("Room");
        cbRoom.setBounds(195, 397, 143, 24);
        cbRoom.addActionListener(new RoomChange());
        contentPane.add(cbRoom);
 
        calendar = new JCalendar();
        calendar.setBounds(12, 116, 329, 133);
        calendar.setBorder(border);
        contentPane.add(calendar);
         
        startTime = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(startTime, "HH:mm");
        startTime.setEditor(timeEditor);
        startTime.setBounds(123, 52, 84, 25);
        startTime.setValue(new java.util.Date());
        contentPane.add(startTime);
         
        endTime = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor2 = new JSpinner.DateEditor(endTime, "HH:mm");
        endTime.setEditor(timeEditor2);
        endTime.setBounds(123, 80, 84, 25);
        endTime.setValue(new java.util.Date());
        contentPane.add(endTime);
         
        capacity = new JSpinner();
        capacity.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
        capacity.setBounds(123, 398, 46, 24);
        capacity.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    fillRoomList();
                }
                catch(SQLException e1) {}
                    
            }
        });
        contentPane.add(capacity);
                 
        memberList = new CheckCombo();
        JPanel combo = memberList.getContent(getUsername());
        combo.setVisible(true);
        combo.setBounds(123, 450, 162, 35);
        contentPane.add(combo);
         
    }
        
    /**
     * Method for filling and adding the available rooms list, 
     * depending on the selected room capacity in "capacity", and the selected time/date
     */
    @SuppressWarnings("deprecation")
    public void fillRoomList() throws SQLException {
    	Timestamp start = new Timestamp(calendar.getDate().getYear(), calendar.getDate().getMonth(), 
    									calendar.getDate().getDate(), getStartStamp().getHours(), 
    									getStartStamp().getMinutes(), 0, 0);
    	
        Timestamp end = new Timestamp(calendar.getDate().getYear(), calendar.getDate().getMonth(), 
        							  calendar.getDate().getDate(), getEndStamp().getHours(), 
        							  getEndStamp().getMinutes(), 0, 0);
        
        rooms = db.getAvailableRooms(start, end, (Integer) capacity.getValue());
         
        contentPane.remove(cbRoom);
         
        //Creates an empty room list, if the selected capacity is 0. (except from the "Annet" option)
        if ((Integer) capacity.getValue() == 0) {
            cbRoom = new JComboBox(init);
            cbRoom.setToolTipText("Room");
            cbRoom.setBounds(195, 397, 143, 24);
            cbRoom.addItem("Annet");
            cbRoom.addActionListener(new RoomChange());
            contentPane.add(cbRoom);
        }
                
        //Creates a room list filled with the available room list in the database, fetched in the "rooms" field over
        cbRoom = new JComboBox(rooms.toArray());
        cbRoom.setToolTipText("Room");
        cbRoom.setBounds(195, 397, 143, 24);
        cbRoom.addItem("Annet");
        cbRoom.addActionListener(new RoomChange());
        contentPane.add(cbRoom);
    }
     
    /**
     * Simple method for formating a java date to a nice string
     */
    public String formatDate(java.util.Date date) {
        java.util.Date utlDate = date;
        java.sql.Date sqlDate = new java.sql.Date(utlDate.getTime());
        date = sqlDate;
        String cleanDate = String.format("%1$td.%1$tm.%1$ty",date); 
        return cleanDate;
    }
     
 
    /**
     * Actionlistener for changing the selected room in the roomlist
     */
    class RoomChange implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (cbRoom.getSelectedItem().equals("Annet")) {
                txtLocation.setVisible(true);
                lblLocation.setVisible(true);
            }
            else {
                txtLocation.setVisible(false);
                lblLocation.setVisible(false);
            }
             
        }
    }
     
    /**
     * Returns the starttime of this event as a Timestamp
     */
    @SuppressWarnings("deprecation")
    public Timestamp getStartStamp() {
        int h = ((java.util.Date) startTime.getModel().getValue()).getHours();
        int m = ((java.util.Date) startTime.getModel().getValue()).getMinutes();
        int y = ((java.util.Date) startTime.getModel().getValue()).getYear();
        int mon = ((java.util.Date) startTime.getModel().getValue()).getMonth();
        int d = ((java.util.Date) startTime.getModel().getValue()).getDay();
        int s = ((java.util.Date) startTime.getModel().getValue()).getSeconds();
        startStamp = new Timestamp(y, mon, d, h, m, s, 0);
        return startStamp;
    }
     
    /**
     * Returns the endtime of this event as a Timestamp
     */
    @SuppressWarnings("deprecation")
    public Timestamp getEndStamp() {
        int h1 = ((java.util.Date) endTime.getModel().getValue()).getHours();
        int m1 = ((java.util.Date) endTime.getModel().getValue()).getMinutes();
        int y1 = ((java.util.Date) startTime.getModel().getValue()).getYear();
        int mon1 = ((java.util.Date) startTime.getModel().getValue()).getMonth();
        int d1 = ((java.util.Date) startTime.getModel().getValue()).getDay();
        int s1 = ((java.util.Date) startTime.getModel().getValue()).getSeconds();
        endStamp = new Timestamp(y1, mon1, d1, h1, m1, s1, 0);
        return endStamp;
    }
         
    @SuppressWarnings("deprecation")
    class Save implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if ((txtDescription.getText() == "") || (txtDate.getText() == "")) {
                lblHeader.setText("Venligst fyll ut alle felter");
            }
            else if ((txtLocation.getText() == "") && (cbRoom.getItemCount() == 0)) {
                lblHeader.setText("Venligst fyll ut alle felter");
            }
             
            Timestamp start = new Timestamp(calendar.getDate().getYear(), calendar.getDate().getMonth(), calendar.getDate().getDate(), getStartStamp().getHours(), getStartStamp().getMinutes(), 0, 0);
            Timestamp end = new Timestamp(calendar.getDate().getYear(), calendar.getDate().getMonth(), calendar.getDate().getDate(), getEndStamp().getHours(), getEndStamp().getMinutes(), 0, 0);
            ArrayList<String> participants = new ArrayList<String>();
            java.sql.Date dateSql = new java.sql.Date(calendar.getDate().getYear(), calendar.getDate().getMonth(), calendar.getDate().getDate());
            

            for (User u : memberList.getSelectedPersons()) {
                participants.add(u.getUsername());
            }
            if (!txtLocation.isVisible()) {
                try {
                    db.createAppointment(dateSql, start, end, txtDescription.getText(), getUsername(), participants, ((Room) cbRoom.getSelectedItem()).getRoomID(),null);
                    if (addExternContacts != null) {
        				
                        otherContactsList = addExternContacts.getMailList();
                        if (otherContactsList.size() > 0) {
                        	new Emailsys(otherContactsList, txtDescription.getText(), dateSql, start, end, ((Room) cbRoom.getSelectedItem()).getLocation());
            			}

            			}
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            else if (txtLocation.isVisible()) {
                try {
                    db.createAppointment(dateSql, start, end, txtDescription.getText(), getUsername(), participants, 7,txtLocation.getText());
                    if (addExternContacts != null) {
        				
                        otherContactsList = addExternContacts.getMailList();
                        if (otherContactsList.size() > 0) {
                        	new Emailsys(otherContactsList, txtDescription.getText(), dateSql, start, end, txtLocation.getText());
            			}

            			}
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            try {
				MainPanel.fireUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
            dispose();
                 
        }
    }
}//class