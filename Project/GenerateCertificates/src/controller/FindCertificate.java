package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.PronadjiForma;

public class FindCertificate implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		PronadjiForma pf = new PronadjiForma();
		pf.setVisible(true);
	}

}
