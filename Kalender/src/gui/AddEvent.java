package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;

import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JSeparator;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import calendar.Database;
import calendar.EventModel;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JCalendar;

public class AddEvent extends JFrame {

	private Database db = new Database();
	private EventModel event = new EventModel();
	
	private JPanel contentPane;
	private static AddEvent frame;
	private JTextArea txtDescription;
	private JTextField txtDate, txtLocation;
	private JComboBox cbCapacity, cbRoom;
	private JList memberList, selectedMemberList;
	private ArrayList participants;
	private int numMembers;
	private JCalendar calendar;
	private JButton btnBekreftDato, btnAddMember, btnRemoveMember;
	private JLabel lblLocation;
	private JSpinner startTime, endTime;
	private Timestamp startStamp, endStamp;
	private JSpinner capacity;

	private ArrayList rooms;
	private String[] init = {""};
	private JScrollPane selectedMemberListScroll;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new AddEvent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public AddEvent() throws SQLException {
		numMembers = db.getAllUsers().size();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 356, 703);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		initGUI();
	}


	public void initGUI() throws SQLException {
		JLabel lblStartTime = new JLabel("Start tid:");
		lblStartTime.setBounds(12, 54, 70, 15);
		contentPane.add(lblStartTime);

		JLabel lblEndTime = new JLabel("Slutt tid:");
		lblEndTime.setBounds(12, 80, 70, 15);
		contentPane.add(lblEndTime);

		JLabel lblHeader = new JLabel("Legg til eller endre avtale:");
		lblHeader.setFont(new Font("Dialog", Font.BOLD, 16));
		lblHeader.setBounds(60, 12, 268, 19);
		contentPane.add(lblHeader);

		JLabel lblDate = new JLabel("Dato:");
		lblDate.setBounds(12, 284, 70, 15);
		contentPane.add(lblDate);

		JLabel lblDescription = new JLabel("Beskrivelse:");
		lblDescription.setBounds(12, 325, 100, 15);
		contentPane.add(lblDescription);

		JLabel lblCapacity = new JLabel("Kapasitet:");
		lblCapacity.setBounds(12, 430, 84, 15);
		contentPane.add(lblCapacity);

		JLabel lblDeltagere = new JLabel("Deltagere:");
		lblDeltagere.setBounds(12, 500, 84, 15);
		contentPane.add(lblDeltagere);

		cbRoom = new JComboBox(init);
		cbRoom.setToolTipText("Room");
		cbRoom.setBounds(198, 425, 143, 24);
		//cbRoom.addActionListener(new RoomChange());
		contentPane.add(cbRoom);

		JButton btnSave = new JButton("Lagre");
		btnSave.setBounds(73, 637, 93, 25);	
		btnSave.addActionListener(new Save());
		contentPane.add(btnSave);

		JButton btnExit = new JButton("Avbryt");
		btnExit.setBounds(195, 637, 93, 25);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		contentPane.add(btnExit);

		JScrollPane memberListScroll = new JScrollPane();
		memberListScroll.setBounds(12, 517, 154, 81);
		contentPane.add(memberListScroll);

		memberList = new JList();
		memberListScroll.setViewportView(memberList);
		memberList.setModel(new AbstractListModel() {
			ArrayList values = db.getAllUsers();
			public int getSize() {
				return values.size();
			}
			public Object getElementAt(int index) {
				return values.get(index);
			}
		});
		memberList.setSelectedIndex(0);


		Border border = BorderFactory.createLineBorder(Color.BLACK);
		txtDescription = new JTextArea();
		txtDescription.setBounds(123, 324, 218, 75);
		txtDescription.setEditable(true);
		txtDescription.setBorder(border);
		String desc = txtDescription.getText();
		contentPane.add(txtDescription);

		JSeparator separator1 = new JSeparator();
		separator1.setBounds(12, 40, 337, 2);
		contentPane.add(separator1);

		JSeparator separator2 = new JSeparator();
		separator2.setBounds(12, 311, 347, 2);
		contentPane.add(separator2);

		JSeparator separator3 = new JSeparator();
		separator3.setBounds(12, 411, 347, 2);
		contentPane.add(separator3);
		
		calendar = new JCalendar();
		calendar.setBounds(12, 133, 329, 133);
		calendar.setBorder(border);
		contentPane.add(calendar);
		
		txtDate = new JTextField();
		txtDate.setBounds(123, 282, 218, 19);
		txtDate.setEditable(false);
		contentPane.add(txtDate);
		txtDate.setColumns(10);
		
		btnBekreftDato = new JButton("Bekfreft dato");
		btnBekreftDato.setBounds(258, 96, 84, 25);
		btnBekreftDato.addActionListener(new ConfirmDate());
		contentPane.add(btnBekreftDato);
		
		txtLocation = new JTextField();
		txtLocation.setBounds(123, 471, 218, 19);
		txtLocation.setVisible(false);
		contentPane.add(txtLocation);
		txtLocation.setColumns(10);
		
		lblLocation = new JLabel("Sted:");
		lblLocation.setBounds(12, 473, 70, 15);
		lblLocation.setVisible(false);
		contentPane.add(lblLocation);
		
		startTime = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(startTime, "HH:mm");
		startTime.setEditor(timeEditor);
		startTime.setBounds(123, 52, 84, 19);
		startTime.setValue(new java.util.Date());
		contentPane.add(startTime);
		
		endTime = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor2 = new JSpinner.DateEditor(endTime, "HH:mm");
		endTime.setEditor(timeEditor2);
		endTime.setBounds(123, 80, 84, 19);
		endTime.setValue(new java.util.Date());
		contentPane.add(endTime);
		
		capacity = new JSpinner();
		capacity.setBounds(123, 425, 46, 24);
		contentPane.add(capacity);
		
		btnAddMember = new JButton("Legg til");
		btnAddMember.setBounds(46, 600, 93, 25);
		btnAddMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedMemberList.setModel(new AbstractListModel() {
					ArrayList values = (ArrayList) memberList.getSelectedValuesList();
					public int getSize() {
						return values.size();
					}
					public Object getElementAt(int index) {
						return values.get(index);
					}
					
				});
				if (!selectedMemberList.isSelectionEmpty()) {
					participants = new ArrayList();
					participants = (ArrayList) selectedMemberList.getSelectedValuesList();
					System.out.println(participants);
				}
			}
		});			
		contentPane.add(btnAddMember);
		
		btnRemoveMember = new JButton("Fjern");
		btnRemoveMember.setBounds(228, 600, 100, 25);
		btnRemoveMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					selectedMemberList.remove(selectedMemberList.getSelectedIndex());
					selectedMemberList.repaint();
				}	
				catch(ArrayIndexOutOfBoundsException arg0) {
					System.out.println("List is empty");
				}
				
			}
		});
		contentPane.add(btnRemoveMember);
		
		selectedMemberListScroll = new JScrollPane();
		selectedMemberListScroll.setBounds(187, 517, 154, 80);
		contentPane.add(selectedMemberListScroll);
		
		selectedMemberList = new JList();
		selectedMemberListScroll.setViewportView(selectedMemberList);
		selectedMemberList.setBorder(border);
		
		JLabel lblValgteDeltagere = new JLabel("Valgte deltagere:");
		lblValgteDeltagere.setBounds(190, 500, 126, 15);
		contentPane.add(lblValgteDeltagere);
	}
	

		
	public void fillRoomList() throws SQLException {
		rooms = db.getAvailableRooms(getStartStamp(), getEndStamp(), (Integer) capacity.getValue());
		
		cbRoom.removeAllItems();
		cbRoom.removeAll();
		cbRoom.setVisible(false);
		
		cbRoom = new JComboBox(rooms.toArray());
		cbRoom.setToolTipText("Room");
		cbRoom.setBounds(198, 425, 143, 24);
		cbRoom.addItem("Annet");
		cbRoom.addActionListener(new RoomChange());
		contentPane.add(cbRoom);
	}
		
		
	
	
	
	public String formatDate(java.util.Date date) {
		java.util.Date utlDate = date;
		java.sql.Date sqlDate = new java.sql.Date(utlDate.getTime());
		date = sqlDate;
		String cleanDate = String.format("%1$td.%1$tm.%1$ty",date); 
		return cleanDate;
	}
	
	
	class ConfirmDate implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			txtDate.setText(formatDate(calendar.getDate()));
			try {
				fillRoomList();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
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
		
		
		
	
	
	class Save implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			event.setDesc(txtDescription.getText());
			event.setDate(formatDate(calendar.getDate()));
			event.setStart(getStartStamp());
			event.setEnd(getEndStamp());
			event.setLocation(txtLocation.getText());
			
	

		}
	}
}//class