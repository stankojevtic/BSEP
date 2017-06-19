package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class KeyStorePassView extends JFrame {
	private JLabel lb1;
	private JPasswordField jpf1;
	private JPanel panel;
	private String filePath;

	public KeyStorePassView(String string) {
		this.filePath = string;

		panel = new JPanel();

		setSize(400, 150);
		setLocationRelativeTo(null);
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Enter KeyStore password");

		panel.setLayout(null);

		JLabel lb1 = new JLabel("Password:");
		lb1.setBounds(60, 30, 80, 25);
		panel.add(lb1);

		jpf1 = new JPasswordField(20);
		jpf1.setBounds(140, 30, 190, 25);
		panel.add(jpf1);

		JButton jb = new JButton("Confirm");
		jb.setBounds(140, 90, 100, 25);
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (MainWindow.getInstance().getKeyStoreWriter()
						.loadKeyStore(filePath, jpf1.getPassword())) {
					MainWindow.getInstance().getKeyStoreWriter()
							.setFileName(filePath);
					setVisible(false);
				}
				
			}
		});

		panel.add(jb);

		add(panel);

	}
}
