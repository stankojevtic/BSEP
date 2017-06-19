package xws_pi_bezb.Firma.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;

@Configuration
public class AppConfig {
	
	@Bean
    public Wss4jSecurityInterceptor securityInterceptor() throws Exception {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();

        // set security actions
        securityInterceptor.setSecurementActions("Signature Encrypt");

        // sign the request
        securityInterceptor.setSecurementUsername("primer");
        securityInterceptor.setSecurementPassword("primer");
        securityInterceptor.setSecurementSignatureCrypto(getCryptoFactoryBean().getObject());

        // encrypt the request
        securityInterceptor.setSecurementEncryptionUser("primer");
        securityInterceptor.setSecurementEncryptionCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setSecurementEncryptionParts("{Content}{http://nalogzaprenos.ws.xml.poslovna.bezbednost/}NalogZaPrenosRequest");

        return securityInterceptor;
    }
	
	@Bean
    public CryptoFactoryBean getCryptoFactoryBean() throws IOException {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStorePassword("primer");
        cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("primer.jks"));
        return cryptoFactoryBean;
    }
	
/*
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("xws_pi_bezb.Firma.wsdl");
		return marshaller;
	}
	@Bean
	public FirmaClient studentClient(Jaxb2Marshaller marshaller) {
		FirmaClient client = new FirmaClient();
		client.setDefaultUri("http://localhost:9000/ws/NalogZaPrenos.wsdl");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	*/
}
