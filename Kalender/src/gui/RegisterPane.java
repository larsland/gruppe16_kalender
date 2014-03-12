package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import calendar.App;

public class RegisterPane extends JFrame {
	private JTextField etUsername;
	private JTextField etName;
	private JPasswordField etPassword;
	private JPasswordField etRepeatPassword;
	private static App app = new App();
	private JButton btnLogIn;
	private JLabel lblErrorMessage;
	
	public RegisterPane() {
		getContentPane().setLayout(null);
		setBounds(100, 100, 318, 240);
		setTitle("Register user");
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(48, 16, 75, 16);
		getContentPane().add(lblUsername);
		
		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setBounds(67, 45, 56, 16);
		getContentPane().add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(48, 77, 81, 16);
		getContentPane().add(lblPassword);
		
		JLabel lblRepeatPassword = new JLabel("Repeat password:");
		lblRepeatPassword.setBounds(12, 109, 111, 16);
		getContentPane().add(lblRepeatPassword);
		
		etUsername = new JTextField();
		etUsername.setBounds(120, 13, 116, 22);
		getContentPane().add(etUsername);
		etUsername.setColumns(10);
		
		etName = new JTextField();
		etName.setBounds(120, 45, 116, 22);
		getContentPane().add(etName);
		etName.setColumns(10);
		
		etPassword = new JPasswordField();
		etPassword.setBounds(120, 74, 116, 22);
		getContentPane().add(etPassword);
		etPassword.setColumns(10);
		
		etRepeatPassword = new JPasswordField();
		etRepeatPassword.setBounds(120, 106, 116, 22);
		getContentPane().add(etRepeatPassword);
		etRepeatPassword.setColumns(10);
		
		lblErrorMessage = new JLabel("Error message");
		lblErrorMessage.setVisible(false);
		lblErrorMessage.setBounds(34, 170, 240, 16);
		getContentPane().add(lblErrorMessage);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(67, 138, 97, 25);
		getContentPane().add(btnRegister);
		
		btnLogIn = new JButton("Log inn");
		btnLogIn.setBounds(174, 138, 97, 25);
		getContentPane().add(btnLogIn);
		
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String username = etUsername.getText().toString();
				String name = etName.getText().toString();
				String password = etPassword.getText().toString();
				String repeatPassword = etRepeatPassword.getText().toString();
				if (!password.equals(repeatPassword)) {
					JOptionPane.showMessageDialog(getContentPane(), "Passord matcher ikke");
				}
				else if (password.length() < 6) {
					JOptionPane.showMessageDialog(getContentPane(), "Passord mŒ v¾re minst 6 bokstaver");
				}
				else {
					try {
						app.registerUser(username, name, password);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		btnLogIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				app.goToLoginn();
			}
		});
	}
}