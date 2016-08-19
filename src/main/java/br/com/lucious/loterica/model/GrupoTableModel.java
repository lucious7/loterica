package br.com.lucious.loterica.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Vector;

public class GrupoTableModel extends GenericTableModel<Grupo> {

	private static final long serialVersionUID = 2207463935337100480L;
	private String[] headerList = { "Mês/Ano", "Data de Início", "Sorteio Inicial", "Sorteio Final", "" };

	public GrupoTableModel(Vector<Grupo> list) {
		super(list);
	}

	@Override
	public Object getValueAt(int row, int column) {
		Grupo entity = null;
		entity = list.get(row);
		switch (column) {
		case 0:
			return DateTimeFormatter.ofPattern("MMMM", Locale.forLanguageTag("pt"))
					.format(entity.getMes()) + "/" + entity.getAno();
		case 1:
			return DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("pt"))
					.format(entity.getDataInicio());
		case 2:
			return entity.getSorteioInicial();
		case 3:
			return entity.getSorteioFinal();
		default:
			return "";
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		Grupo entity = null;
		entity = list.get(row);
		switch (col) {
		case 0:
			entity.setMes((Month) value);
			break;
		case 1:
			entity.setDataInicio((LocalDate) value);
			break;
		case 2:
			entity.setSorteioInicial((Integer) value);
			break;
		case 3:
			entity.setSorteioFinal((Integer) value);
		default:
			break;

		}
		fireTableCellUpdated(row, col);
	}

	@Override
	protected String[] getHeaderList() {
		return headerList;
	}

}
