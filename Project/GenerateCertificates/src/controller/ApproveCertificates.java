package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.CertificatesToApproveView;

public class ApproveCertificates implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		CertificatesToApproveView.getInstance().setVisible(true);
		CertificatesToApproveView.getInstance().tablePopulate();
		CertificatesToApproveView.getInstance().repaint();
		CertificatesToApproveView.getInstance().invalidate();
		CertificatesToApproveView.getInstance().revalidate();

	}

}
