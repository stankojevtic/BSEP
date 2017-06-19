package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import view.CreateKeyStorePassView;
import view.MainWindow;

public class NewKeyStore implements ActionListener{
	
	
	

	public NewKeyStore() {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//KeyStoreWriter ksw = new KeyStoreWriter();
		MainWindow.getInstance().getKeyStoreWriter().
						loadKeyStore(null, new char[0]);
		
	/*	MainWindow.getInstance().newKeyPair.setEnabled(true);
		
		MainWindow.getInstance().repaint();
		MainWindow.getInstance().invalidate();
		MainWindow.getInstance().revalidate();*/
		
		
	//	MainWindow.getInstance().setVisible(false);
		//MainWindow.getInstance().setVisible(true);
		
        JFileChooser jfc = new JFileChooser();
		
		
		
		if (jfc.showSaveDialog(MainWindow.getInstance()) == JFileChooser.APPROVE_OPTION) {
			
			CreateKeyStorePassView kspw = new CreateKeyStorePassView(jfc.getSelectedFile().toString());
			
			kspw.setVisible(true);
			
			
			/*MainWindow.getInstance().getKeyStoreWriter().saveKeyStore(jfc.getSelectedFile().toString(),);
			MainWindow.getInstance().getKeyStoreWriter().setFileName(jfc.getSelectedFile().toString());
			
			
		
			
			MainWindow.getInstance().repaint();
			MainWindow.getInstance().revalidate();*/
			
		}
		
	

		
		/*KeyStoreWriter ksw = new KeyStoreWriter();
		ksw.loadKeyStore(null, array1);

		ksw.saveKeyStore("C:\\Users\\Jevtic\\Desktop\\NF\\ksssadas", array1);
		
		Forma f = new Forma();
		f.setVisible(true);*/
	}

}
