package com.learningproj.accounts;

import com.learningproj.accounts.dto.AccountsContactInfo;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API documentation",
				description = "ABC Bank accounts microservice",
				version = "v1",
				contact = @Contact(
						name = "Raja",
						email = "rajaro99999@gmail.com",
						url = "www.example.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "www.example.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "ABC Bank accounts microservice external documentation",
				url = "www.example.com"
		)
)
@EnableConfigurationProperties(value={AccountsContactInfo.class})
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
