package br.com.mcm.ms_api_items;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsApiItemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsApiItemsApplication.class, args);
	}

}
