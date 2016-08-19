package br.com.lucious.loterica.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import br.com.lucious.loterica.ctrl.SorteiosController;
import br.com.lucious.loterica.model.Sorteio;
import br.com.lucious.loterica.model.SorteioTableModel;
import br.com.lucious.loterica.view.components.JNumericTextField;
import net.miginfocom.swing.MigLayout;

public class SorteiosPanel extends AbstractPanel {

	private static final long serialVersionUID = 4762078014586297092L;

	private ArrayList<JNumericTextField> resultadoField;
	private JNumericTextField numeroSorteio;
	private JTable sorteios;

	private SorteiosController controller;

	private Sorteio selected;

	public SorteiosPanel(SorteiosController ctrl) {
		super(new BorderLayout());
		this.controller = ctrl;

		JPanel formPanel = new JPanel(new MigLayout());

		formPanel.add(new JLabel("Número do Sorteio: "));
		numeroSorteio = new JNumericTextField(40);
		formPanel.add(numeroSorteio);
		formPanel.add(Box.createHorizontalStrut(400), "wrap, span");

		resultadoField = new ArrayList<JNumericTextField>();
		JPanel dezenasBand = new JPanel(new GridLayout(1, 10));
		for (int i = 0; i < 10; i++) {
			JNumericTextField dezena = new JNumericTextField(5);
			dezena.setFont(new Font("SansSerif", Font.BOLD, 20));

			resultadoField.add(dezena);
			dezenasBand.add(dezena);
		}
		formPanel.add(dezenasBand, "wrap, span");

		JButton botaoLimpar = getBotaoLimpar();
		JButton botaoSalvar = getBotaoSalvar();

		formPanel.add(botaoLimpar);
		formPanel.add(botaoSalvar, "wrap, span, align right");
		add(formPanel, BorderLayout.NORTH);

		sorteios = getTabela(ctrl.getTableModel());
		sorteios.getColumnModel().getColumn(0).setMinWidth(70);
		sorteios.getColumnModel().getColumn(0).setPreferredWidth(70);
		sorteios.getColumnModel().getColumn(11).setMinWidth(70);
		sorteios.getColumnModel().getColumn(11).setPreferredWidth(70);
		JScrollPane scroll = new JScrollPane(sorteios);

		add(scroll, BorderLayout.CENTER);
	}

	@Override
	protected void salvar() {
		Sorteio sorteio = new Sorteio();

		Vector<Integer> dezenas = new Vector<Integer>();

		for (JNumericTextField dezena : resultadoField) {
			dezenas.add(dezena.getValue());
		}

		sorteio.setNumero(numeroSorteio.getValue());
		sorteio.setDezenas(dezenas);

		if (selected != null) {
			sorteio.setId(selected.getId());
		}

		controller.salvarSorteio(sorteio);
		limparForm();
	}

	@Override
	protected void preencherForm(int row) {
		selected = ((SorteioTableModel) sorteios.getModel()).getRow(row);

		numeroSorteio.setValue(selected.getNumero());
		for (int i = 0; i < resultadoField.size(); i++) {
			resultadoField.get(i).setValue(selected.getDezenas().get(i));
		}
	}

	@Override
	protected void limparForm() {
		selected = null;
		numeroSorteio.setText("");
		for (int i = 0; i < resultadoField.size(); i++) {
			resultadoField.get(i).setText("");
		}
	}

	@Override
	protected void removerRegistro(int row) {
		controller.removerSorteio(row);
	}

	@Override
	protected void reload() {
	}

	@Override
	protected boolean validar() {
		boolean valido = (!numeroSorteio.getText().isEmpty());
		for (int i = 0; i < resultadoField.size(); i++) {
			valido = valido && (!resultadoField.get(i).getText().isEmpty());
		}
		if(!valido){
			exibirAviso("Todos os campos devem ser preenchidos.");
		}
		return valido;
	}

}
