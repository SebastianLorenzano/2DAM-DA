package com.sl2425.da.sellersapp.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.sl2425.da.sellersapp.restapi.model.Entities")
public class SellersAppRestApiApplication {


	public static void main(String[] args) {
		SpringApplication.run(SellersAppRestApiApplication.class, args);
	}

}
