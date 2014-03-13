package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JEditorPane;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSeparator;
import javax.swing.JInternalFrame;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;

import java.awt.Button;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;

import calendar.App;
import calendar.CalendarModel;
import calendar.User;

import javax.swing.JList;
import javax.swing.JTextPane;

public class MainPanel extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private CalendarModel model;
	private String username;
	private static JPanel appointment;
	private static JPanel participants;
	private static App app = new App();
	private final JLabel lblMandag;
	private final JLabel lblTirsdag;
	private final JLabel lblOnsdag;
	private final JLabel lblTorsdag;
	private final JLabel lblFredag;
	private final JLabel lblLrdag;
	private final JLabel lblSndag;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public MainPanel(final String username) throws SQLException {
		this.username = username;
		this.model = new CalendarModel(username);
		setTitle("Min kalender - " + username);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1071, 768);
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("0");
		button.setForeground(Color.BLACK);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setBounds(6, 6, 37, 29);
		contentPane.add(button);
		
		JButton btnNewButton = new JButton("Forrige uke");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model.setThisWeeksAppointments(-1);
					lblMandag.setText(model.getDateString(1));
					lblTirsdag.setText(model.getDateString(2));
					lblOnsdag.setText(model.getDateString(3));
					lblTorsdag.setText(model.getDateString(4));
					lblFredag.setText(model.getDateString(5));
					lblLrdag.setText(model.getDateString(6));
					lblSndag.setText(model.getDateString(7));
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(48, 47, 117, 29);
		contentPane.add(btnNewButton);
		
		lblMandag = new JLabel(model.getDateString(1));
		lblMandag.setBounds(127, 88, 61, 16);
		contentPane.add(lblMandag);
		
		lblTirsdag = new JLabel(model.getDateString(2));
		lblTirsdag.setBounds(223, 88, 61, 16);
		contentPane.add(lblTirsdag);
		
		lblOnsdag = new JLabel(model.getDateString(3));
		lblOnsdag.setBounds(316, 88, 61, 16);
		contentPane.add(lblOnsdag);
		
		lblTorsdag = new JLabel(model.getDateString(4));
		lblTorsdag.setBounds(412, 88, 61, 16);
		contentPane.add(lblTorsdag);
		
		lblFredag = new JLabel(model.getDateString(5));
		lblFredag.setBounds(510, 88, 61, 16);
		contentPane.add(lblFredag);
		
		lblLrdag = new JLabel(model.getDateString(6));
		lblLrdag.setBounds(603, 88, 61, 16);
		contentPane.add(lblLrdag);
		
		lblSndag = new JLabel(model.getDateString(7));
		lblSndag.setBounds(704, 88, 61, 16);
		contentPane.add(lblSndag);
		
		JButton btnNesteUke = new JButton("Neste uke");
		btnNesteUke.setBounds(684, 47, 117, 29);
		contentPane.add(btnNesteUke);
		
		btnNesteUke.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					model.setThisWeeksAppointments(1);
					lblMandag.setText(model.getDateString(1));
					lblTirsdag.setText(model.getDateString(2));
					lblOnsdag.setText(model.getDateString(3));
					lblTorsdag.setText(model.getDateString(4));
					lblFredag.setText(model.getDateString(5));
					lblLrdag.setText(model.getDateString(6));
					lblSndag.setText(model.getDateString(7));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(55, 107, 746, 460);
		contentPane.add(scrollPane);
		
		table = new JTable(model);
		table.setGridColor(Color.BLACK);
		table.setBackground(Color.WHITE);
		table.setCellSelectionEnabled(false);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		table.getColumnModel().getColumn(5).setPreferredWidth(150);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		table.getColumnModel().getColumn(7).setPreferredWidth(150);
		
		table.getColumnModel().getColumn(1).setCellRenderer(new EventCellRender());
		table.getColumnModel().getColumn(1).setCellEditor(new EventCellEditor());

		table.getColumnModel().getColumn(2).setCellRenderer(new EventCellRender());
		table.getColumnModel().getColumn(2).setCellEditor(new EventCellEditor());
		
		table.getColumnModel().getColumn(3).setCellRenderer(new EventCellRender());
		table.getColumnModel().getColumn(3).setCellEditor(new EventCellEditor());
		
		table.getColumnModel().getColumn(4).setCellRenderer(new EventCellRender());
		table.getColumnModel().getColumn(4).setCellEditor(new EventCellEditor());
		
		table.getColumnModel().getColumn(5).setCellRenderer(new EventCellRender());
		table.getColumnModel().getColumn(5).setCellEditor(new EventCellEditor());
		
		table.getColumnModel().getColumn(6).setCellRenderer(new EventCellRender());
		table.getColumnModel().getColumn(6).setCellEditor(new EventCellEditor());

		table.getColumnModel().getColumn(7).setCellRenderer(new EventCellRender());
		table.getColumnModel().getColumn(7).setCellEditor(new EventCellEditor());

		table.setRowHeight(50);
		table.setRowSelectionAllowed(true);
	    table.setColumnSelectionAllowed(false);

		scrollPane.setViewportView(table);
		appointment = new JPanel();
		participants = new JPanel();
		appointment.setName("Informasjon");
		participants.setName("Deltakere");
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(819, 107, 246, 366);
		tabbedPane.add(appointment);
	
		
		JList list = new JList(model.getListModel());
		list.setBounds(83, 89, 1, 1);
		appointment.add(list);
		tabbedPane.add(participants);
		
		JList list2 = new JList(model.getParticipantsModel());
		list2.setCellRenderer(new ParticipantsRenderer());
		participants.add(list2);
		list.setBounds(83, 89, 1, 1);
		contentPane.add(tabbedPane);
		
		JButton btnNyAvtale = new JButton("Ny Avtale");
		btnNyAvtale.setBounds(829, 485, 117, 29);
		btnNyAvtale.addActionListener(app);
		contentPane.add(btnNyAvtale);
		
		JButton btnLogout = new JButton("Logg ut");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				app.goToLoginn();
			}
		});
		btnLogout.setBounds(944, 8, 97, 25);
		contentPane.add(btnLogout);
		
		final JComboBox personsComboBox = new JComboBox();
		personsComboBox.setModel(new PersonComboBoxModel());
		personsComboBox.setBounds(48, 624, 140, 27);
		contentPane.add(personsComboBox);
		
		JButton btnLeggTil = new JButton("Legg til");
		btnLeggTil.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					model.addOtherPersonsAppointments((User) personsComboBox.getSelectedItem());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		btnLeggTil.setBounds(190, 623, 117, 29);
		contentPane.add(btnLeggTil);
		
		JLabel lblSeAndrePersoners = new JLabel("Se andre personers avtaler");
		lblSeAndrePersoners.setBounds(55, 597, 177, 16);
		contentPane.add(lblSeAndrePersoners);
		
	}
}