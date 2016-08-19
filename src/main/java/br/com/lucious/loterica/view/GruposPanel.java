package br.com.lucious.loterica.view;

import java.awt.BorderLayout;
import java.time.Month;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.github.lgooddatepicker.datepicker.DatePicker;
import com.github.lgooddatepicker.datepicker.DatePickerSettings;

import br.com.lucious.loterica.ctrl.GruposController;
import br.com.lucious.loterica.model.Grupo;
import br.com.lucious.loterica.model.GrupoTableModel;
import br.com.lucious.loterica.view.components.JNumericTextField;
import net.miginfocom.swing.MigLayout;

public class GruposPanel extends AbstractPanel {

	private static final long serialVersionUID = 6607039687864190073L;
	private Month[] meses = Month.values();
	private GruposController controller;
	private JTable grupos;
	private JNumericTextField sorteioInicialField;
	private JNumericTextField sorteioFinalField;
	private DatePicker dtInicioPicker;
	private JComboBox<Month> mesCombo;
	private JNumericTextField anoField;

	private Grupo selected;

	public GruposPanel(GruposController ctrl) {
		super(new BorderLayout());
		controller = ctrl;

		JPanel gruposPanel = new JPanel(new MigLayout());
		JLabel mesAnoLabel = new JLabel("Mês/Ano:");
		mesCombo = new JComboBox<Month>(meses);
		mesCombo.setSelectedIndex(-1);

		gruposPanel.add(mesAnoLabel);
		gruposPanel.add(mesCombo, "split 2");

		anoField = new JNumericTextField(10);

		gruposPanel.add(anoField);

		JLabel dtInicioLabel = new JLabel("Data de Início:");
		DatePickerSettings settings = new DatePickerSettings(new Locale("pt"));
		settings.setFormatForDatesCommonEra("dd MMM yyyy");
		settings.setFormatForDatesBeforeCommonEra("dd MMM uuuu");
		settings.setAllowKeyboardEditing(false);
		dtInicioPicker = new DatePicker(settings);

		gruposPanel.add(dtInicioLabel);
		gruposPanel.add(dtInicioPicker, "wrap, span, grow");

		JLabel sorteioInicialLabel = new JLabel("Sorteio Inicial:");
		sorteioInicialField = new JNumericTextField(50);

		gruposPanel.add(sorteioInicialLabel);
		gruposPanel.add(sorteioInicialField);

		JLabel sorteioFinalLabel = new JLabel("Sorteio Final:");
		sorteioFinalField = new JNumericTextField(50);

		gruposPanel.add(sorteioFinalLabel);
		gruposPanel.add(sorteioFinalField, "wrap, span, grow");

		JButton botaoLimpar = getBotaoLimpar();
		JButton botaoSalvar = getBotaoSalvar();

		gruposPanel.add(botaoLimpar, "span 2, align left");
		gruposPanel.add(botaoSalvar, "wrap, span, align right");
		add(gruposPanel, BorderLayout.NORTH);

		grupos = getTabela(ctrl.createTableModel());
		grupos.getColumnModel().getColumn(4).setMinWidth(70);
		grupos.getColumnModel().getColumn(4).setPreferredWidth(70);
		JScrollPane scroll = new JScrollPane(grupos);

		add(scroll, BorderLayout.CENTER);
	}

	@Override
	protected void preencherForm(int index) {
		Grupo row = ((GrupoTableModel) grupos.getModel()).getRow(index);
		this.selected = row;
		mesCombo.setSelectedItem(row.getMes());
		anoField.setValue(row.getAno());
		dtInicioPicker.setDate(row.getDataInicio());
		sorteioInicialField.setText(row.getSorteioInicial().toString());
		sorteioFinalField.setText(row.getSorteioFinal().toString());
	}

	@Override
	protected void limparForm() {
		this.selected = null;
		mesCombo.setSelectedIndex(-1);
		anoField.setText("");
		dtInicioPicker.setDate(null);
		sorteioInicialField.setText("");
		sorteioFinalField.setText("");
	}

	@Override
	protected void removerRegistro(int row) {
		controller.removerGrupo(row);
	}

	@Override
	protected void salvar() {
		Grupo novoGrupo = new Grupo(((Month) mesCombo.getSelectedItem()), anoField.getValue(), dtInicioPicker.getDate(),
				sorteioInicialField.getValue(), sorteioFinalField.getValue());
		if (this.selected != null) {
			novoGrupo.setId(this.selected.getId());
		}

		controller.salvarGrupo(novoGrupo);
		limparForm();
	}

	@Override
	protected boolean validar() {
		boolean valido = mesCombo.getSelectedIndex() > -1 && !anoField.getText().isEmpty() && dtInicioPicker.getDate() != null
				&& !sorteioInicialField.getText().isEmpty() && !sorteioFinalField.getText().isEmpty();

		if (!valido) {
			exibirAviso("Todos os campos desta tela são obrigatórios.");
		}
		return valido;
	}

	@Override
	protected void reload() {
		grupos = getTabela(controller.createTableModel());
	}

}
