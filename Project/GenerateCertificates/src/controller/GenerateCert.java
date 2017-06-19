package controller;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import keystore_reader_writer.KeyStoreReader;
import model.IssuerData;
import model.SubjectData;
import model.SubjectInfo;

import org.bouncycastle.asn1.ocsp.OCSPResponse;
import org.bouncycastle.asn1.pkcs.CertificationRequest;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;

import view.LoginView;
import view.MainWindow;

public class GenerateCert {

	private static final String ALGORITHM = "RSA";

	// readIssuerFromStore trazi sifru sertifikata, kako je skladistiti?
	// da li treba registracija
	// posebna baza za fizicka i pravna lica
	// cuvanje sifara za sertifikat u property fajlu, hashovati je?
	// na kom nivou je keyStore i fizicko lice da li treba da ga otvara
	// algoritam za sifru, vise pogresnih logovanja
	// csr

	/*
	 * public String OcspStatus(String serial){ long ser =
	 * Long.parseLong(serial);
	 * 
	 * //Certificates cer = service.vratiPrekoSerijskog(ser);
	 * if(!cer.isOcspValid())return new
	 * MessageWithObj("Sertifikat nije u upotrebi!", true, null); else{ return
	 * new MessageWithObj("Sertifikat je validan!", true, null); }
	 * 
	 * 
	 * }
	 */
	public static X509Certificate sign(CertificationRequest csr,
			PrivateKey caPrivate, KeyPair pair, SubjectData subjectData,
			IssuerData issuerData) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException,
			SignatureException, IOException, OperatorCreationException,
			CertificateException {

		AlgorithmIdentifier sigAlgId = new DefaultSignatureAlgorithmIdentifierFinder()
				.find("SHA1withRSA");
		AlgorithmIdentifier digAlgId = new DefaultDigestAlgorithmIdentifierFinder()
				.find(sigAlgId);

		AsymmetricKeyParameter foo = PrivateKeyFactory.createKey(caPrivate
				.getEncoded());
		SubjectPublicKeyInfo keyInfo = SubjectPublicKeyInfo.getInstance(pair
				.getPublic().getEncoded());

		PKCS10CertificationRequest pk10Holder = new PKCS10CertificationRequest(
				csr);

		X509v3CertificateBuilder myCertificateGenerator = new X509v3CertificateBuilder(
				issuerData.getX500name(), new BigInteger(
						subjectData.getSerialNumber()),
				subjectData.getStartDate(), subjectData.getEndDate(),
				pk10Holder.getSubject(), keyInfo);

		ContentSigner sigGen = new BcRSAContentSignerBuilder(sigAlgId, digAlgId)
				.build(foo);

		X509CertificateHolder holder = myCertificateGenerator.build(sigGen);
		// X509CertificateStructure eeX509CertificateStructure =
		// holder.toASN1Structure();
		// in newer version of BC such as 1.51, this is
		org.bouncycastle.asn1.x509.Certificate eeX509CertificateStructure = holder
				.toASN1Structure();

		CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");

		// Read Certificate
		InputStream is1 = new ByteArrayInputStream(
				eeX509CertificateStructure.getEncoded());
		X509Certificate theCert = (X509Certificate) cf.generateCertificate(is1);

		PEMWriter pemWriter = new PEMWriter(new PrintWriter(System.out));
		pemWriter.writeObject(theCert);
		pemWriter.flush();
		// System.out.println(theCert);
		is1.close();
		return theCert;
		// return null;
	}

	public void ocspStatus(String serial) {

		long ser = Long.parseLong(serial);

		/*Certificate cer;
		if (!cer) {

		} else {

		}*/
	}

