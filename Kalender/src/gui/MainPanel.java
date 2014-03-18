package gui;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.print.DocFlavor.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
import javax.swing.JPopupMenu;
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
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;

import calendar.App;
import calendar.CalendarModel;
import calendar.Notification;
import calendar.User;

import javax.swing.JList;
import javax.swing.JTextPane;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.Font;

import javax.swing.SwingConstants;

public class MainPanel extends JFrame  implements ListSelectionListener{

	private JPanel contentPane;
	private JTable table;
	private static CalendarModel model;
	private static String username;
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
	private CheckCombo personsComboBox = new CheckCombo();
	private JPopupMenu notPanel;
	private JList notList;
	private static Notification notification;
	private JButton btnNoti;
	private DefaultListModel otherPersonsListModel = new DefaultListModel();
	private Color[] personColors = {Color.red, Color.blue, Color.cyan, Color.magenta, Color.yellow, Color.pink, Color.orange};
	private JLabel lblDato;
	private JList list;
	private static JPanel statusBtnPanel;
	private static JPanel creatorBtnPanel;

	
	public static String getUsername() {
		return username;
	}

	/**
	 * Create the frame.
	 * @throws SQLException
	 * @throws LineUnavailableException 
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 */
	public MainPanel(final String username) throws SQLException {
		this.username = username;
		this.model = new CalendarModel(username);
		this.notification = new Notification(username);
		setTitle("Min kalender - " + username);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1071, 768);
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Timer timer = new Timer();
		timer.schedule(new update(), 0, 10000);

		notPanel = new JPopupMenu();
		notPanel.setLayout(new BorderLayout());
		notList = new JList(notification.getNotMessages().toArray());
		

		notList.addListSelectionListener(this);
		
		if (notification.getNotAvtaleID().size() > 0) {
			       try{
			    	   java.net.URL url = getClass().getResource("/sounds/notification.mp3");
			    	   AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			    	   Clip clip = AudioSystem.getClip();
			    	   clip.open(ais);
			    }
			   catch(Exception ex)
			   {  System.out.println(ex);}
		}

		notPanel.add(notList);
		notPanel.setVisible(false);
		contentPane.add(notPanel);

