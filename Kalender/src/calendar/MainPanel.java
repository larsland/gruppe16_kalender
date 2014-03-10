package calendar;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JEditorPane;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSeparator;
import javax.swing.JInternalFrame;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.Button;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;

public class MainPanel extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private CalendarModel model;
	private String username;
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
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(48, 47, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblMandag = new JLabel("Mandag");
		lblMandag.setBounds(127, 88, 61, 16);
		contentPane.add(lblMandag);
		
		JLabel lblTirsdag = new JLabel("Tirsdag");
		lblTirsdag.setBounds(223, 88, 61, 16);
		contentPane.add(lblTirsdag);
		
		JLabel lblOnsdag = new JLabel("Onsdag");
		lblOnsdag.setBounds(316, 88, 61, 16);
		contentPane.add(lblOnsdag);
		
		JLabel lblTorsdag = new JLabel("Torsdag");
		lblTorsdag.setBounds(412, 88, 61, 16);
		contentPane.add(lblTorsdag);
		
		JLabel lblFredag = new JLabel("Fredag");
		lblFredag.setBounds(510, 88, 61, 16);
		contentPane.add(lblFredag);
		
		JLabel lblLrdag = new JLabel("L\u00F8rdag");
		lblLrdag.setBounds(603, 88, 61, 16);
		contentPane.add(lblLrdag);
		
		JLabel lblSndag = new JLabel("S\u00F8ndag");
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
		
		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setBackground(Color.WHITE);
		table.setCellSelectionEnabled(false);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setModel(model);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		table.getColumnModel().getColumn(5).setPreferredWidth(150);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		table.getColumnModel().getColumn(7).setPreferredWidth(150);
		table.setRowHeight(50);
		scrollPane.setViewportView(table);
		JPanel appointment = new JPanel();
		JPanel participants = new JPanel();
		appointment.setName("Informasjon");
		participants.setName("Deltakere");
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(819, 107, 246, 366);
		tabbedPane.add(appointment);
		GridBagLayout gbl_appointment = new GridBagLayout();
		gbl_appointment.columnWidths = new int[]{98, 29, 0};
		gbl_appointment.rowHeights = new int[]{16, 0, 0, 0, 0, 0};
		gbl_appointment.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_appointment.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		appointment.setLayout(gbl_appointment);
		
		JLabel lblStart = new JLabel("Start:");
		GridBagConstraints gbc_lblStart = new GridBagConstraints();
		gbc_lblStart.insets = new Insets(0, 0, 5, 5);
		gbc_lblStart.gridx = 0;
		gbc_lblStart.gridy = 0;
		appointment.add(lblStart, gbc_lblStart);
		
		JLabel startTimeLabel = new JLabel();
		GridBagConstraints gbc_startTimeLabel = new GridBagConstraints();
		gbc_startTimeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_startTimeLabel.gridx = 1;
		gbc_startTimeLabel.gridy = 0;
		appointment.add(startTimeLabel, gbc_startTimeLabel);
		
		JLabel lblSlutt = new JLabel("Slutt:");
		GridBagConstraints gbc_lblSlutt = new GridBagConstraints();
		gbc_lblSlutt.insets = new Insets(0, 0, 5, 5);
		gbc_lblSlutt.gridx = 0;
		gbc_lblSlutt.gridy = 1;
		appointment.add(lblSlutt, gbc_lblSlutt);
		
		JLabel sluttTimeLabel = new JLabel();
		GridBagConstraints gbc_sluttTimeLabel = new GridBagConstraints();
		gbc_sluttTimeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_sluttTimeLabel.gridx = 1;
		gbc_sluttTimeLabel.gridy = 1;
		appointment.add(sluttTimeLabel, gbc_sluttTimeLabel);
		
		JLabel lblDato = new JLabel("Dato:");
		GridBagConstraints gbc_lblDato = new GridBagConstraints();
		gbc_lblDato.insets = new Insets(0, 0, 5, 5);
		gbc_lblDato.gridx = 0;
		gbc_lblDato.gridy = 2;
		appointment.add(lblDato, gbc_lblDato);
		
		JLabel dateLabel = new JLabel("");
		GridBagConstraints gbc_dateLabel = new GridBagConstraints();
		gbc_dateLabel.insets = new Insets(0, 0, 5, 0);
		gbc_dateLabel.gridx = 1;
		gbc_dateLabel.gridy = 2;
		appointment.add(dateLabel, gbc_dateLabel);
		
		JLabel lblSted = new JLabel("Sted:");
		GridBagConstraints gbc_lblSted = new GridBagConstraints();
		gbc_lblSted.insets = new Insets(0, 0, 5, 5);
		gbc_lblSted.gridx = 0;
		gbc_lblSted.gridy = 3;
		appointment.add(lblSted, gbc_lblSted);
		
		JLabel lblBeskrivelse = new JLabel("Beskrivelse:");
		GridBagConstraints gbc_lblBeskrivelse = new GridBagConstraints();
		gbc_lblBeskrivelse.insets = new Insets(0, 0, 0, 5);
		gbc_lblBeskrivelse.gridx = 0;
		gbc_lblBeskrivelse.gridy = 4;
		appointment.add(lblBeskrivelse, gbc_lblBeskrivelse);
		tabbedPane.add(participants);
		contentPane.add(tabbedPane);
	}
}