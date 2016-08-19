package br.com.lucious.loterica.view;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import br.com.lucious.loterica.view.components.ButtonColumn;

@SuppressWarnings("serial")
public abstract class AbstractPanel extends JPanel {

	private JTable tabela;
	private JButton botaoLimpar;
	private JButton botaoSalvar;

	public AbstractPanel(LayoutManager layout) {
		super(layout);
	}

	protected JTable getTabela(TableModel model) {
		tabela = new JTable(model);
		tabela.setFillsViewportHeight(true);
		tabela.setDefaultEditor(Object.class, null);
		tabela.getColumnModel().getColumn(0).setMinWidth(250);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(250);

		tabela.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable table = (JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				if (me.getClickCount() == 2 && row != -1) {
					preencherForm(row);
				}
			}
		});

		Action remover = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int modelRow = Integer.valueOf(e.getActionCommand());
				if (confirmarExclusao()) {
					removerRegistro(modelRow);
				}
			}
		};
		ButtonColumn btnColumn = new ButtonColumn(tabela, remover, tabela.getColumnCount() - 1, "Excluir");
		btnColumn.setMnemonic(KeyEvent.VK_D);

		return tabela;
	}

	protected JButton getBotaoLimpar() {
		botaoLimpar = new JButton("Limpar");
		botaoLimpar.setBackground(Color.RED);
		botaoLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparForm();
			}
		});
		return botaoLimpar;
	}

	protected JButton getBotaoSalvar() {
		botaoSalvar = new JButton("Salvar");
		botaoSalvar.setBackground(Color.GREEN);
		botaoSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validar()){
					salvar();
				}
			}
		});
		return botaoSalvar;
	}

	protected abstract boolean validar();
	
	protected abstract void salvar();

	abstract protected void preencherForm(int row);

	abstract protected void limparForm();

	abstract protected void removerRegistro(int row);
	
	abstract protected void reload();

	private boolean confirmarExclusao() {
		int opcao = JOptionPane.showConfirmDialog(this, "Deseja mesmo excluir esse registro?", "Atenção",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
		return opcao == JOptionPane.YES_OPTION;
	}
	
	protected void exibirAviso(String msg) {
		JOptionPane.showConfirmDialog(this, msg, "Atenção",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null);
	}

}
