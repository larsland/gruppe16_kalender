package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;

import calendar.App;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class LogInPanel extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private App app = new App();


	/**
	 * Create the frame.
	 */
	public LogInPanel() {
		setTitle("Logg inn");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 318, 180);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(92, 36, 134, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(92, 65, 134, 28);
		contentPane.add(passwordField);
		
		JLabel lblBrukernavn = new JLabel("Brukernavn:");
		lblBrukernavn.setBounds(6, 42, 74, 16);
		contentPane.add(lblBrukernavn);
		
		JLabel lblPassord = new JLabel("Passord:");
		lblPassord.setBounds(28, 71, 61, 16);
		contentPane.add(lblPassord);
		
		JButton btnLoggInn = new JButton("Logg inn");
		btnLoggInn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					app.validateUser(textField.getText(), passwordField.getText());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		passwordField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					app.validateUser(textField.getText(), passwordField.getText());
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
			}
			
		});
		btnLoggInn.setBounds(181, 105, 117, 29);
		contentPane.add(btnLoggInn);
	}
}