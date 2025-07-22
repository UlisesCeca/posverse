package com.ulises.posverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {
		"com.ulises.posverse.rest.api",
		"com.ulises.posverse.persistence",
		"com.ulises.posverse.exceptions.handlers",
		"com.ulises.posverse.domain.services.impl",
		"com.ulises.posverse.common.mappers",
		"com.ulises.posverse.config",
		"com.ulises.posverse.common.redis.keygenerators"
})
@EnableCaching
public class PosverseApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosverseApplication.class, args);
	}

}
