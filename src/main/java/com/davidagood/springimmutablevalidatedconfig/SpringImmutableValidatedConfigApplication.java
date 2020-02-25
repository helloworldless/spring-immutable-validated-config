package com.davidagood.springimmutablevalidatedconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AppConfig.class})
public class SpringImmutableValidatedConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringImmutableValidatedConfigApplication.class, args);
	}

}
