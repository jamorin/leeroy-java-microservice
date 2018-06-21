package com.example.leeroyweb;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WebController {

  private final RestTemplate restTemplate;
  private final Environment environment;

  public WebController(RestTemplateBuilder builder, Environment environment){
    this.restTemplate = builder
        .build();
    this.environment = environment;
  }

  @GetMapping("${web.endpoint}")
  public String get() {
    return restTemplate.getForObject(environment.getProperty("api.url"), String.class);
  }
}
