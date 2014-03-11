package gui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class EventCellRender implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		  if (arg1 != null) {
		       return (Component) arg1;
		   }
		   else {
		       return null;
		   }
	}

}
