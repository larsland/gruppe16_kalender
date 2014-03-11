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

public class EventBox extends JButton implements MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer appointmentId;

	public EventBox(int i) throws SQLException {
		addMouseListener(this);
		this.appointmentId = i;
		setText(appointmentId.toString());
		setOpaque(true);
		setFocusable(true);
		setRolloverEnabled(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		setText("faen");
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		setText("faen");
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}