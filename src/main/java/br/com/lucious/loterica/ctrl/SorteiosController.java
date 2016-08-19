package br.com.lucious.loterica.ctrl;

import java.util.Vector;

import javax.swing.table.TableModel;

import br.com.lucious.loterica.dao.LotericaDao;
import br.com.lucious.loterica.model.Sorteio;
import br.com.lucious.loterica.model.SorteioTableModel;

public class SorteiosController {
	
	private LotericaDao dao;
	private SorteioTableModel tableModel;
	
	
	public SorteiosController() {
		dao = new LotericaDao();
		tableModel = new SorteioTableModel(listarSorteios());
	}

	public void salvarSorteio(Sorteio sorteio) {
		if(sorteio.getId() == null){
			dao.adicionarSorteio(sorteio);
			tableModel.addRow(sorteio);
		}else{
			dao.alterarSorteio(sorteio);
			tableModel.updateRow(sorteio);
		}
	}

	public Vector<Sorteio> listarSorteios() {
		return dao.listarSorteios();
	}
	
	public void removerSorteio(Integer index){
		Sorteio sorteio = tableModel.getRow(index);
		dao.removerSorteio(sorteio.getId());
		tableModel.removeRow(index);
	}

	public TableModel getTableModel() {
		return tableModel;
	}

}
