package org.fp024.examples.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @GetMapping("/hello")
  public String hello(Authentication auth) {
    return "Hello " + auth.getName();
  }

  @GetMapping("ciao")
  public String ciao() {
    return "Ciao!";
  }
}
