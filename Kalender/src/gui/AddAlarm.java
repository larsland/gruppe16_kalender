package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.Border;
import calendar.CalendarModel;
import calendar.Database;
import java.awt.Font;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
public class AddAlarm extends JPanel {
	private JLabel lblInfo;
	private JButton btnAddAlarm;
	private JSpinner alarmTimer;
	private int minutes;
	private int appId;
	
	private Database db = new Database();
	private CalendarModel cm;
	
	public AddAlarm() throws SQLException {
		setLayout(null);
		lblInfo = new JLabel("Still inn alarm: ");
		lblInfo.setBounds(17, 11, 106, 15);
		add(lblInfo);
		
		btnAddAlarm = new JButton("Ok");
		btnAddAlarm.setFont(new Font("Dialog", Font.BOLD, 9));
		btnAddAlarm.setBounds(174, 5, 50, 30);
		btnAddAlarm.setPreferredSize(new Dimension(100, 30));
		btnAddAlarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				getTime();
			}		
		});
		add(btnAddAlarm);
		
		alarmTimer = new JSpinner();
		alarmTimer.setBounds(119, 5, 50, 30);
		alarmTimer.setPreferredSize(new Dimension(30, 30));
		add(alarmTimer);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		this.setBorder(border);
		this.setVisible(true);
	}
	public void getTime() {
		appId = cm.getSelectedAppId();
		minutes = (Integer) alarmTimer.getValue();
		Timestamp startTime = null;
		Date date = null;
		String user = null;
		try {
			ResultSet rs = db.getAppointmentInfo(appId);
			System.out.println(rs + "");
			if (rs.next()) {
				startTime = rs.getTimestamp("Starttid");
				date = rs.getDate("Dato");
				user = rs.getString("Opprettet_av");
			}
			Timestamp ts = new Timestamp(startTime.getYear(), startTime.getMonth(), startTime.getDate(), startTime.getHours(), startTime.getMinutes() - minutes, startTime.getSeconds(), startTime.getNanos());
			db.sendAlarmToOne(appId, user, "M¿te om" + minutes + "minutter!", ts);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}