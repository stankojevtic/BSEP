package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;

import view.CreateKeyStorePassView;
import view.MainWindow;

public class SaveKeyStore implements ActionListener {

	// char[] array1 = { 'a' };

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// KeyStoreWriter ksw = new KeyStoreWriter();
		// ksw.saveKeyStore("", array1);
		// ksw.loadKeyStore(null, array1);
		
		
		
		if(MainWindow.getInstance().getKeyStoreWriter().getFileName() == null) {
			JFileChooser jfc = new JFileChooser();

			if (jfc.showSaveDialog(MainWindow.getInstance()) == JFileChooser.APPROVE_OPTION) {

				CreateKeyStorePassView kspw = new CreateKeyStorePassView(jfc.getSelectedFile().toString());
				
				kspw.setVisible(true);
				
				/*MainWindow.getInstance().getKeyStoreWriter().saveKeyStore(jfc.getSelectedFile().toString(),	MainWindow.getInstance().getProp().getProperty(fileName, stringValueOf););
				MainWindow.getInstance().getKeyStoreWriter().setFileName(jfc.getSelectedFile().toString());*/
				
				
				/*MainWindow
						.getInstance()
						.getKeyStoreWriter()
						.saveKeyStore(
								MainWindow.getInstance().getKeyStoreWriter()
										.getFileName(), array1);*/

			}
		}
		else {
			
			Path p = Paths.get(MainWindow.getInstance().getKeyStoreWriter()
					.getFileName());
			
			
			String fileName = p.getFileName().toString();
			
			/*System.out.println("fileName" + fileName);
			
			String stringValueOf = String.valueOf(MainWindow.getInstance().getProp().getProperty(fileName));
			
			System.out.println("StringValueOF" + stringValueOf);*/
			
			String pass = MainWindow.getInstance().getProp().getProperty(fileName);
		
			char[] charArray = pass.toCharArray();
			
			MainWindow
			.getInstance()
			.getKeyStoreWriter()
			.saveKeyStore(
					MainWindow.getInstance().getKeyStoreWriter()
							.getFileName(), charArray);
		}
		
		
		

	}
}
