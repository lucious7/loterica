package br.com.lucious.loterica.model;

import java.util.Vector;

public class ResultadoTableModel extends GenericTableModel<Resultado> {

	private static final long serialVersionUID = -3432929942820168216L;

	public ResultadoTableModel(Vector<Resultado> list) {
		super(list);
	}

	private final String[] colunas = { "Jogador", "1a", "2a", "3a", "4a", "5a", "6a", "7a", "8a", "9a", "10a", "Acertos" };

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Resultado entity = null;
		entity = list.get(rowIndex);
		if (columnIndex == 0) {
			return entity.getJogo().getNomeJogador();
		} else if (columnIndex > 0 && columnIndex < 11) {
			return entity.getJogo().getDezenas().get(columnIndex - 1);
		}else if(columnIndex == 11 ){
			return entity.getAcertos();
		} else return "";
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		Resultado entity = null;
		entity = list.get(row);
		if (col == 0) {
			entity.getJogo().setNomeJogador((String) value);
		} else if (col >= 1 && col <= 10) {
			entity.getJogo().getDezenas().set(col - 1, (Integer) value);
		}else if(col == 11){
			entity.setAcertos((Integer)value);
		}
		fireTableCellUpdated(row, col);
	}

	@Override
	protected String[] getHeaderList() {
		return colunas;
	}

}
