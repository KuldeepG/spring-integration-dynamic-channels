package com.github.kuldeepg.springintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@EnableConfigurationProperties(ComplexProperties.class)
@EnableJpaRepositories
@IntegrationComponentScan
@PropertySource("classpath:/complex.properties")
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
