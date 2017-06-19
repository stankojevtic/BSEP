package xws_pi_bezb.Firma;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import javax.crypto.SecretKey;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.soap.SOAPException;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.w3c.dom.Document;

import bezbednost.poslovna.xml.ws.izvod.IzvodRequest;
import bezbednost.poslovna.xml.ws.izvod.IzvodResponse;
import bezbednost.poslovna.xml.ws.nalogzaprenos.NalogZaPrenosRequest;
import bezbednost.poslovna.xml.ws.nalogzaprenos.NalogZaPrenosResponse;
import bezbednost.poslovna.xml.ws.nalogzaprenos.TNalog;
import bezbednost.poslovna.xml.ws.nalogzaprenos.TPodaciORacunu;
import xws_pi_bezb.xml.secutiry.Base64Utility;
import xws_pi_bezb.xml.secutiry.DocumentLoader;
import xws_pi_bezb.xml.secutiry.KeyStoreReader;
import xws_pi_bezb.xml.secutiry.SoapPoruka;
import xws_pi_bezb.xml.secutiry.XMLEncryptionUtility;
import xws_pi_bezb.xml.secutiry.XMLSigningUtility;

public class FirmaClient extends WebServiceGatewaySupport {

	public void posaljiNalogZaPrenos() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(NalogZaPrenosRequest.class, NalogZaPrenosResponse.class);
		setMarshaller(marshaller);
		setUnmarshaller(marshaller);

