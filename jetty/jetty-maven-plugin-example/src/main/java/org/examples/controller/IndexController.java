package org.examples.controller;

import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  @GetMapping(path = "/")
  public String index(Model model) {
    model.addAttribute("message", "Hello Jetty!");
    model.addAttribute("date", LocalDate.now());
    return "index";
  }
}
