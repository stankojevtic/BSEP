package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import view.KeyStorePassView;


public class OpenKeyStore implements ActionListener {

	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		

        JFileChooser jfc = new JFileChooser();
		
		
		
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			
			KeyStorePassView kspw = new KeyStorePassView(jfc.getSelectedFile().toString());
			
			kspw.setVisible(true);
			
			/*MainWindow.getInstance().getKeyStoreWriter().loadKeyStore(jfc.getSelectedFile().toString(),array1);
			MainWindow.getInstance().getKeyStoreWriter().setFileName(jfc.getSelectedFile().toString());*/
		}
		
		
	
		
		
	}

}
