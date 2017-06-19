package view;

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
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.xml.bind.DatatypeConverter;

import keystore_reader_writer.KeyStoreReader;

public class PopUpMenu extends JPopupMenu {

	private JMenuItem export, delete, details;

	public PopUpMenu() {

		details = new JMenuItem("Details");
		details.setIcon(new ImageIcon("images/details.png"));
		details.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				KeyStoreReader ksr = new KeyStoreReader();
				Path p = Paths.get(MainWindow.getInstance().getKeyStoreWriter()
						.getFileName());
				String fileName = p.getFileName().toString();

				String pass = MainWindow.getInstance().getProp()
						.getProperty(fileName);

				Certificate cert = ksr.readCertificate(
						MainWindow.getInstance().getKeyStoreWriter()
								.getFileName(),
						pass,
						(String) MainWindow
								.getInstance()
								.getJt()
								.getValueAt(
										MainWindow.getInstance().getJt()
												.getSelectedRow(), 1));

				X509Certificate xcert = (X509Certificate) cert;

				CertificateDetailsView cdv = new CertificateDetailsView(xcert);
				
				cdv.setVisible(true);

			}
		});
		add(details);
		addSeparator();
		delete = new JMenuItem("Delete");
		delete.setIcon(new ImageIcon("images/delete.png"));
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				String alias = (String) MainWindow
						.getInstance()
						.getJt()
						.getValueAt(
								MainWindow.getInstance().getJt()
										.getSelectedRow(), 1);

				MainWindow.getInstance().getKeyStoreWriter().delete(alias);

				Enumeration<String> enum123 = MainWindow.getInstance()
						.getKeyStoreWriter().getAlias();

				ArrayList<String> listaSerta = new ArrayList<String>();
				while (enum123.hasMoreElements()) {
					String show = enum123.nextElement();
					listaSerta.add(show);
				}
				MainWindow.getInstance().tablePopulate(listaSerta);

				// MainWindow.getInstance().getKeyStoreWriter().
				/*
				 * MainWindow.getInstance().getKeyStoreWriter().saveKeyStore(
				 * MainWindow.getInstance().getKeyStoreWriter()
				 * .getFileName(),array1);
				 */

			}
		});
		add(delete);

		export = new JMenuItem("Export");
		export.setIcon(new ImageIcon("images/export.png"));
		export.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				KeyStoreReader ksr = new KeyStoreReader();

				Path p = Paths.get(MainWindow.getInstance().getKeyStoreWriter()
						.getFileName());

				String fileName = p.getFileName().toString();

				String pass = MainWindow.getInstance().getProp()
						.getProperty(fileName);

				Certificate cert = ksr.readCertificate(
						MainWindow.getInstance().getKeyStoreWriter()
								.getFileName(),
						pass,
						(String) MainWindow
								.getInstance()
								.getJt()
								.getValueAt(
										MainWindow.getInstance().getJt()
												.getSelectedRow(), 1));

				X509Certificate xcert = (X509Certificate) cert;
				JFileChooser jfc = new JFileChooser();
				if (jfc.showSaveDialog(MainWindow.getInstance()) == JFileChooser.APPROVE_OPTION) {

					save(xcert, jfc.getSelectedFile().toString());

				}

				/*
				 * MainWindow.getInstance().getJt().getValueAt(MainWindow.
				 * getInstance().getJt().getSelectedRow(), 0);
				 */
			}

		});
		add(export);

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
