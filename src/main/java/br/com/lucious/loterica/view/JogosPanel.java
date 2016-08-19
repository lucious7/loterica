package br.com.lucious.loterica.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import br.com.lucious.loterica.ctrl.JogosController;
import br.com.lucious.loterica.model.Grupo;
import br.com.lucious.loterica.model.Jogo;
import br.com.lucious.loterica.model.JogoTableModel;
import br.com.lucious.loterica.view.components.JNumericTextField;
import net.miginfocom.swing.MigLayout;

public class JogosPanel extends AbstractPanel {

	private static final long serialVersionUID = 6607039687864190073L;
	private JogosController controller;
	private JTextField nomeJogadorField;
	private ArrayList<JNumericTextField> listaDezenas;
	private JTable jogos;
	private JComboBox<Grupo> grupoCombo;

	private Jogo selected;
	private JButton botaoGerarPdf;

	public JogosPanel(JogosController ctrl) {
		super(new BorderLayout());
		controller = ctrl;

		JPanel novoJogoPanel = new JPanel(new MigLayout());

		JLabel grupoLabel = new JLabel("Grupo:");
		Vector<Grupo> grupos = controller.listarGrupos();
		grupoCombo = new JComboBox<Grupo>(grupos);
		grupoCombo.setSelectedIndex(-1);
		grupoCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					Grupo selected = (Grupo) e.getItem();
					carregarJogos(selected);
					botaoGerarPdf.setEnabled(selected != null);
				}
			}
		});
		novoJogoPanel.add(grupoLabel);
		novoJogoPanel.add(grupoCombo);

		botaoGerarPdf = new JButton("Gerar PDF");
		botaoGerarPdf.setEnabled(false);
		botaoGerarPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Grupo grupo = (Grupo) grupoCombo.getSelectedItem();
				if (grupo != null) {
					controller.gerarPDF(grupo);
				} else {
					exibirAviso("Escolha um grupo.");
				}
			}
		});
		novoJogoPanel.add(botaoGerarPdf, "wrap, span");

		JLabel nomeJogadorLabel = new JLabel("Nome:");
		nomeJogadorField = new JTextField();
		nomeJogadorField.setColumns(100);

		novoJogoPanel.add(nomeJogadorLabel);
		novoJogoPanel.add(nomeJogadorField, "wrap, span, grow");

		novoJogoPanel.add(new JLabel("Jogo:"));
		JPanel dezenasBand = new JPanel(new GridLayout(1, 10));
		listaDezenas = new ArrayList<JNumericTextField>();
		for (int j = 0; j < 10; j++) {
			JNumericTextField dezena = new JNumericTextField(10);
			listaDezenas.add(dezena);
			dezenasBand.add(dezena);
		}
		novoJogoPanel.add(dezenasBand, "wrap, span");

		JButton botaoLimpar = getBotaoLimpar();
		JButton botaoSalvar = getBotaoSalvar();

		novoJogoPanel.add(botaoLimpar);
		novoJogoPanel.add(botaoSalvar, "wrap, span, align right");
		add(novoJogoPanel, BorderLayout.NORTH);

		jogos = getTabela(ctrl.getTableModel());
		jogos.getColumnModel().getColumn(11).setMinWidth(70);
		jogos.getColumnModel().getColumn(11).setPreferredWidth(70);
		JScrollPane scroll = new JScrollPane(jogos);

		add(scroll, BorderLayout.CENTER);
	}

	protected void carregarJogos(Grupo grupo) {
		controller.updateTableModel(grupo);
	}

	@Override
	protected void limparForm() {
		limparForm(true);
	}

	protected void limparForm(boolean limparTudo) {
		if (limparTudo) {
			grupoCombo.setSelectedIndex(-1);
			controller.updateTableModel(null);
		}
		selected = null;

		nomeJogadorField.setText("");

		for (int i = 0; i < listaDezenas.size(); i++) {
			listaDezenas.get(i).setText("");
		}
	}

	@Override
	protected void removerRegistro(int row) {
		controller.removerJogo(row);
	}

	@Override
	protected void preencherForm(int row) {
		selected = ((JogoTableModel) jogos.getModel()).getRow(row);
		grupoCombo.setSelectedItem(selected.getGrupo());
		nomeJogadorField.setText(selected.getNomeJogador());

		for (int i = 0; i < listaDezenas.size(); i++) {
			Integer dezena = selected.getDezenas().get(i);
			listaDezenas.get(i).setText(dezena.toString());
		}
	}

	@Override
	protected void salvar() {
		Grupo grupo = (Grupo) grupoCombo.getSelectedItem();

		String nomeJogador = nomeJogadorField.getText();
		Vector<Integer> dezenas = new Vector<Integer>();

		for (JNumericTextField dezena : listaDezenas) {
			dezenas.add(dezena.getValue());
		}

		Jogo jogo = new Jogo(grupo, nomeJogador, dezenas);

		if (selected != null) {
			jogo.setId(selected.getId());
		}

		controller.salvarJogo(jogo);
		limparForm(false);
	}

	@Override
	protected void reload() {
		Grupo selected = (Grupo) grupoCombo.getSelectedItem();
		Vector<Grupo> grupos = controller.listarGrupos();
		grupoCombo.removeAllItems();
		for (Grupo grupo : grupos) {
			grupoCombo.addItem(grupo);
		}
		grupoCombo.setSelectedItem(selected);
		controller.updateTableModel(selected);
	}

	@Override
	protected boolean validar() {
		boolean valido = (!nomeJogadorField.getText().isEmpty());
		for (int i = 0; i < listaDezenas.size(); i++) {
			valido = valido && (!listaDezenas.get(i).getText().isEmpty());
		}
		if (!valido) {
			exibirAviso("Todos os campos devem ser preenchidos.");
		}
		return valido;
	}

}
