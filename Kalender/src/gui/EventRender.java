package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jws.WebParam.Mode;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;

public class EventRender implements ListCellRenderer {

	final private ImageIcon acceptedIcon = new ImageIcon(getClass().getResource("/img/bullet_green.png"));
	final private ImageIcon pendingIcon = new ImageIcon(getClass().getResource("/img/bullet_yellow.png"));
	final private ImageIcon rejectedIcon = new ImageIcon(getClass().getResource("/img/bullet_red.png"));
	private JLabel l;
	private JButton b;
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		//getListCellRendererComponent(list, value, index, false, false);
		//ArrayList liste = (ArrayList) value;
		l = new JLabel((String) value);
		l.setBackground(new Color(238, 238, 238));
		l.setOpaque(true);
		if (index == 7) {
			if (((String) value).equals("1")) {
				l.setText("Du har opprettet denne avtalen");
			}
			else {
				l.setText("Noen andre har opprettet avtalen");
			}
		}
		if (index == 8) {
			if (((String) value).equals("1")) {
				l.setText("Du har godtatt");
				l.setIcon(acceptedIcon);
			}
			else if (((String) value).equals("-1")){
				l.setText("Du har avslått");
				l.setIcon(rejectedIcon);
			}
			else if (((String) value).equals("0")) {
				l.setText("Du har ikke svart");
				l.setIcon(pendingIcon);
			}
			else {
				l.setText(" ");
			}
		}
		
		//l.setText(((String) value));
		return l;
	}

}