package com.mikky.bank;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@OpenAPIDefinition(
		info = @Info(
				title = "Mikky Bank",
				description = "Backend Rest APIs for Mikky Bank",
				version = "v1.0",
				contact = @Contact(
						name = "Praveen Kumar Saw",
						email = "praveen.saw@outlook.com",
						url = "https://github.com/praveen8409/Mikky-Bank"
				),
				license = @License(
						name = "Mikky Bank",
						url = "https://github.com/praveen8409/Mikky-Bank"
				)
		)
)
public class MikkyBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MikkyBankApplication.class, args);
	}

}
