package com.kensbunker.micronaut;

import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Value;
import javax.inject.Singleton;

@Singleton
public class HelloWorldService {

  @Property(name = "hello.service.greeting", defaultValue = "default value")
  private String greeting;

  public String sayHi() {
    return greeting;
  }
}
