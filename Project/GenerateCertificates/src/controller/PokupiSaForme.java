package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.SubjectInfo;
import view.Forma;

public class PokupiSaForme implements ActionListener {

	private JTextField jtf1, jtf2, jtf3, jtf4, jtf5, jtf6, jtf8;
	private JPasswordField jpf1, jpf2;
	private Forma forma;
	private String s;

	public PokupiSaForme(Forma forma, JTextField j1, JTextField j2,
			JTextField j3, JTextField j4, JTextField j5, JTextField j6,
			JPasswordField p1, JPasswordField p2, JTextField j8, String s) {
		this.jtf1 = j1;
		this.jtf2 = j2;
		this.jtf3 = j3;
		this.jtf4 = j4;
		this.jtf5 = j5;
		this.jtf6 = j6;
		this.jpf1 = p1;
		this.jpf2 = p2;
		this.jtf8 = j8;
		this.s = s;
		this.forma = forma;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		SubjectInfo subjectInfo = new SubjectInfo(jtf1.getText(),
				jtf2.getText(), jtf3.getText(), jtf4.getText(), jtf5.getText(),
				jtf6.getText(), jpf1.getPassword(), jtf8.getText());

		GenerateCert gc = new GenerateCert();

		if (s.equals("Final")) {
			gc.signedFinalCert(subjectInfo);
		} else if (jtf8.getText().isEmpty()) {
			gc.selfSignedCert(subjectInfo);
		} else {
			gc.signedCert(subjectInfo);
		}

		forma.setVisible(false);

		/*JOptionPane msg = new JOptionPane(
				"<html>Certificate<br>succesfully generated.</html>",
				JOptionPane.OK_OPTION);
		
		  final JDialog dlg = msg.createDialog("Message");
		  dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		    new Thread(new Runnable() {
		      @Override
		      public void run() {
		        try {
		          Thread.sleep(2500);
		        } catch (InterruptedException e) {
		          e.printStackTrace();
		        }
		        dlg.setVisible(false);
		      }
		    }).start();
		    dlg.setVisible(true);*/
		
		
		
	

	}

}
