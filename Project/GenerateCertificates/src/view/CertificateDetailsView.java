package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class CertificateDetailsView extends JFrame {

	private X509Certificate xcert;
	private JPanel panel, panel1;
	private JTextField jtf1;
	private JTextField jtf2;
	private JTextField jtf3;
	private JTextField jtf4;
	private JTextField jtf5;
	private JTextField jtf6;
	private JTextComponent jtf7;

	public CertificateDetailsView(X509Certificate xcert) {
		this.xcert = xcert;

		setSize(600, 300);
		setLocationRelativeTo(null);
		setTitle("Certificate details");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		panel = new JPanel();
		panel1 = new JPanel();
		panel.setLayout(null);

		JLabel lb1 = new JLabel("Subject:");
		lb1.setBounds(70, 30, 80, 25);
		panel.add(lb1);

		jtf1 = new JTextField(xcert.getSubjectDN().toString());
		jtf1.setEditable(false);
		jtf1.setBounds(140, 30, 380, 25);
		panel.add(jtf1);

		JLabel lb2 = new JLabel("Issuer:");
		lb2.setBounds(75, 60, 80, 25);
		panel.add(lb2);

		jtf2 = new JTextField(xcert.getIssuerDN().toString());
		jtf2.setEditable(false);
		jtf2.setBounds(140, 60, 380, 25);
		panel.add(jtf2);

		JLabel lb3 = new JLabel("Serial number:");
		lb3.setBounds(34, 90, 120, 25);
		panel.add(lb3);

		jtf3 = new JTextField(xcert.getSerialNumber().toString());
		jtf3.setEditable(false);
		jtf3.setBounds(140, 90, 120, 25);
		panel.add(jtf3);

		JLabel lb4 = new JLabel("Valid from:");
		lb4.setBounds(56, 120, 120, 25);
		panel.add(lb4);

		jtf4 = new JTextField(formatDate(xcert.getNotBefore()));
		jtf4.setEditable(false);
		jtf4.setBounds(140, 120, 80, 25);
		panel.add(jtf4);

		JLabel lb5 = new JLabel("Valid until:");
		lb5.setBounds(57, 150, 120, 25);
		panel.add(lb5);

		jtf5 = new JTextField(formatDate(xcert.getNotAfter()));
		jtf5.setEditable(false);
		jtf5.setBounds(140, 150, 80, 25);
		panel.add(jtf5);

		JLabel lb6 = new JLabel("Signature algorithm:");
		lb6.setBounds(3, 180, 120, 25);
		panel.add(lb6);

		jtf6 = new JTextField(xcert.getSigAlgName());
		jtf6.setEditable(false);
		jtf6.setBounds(140, 180, 120, 25);
		panel.add(jtf6);

		JLabel lb7 = new JLabel("PK algorithm:");
		lb7.setBounds(41, 210, 120, 25);
		panel.add(lb7);

		jtf7 = new JTextField(xcert.getPublicKey().getAlgorithm());
		jtf7.setEditable(false);
		jtf7.setBounds(140, 210, 50, 25);
		panel.add(jtf7);

		JButton button = new JButton("OK");
		button.setBounds(260, 240, 80, 25);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		panel.add(button);

		add(panel);

	}

	private String formatDate(Date date) {

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String reportDate = df.format(date);

		return reportDate;
	}
}
