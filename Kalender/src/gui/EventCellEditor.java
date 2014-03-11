package gui;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

public class EventCellEditor extends AbstractCellEditor implements TableCellEditor {
	 Component	comp_	= null;
	@Override
	public void addCellEditorListener(CellEditorListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelCellEditing() {
		// TODO Auto-generated method stub
		  super.cancelCellEditing();

	}

	@Override
	public Component getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCellEditable(EventObject arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void removeCellEditorListener(CellEditorListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean shouldSelectCell(EventObject arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stopCellEditing() {
		// TODO Auto-generated method stub
		super.stopCellEditing();
	      return true;
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		if (arg1 instanceof Component) {
				comp_ = (Component) arg1;
		       return (Component) arg1;
		   }
		   else {
		      return null;
		   }
	}

}
