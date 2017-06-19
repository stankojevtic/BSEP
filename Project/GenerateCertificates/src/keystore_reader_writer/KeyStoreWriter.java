package keystore_reader_writer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JOptionPane;

import view.MainWindow;

public class KeyStoreWriter {
	// KeyStore je Java klasa za citanje specijalizovanih datoteka koje se
	// koriste za cuvanje kljuceva
	// Tri tipa entiteta koji se obicno nalaze u ovakvim datotekama su:
	// - Sertifikati koji ukljucuju javni kljuc
	// - Privatni kljucevi
	// - Tajni kljucevi, koji se koriste u simetricnima siframa
	private KeyStore keyStore;
	private String fileName;
	private String fileName1;

	public KeyStoreWriter() {
		try {
			keyStore = KeyStore.getInstance("JKS", "SUN");

		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
	}

	public Enumeration<String> getAlias() {
		try {
			return keyStore.aliases();

		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public boolean loadKeyStore(String fileName, char[] password) {
		try {
			if (fileName != null) {
				try {

					keyStore.load(new FileInputStream(fileName), password);

					//MainWindow.getInstance().setVisible(false);
					Enumeration<String> enum123 = MainWindow.getInstance().getKeyStoreWriter().getAlias();
					ArrayList<String> listaSerta = new ArrayList<String>();
					while (enum123.hasMoreElements()) {

						String show = enum123.nextElement();

						listaSerta.add(show);

					}

					MainWindow.getInstance().tablePopulate(listaSerta);
					
					MainWindow.getInstance().newKeyPair.setEnabled(true);
					MainWindow.getInstance().newKeyPairPL2.setEnabled(true);
					MainWindow.getInstance().saveItem.setEnabled(true);
					MainWindow.getInstance().saveAsItem.setEnabled(true);
					MainWindow.getInstance().newKeyPair.setEnabled(true);
					MainWindow.getInstance().takeCertificate.setEnabled(true);
					MainWindow.getInstance().takeCertificate1.setEnabled(true);
					MainWindow.getInstance().removeCertificate.setEnabled(true);
					MainWindow.getInstance().importCert.setEnabled(true);
					//MainWindow.getInstance().importCertG.setEnabled(true);
					MainWindow.getInstance().approveCertificate.setEnabled(true);
					MainWindow.getInstance().btn1.setEnabled(true);
					MainWindow.getInstance().btn2.setEnabled(true);
					MainWindow.getInstance().btn3.setEnabled(true);
					MainWindow.getInstance().ocspA.setEnabled(true);
					MainWindow.getInstance().ocspG.setEnabled(true);
					MainWindow.getInstance().ocspL.setEnabled(true);
					
					MainWindow.getInstance().repaint();
					MainWindow.getInstance().invalidate();
					MainWindow.getInstance().revalidate();
					return true;

				} catch (Exception e) {

					JOptionPane.showMessageDialog(null,
							"Incorrect password.");
					return false;
				}
				/*
				 * Enumeration<String> enum123 = MainWindow.getInstance()
				 * .getKeyStoreWriter().getAlias(); ArrayList<String> listaSerta
				 * = new ArrayList<String>(); while (enum123.hasMoreElements())
				 * {
				 * 
				 * 
				 * String show = enum123.nextElement();
				 * 
				 * listaSerta.add(show);
				 * 
				 * }
				 * 
				 * MainWindow.getInstance().tablePopulate(listaSerta);
				 */
				// MainWindow.getInstance().enableTools();

			} else {
				// Ako je cilj kreirati novi KeyStore poziva se i dalje load,
				// pri cemu je prvi parametar null
				keyStore.load(null, password);
				
				MainWindow.getInstance().newKeyPair.setEnabled(true);
				MainWindow.getInstance().newKeyPairPL2.setEnabled(true);
				MainWindow.getInstance().saveItem.setEnabled(true);
				MainWindow.getInstance().saveAsItem.setEnabled(true);
				MainWindow.getInstance().newKeyPair.setEnabled(true);
				MainWindow.getInstance().takeCertificate.setEnabled(true);
				MainWindow.getInstance().takeCertificate1.setEnabled(true);
				MainWindow.getInstance().removeCertificate.setEnabled(true);
				MainWindow.getInstance().importCert.setEnabled(true);
				MainWindow.getInstance().ocspA.setEnabled(true);
				MainWindow.getInstance().ocspG.setEnabled(true);
				MainWindow.getInstance().ocspL.setEnabled(true);
				MainWindow.getInstance().approveCertificate.setEnabled(true);
				MainWindow.getInstance().btn1.setEnabled(true);
				MainWindow.getInstance().btn2.setEnabled(true);
				MainWindow.getInstance().btn3.setEnabled(true);
				
				MainWindow.getInstance().repaint();
				MainWindow.getInstance().invalidate();
				MainWindow.getInstance().revalidate();
				return true;
				// MainWindow.getInstance().enableTools();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void saveKeyStore(String fileName, char[] password) {
		try {
			keyStore.store(new FileOutputStream(fileName), password);

			// MainWindow.getInstance().enableTools();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(String alias, PrivateKey privateKey, char[] password,
			Certificate certificate) {
		try {
			keyStore.setKeyEntry(alias, privateKey, password,
					new Certificate[] { certificate });
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}

	public void delete(String alias) {

		try {
			keyStore.deleteEntry(alias);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName1() {
		return fileName1;
	}

	public void setFileName1(String fileName1) {
		this.fileName1 = fileName1;
	}

	public KeyStore getKeyStore() {
		return keyStore;
	}
}
