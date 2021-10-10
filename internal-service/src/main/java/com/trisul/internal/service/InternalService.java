package com.trisul.internal.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.trisul.core.*", "com.trisul.data.access.*"})
public class InternalService {
  public static void main(String[] args) {
    SpringApplication.run(InternalService.class, args);
  }
}
