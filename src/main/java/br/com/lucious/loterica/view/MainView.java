package br.com.lucious.loterica.view;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.lucious.loterica.ctrl.GruposController;
import br.com.lucious.loterica.ctrl.JogosController;
import br.com.lucious.loterica.ctrl.ResultadosController;
import br.com.lucious.loterica.ctrl.SorteiosController;

public class MainView extends JFrame {

	private static final long serialVersionUID = -4419748004023493799L;

	private JTabbedPane abas;

	private JPanel gruposPanel;
	private JPanel jogosPanel;
	private JPanel sorteiosPanel;

	public MainView() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(650, 600);
		setTitle("Quina Mensal Entre Amigos");
		URL url = getClass().getResource("/img/quina.jpg");
		ImageIcon icon = new ImageIcon(url);
		setIconImage(icon.getImage());
		setLocationRelativeTo(null);
		initComponents();
	}

	private void initComponents() {
		abas = new JTabbedPane();

		gruposPanel = new GruposPanel(new GruposController());
		abas.add("Grupos", gruposPanel);

		jogosPanel = new JogosPanel(new JogosController());
		abas.add("Jogos", jogosPanel);

		sorteiosPanel = new SorteiosPanel(new SorteiosController());
		abas.add("Sorteios", sorteiosPanel);

		ResultadosPanel resultadosPanel = new ResultadosPanel(new ResultadosController());
		abas.add("Resultados", resultadosPanel);

		abas.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int activePane = abas.getSelectedIndex();
				if (activePane == 1) {
					((AbstractPanel) jogosPanel).reload();
				} else if (activePane == 3) {
					resultadosPanel.reload();
				}
			}
		});

		add(abas);
	}

}
