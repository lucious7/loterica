package br.com.lucious.loterica.ctrl;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.table.TableModel;

import br.com.lucious.loterica.dao.LotericaDao;
import br.com.lucious.loterica.model.Grupo;
import br.com.lucious.loterica.model.Jogo;
import br.com.lucious.loterica.model.Resultado;
import br.com.lucious.loterica.model.ResultadoTableModel;
import br.com.lucious.loterica.model.Sorteio;
import br.com.lucious.loterica.pdf.PdfCreator;

public class ResultadosController {

	private LotericaDao dao;
	private ResultadoTableModel tableModel;

	private HashSet<Integer> dezenasSorteadas;

	private Comparator<Resultado> comparator = new Comparator<Resultado>() {
		@Override
		public int compare(Resultado r1, Resultado r2) {
			return r2.getAcertos() - r1.getAcertos();
		}
	};

	public ResultadosController() {
		dao = new LotericaDao();
		tableModel = new ResultadoTableModel(new Vector<Resultado>());
	}

	public Vector<Jogo> listarJogos(Grupo grupo) {
		return dao.listarJogosPorGrupo(grupo.getId());
	}

	public TableModel getTableModel() {
		return tableModel;
	}

	public Vector<Grupo> listarGrupos() {
		return dao.listarGrupos();
	}

	private Vector<Sorteio> listarSorteios(Grupo grupo) {
		return dao.listarSorteiosPorGrupo(grupo);
	}

	public void updateTableModel(Grupo grupo) {
		if (grupo != null) {
			Vector<Resultado> resultados = verificarJogos(grupo);
			tableModel.setList(resultados);
		} else {
			tableModel.setList(new Vector<Resultado>());
		}
		tableModel.fireTableDataChanged();
	}

	@SuppressWarnings("unchecked")
	private Vector<Resultado> verificarJogos(Grupo grupo) {
		Vector<Jogo> listaJogos = listarJogos(grupo);
		Vector<Sorteio> listaSorteios = listarSorteios(grupo);
		Vector<Resultado> listaResultados = new Vector<Resultado>();
		dezenasSorteadas = new HashSet<Integer>();

		for (Sorteio sorteio : listaSorteios) {
			dezenasSorteadas.addAll(sorteio.getDezenas());
		}

		for (Jogo jogo : listaJogos) {
			Resultado resultado = new Resultado(jogo);
			HashSet<Integer> dezenas = (HashSet<Integer>) dezenasSorteadas.clone();
			dezenas.retainAll(jogo.getDezenas());
			resultado.setAcertos(dezenas.size());
			listaResultados.add(resultado);
		}

		Collections.sort(listaResultados, comparator);
		return listaResultados;
	}

	public HashSet<Integer> getDezenasSorteadas() {
		return dezenasSorteadas;
	}

	public void gerarPDF(Grupo grupo) {
		PdfCreator.create(dezenasSorteadas, grupo, verificarJogos(grupo));
		try {
			Desktop.getDesktop().open(new File(PdfCreator.filename(grupo)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