		NalogZaPrenosRequest request = new NalogZaPrenosRequest();
		TNalog nalog = new TNalog();
		nalog.setDuznik("Duznika - bla bla");
		nalog.setPrimalac("Primalac bla bla");
		try {
			nalog.setDatumNaloga(DatatypeFactory.newInstance().newXMLGregorianCalendarDate(2017, 4, 15, 1));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			nalog.setDatumValute(DatatypeFactory.newInstance().newXMLGregorianCalendarDate(2017, 4, 10, 1));
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TPodaciORacunu duznikRacun = new TPodaciORacunu();
		duznikRacun.setModel(90);
		duznikRacun.setPozivNaBroj("neki broj...");
		duznikRacun.setRacun("111-1112223334445-67");

		TPodaciORacunu poverilacRacun = new TPodaciORacunu();
		poverilacRacun.setModel(90);
		poverilacRacun.setPozivNaBroj("neki broj...111");
		poverilacRacun.setRacun("111-2223334445556-78");

		nalog.setDuznikRacun(duznikRacun);
		nalog.setIznos(new BigDecimal(200000));
		nalog.setPoverilacRacun(poverilacRacun);
		nalog.setSvrhaPlacanja("Svrha placanja - bla bla");

		request.setIDPoruke("nalog-za-placanje");
		request.setHitno(true);
		request.setNalog(nalog);
		request.setOznakaValute("RSD");

/*
		File file0 = new File("univerzitet.xml");
		File file = new File("test.xml");
		File file1 = new File("test.xml");
		File file2 = new File("test.xml");
		File file3 = new File("test.xml");
		// StringWriter sw = new StringWriter();
		// String xmlDoc = "";
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(NalogZaPrenosRequest.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(request, file);

			JAXBContext jaxbContext1 = JAXBContext.newInstance(NalogZaPrenosRequest.class);
			Marshaller jaxbMarshaller1 = jaxbContext1.createMarshaller();

			// output pretty printed
			jaxbMarshaller1.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			jaxbMarshaller1.marshal(request, file1);

			JAXBContext jaxbContext2 = JAXBContext.newInstance(NalogZaPrenosRequest.class);
			Marshaller jaxbMarshaller2 = jaxbContext2.createMarshaller();

			// output pretty printed
			jaxbMarshaller2.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "/seme/NalogZaPrenos.xsd");
			jaxbMarshaller2.marshal(request, file2);

			JAXBContext jaxbContext3 = JAXBContext.newInstance(NalogZaPrenosRequest.class);
			Marshaller jaxbMarshaller3 = jaxbContext3.createMarshaller();

			// output pretty printed
			jaxbMarshaller3.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "/seme/NalogZaPrenos.xsd");
			jaxbMarshaller3.marshal(request, file3);
			// jaxbMarshaller.marshal(request, sw);
			// xmlDoc = sw.toString();
			// jaxbMarshaller.marshal(request, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

		Document document = DocumentLoader.loadDocument(file);

		KeyStoreReader ksReader = new KeyStoreReader();
		XMLEncryptionUtility encUtility = new XMLEncryptionUtility();
		XMLSigningUtility sigUtility = new XMLSigningUtility();

		SecretKey secretKey = encUtility.generateDataEncryptionKey();
		Base64Utility.encode(secretKey.getEncoded());
		Certificate cert = ksReader.readCertificate("primer.jks", "primer", "primer");
		document = encUtility.encrypt(document, secretKey, cert);
		PrivateKey privateKey = ksReader.readPrivateKey("primer.jks", "primer", "primer", "primer");
		document = sigUtility.signDocument(document, privateKey, cert);
		// DocumentLoader.saveDocument(document, "testProvera5.xml");
*/
		File file = new File("test.xml");
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(NalogZaPrenosRequest.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(request, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		KeyStoreReader ksReader = new KeyStoreReader();
		XMLEncryptionUtility encUtility = new XMLEncryptionUtility();
		XMLSigningUtility sigUtility = new XMLSigningUtility();
		
		Document document4 = DocumentLoader.loadDocument(file);

		SecretKey secretKey4 = encUtility.generateDataEncryptionKey();
		Base64Utility.encode(secretKey4.getEncoded());
		Certificate cert4 = ksReader.readCertificate("primer.jks", "primer", "primer");
		document4 = encUtility.encrypt(document4, secretKey4, cert4);
		PrivateKey privateKey4 = ksReader.readPrivateKey("primer.jks", "primer", "primer", "primer");
		document4 = sigUtility.signDocument(document4, privateKey4, cert4);
		
/*
		DocumentLoader.saveDocument(document4, "testProvera9.xml");
		Document temp = DocumentLoader.loadDocument("testProvera9.xml");

		boolean res = sigUtility.verifySignature(temp);
		boolean res1 = sigUtility.verifySignature(temp1);
		
		boolean res12 = sigUtility.verifySignature(document4);
		if (res12) {
			System.out.println("\n===== Potpis je validan, dokument se desifruje =====");
			document4 = encUtility.decrypt(document4, privateKey4);
			DocumentLoader.saveDocument(document4, "testADSASD.xml");
			System.out.println("\n===== Desifrovanje zavrseno, tacka B je primila dokument =====");
			System.out.println("===== Za pregled rezultujucih dokumenata otvoriti files/data/xml folder =====");
		} else {
			System.out.println("\n===== Potpis nije validan, dokument se odbacuje =====");
		}
*/
		
		String soapMessage = "";
		try {
			soapMessage = SoapPoruka.porukaNalogZaPrenos(document4);
		} catch (SOAPException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		try {
			SoapPoruka.callSoapService(soapMessage, "http://localhost:9000/ws/NalogZaPrenos");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}


		
		/*
		String uri = "http://localhost:9000/ws/NalogZaPrenos";
		Object o = getWebServiceTemplate().marshalSendAndReceive(uri, request);
		NalogZaPrenosResponse response = (NalogZaPrenosResponse) o;
		System.out.println("nalog za prenos odgovor: " + response.getOdgovor());
		*/ 

	}

	public void posaljiZahtevZaIzvod() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(IzvodRequest.class, IzvodResponse.class);
		setMarshaller(marshaller);
		setUnmarshaller(marshaller);

		IzvodRequest request = new IzvodRequest();
		String uri = "http://localhost:9000/ws/Izvod";
		Object o = getWebServiceTemplate().marshalSendAndReceive(uri, request);
		IzvodResponse response = (IzvodResponse) o;
		System.out.println("primljen izvod");

	}

}
