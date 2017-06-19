package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import view.CreateKeyStorePassView;
import view.MainWindow;

public class SaveAsKeyStore implements ActionListener {

	 char[] array1 = { 'a' };

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// KeyStoreWriter ksw = new KeyStoreWriter();
		// ksw.saveKeyStore("", array1);
		// ksw.loadKeyStore(null, array1);
		JFileChooser jfc = new JFileChooser();

		if (jfc.showSaveDialog(MainWindow.getInstance()) == JFileChooser.APPROVE_OPTION) {
			
			CreateKeyStorePassView kspw = new CreateKeyStorePassView(jfc.getSelectedFile().toString());
			
				kspw.setVisible(true);
			
			/*	MainWindow.getInstance().getKeyStoreWriter().saveKeyStore(jfc.getSelectedFile().toString(),array1);
				MainWindow.getInstance().getKeyStoreWriter().setFileName(jfc.getSelectedFile().toString());*/
			
			
			
			
			
			/*MainWindow
					.getInstance()
					.getKeyStoreWriter()
					.saveKeyStore(
							MainWindow.getInstance().getKeyStoreWriter()
									.getFileName(), array1);*/

		}

	}
}