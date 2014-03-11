package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import calendar.CalendarModel;
import calendar.App;

public class EventBox extends JButton implements ActionListener {
	
	public EventBox(String s) {
		super(s);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ((e != null) && (e.getSource() == this)) {
		    JOptionPane.showMessageDialog(null, getText() + " was pressed!");
		 }
	}

}