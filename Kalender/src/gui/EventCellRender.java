package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.ScrollPane;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class EventCellRender implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		  if (arg1 != null) {
			  if (arg1 instanceof JPanel) {
				  ((JPanel) arg1).setBorder(new EmptyBorder(-5,0,0,0));
				  if (((JPanel) arg1).getComponentCount() > 6){
					  //do something
				  }
			  }
		      return (Component) arg1;
		   }

		   else {
		       return null;
		   }
	}

}
