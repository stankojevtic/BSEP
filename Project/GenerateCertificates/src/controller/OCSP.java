/*package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.cert.CRLReason;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.Extension;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bouncycastle.asn1.crmf.CertId;
import org.bouncycastle.asn1.ocsp.OCSPRequest;
import org.bouncycastle.asn1.ocsp.OCSPResponse;

import sun.security.provider.certpath.OCSPResponse.ResponseStatus;
import sun.security.util.Debug;
import sun.security.x509.AccessDescription;
import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.URIName;
import sun.security.x509.X509CertImpl;

*//**
 * 54 * This is a class that checks the revocation status of a certificate(s)
 * using 55 * OCSP. It is not a PKIXCertPathChecker and therefore can be used
 * outside of 56 * the CertPathValidator framework. It is useful when you want
 * to 57 * just check the revocation status of a certificate, and you don't want
 * to 58 * incur the overhead of validating all of the certificates in the 59 *
 * associated certificate chain. 60 * 61 * @author Sean Mullan 62
 *//*
public final class OCSP {

	private static final Debug debug = Debug.getInstance("certpath");

	private static final int CONNECT_TIMEOUT = 15000; // 15 seconds

	private OCSP() {
	}

	*//**
	 * 72 * Obtains the revocation status of a certificate using OCSP using the
	 * most 73 * common defaults. The OCSP responder URI is retrieved from the
	 * 74 * certificate's AIA extension. The OCSP responder certificate is
	 * assumed 75 * to be the issuer's certificate (or issued by the issuer CA).
	 * 76 * 77 * @param cert the certificate to be checked 78 * @param
	 * issuerCert the issuer certificate 79 * @return the RevocationStatus 80 * @throws
	 * IOException if there is an exception connecting to or 81 * communicating
	 * with the OCSP responder 82 * @throws CertPathValidatorException if an
	 * exception occurs while 83 * encoding the OCSP Request or validating the
	 * OCSP Response 84
	 *//*
	public static RevocationStatus check(X509Certificate cert,
			X509Certificate issuerCert) throws IOException,
			CertPathValidatorException {
		CertId certId = null;
		URI responderURI = null;
		try {
			X509CertImpl certImpl = X509CertImpl.toImpl(cert);
			responderURI = getResponderURI(certImpl);
			if (responderURI == null) {
				throw new CertPathValidatorException(
						"No OCSP Responder URI in certificate");
			}
			certId = new CertId(issuerCert., certImpl.getSerialNumberObject());
		} catch (CertificateException ce) {
			throw new CertPathValidatorException(
					"Exception while encoding OCSPRequest", ce);
		} catch (IOException ioe) {
			throw new CertPathValidatorException(
					"Exception while encoding OCSPRequest", ioe);
		}
		OCSPResponse ocspResponse = check(Collections.singletonList(certId),
				responderURI, issuerCert, null);
		return (RevocationStatus) ocspResponse.getSingleResponse(certId);
	}

	*//**
	 * Obtains the revocation status of a certificate using OCSP. 112 * 113 * @param
	 * cert the certificate to be checked 114 * @param issuerCert the issuer
	 * certificate 115 * @param responderURI the URI of the OCSP responder 116 * @param
	 * responderCert the OCSP responder's certificate 117 * @param date the time
	 * the validity of the OCSP responder's certificate 118 * should be checked
	 * against. If null, the current time is used. 119 * @return the
	 * RevocationStatus 120 * @throws IOException if there is an exception
	 * connecting to or 121 * communicating with the OCSP responder 122 * @throws
	 * CertPathValidatorException if an exception occurs while 123 * encoding
	 * the OCSP Request or validating the OCSP Response 124
	 *//*
	public static RevocationStatus check(X509Certificate cert,
			X509Certificate issuerCert, URI responderURI,
			X509Certificate responderCert, Date date) throws IOException,
			CertPathValidatorException {
		CertId certId = null;
		try {
			X509CertImpl certImpl = X509CertImpl.toImpl(cert);
			certId = new CertId(issuerCert, certImpl.getSerialNumberObject());
		} catch (CertificateException ce) {
			throw new CertPathValidatorException(
					"Exception while encoding OCSPRequest", ce);
		} catch (IOException ioe) {
			throw new CertPathValidatorException(
					"Exception while encoding OCSPRequest", ioe);
		}
		OCSPResponse ocspResponse = check(Collections.singletonList(certId),
				responderURI, responderCert, date);
		return (RevocationStatus) ocspResponse.getSingleResponse(certId);
	}

	*//**
	 * 146 * Checks the revocation status of a list of certificates using OCSP.
	 * 147 * 148 * @param certs the CertIds to be checked 149 * @param
	 * responderURI the URI of the OCSP responder 150 * @param responderCert the
	 * OCSP responder's certificate 151 * @param date the time the validity of
	 * the OCSP responder's certificate 152 * should be checked against. If
	 * null, the current time is used. 153 * @return the OCSPResponse 154 * @throws
	 * IOException if there is an exception connecting to or 155 * communicating
	 * with the OCSP responder 156 * @throws CertPathValidatorException if an
	 * exception occurs while 157 * encoding the OCSP Request or validating the
	 * OCSP Response 158
	 *//*
	static OCSPResponse check(List<CertId> certIds, URI responderURI,
			X509Certificate responderCert, Date date) throws IOException,
			CertPathValidatorException {

		byte[] bytes = null;
		try {
			OCSPRequest request = new OCSPRequest(certIds);
			bytes = request.encodeBytes();
		} catch (IOException ioe) {
			throw new CertPathValidatorException(
					"Exception while encoding OCSPRequest", ioe);
		}

		InputStream in = null;
		OutputStream out = null;
		byte[] response = null;
		try {
			URL url = responderURI.toURL();
			if (debug != null) {
				debug.println("connecting to OCSP service at: " + url);
			}
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(CONNECT_TIMEOUT);
			con.setReadTimeout(CONNECT_TIMEOUT);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-type", "application/ocsp-request");
			con.setRequestProperty("Content-length",
					String.valueOf(bytes.length));
			out = con.getOutputStream();
			out.write(bytes);
			out.flush();
			// Check the response
			if (debug != null
					&& con.getResponseCode() != HttpURLConnection.HTTP_OK) {
				debug.println("Received HTTP error: " + con.getResponseCode()
						+ " - " + con.getResponseMessage());
			}
			in = con.getInputStream();
			int contentLength = con.getContentLength();
			if (contentLength == -1) {
				contentLength = Integer.MAX_VALUE;
			}
			response = new byte[contentLength > 2048 ? 2048 : contentLength];
			int total = 0;
			while (total < contentLength) {
				int count = in.read(response, total, response.length - total);
				if (count < 0)
					break;

				total += count;
				if (total >= response.length && total < contentLength) {
					response = Arrays.copyOf(response, total * 2);
				}
			}
			response = Arrays.copyOf(response, total);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ioe) {
					throw ioe;
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException ioe) {
					throw ioe;
				}
			}
		}

		OCSPResponse ocspResponse = null;
		try {
			ocspResponse = new OCSPResponse(response, date, responderCert);
		} catch (IOException ioe) {
			// response decoding exception
			throw new CertPathValidatorException(ioe);
		}
		if (ocspResponse.getResponseStatus() != ResponseStatus.SUCCESSFUL) {
			throw new CertPathValidatorException("OCSP response error: "
					+ ocspResponse.getResponseStatus());
		}

		// Check that the response includes a response for all of the
		// certs that were supplied in the request
		for (CertId certId : certIds) {
			SingleResponse sr = ocspResponse.getSingleResponse(certId);
			if (sr == null) {
				if (debug != null) {
					debug.println("No response found for CertId: " + certId);
				}
				throw new CertPathValidatorException(
						"OCSP response does not include a response for a "
								+ "certificate supplied in the OCSP request");
			}
			if (debug != null) {
				debug.println("Status of certificate (with serial number "
						+ certId.getSerialNumber() + ") is: "
						+ sr.getCertStatus());
			}
		}
		return ocspResponse;
	}

	*//**
	 * 267 * Returns the URI of the OCSP Responder as specified in the 268 *
	 * certificate's Authority Information Access extension, or null if 269 *
	 * not specified. 270 * 271 * @param cert the certificate 272 * @return the
	 * URI of the OCSP Responder, or null if not specified 273
	 *//*
	public static URI getResponderURI(X509Certificate cert) {
		try {
			return getResponderURI(X509CertImpl.toImpl(cert));
		} catch (CertificateException ce) {
			// treat this case as if the cert had no extension
			return null;
		}
	}

	static URI getResponderURI(X509CertImpl certImpl) {

		// Examine the certificate's AuthorityInfoAccess extension
		AuthorityInfoAccessExtension aia = certImpl
				.getAuthorityInfoAccessExtension();
		if (aia == null) {
			return null;
		}

		List<AccessDescription> descriptions = aia.getAccessDescriptions();
		for (AccessDescription description : descriptions) {
			if (description.getAccessMethod().equals(
					AccessDescription.Ad_OCSP_Id)) {

				GeneralName generalName = description.getAccessLocation();
				if (generalName.getType() == GeneralNameInterface.NAME_URI) {
					URIName uri = (URIName) generalName.getName();
					return uri.getURI();
				}
			}
		}
		return null;
	}

	*//**
	 * 308 * The Revocation Status of a certificate. 309
	 *//*
	public static interface RevocationStatus {
		public enum CertStatus {
			GOOD, REVOKED, UNKNOWN
		};

		*//**
		 * 314 * Returns the revocation status. 315
		 *//*
		CertStatus getCertStatus();

		*//**
		 * 318 * Returns the time when the certificate was revoked, or null 319
		 * * if it has not been revoked. 320
		 *//*
		Date getRevocationTime();

		*//**
		 * 323 * Returns the reason the certificate was revoked, or null if it
		 * 324 * has not been revoked. 325
		 *//*
		CRLReason getRevocationReason();

		*//**
		 * 329 * Returns a Map of additional extensions. 330
		 *//*
		Map<String, Extension> getSingleExtensions();
	}
}*/