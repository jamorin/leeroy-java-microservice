package com.example.leeroyapp;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ApiController {

  private final RestTemplate restTemplate;
  private final Environment environment;

  public ApiController(RestTemplateBuilder builder, Environment environment) {
    this.restTemplate = builder.build();
    this.environment = environment;
  }

  @GetMapping("/api")
  public String api() {
    return "api response from " + environment.getProperty("spring.application.name", String.class) + "!!";
  }

  @GetMapping("/proxy")
  public String proxy(@RequestParam(name = "url", defaultValue = "http://localhost:8080/leeroy/api") String url) {
    return "proxied: " + restTemplate.getForObject(url, String.class);
  }

  @GetMapping("/name")
  public String name() {
    return "hello my name is " + environment.getProperty("spring.application.name", String.class);
  }
}
