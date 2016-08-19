package br.com.lucious.loterica.ctrl;

import java.util.Vector;

import javax.swing.table.TableModel;

import br.com.lucious.loterica.dao.LotericaDao;
import br.com.lucious.loterica.model.Grupo;
import br.com.lucious.loterica.model.GrupoTableModel;

public class GruposController {

	private LotericaDao dao;
	private GrupoTableModel tableModel;

	public GruposController() {
		dao = new LotericaDao();
	}

	public Vector<Grupo> listarGrupos() {
		return dao.listarGrupos();
	}

	public TableModel createTableModel() {
		tableModel = new GrupoTableModel(listarGrupos());
		return tableModel;
	}

	public TableModel getTableModel() {
		return tableModel;
	}

	public void removerGrupo(int index) {
		Grupo grupo = tableModel.getRow(index);
		dao.removerGrupo(grupo.getId());
		tableModel.removeRow(index);
	}

	public void salvarGrupo(Grupo grupo) {
		if (grupo.getId() != null) {
			dao.alterarGrupo(grupo);
			tableModel.updateRow(grupo);
		} else {
			dao.adicionarGrupo(grupo);
			tableModel.addRow(grupo);
		}
	}

}
