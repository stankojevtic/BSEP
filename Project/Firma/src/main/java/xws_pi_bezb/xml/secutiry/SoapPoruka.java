package xws_pi_bezb.xml.secutiry;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.w3c.dom.Document;

public class SoapPoruka {
	
	public static String porukaNalogZaPrenos(Document document) throws SOAPException, IOException {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String serverURI = "http://xml.poslovna.bezbednost/ws/";
		String sigURI = "http://www.w3.org/2000/09/xmldsig#";
		String xencURI = "http://www.w3.org/2001/04/xmlenc#";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("nzp", serverURI);
		envelope.addNamespaceDeclaration("ds", sigURI);
		envelope.addNamespaceDeclaration("xenc", xencURI);

		SOAPBody soapBody = envelope.getBody();
		soapBody.addDocument(document);
		System.out.println(soapMessage.toString());
		soapMessage.saveChanges();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		soapMessage.writeTo(out);
		String strMsg = new String(out.toByteArray());
		return strMsg;
	}
	
	public static String callSoapService(String xmlRequest, String apiBanke) throws IOException {
		URL url = new URL(apiBanke);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "text/xml");
		// Send the request
		java.io.OutputStreamWriter wr = new java.io.OutputStreamWriter(conn.getOutputStream());
		wr.write(xmlRequest);
		wr.flush();

		// Read the response
		java.io.BufferedReader rd = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
		String line;
		String retVal = "";
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
			retVal += line;
		}
		conn.disconnect();

		return retVal;
	}
}
	