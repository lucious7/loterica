package br.com.lucious.loterica.model;

import java.util.Vector;

public class SorteioTableModel extends GenericTableModel<Sorteio> {

	private static final long serialVersionUID = -6345381367367747573L;

	public SorteioTableModel(Vector<Sorteio> list) {
		super(list);
	}

	private final String[] colunas = { "Número", "1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", "9a", "10a", "" };

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Sorteio entity = null;
		entity = list.get(rowIndex);
		if(columnIndex == 0 ){
			return entity.getNumero();
		}else if (columnIndex >= 1 && columnIndex <= 10) {
			return entity.getDezenas().get(columnIndex - 1);
		} else
			return "";
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		Sorteio entity = null;
		entity = list.get(row);
		if(col == 0){
			entity.setNumero((Integer) value);
		}else if (col >= 1 && col <= 10) {
			entity.getDezenas().set(col - 1, (Integer) value);
		}
		fireTableCellUpdated(row, col);
	}

	@Override
	protected String[] getHeaderList() {
		return colunas;
	}

}
