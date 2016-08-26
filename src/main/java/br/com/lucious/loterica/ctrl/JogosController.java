package br.com.lucious.loterica.ctrl;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.table.TableModel;

import br.com.lucious.loterica.dao.LotericaDao;
import br.com.lucious.loterica.model.Grupo;
import br.com.lucious.loterica.model.Jogo;
import br.com.lucious.loterica.model.JogoTableModel;
import br.com.lucious.loterica.pdf.PdfCreator;

public class JogosController {
	
	private LotericaDao dao;
	private JogoTableModel tableModel;
	
	public JogosController() {
		dao = new LotericaDao();
		tableModel = new JogoTableModel(new Vector<Jogo>());
	}

	public void salvarJogo(Jogo jogo) {
		if (jogo.getId() != null) {
			dao.alterarJogo(jogo);
			tableModel.updateRow(jogo);
		} else {
			dao.adicionarJogo(jogo);
			tableModel.addRow(jogo);
		}
	}

	public Vector<Jogo> listarJogos(Grupo grupo) {
		return dao.listarJogosPorGrupo(grupo.getId());
	}

	public void updateTableModel(Grupo grupo) {
		if(grupo != null){
			tableModel.setList(listarJogos(grupo));
		}else{
			tableModel.setList(new Vector<Jogo>());
		}
		tableModel.fireTableDataChanged();
	}

	public TableModel getTableModel() {
		return tableModel;
	}

	public void removerJogo(int index) {
		Jogo jogo = tableModel.getRow(index);
		dao.removerJogo(jogo.getId());
		tableModel.removeRow(index);
	}

	public Vector<Grupo> listarGrupos() {
		return dao.listarGrupos();
	}

	public void gerarPDF(Grupo grupo) {
		PdfCreator.create(grupo, listarJogos(grupo));
		try {
			Desktop.getDesktop().open(new File(PdfCreator.filename(grupo)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
