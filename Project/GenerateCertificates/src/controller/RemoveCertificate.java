package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.RemoveForma;

public class RemoveCertificate implements ActionListener{
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		RemoveForma rf = new RemoveForma();
		rf.setVisible(true);
	}

}
