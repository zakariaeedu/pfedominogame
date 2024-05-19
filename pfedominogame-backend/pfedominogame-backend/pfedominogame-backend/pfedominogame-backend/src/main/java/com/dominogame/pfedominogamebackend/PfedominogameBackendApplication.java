package com.dominogame.pfedominogamebackend;
import com.dominogame.pfedominogamebackend.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@SpringBootApplication
public class PfedominogameBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfedominogameBackendApplication.class, args);
		System.out.println("db configured ");
        System.out.println("Hello, world!");

		//http://localhost:8080/swagger-ui/index.html

	}
}


