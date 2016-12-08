package br.com.lucious.loterica;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import br.com.lucious.loterica.db.ConnectionManager;
import br.com.lucious.loterica.db.DbUtil;
import br.com.lucious.loterica.view.MainView;
import br.com.lucious.loterica.view.SplashScreen;

/**
 * 
 *
 */
public class App {

	public static void main(String[] args) {
		SplashScreen spScreen = new SplashScreen();
		
		MainView view = new MainView();
		view.setVisible(true);
		view.addWindowListener(getWindowListener());
		
		spScreen.close();
	}

	private static WindowListener getWindowListener() {
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					ConnectionManager.closeConnection();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void windowOpened(WindowEvent e) {
				try {
					DbUtil.initDB(ConnectionManager.getConnection());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		};
	}

}