	public void selfSignedCert(SubjectInfo info) {
		try {

			// generise par kljuceva
			KeyPair keyPairSelfSigned = generateKeyPair();

			SubjectData subjectData = generateSubjectData(info);
			IssuerData issuerData = generateIssuerData(
					keyPairSelfSigned.getPrivate(), info);

			// Generise se sertifikat za subjekta, potpisan od strane issuer-a
			CertificateGenerator cg = new CertificateGenerator();
			X509Certificate cert = cg.generateCertificate(subjectData,
					issuerData, true);
			
			
			System.out.println("Proveri ispravnost\n\n");

			cert.verify(keyPairSelfSigned.getPublic());

			System.out.println("\n\nValidacija uspesna");

			System.out.println(cert);

			// char[] array1 = { 'a' };

			/*
			 * KeyStoreWriter ksw = new KeyStoreWriter();
			 * ksw.loadKeyStore("C:\\Users\\Jevtic\\Desktop\\NF\\kss", array1);
			 */

			MainWindow
					.getInstance()
					.getKeyStoreWriter()
					.write(info.getAlias(), keyPairSelfSigned.getPrivate(),
							info.getPassword().toCharArray(), cert);

			OutputStream output = null;

			try {

				output = new FileOutputStream("config.properties");

				MainWindow
						.getInstance()
						.getProp()
						.setProperty(info.getAlias() + "alias",
								String.valueOf(info.getPassword()));

				MainWindow.getInstance().getProp().store(output, null);

			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}

			LoginView.getInstance().getDatabase()
					.insert(subjectData.getSerialNumber(), info.getAlias(), 1);
			System.out.println("######################################");

			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

			Enumeration<String> enum123 = MainWindow.getInstance()
					.getKeyStoreWriter().getAlias();

			ArrayList<String> listaSerta = new ArrayList<String>();
			while (enum123.hasMoreElements()) {
				String show = enum123.nextElement();
				listaSerta.add(show);
			}

			Path p = Paths.get(MainWindow.getInstance().getKeyStoreWriter()
					.getFileName());

			String fileName = p.getFileName().toString();

			/*
			 * System.out.println("fileName" + fileName);
			 * 
			 * String stringValueOf =
			 * String.valueOf(MainWindow.getInstance().getProp
			 * ().getProperty(fileName));
			 * 
			 * System.out.println("StringValueOF" + stringValueOf);
			 */

			String pass = MainWindow.getInstance().getProp()
					.getProperty(fileName);

			char[] charArray = pass.toCharArray();

			MainWindow
					.getInstance()
					.getKeyStoreWriter()
					.saveKeyStore(
							MainWindow.getInstance().getKeyStoreWriter()
									.getFileName(), charArray);
			MainWindow.getInstance().tablePopulate(listaSerta);
			
			

		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void signedCert(SubjectInfo info) {

		KeyStoreReader ksr = new KeyStoreReader();

		Path p = Paths.get(MainWindow.getInstance().getKeyStoreWriter()
				.getFileName());

		String fileName = p.getFileName().toString();

		String pass = MainWindow.getInstance().getProp().getProperty(fileName);

		Certificate certWhoSigns = ksr.readCertificate(MainWindow.getInstance()
				.getKeyStoreWriter().getFileName(), pass, info.getCa());

		X509Certificate xcertWhoSigns = (X509Certificate) certWhoSigns;

		/*
		 * try { xcertWhoSigns.checkValidity();
		 * System.out.println("Cert validan"); } catch
		 * (CertificateExpiredException e1) { // TODO Auto-generated catch block
		 * System.out.println("Cert isteko"); e1.printStackTrace(); } catch
		 * (CertificateNotYetValidException e1) { // TODO Auto-generated catch
		 * block System.out.println("Cert jos nije validan");
		 * e1.printStackTrace(); }
		 */

		if (xcertWhoSigns.getBasicConstraints() != -1
				&& LoginView.getInstance().getDatabase().isValid(info.getCa())) {

			KeyPair keyPair = generateKeyPair();

			SubjectData subjectData = generateSubjectData(info);

			// KeyStoreReader ksr = new KeyStoreReader();

			/*
			 * Path p = Paths.get(MainWindow.getInstance().getKeyStoreWriter()
			 * .getFileName());
			 */

			/*
			 * String fileName = p.getFileName().toString();
			 * 
			 * 
			 * 
			 * String pass =
			 * MainWindow.getInstance().getProp().getProperty(fileName);
			 */

			// char[] pass1 = pass.toCharArray();

			// char[] pass2 = { 'a' };

			/*
			 * IssuerData issuerDataX = ksr.readIssuerFromStore(MainWindow
			 * .getInstance().getKeyStoreWriter().getFileName(), info.getCa(),
			 * pass2, pass1);
			 */

			// X500Name x500Issuer = issuerData.getX500name();
			/*
			 * try { X500Name x500Issuer = new
			 * JcaX509CertificateHolder(xcertWhoSigns).getSubject(); } catch
			 * (CertificateEncodingException e1) { // TODO Auto-generated catch
			 * block e1.printStackTrace(); }
			 */

			/*
			 * Certificate certWhoSigns = ksr.readCertificate(MainWindow
			 * .getInstance().getKeyStoreWriter().getFileName(), pass,
			 * info.getCa());
			 * 
			 * X509Certificate xcertWhoSigns = (X509Certificate) certWhoSigns;
			 * 
			 * if(xcertWhoSigns.getBasicConstraints() != -1) {
			 * 
			 * }
			 */

			// preuzmemo sertifikat koji potpisuje
			/*
			 * Certificate certWhoSigns = ksr.readCertificate(MainWindow
			 * .getInstance().getKeyStoreWriter().getFileName(), pass,
			 * info.getCa());
			 * 
			 * X509Certificate xcertWhoSigns = (X509Certificate) certWhoSigns;
			 * 
			 * 
			 * 
			 * X500Name x500Issuer = new JcaX509CertificateHolder(
			 * xcertWhoSigns).getSubject();
			 */

			// KeyPair keyPairIssuer = generateKeyPair();

			// String pass1 =
			// MainWindow.getInstance().getProp().getProperty(info.getCa());

			String password = MainWindow.getInstance().getProp()
					.getProperty(info.getCa() + "alias");

			char[] passChar = password.toCharArray();

			PrivateKey privKey = null;
			try {
				privKey = (PrivateKey) MainWindow.getInstance()
						.getKeyStoreWriter().getKeyStore()
						.getKey(info.getCa(), passChar);
			} catch (UnrecoverableKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			X500Name x500Issuer = null;
			try {
				x500Issuer = new JcaX509CertificateHolder(xcertWhoSigns)
						.getSubject();
			} catch (CertificateEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * IssuerData issuerData = new IssuerData( privKey, x500Issuer);
			 */
			IssuerData issuerData = new IssuerData(privKey, x500Issuer);
			CertificateGenerator cg = new CertificateGenerator();
			X509Certificate cert = cg.generateCertificate(subjectData,
					issuerData, true);

			System.out.println(cert);

			/*
			 * MainWindow .getInstance() .getDatabase() .insert(info.getAlias(),
			 * subjectData.getSerialNumber(), 1, 1);
			 */

			OutputStream output = null;

			try {

				output = new FileOutputStream("config.properties");

				MainWindow
						.getInstance()
						.getProp()
						.setProperty(info.getAlias() + "alias",
								String.valueOf(info.getPassword()));

				MainWindow.getInstance().getProp().store(output, null);

			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}

			// todo: keypairissuer?
			MainWindow
					.getInstance()
					.getKeyStoreWriter()
					.write(info.getAlias(), keyPair.getPrivate(),
							info.getPassword().toCharArray(), cert);

			String passKeyStore = MainWindow.getInstance().getProp()
					.getProperty(fileName);

			char[] charArray = passKeyStore.toCharArray();

			MainWindow
					.getInstance()
					.getKeyStoreWriter()
					.saveKeyStore(
							MainWindow.getInstance().getKeyStoreWriter()
									.getFileName(), charArray);
			LoginView.getInstance().getDatabase()
					.insert(subjectData.getSerialNumber(), info.getAlias(), 1);
			/*
			 * MainWindow .getInstance() .getKeyStoreWriter() .saveKeyStore(
			 * MainWindow.getInstance().getKeyStoreWriter() .getFileName(),
			 * info.getPassword().toCharArray());
			 */
			// MainWindow.getInstance().getDatabase().insert(info.getAlias(),
			// 0);
			// System.out.println("######################################");

			// System.out.println(serto);

			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

			Enumeration<String> enum123 = MainWindow.getInstance()
					.getKeyStoreWriter().getAlias();

			ArrayList<String> listaSerta = new ArrayList<String>();
			while (enum123.hasMoreElements()) {
				String show = enum123.nextElement();
				listaSerta.add(show);
			}
			MainWindow.getInstance().tablePopulate(listaSerta);
		} else {
			System.out.println("Ne moze da potpisuje");
		}

	}

	public void signedFinalCert(SubjectInfo info) {

		KeyStoreReader ksr = new KeyStoreReader();

		Path p = Paths.get(MainWindow.getInstance().getKeyStoreWriter()
				.getFileName());

		String fileName = p.getFileName().toString();

		String pass = MainWindow.getInstance().getProp().getProperty(fileName);

		Certificate certWhoSigns = ksr.readCertificate(MainWindow.getInstance()
				.getKeyStoreWriter().getFileName(), pass, info.getCa());

		X509Certificate xcertWhoSigns = (X509Certificate) certWhoSigns;

		if (xcertWhoSigns.getBasicConstraints() != -1
				&& LoginView.getInstance().getDatabase().isValid(info.getCa())) {

			KeyPair keyPair = generateKeyPair();

			SubjectData subjectData = generateSubjectData(info);

			// char[] pass1 = pass.toCharArray();

			// char[] pass2 = { 'b' };

			/*
			 * IssuerData issuerData = ksr.readIssuerFromStore(MainWindow
			 * .getInstance().getKeyStoreWriter().getFileName(), info.getCa(),
			 * pass2, pass1);
			 * 
			 * X500Name x500Issuer = issuerData.getX500name();
			 */

			/*
			 * Certificate certWhoSigns = ksr.readCertificate(MainWindow
			 * .getInstance().getKeyStoreWriter().getFileName(), pass,
			 * info.getCa());
			 * 
			 * X509Certificate xcertWhoSigns = (X509Certificate) certWhoSigns;
			 * 
			 * if(xcertWhoSigns.getBasicConstraints() != -1) {
			 * 
			 * }
			 */

			// preuzmemo sertifikat koji potpisuje
			/*
			 * Certificate certWhoSigns = ksr.readCertificate(MainWindow
			 * .getInstance().getKeyStoreWriter().getFileName(), pass,
			 * info.getCa());
			 * 
			 * X509Certificate xcertWhoSigns = (X509Certificate) certWhoSigns;
			 * 
			 * 
			 * 
			 * X500Name x500Issuer = new JcaX509CertificateHolder(
			 * xcertWhoSigns).getSubject();
			 */

			// KeyPair keyPairIssuer = generateKeyPair();

			// String pass1 =
			// MainWindow.getInstance().getProp().getProperty(info.getCa());

			// ///////////////////////////////
			String password = MainWindow.getInstance().getProp()
					.getProperty(info.getCa() + "alias");

			char[] passChar = password.toCharArray();

			PrivateKey privKey = null;
			try {
				privKey = (PrivateKey) MainWindow.getInstance()
						.getKeyStoreWriter().getKeyStore()
						.getKey(info.getCa(), passChar);
			} catch (UnrecoverableKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			X500Name x500Issuer = null;
			try {
				x500Issuer = new JcaX509CertificateHolder(xcertWhoSigns)
						.getSubject();
			} catch (CertificateEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * IssuerData issuerData = new IssuerData( privKey, x500Issuer);
			 */
			IssuerData issuerData = new IssuerData(privKey, x500Issuer);
			// /////////////////////////////

			// IssuerData issuerData = new IssuerData(
			// privKey, x500Issuer);

			// CertificateGenerator cg = new CertificateGenerator();
			// X509Certificate cert =
			// cg.generateCertificate(subjectData,issuerData,false);

			// System.out.println(cert);

			// X509Certificate cert = sign(, caPrivate, pair)
			SubjectData subjectData123 = generateSubjectData(info);
			KeyPair pair = generateKeyPair();
			PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
					subjectData123.getX500name(), pair.getPublic());
			JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder(
					"SHA256withRSA");
			ContentSigner signer = null;
			try {
				signer = csBuilder.build(pair.getPrivate());
			} catch (OperatorCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PKCS10CertificationRequest csr = p10Builder.build(signer);

			CertificationRequest csr1 = csr.toASN1Structure();

			X509Certificate cert = null;
			try {
				cert = sign(csr1, privKey, pair, subjectData123, issuerData);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchProviderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SignatureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OperatorCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(cert);

			OutputStream output = null;

			try {

				output = new FileOutputStream("config.properties");

				MainWindow
						.getInstance()
						.getProp()
						.setProperty(info.getAlias() + "alias",
								String.valueOf(info.getPassword()));

				MainWindow.getInstance().getProp().store(output, null);

			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}

			MainWindow
					.getInstance()
					.getKeyStoreWriter()
					.write(info.getAlias(), keyPair.getPrivate(),
							info.getPassword().toCharArray(), cert);

			String passKeyStore = MainWindow.getInstance().getProp()
					.getProperty(fileName);

			char[] charArray = passKeyStore.toCharArray();

			MainWindow
					.getInstance()
					.getKeyStoreWriter()
					.saveKeyStore(
							MainWindow.getInstance().getKeyStoreWriter()
									.getFileName(), charArray);

			LoginView.getInstance().getDatabase()
					.insert(subjectData.getSerialNumber(), info.getAlias(), 0);

			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

			Enumeration<String> enum123 = MainWindow.getInstance()
					.getKeyStoreWriter().getAlias();

			ArrayList<String> listaSerta = new ArrayList<String>();
			while (enum123.hasMoreElements()) {
				String show = enum123.nextElement();
				listaSerta.add(show);
			}
			MainWindow.getInstance().tablePopulate(listaSerta);
		} else {
			System.out.println("Ne moze da potpisuje");
		}

	}

	/*
	 * public void signedFinalCert(SubjectInfo info) { // TODO Auto-generated
	 * method stub try { // subjekat
	 * 
	 * int mozeDaPotpisuje = MainWindow.getInstance().getDatabase()
	 * .getCaNumber(info.getCa()); int validan =
	 * MainWindow.getInstance().getDatabase() .getValidity(info.getCa()); if
	 * (mozeDaPotpisuje == 1 && validan == 1) {
	 * 
	 * SubjectData subjectData = generateSubjectData(info);
	 * 
	 * KeyStoreReader ksr = new KeyStoreReader();
	 * 
	 * // preuzmemo sertifikat koji potpisuje Certificate certWhoSigns =
	 * ksr.readCertificate(MainWindow
	 * .getInstance().getKeyStoreWriter().getFileName(), info.getPassword(),
	 * info.getCa());
	 * 
	 * X509Certificate xcertWhoSigns = (X509Certificate) certWhoSigns;
	 * 
	 * X500Name x500Issuer = new JcaX509CertificateHolder(
	 * xcertWhoSigns).getSubject();
	 * 
	 * KeyPair keyPairIssuer = generateKeyPair(); IssuerData issuerData = new
	 * IssuerData( keyPairIssuer.getPrivate(), x500Issuer);
	 * 
	 * CertificateGenerator cg = new CertificateGenerator(); X509Certificate
	 * cert = cg.generateCertificate(subjectData, issuerData,false);
	 * 
	 * System.out.println(cert);
	 * 
	 * MainWindow .getInstance() .getDatabase() .insert(info.getAlias(),
	 * subjectData.getSerialNumber(), 0, 1);
	 * 
	 * // todo: keypairissuer? MainWindow .getInstance() .getKeyStoreWriter()
	 * .write(info.getAlias(), keyPairIssuer.getPrivate(),
	 * info.getPassword().toCharArray(), cert);
	 * 
	 * MainWindow .getInstance() .getKeyStoreWriter() .saveKeyStore(
	 * MainWindow.getInstance().getKeyStoreWriter() .getFileName(),
	 * info.getPassword().toCharArray());
	 * 
	 * // MainWindow.getInstance().getDatabase().insert(info.getAlias(), // 0);
	 * // System.out.println("######################################");
	 * 
	 * // System.out.println(serto);
	 * 
	 * System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	 * 
	 * Enumeration<String> enum123 = MainWindow.getInstance()
	 * .getKeyStoreWriter().getAlias();
	 * 
	 * ArrayList<String> listaSerta = new ArrayList<String>(); while
	 * (enum123.hasMoreElements()) { String show = enum123.nextElement();
	 * listaSerta.add(show); }
	 * MainWindow.getInstance().tablePopulate(listaSerta); } else if
	 * (mozeDaPotpisuje == 0 && validan == 1) { System.out
	 * .println("Odabrani sertifikat nema prava za potpisivanje drugih sertifikata."
	 * ); } else { System.out.println("Odabrani sertifikat je povucen."); }
	 * 
	 * } catch (CertificateException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 */

	private IssuerData generateIssuerData(PrivateKey issuerKey, SubjectInfo info) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, info.getIme() + " " + info.getPrezime());
		builder.addRDN(BCStyle.SURNAME, info.getPrezime());
		builder.addRDN(BCStyle.GIVENNAME, info.getIme());
		// builder.addRDN(BCStyle.O, "UNS-FTN");
		builder.addRDN(BCStyle.OU, info.getKompanija());
		// builder.addRDN(BCStyle.C, "RS");
		builder.addRDN(BCStyle.E, info.getEmail());

		// UID (USER ID) je ID korisnika

		// Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
		// - privatni kljuc koji ce se koristiti da potpise sertifikat koji se
		// izdaje
		// - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(issuerKey, builder.build());
	}

	private SubjectData generateSubjectData(SubjectInfo info) {
		// Datumi od kad do kad vazi sertifikat
		KeyPair kp = generateKeyPair();

		// SimpleDateFormat iso8601Formater = new
		// SimpleDateFormat("dd-MM-yyyy");

		Date startDate = new Date();
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		cal.add(Calendar.DATE, Integer.parseInt(info.getDays()));
		Date endDate = null;
		startDate = cal1.getTime();
		endDate = cal.getTime();
		BigInteger b = new BigInteger(50, new Random());
		String sn = b.toString();

		// klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke
		// o vlasniku
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, info.getIme() + " " + info.getPrezime());
		builder.addRDN(BCStyle.SURNAME, info.getPrezime());
		builder.addRDN(BCStyle.GIVENNAME, info.getIme());
		// builder.addRDN(BCStyle.O, "UNS-FTN");
		builder.addRDN(BCStyle.OU, info.getKompanija());
		// builder.addRDN(BCStyle.C, "RS");
		builder.addRDN(BCStyle.E, info.getEmail());
		// UID (USER ID) je ID korisnika

		// Kreiraju se podaci za sertifikat, sto ukljucuje:
		// - javni kljuc koji se vezuje za sertifikat
		// - podatke o vlasniku
		// - serijski broj sertifikata
		// - od kada do kada vazi sertifikat
		return new SubjectData(kp.getPublic(), builder.build(), sn, startDate,
				endDate);
	}

	private KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void printCertBySerialNumber(String text) {

		try {
			KeyStoreReader ksr = new KeyStoreReader();

			Certificate certWhoSigns = ksr.readCertificate(MainWindow
					.getInstance().getKeyStoreWriter().getFileName(), "a",
					LoginView.getInstance().getDatabase()
							.getAliasBySerialNumber(text));

			X509Certificate xcert = (X509Certificate) certWhoSigns;

			System.out.println(xcert);
		} catch (Exception e) {
			System.out.println("Exception");
		}

	}

}
