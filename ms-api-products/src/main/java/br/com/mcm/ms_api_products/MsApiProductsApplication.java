package br.com.mcm.ms_api_products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class MsApiProductsApplication {

	private static final Logger LOG = LoggerFactory.getLogger(MsApiProductsApplication.class);

	public static void main(String[] args) {
		LOG.info("[step:to-be-init] [id:1] Inicializando o Spring");
		System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "dev");
		SpringApplication.run(MsApiProductsApplication.class, args);
		LOG.info("[step:inittialized] [id:2] Spring inicializado..");
	}
}
