package br.com.lucious.loterica.view.components;

import java.awt.Color;
import java.awt.Component;
import java.util.HashSet;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

public class ResultadoTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 3160023623155713181L;

	private HashSet<Integer> dezenasSorteadas;
	private Color BGCOLOR_DEFAULT = UIManager.getColor("Table.background");
	private Color BGCOLOR_SELECTED = UIManager.getColor("Table.selectionBackground");
	private Color BGCOLOR_SORTEADO = Color.GREEN;
	private Color FGCOLOR_DEFAULT = UIManager.getColor("Table.foreground");
	private Color FGCOLOR_SELECTED = UIManager.getColor("Table.selectionForeground");
	private Color FGCOLOR_SORTEADO = Color.BLACK;

	public ResultadoTableCellRenderer() {
		dezenasSorteadas = new HashSet<Integer>();
	}

	public ResultadoTableCellRenderer(HashSet<Integer> dezenas) {
		dezenasSorteadas = dezenas;
	}

	public void setDezenasSorteadas(HashSet<Integer> dezenas) {
		dezenasSorteadas = dezenas;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int col) {

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

		if (isSelected) {
			c.setBackground(BGCOLOR_SELECTED);
			c.setForeground(FGCOLOR_SELECTED);
		} else {
			c.setBackground(BGCOLOR_DEFAULT);
			c.setForeground(FGCOLOR_DEFAULT);
		}

		if (col > 0 && col < 11) {
			Integer valor = (Integer) table.getModel().getValueAt(row, col);

			if (dezenasSorteadas.contains(valor)) {
				c.setBackground(BGCOLOR_SORTEADO);
				c.setForeground(FGCOLOR_SORTEADO);
			}
		}

		return c;
	}

}
