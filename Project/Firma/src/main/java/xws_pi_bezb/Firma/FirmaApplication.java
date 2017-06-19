package xws_pi_bezb.Firma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirmaApplication.class, args);
		FirmaClient fc = new FirmaClient();
		fc.posaljiNalogZaPrenos();
		//fc.posaljiZahtevZaIzvod();
		/*
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        FirmaClient firmaClient = ctx.getBean(FirmaClient.class);
        //NalogZaPrenosResponse response = firmaClient.posaljiNalogZaPrenos();
        System.out.println("Gledamo: " + response);
        */
	}
}
