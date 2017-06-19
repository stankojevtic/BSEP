package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.RevokedView;

public class OcspRevoke implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		RevokedView rv = new RevokedView();
		rv.setVisible(true);
	}

}