		btnNoti = new JButton(notification.getNotAvtaleID().size() + "");
		btnNoti.setForeground(Color.BLACK);
		btnNoti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                int x = 29;
                int y = 21;
	            if (notification.getNotAvtaleID().size() > 0) {
                	notPanel.show(btnNoti, x, y);
					notPanel.setVisible(true);
	            }
			}
		});
		btnNoti.setBounds(6, 6, 45, 29);
		contentPane.add(btnNoti);

		JButton btnNewButton = new JButton("Forrige uke");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearButtons();
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
				clearButtons();
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
		table.setCellSelectionEnabled(true);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(true);
		
		for (int i = 1; i < 8; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(150);
			table.getColumnModel().getColumn(i).setCellRenderer(new EventCellRender());
			table.getColumnModel().getColumn(i).setCellEditor(new EventCellEditor());
		}
		
		
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable) e.getSource();
				clearButtons();
				model.getListModel().removeAllElements();
				model.getParticipantsModel().removeAllElements();
				
			}
		});

		table.setRowHeight(55);
		table.setRowSelectionAllowed(true);
	    table.setColumnSelectionAllowed(true);

		scrollPane.setViewportView(table);
		appointment = new JPanel();
		participants = new JPanel();
		appointment.setName("Informasjon");
		participants.setName("Deltakere");
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(819, 107, 246, 366);
		tabbedPane.add(appointment);


		list = new JList(model.getListModel());
		list.setBackground(null);
		list.setCellRenderer(new EventRender());
		appointment.add(list);
		list.setBounds(83, 89, 1, 1);
		tabbedPane.add(participants);

		JList list2 = new JList(model.getParticipantsModel());
		list2.setCellRenderer(new ParticipantsRenderer());
		participants.add(list2);
		list.setBounds(83, 89, 1, 1);
		contentPane.add(tabbedPane);
		
		statusBtnPanel = new JPanel();
		creatorBtnPanel = new JPanel();
		appointment.add(statusBtnPanel);
		appointment.add(creatorBtnPanel);

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

		JPanel combo = personsComboBox.getContent();
		combo.setBounds(40, 620, 190, 30);
		combo.setVisible(true);
		contentPane.add(combo);
		JButton btnLeggTil = new JButton("Ok");
		btnLeggTil.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearButtons();
				model.setOtherPersons(personsComboBox.getSelectedPersons());
				otherPersonsListModel.removeAllElements();
				try {
					model.clear();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				try {
					model.addOtherPersonsAppointments(model.getUsername(), Color.green);
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				for (int i = 0; i < personsComboBox.getSelectedPersons().size(); i++) {
					try {
						if(personsComboBox.getSelectedPersons().get(i).getUsername().equals(model.getUsername())){continue;}
						model.addOtherPersonsAppointments(personsComboBox.getSelectedPersons().get(i).getUsername(), personColors[i]);
						ArrayList<Object> personAndColor = new ArrayList<Object>();
						personAndColor.add(personsComboBox.getSelectedPersons().get(i));
						personAndColor.add(personColors[i]);
						otherPersonsListModel.addElement(personAndColor);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});

		btnLeggTil.setBounds(233, 622, 87, 29);
		contentPane.add(btnLeggTil);

		JLabel lblSeAndrePersoners = new JLabel("Se andre personers avtaler");
		lblSeAndrePersoners.setBounds(55, 597, 177, 16);
		contentPane.add(lblSeAndrePersoners);

		JList otherPersonsList = new JList(otherPersonsListModel);
		otherPersonsList.setCellRenderer(new OtherPersonRenderer());
		otherPersonsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		otherPersonsList.setVisibleRowCount(2);
		otherPersonsList.setBackground(new Color(238,238,238));
		otherPersonsList.setBounds(50, 650, 735, 50);
		contentPane.add(otherPersonsList);
		
		JLabel lblNewLabel = new JLabel(this.username);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel.setBackground(Color.GREEN);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBounds(56, 572, 117, 16);
		contentPane.add(lblNewLabel);
		
		lblDato = new JLabel();
		lblDato.setHorizontalAlignment(SwingConstants.CENTER);
		lblDato.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblDato.setBounds(325, 50, 196, 16);
		contentPane.add(lblDato);
	}
	
	public static void setStatusChangeButtons(final int appId, String status) {
		JButton btnAccept = new JButton("Godta");
		JButton btnDecline = new JButton("Avslå");
		JButton btnDeleteInvited = new JButton("Slett");
		creatorBtnPanel.add(btnDeleteInvited);
		statusBtnPanel.add(btnAccept);
		statusBtnPanel.add(btnDecline);
		
		btnAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.setStatus(appId, 1);
				try {
					model.setThisWeeksAppointments(0);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				model.fillSidePanel(appId);
			}
		});
		btnDecline.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.setStatus(appId, -1);
				notification.sendNotification(appId, username, username + " har avkreftet avtalen ");
				
				try {
					model.setThisWeeksAppointments(0);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				model.fillSidePanel(appId);
			}
		});
		btnDeleteInvited.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.deleteAttendence(appId);
				notification.sendNotification(appId, username, username + " har slettet avtalen ");
				try {
					model.setThisWeeksAppointments(0);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				clearButtons();
				model.fillSidePanel(0);
			}
		});
	}
	
	public static void setCreatorButtons(final int appID) {
		JButton btnChangeEvent = new JButton("Endre");
		creatorBtnPanel.add(btnChangeEvent);
		JButton btnDeleteCreator = new JButton("Slett");
		creatorBtnPanel.add(btnDeleteCreator);
		
		btnChangeEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {

					public void run() {
						try {
							JFrame editFrame = new EditEvent(getUsername(), appID);
							editFrame.setVisible(true);
							editFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnDeleteCreator.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.deleteAppointment(appID);
				try {
					model.setThisWeeksAppointments(0);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				model.fillSidePanel(0);
			}
		});
	}
	
	public static void clearButtons() {
		statusBtnPanel.removeAll();
		creatorBtnPanel.removeAll();
	}

	class update extends TimerTask {
	    public void run() {
	    	try {
				model.setThisWeeksAppointments(0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			notification.setValues(username);
			notPanel.removeAll();
			notList = new JList(notification.getNotMessages().toArray());
			notList.addListSelectionListener(MainPanel.this);
			notPanel.add(notList);
			btnNoti.setText(notification.getNotAvtaleID().size() + "");	    
	    }
	 }

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		notPanel.setVisible(false);
        CalendarModel.fillSidePanel(notification.getNotAvtaleID().get(notList.getSelectedIndex()));
        notification.removeNoti(notList.getSelectedIndex());
        notPanel.remove(notList);
        notList = new JList(notification.getNotMessages().toArray());
        notPanel.add(notList);
        notList.addListSelectionListener(this);

        btnNoti.setText(notification.getNotAvtaleID().size() + "");
	}
}
