package com.awards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.awards")
@EntityScan(basePackages = "com.awards")
@ComponentScan(basePackages = { "com.awards" })
public class GoldenRaspberryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldenRaspberryApplication.class, args);
	}

}
