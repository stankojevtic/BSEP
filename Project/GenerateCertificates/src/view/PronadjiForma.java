package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.bind.DatatypeConverter;

import keystore_reader_writer.KeyStoreReader;

public class PronadjiForma extends JFrame {

	private JLabel lb1;
	private JTextField jtf1;
	private JPanel panel;

	public PronadjiForma() {

		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 10));

		setSize(380, 150);
		setLocationRelativeTo(null);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Find certificate");
		panel.setLayout(null);

		JLabel lb1 = new JLabel("Serial number");
		lb1.setBounds(30, 30, 80, 25);
		panel.add(lb1);

		jtf1 = new JTextField(20);
		jtf1.setBounds(140, 30, 190, 25);
		panel.add(jtf1);

		JButton submitButton = new JButton("Search");
		submitButton.setBounds(140, 70, 100, 25);
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// GenerateCert gc = new GenerateCert();
				// gc.printCertBySerialNumber(jtf1.getText());
				/*
				 * LoginView.getInstance().getDatabase().exportCertBySerialNumber
				 * (jtf1.getText()); setVisible(false);
				 */
				String ca = LoginView.getInstance().getDatabase()
						.getAliasBySerialNumber(jtf1.getText());

				if (ca != null && ca != "") {
					
					
					
					KeyStoreReader ksr = new KeyStoreReader();

					Path p = Paths.get(MainWindow.getInstance()
							.getKeyStoreWriter().getFileName());

					String fileName = p.getFileName().toString();

					String pass = MainWindow.getInstance().getProp()
							.getProperty(fileName);

					Certificate cert = ksr.readCertificate(MainWindow
							.getInstance().getKeyStoreWriter().getFileName(),
							pass, ca);

					X509Certificate xCert = (X509Certificate) cert;

					JFileChooser jfc = new JFileChooser();
					if (jfc.showSaveDialog(MainWindow.getInstance()) == JFileChooser.APPROVE_OPTION) {

						save(xCert, jfc.getSelectedFile().toString());
						setVisible(false);

					}
					
				} else {
					JOptionPane.showMessageDialog (null, "This serial number is not existing in key store!", "Error", JOptionPane.ERROR_MESSAGE);
				}

				
			}

		});

		add(submitButton);

		add(panel);
	}

	public void save(X509Certificate cert, String filePath) {
		StringWriter sw = new StringWriter();
		try {
			sw.write(DatatypeConverter.printBase64Binary(cert.getEncoded())
					.replaceAll("(.{64})", "$1\n"));
		} catch (CertificateEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		FileWriter fw;
		try {
			// fw = new FileWriter("C:\\Users\\Jevtic\\Desktop\\xx11.cer");
			fw = new FileWriter(filePath + ".cer");
			fw.write(sw.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
