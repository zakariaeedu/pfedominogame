package com.dominogame.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.dominogame.backend")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class DominogamepfeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DominogamepfeApplication.class, args);
	}

}
