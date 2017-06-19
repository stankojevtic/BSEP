package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Forma;

public class NewKeyPair implements ActionListener{
	
	
	private String s;
	public NewKeyPair(String s) {
		this.s = s;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			Forma f = new Forma(s);
			f.setVisible(true);
		
	}
	
}
