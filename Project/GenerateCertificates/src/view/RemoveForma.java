package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RemoveForma extends JFrame{
	
	private JLabel lb1;
	private JTextField jtf1;
	private JPanel panel;
	
	public RemoveForma()
	{
	
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(2,10));
		
		setSize(400, 180);
		setLocationRelativeTo(null);
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Remove certificate");
		panel.setLayout(null);
		
		JLabel lb1 = new JLabel("Serial number");
		lb1.setBounds(60, 30, 80, 25);
		panel.add(lb1);

		jtf1 = new JTextField(20);
		jtf1.setBounds(140, 30, 190, 25);
		panel.add(jtf1);
		
		JButton submitButton = new JButton("Remove");
		submitButton.setBounds(140, 100, 100, 25);
		submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(LoginView.getInstance().getDatabase().removeCertBySerialNumber(jtf1.getText())) {
                	JOptionPane.showMessageDialog (null, "Certificate revoked.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                	JOptionPane.showMessageDialog (null, "Certificate not existing.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                setVisible(false);
            }

        });
		
		
		add(submitButton);
		
		
		
		add(panel);
	}
}
