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

	}

	@Override
	public void cancelCellEditing() {
		super.cancelCellEditing();

	}

	@Override
	public Component getCellEditorValue() {
		return null;
	}

	@Override
	public boolean isCellEditable(EventObject arg0) {
		return true;
	}

	@Override
	public void removeCellEditorListener(CellEditorListener arg0) {

	}

	@Override
	public boolean shouldSelectCell(EventObject arg0) {
		return false;
	}

	@Override
	public boolean stopCellEditing() {
		super.stopCellEditing();
	    return true;
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
		if (arg1 instanceof Component) {
				comp_ = (Component) arg1;
		       return (Component) arg1;
		   }
		   else {
		      return null;
		   }
	}

}
