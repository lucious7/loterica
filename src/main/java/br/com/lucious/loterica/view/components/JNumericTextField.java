package br.com.lucious.loterica.view.components;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTextField;

public class JNumericTextField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -302886798935644379L;

	private final List<Integer> VALID_KEY_CODES = Arrays.asList(KeyEvent.VK_LEFT, KeyEvent.VK_KP_LEFT,
			KeyEvent.VK_RIGHT, KeyEvent.VK_KP_RIGHT, KeyEvent.VK_BACK_SPACE);

	public JNumericTextField(int columns) {
		super(columns);
		setHorizontalAlignment(JTextField.CENTER);
	}

	@Override
	public void processKeyEvent(KeyEvent ev) {
		if (Character.isDigit(ev.getKeyChar()) || VALID_KEY_CODES.contains(ev.getKeyCode())) {
			super.processKeyEvent(ev);
		}
	}

	public Integer getValue() {
		String sValue = getText();
		if (sValue.trim().equals("")) {
			return 0;
		} else
			return Integer.valueOf(sValue);
	}

	public void setValue(Integer value) {
		setText(value + "");
	}
}
