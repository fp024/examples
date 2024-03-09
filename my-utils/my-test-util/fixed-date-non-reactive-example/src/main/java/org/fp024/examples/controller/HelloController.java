package org.fp024.examples.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {
  @GetMapping("/hello")
  public Mono<String> hello(Mono<Authentication> auth) {
    return auth.map(a -> "Hello " + a.getName());
  }

  @GetMapping("ciao")
  public Mono<String> ciao() {
    return Mono.just("Ciao!");
  }
}
