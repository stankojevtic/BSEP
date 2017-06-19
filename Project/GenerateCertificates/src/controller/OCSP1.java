/*package controller;

import java.security.KeyStore;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.asn1.ocsp.CertID;
import org.bouncycastle.asn1.ocsp.OCSPRequest;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.crypto.tls.HashAlgorithm;

import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

public class OCSP1 {
	
	
	public OCSPRequest generateOcspRequest(String bank, String alias, String requestorName) {
		String swiftCode = null; //bank.getSwiftCode();
		try {
			KeyStore ks = null; //getKeyStore(swiftCode);
			if (ks != null) {
				Certificate certificat = ks.getCertificate(alias);
				if (certificat != null) {
					X500Name x500name = new JcaX509CertificateHolder((X509Certificate) certificat).getSubject();
					if (x500name != null) {
						X509Certificate x509Cert = (X509Certificate) certificat;
						BigInteger serialNumber = x509Cert.getSerialNumber();
						CertID certID = new CertID(HashAlgorithm.SHA1withRSA, "nestoI", "nestoK", serialNumber);
						Request req = new Request(certID);
						TBSRequest tbsReq = new TBSRequest(Version.V1, requestorName);
						tbsReq.add(req);
						OCSPRequest ocspReq = new OCSPRequest(tbsReq);

						return ocspReq;
					}
				}
			}
		} catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
*/