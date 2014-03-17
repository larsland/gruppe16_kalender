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

	private ImageIcon acceptedIcon = new ImageIcon("img/bullet_green.png");
	private ImageIcon pendingIcon = new ImageIcon("img/bullet_yellow.png");
	private ImageIcon rejectedIcon = new ImageIcon("img/bullet_red.png");
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
		if (index == 6) {
			if (((String) value).equals("1")) {
				l.setText("Du har opprettet denne avtalen");
			}
			else {
				l.setText("Noen andre har opprettet avtalen");
			}
		}
		if (index == 7) {
			if (((String) value).equals("1")) {
				l.setText("Du har godtatt");
				l.setIcon(acceptedIcon);
			}
			else if (((String) value).equals("-1")){
				l.setText("Du har avslått");
				l.setIcon(rejectedIcon);
			}
			else {
				l.setText("Du har ikke svart");
				l.setIcon(pendingIcon);
			}
		}
		
		//l.setText(((String) value));
		return l;
	}

}