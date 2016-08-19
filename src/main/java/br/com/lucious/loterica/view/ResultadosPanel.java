package br.com.lucious.loterica.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import br.com.lucious.loterica.ctrl.ResultadosController;
import br.com.lucious.loterica.model.Grupo;
import br.com.lucious.loterica.view.components.ResultadoTableCellRenderer;
import net.miginfocom.swing.MigLayout;

public class ResultadosPanel extends JPanel {

	private static final long serialVersionUID = 6607039687864190073L;
	private ResultadosController controller;
	private JTable jogos;
	private JComboBox<Grupo> grupoCombo;
	private JButton botaoGerarPdf;

	private ResultadoTableCellRenderer renderer;

	public ResultadosPanel(ResultadosController ctrl) {
		super(new BorderLayout());
		controller = ctrl;

		JPanel resultadosPanel = new JPanel(new MigLayout());

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

		resultadosPanel.add(grupoLabel);
		resultadosPanel.add(grupoCombo, "span 2");

		botaoGerarPdf = new JButton("Gerar PDF");
		botaoGerarPdf.setEnabled(false);
		botaoGerarPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Grupo selected = (Grupo) grupoCombo.getSelectedItem();
				if(selected != null){
					controller.gerarPDF(selected);
				}else {
					exibirAviso("Escolha um grupo.");
				}
				
			}
		});

		resultadosPanel.add(botaoGerarPdf, "wrap, span 3");

		JLabel acumuladoLabel = new JLabel("Acumulado:");
		JTextField acumuladoField = new JTextField(50);
		acumuladoField.setEnabled(false);

		resultadosPanel.add(acumuladoLabel);
		resultadosPanel.add(acumuladoField);

		JLabel valorMesLabel = new JLabel("Valor do Mês:");
		JTextField valorMesField = new JTextField(50);
		valorMesField.setEnabled(false);

		resultadosPanel.add(valorMesLabel);
		resultadosPanel.add(valorMesField);

		JLabel totalLabel = new JLabel("Total do Prêmio:");
		JTextField totalField = new JTextField(50);
		totalField.setEnabled(false);

		resultadosPanel.add(totalLabel);
		resultadosPanel.add(totalField);

		add(resultadosPanel, BorderLayout.NORTH);

		jogos = new JTable(ctrl.getTableModel());
		jogos.setFillsViewportHeight(true);
		jogos.setDefaultEditor(Object.class, null);
		jogos.getColumnModel().getColumn(0).setMinWidth(250);
		jogos.getColumnModel().getColumn(0).setPreferredWidth(250);
		jogos.getColumnModel().getColumn(11).setMinWidth(50);
		jogos.getColumnModel().getColumn(11).setPreferredWidth(50);
		JScrollPane scroll = new JScrollPane(jogos);

		add(scroll, BorderLayout.CENTER);

		renderer = new ResultadoTableCellRenderer();
		jogos.setDefaultRenderer(Object.class, renderer);
	}

	protected void carregarJogos(Grupo grupo) {
		controller.updateTableModel(grupo);
		renderer.setDezenasSorteadas(controller.getDezenasSorteadas());
	}

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
	
	private void exibirAviso(String msg) {
		JOptionPane.showConfirmDialog(this, msg, "Atenção",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null);
	}
}
