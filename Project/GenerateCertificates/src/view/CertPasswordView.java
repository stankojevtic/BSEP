package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class CertPasswordView extends JFrame{
	private JLabel lb1,lb2;
	private JPasswordField jpf1,jpf2;
	private JPanel panel;
	
	public CertPasswordView()
	{
		panel = new JPanel();
		
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Enter certificate password");

		panel.setLayout(null);

		JLabel lb1 = new JLabel("Password");
		lb1.setBounds(60, 30, 80, 25);
		panel.add(lb1);

		jpf1 = new JPasswordField(20);
		jpf1.setBounds(140, 30, 190, 25);
		panel.add(jpf1);
		
		JLabel lb2 = new JLabel("Repeat");
		lb2.setBounds(60, 60, 80, 25);
		panel.add(lb2);

		jpf2 = new JPasswordField(20);
		jpf2.setBounds(140, 60, 190, 25);
		panel.add(jpf2);
		
		add(panel);
	
	}
}
