package com.example.leeroyapp;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class ApiController {

  private final RestTemplate restTemplate;
  private final Environment environment;

  public ApiController(RestTemplateBuilder builder, Environment environment) {
    this.restTemplate = builder
        .setConnectTimeout(2_000)
        .setReadTimeout(5_000)
        .build();
    this.environment = environment;
  }

  @GetMapping("/api")
  public String api() {
    HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String remoteAddr = servletRequest.getRemoteAddr();
    return "hello " + remoteAddr +  " api response from " + environment.getProperty("spring.application.name", String.class) + "!!";
  }

  @GetMapping("/proxy")
  public String proxy(@RequestParam(name = "url") String url) {
    return "proxied: " + restTemplate.getForObject(url, String.class);
  }

  @GetMapping("/name")
  public String name() {
    HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String remoteAddr = servletRequest.getRemoteAddr();
    return "hello " + remoteAddr + " my name is " + environment.getProperty("spring.application.name", String.class);
  }

  @GetMapping("/metadata")
  public Map metadata() {
    return restTemplate.getForObject("http://169.254.170.2/v2/metadata", Map.class);
  }

  @GetMapping("/stats")
  public Map stats() {
    return restTemplate.getForObject("http://169.254.170.2/v2/stats", Map.class);
  }
}
