package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JDesktopPane;
import javax.swing.JSeparator;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;

public class addEvent extends JFrame {

	private JPanel contentPane;
	private JTextField txtStartTime;
	private JTextField txtEndTime;
	
	private String[] capacity = {"1", "2", "3", "4", "5"};
	private String[] rooms = {"H1", "H2", "S4"};
	private String[] participants = {"Siv", "Per", "Beate"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addEvent frame = new addEvent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public addEvent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 373, 487);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		initGUI();
	}
		
		
	
	public void initGUI() {
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
		
		txtStartTime = new JTextField();
		txtStartTime.setBounds(123, 52, 70, 19);
		contentPane.add(txtStartTime);
		txtStartTime.setColumns(10);
		
		txtEndTime = new JTextField();
		txtEndTime.setBounds(123, 80, 70, 19);
		contentPane.add(txtEndTime);
		txtEndTime.setColumns(10);
		
		JLabel lblDate = new JLabel("Dato:");
		lblDate.setBounds(12, 107, 70, 15);
		contentPane.add(lblDate);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(123, 107, 218, 19);
		contentPane.add(dateChooser);
		
		JLabel lblDescription = new JLabel("Beskrivelse:");
		lblDescription.setBounds(12, 145, 100, 15);
		contentPane.add(lblDescription);
		
		JLabel lblCapacity = new JLabel("Kapasitet:");
		lblCapacity.setBounds(12, 251, 84, 15);
		contentPane.add(lblCapacity);
		
		JLabel lblDeltagere = new JLabel("Deltagere:");
		lblDeltagere.setBounds(12, 297, 84, 15);
		contentPane.add(lblDeltagere);
		
		JComboBox cbCapacity = new JComboBox(capacity);
		cbCapacity.setToolTipText("#");
		cbCapacity.setBounds(123, 246, 46, 24);
		contentPane.add(cbCapacity);
		
		JComboBox cbRoom = new JComboBox(rooms);
		cbRoom.setToolTipText("Room");
		cbRoom.setBounds(198, 246, 143, 24);
		contentPane.add(cbRoom);
		
		JButton btnSave = new JButton("Lagre");
		btnSave.setBounds(78, 421, 93, 25);
		contentPane.add(btnSave);
		
		JButton btnExit = new JButton("Avbryt");
		btnExit.setBounds(200, 421, 93, 25);
		contentPane.add(btnExit);
		
		JSeparator separator1 = new JSeparator();
		separator1.setBounds(12, 40, 347, 2);
		contentPane.add(separator1);
		
		JSeparator separator2 = new JSeparator();
		separator2.setBounds(12, 134, 347, 2);
		contentPane.add(separator2);
		
		JSeparator separator3 = new JSeparator();
		separator3.setBounds(12, 232, 347, 2);
		contentPane.add(separator3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(123, 296, 218, 81);
		contentPane.add(scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Per", "Siv", "Beate", "Anders", "Nils", "Kål", "Bjartis"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectedIndex(0);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		JTextArea txtDescription = new JTextArea();
		txtDescription.setBounds(123, 145, 208, 75);
		txtDescription.setEditable(true);
		txtDescription.setBorder(border);
		
		contentPane.add(txtDescription);
		
		
		
		
	
	
		
		
		
	}
}
		
