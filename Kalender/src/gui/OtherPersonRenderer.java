package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import calendar.User;

public class OtherPersonRenderer implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList arg0, Object arg1,
			int arg2, boolean arg3, boolean arg4) {
		// TODO Auto-generated method stub
		if (arg1 instanceof ArrayList) {
			JLabel lbl = new JLabel(((User) ((ArrayList) arg1).get(0)).getName());
			Color color = (Color) ((ArrayList) arg1).get(1);
			lbl.setBorder(new EmptyBorder(3,3,3,3));
			lbl.setFont(new Font("Dialog", Font.BOLD, 16));
			lbl.setBackground((color));
			lbl.setForeground(new Color(255-color.getRed(), 255-color.getGreen(), 255-color.getBlue()));
			lbl.setOpaque(true);
			return lbl;
		}
	
		return null;
	}

}
