package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.swing.JFileChooser;

import view.MainWindow;

public class ImportCertificate implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		KeyStore trustStore = MainWindow.getInstance().getKeyStoreWriter()
				.getKeyStore();
		InputStream fis=null;
		try {
			fis = new FileInputStream(
					"C:\\Users\\Jevtic\\Desktop\\a\\h1.cer");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedInputStream bis = new BufferedInputStream(fis);

		CertificateFactory cf=null;
		try {
			cf = CertificateFactory.getInstance("X.509");
		} catch (CertificateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CertificateGenerator cg = new CertificateGenerator();
		try {
			while (bis.available() > 0) {
				X509Certificate cert = (X509Certificate) cf.generateCertificate(bis);
				trustStore.setCertificateEntry("a" + bis.available(), cert);
			}
		} catch (CertificateException | KeyStoreException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// /////////////

		/*
		 * JFileChooser jfc = new JFileChooser();
		 * 
		 * if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		 * 
		 * // MainWindow.getInstance().getKeyStoreWriter().getKeyStore()
		 * InputStream fis = new
		 * ByteArrayInputStream(jfc.getSelectedFile().toString
		 * ().getBytes(StandardCharsets.UTF_8)); BufferedInputStream bis = new
		 * BufferedInputStream(fis);
		 * 
		 * CertificateFactory cf=null; try { cf =
		 * CertificateFactory.getInstance("X.509"); while (bis.available() > 0)
		 * { Certificate cert = cf.generateCertificate(bis);
		 * MainWindow.getInstance
		 * ().getKeyStoreWriter().getKeyStore().setCertificateEntry("Alias",
		 * cert); } } catch (CertificateException e1) { // TODO Auto-generated
		 * catch block e1.printStackTrace(); } catch (KeyStoreException e1) { //
		 * TODO Auto-generated catch block e1.printStackTrace(); } catch
		 * (IOException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); }
		 * 
		 * 
		 * }
		 */

	}
}
