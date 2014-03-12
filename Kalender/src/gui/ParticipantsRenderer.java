package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Label;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ParticipantsRenderer implements ListCellRenderer {
	private ImageIcon acceptedIcon = new ImageIcon("img/bullet_green.png");
	private ImageIcon pendingIcon = new ImageIcon("img/bullet_yellow.png");
	private ImageIcon rejectedIcon = new ImageIcon("img/bullet_red.png");

	@Override
	public Component getListCellRendererComponent(JList arg0, Object arg1,
			int arg2, boolean arg3, boolean arg4) {
		// TODO Auto-generated method stub
		ArrayList liste = (ArrayList) arg1;
		JLabel l = new JLabel();
		l.setBackground(new Color(238, 238, 238));
		l.setOpaque(true);
		l.setText(((String) liste.get(0)));
		Integer status = ((Integer) liste.get(1));
		if (status == 0) {
			l.setIcon(pendingIcon);
		}
		else if (status == -1) {
			l.setIcon(rejectedIcon);
		}
		else{
			l.setIcon(acceptedIcon);
		}
		
		return l;

	}

}
