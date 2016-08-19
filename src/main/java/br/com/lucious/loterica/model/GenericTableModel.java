package br.com.lucious.loterica.model;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public abstract class GenericTableModel<T> extends AbstractTableModel {

	private static final long serialVersionUID = 2207463935337100480L;
	protected Vector<T> list;

	public GenericTableModel(Vector<T> list) {
		this.list = list;
	}
	
	public void setList(Vector<T> lista) {
		this.list = lista;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return getHeaderList().length;
	}

	// This method will be used to display the name of columns
	@Override
	public String getColumnName(int col) {
		return getHeaderList()[col];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	public void removeRow(int index) {
		list.remove(index);
		fireTableRowsDeleted(index, index);
	}

	public void addRow(T object) {
		int size = list.size();
		list.add(object);
		fireTableRowsInserted(size, size);
	}

	public void updateRow(T obj) {
		int index = list.indexOf(obj);
		if (index > -1) {
			list.remove(index);
			list.add(index, obj);
			fireTableRowsUpdated(index, index);
		}
	}

	public T getRow(int index) {
		return list.get(index);
	}

	abstract protected String[] getHeaderList();
}
