package com.trisul.data.access;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = {"com.trisul.core.*"})
public class DataAccess {
  public static void main(String[] args) {
    SpringApplication.run(DataAccess.class, args);
  }
}
