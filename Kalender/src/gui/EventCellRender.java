package gui;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTable;
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
