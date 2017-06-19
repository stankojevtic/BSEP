package controller;

import java.math.BigInteger;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import model.IssuerData;
import model.SubjectData;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class CertificateGenerator {
	public CertificateGenerator() {
	}

	public X509Certificate generateCertificate(SubjectData subjectData,
			IssuerData issuerData, boolean condition) {
		try {

			JcaContentSignerBuilder builder = new JcaContentSignerBuilder(
					"SHA256WithRSAEncryption");

			builder = builder.setProvider("BC");

			ContentSigner contentSigner = builder.build(issuerData
					.getPrivateKey());

			X509v3CertificateBuilder certGen;
			try {
				certGen = new JcaX509v3CertificateBuilder(
						issuerData.getX500name(), new BigInteger(
								subjectData.getSerialNumber()),
						subjectData.getStartDate(), subjectData.getEndDate(),
						subjectData.getX500name(), subjectData.getPublicKey())
						.addExtension(new ASN1ObjectIdentifier("2.5.29.19"),
								false, new BasicConstraints(condition));
				X509CertificateHolder certHolder = certGen.build(contentSigner);
				JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
				certConverter = certConverter.setProvider("BC");

				return certConverter.getCertificate(certHolder);
			} catch (CertIOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		return null;
	}
}
