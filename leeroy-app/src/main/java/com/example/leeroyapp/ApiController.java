package com.example.leeroyapp;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

  private final Environment environment;

  public ApiController(Environment environment) {
    this.environment = environment;
  }

  @GetMapping("${api.endpoint}")
  public String leeroooooy() {
    return environment.getProperty("api.message");
  }

}
