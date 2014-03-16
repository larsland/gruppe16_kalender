package gui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class OtherPersonRenderer implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList arg0, Object arg1,
			int arg2, boolean arg3, boolean arg4) {
		// TODO Auto-generated method stub
		if (arg1 instanceof ArrayList) {
			JLabel lbl = new JLabel((String) ((ArrayList) arg1).get(0));
			Color color = (Color) ((ArrayList) arg1).get(1);
			lbl.setBackground((color));
			lbl.setForeground(Color.white);
			lbl.setOpaque(true);
			return lbl;
		}
		return null;
	}

}
