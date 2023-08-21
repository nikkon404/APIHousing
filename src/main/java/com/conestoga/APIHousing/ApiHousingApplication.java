package com.conestoga.APIHousing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ApiHousingApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiHousingApplication.class, args);
  }

  @RestController
  public static class HomeController {

    @GetMapping("/")
    public String home() {
      return "<h1>Welcome to the API Housing application!</h1>";
    }

    @Bean
    public ObjectMapper objectMapper() {
      return Jackson2ObjectMapperBuilder.json()
          .featuresToEnable(SerializationFeature.WRITE_NULL_MAP_VALUES)
          .build();
    }
  }
}
