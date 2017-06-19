package main;

import javax.swing.UIManager;

import view.LoginView;

public class MainClass {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
	/*	Properties defaultProps = new Properties();
		
		FileInputStream in = null;
		try {
			in = new FileInputStream("defaultProperties");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			defaultProps.load(in);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
		try 
	    { 
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
	    } 
	    catch(Exception e){ 
	    }
	/*	MainWindow mf = MainWindow.getInstance();

		mf.setVisible(true);*/
		
		
		LoginView.getInstance().setVisible(true);
		//LoginView.getInstance().setVisible(false);
	
		
	}
}
