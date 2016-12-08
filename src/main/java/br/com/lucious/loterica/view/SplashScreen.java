package br.com.lucious.loterica.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class SplashScreen extends JWindow {

	private static final long serialVersionUID = -3135593311207248642L;

	public SplashScreen() {
//		super(f);
		URL url = getClass().getResource("/img/splashscreen.png");
		JLabel l = new JLabel(new ImageIcon(url));
		getContentPane().add(l, BorderLayout.CENTER);
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension labelSize = l.getPreferredSize();
		setLocation(screenSize.width / 2 - (labelSize.width / 2), 
				screenSize.height / 2 - (labelSize.height / 2));
		setVisible(true);
		screenSize = null;
		labelSize = null;
	}

	public void close() {
		setVisible(false);
		dispose();
	}
}
