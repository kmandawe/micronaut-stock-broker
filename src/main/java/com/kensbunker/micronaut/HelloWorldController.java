package com.kensbunker.micronaut;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("${hello.controller.path}")
public class HelloWorldController {

  private final HelloWorldService service;

  public HelloWorldController(final HelloWorldService service) {
    this.service = service;
  }

  @Get("/")
  public String index() {
    return service.sayHi();
  }

}
