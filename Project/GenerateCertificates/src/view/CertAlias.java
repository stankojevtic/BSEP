package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CertAlias extends JFrame{
	private JLabel lb1;
	private JTextField jtf1;
	private JPanel panel;
	
	public CertAlias()
	{
		panel = new JPanel();
		
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Enter alias name");

		panel.setLayout(null);

		JLabel lb1 = new JLabel("Alias");
		lb1.setBounds(60, 30, 80, 25);
		panel.add(lb1);

		jtf1 = new JTextField(20);
		jtf1.setBounds(140, 30, 190, 25);
		panel.add(jtf1);
		
		add(panel);
	
	}
}
