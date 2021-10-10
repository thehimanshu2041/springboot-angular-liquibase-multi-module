package com.trisul.controller;

import com.trisul.core.security.swagger.ApiSecuritySchemes;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@OpenAPIDefinition(
    info = @Info(title = "Trisul Service", description = "Trisul Service", version = "0.1"))
@ApiSecuritySchemes
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.trisul.data.access.repository")
@EntityScan("com.trisul.data.access.entity")
@SpringBootApplication(
    scanBasePackages = {
      "com.trisul.core.*",
      "com.trisul.data.access.*",
      "com.trisul.internal.service.*",
      "com.trisul.controller.*"
    })
public class Controller {
  public static void main(String[] args) {
    SpringApplication.run(Controller.class, args);
  }
}
