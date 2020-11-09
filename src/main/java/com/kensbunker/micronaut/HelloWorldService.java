package com.kensbunker.micronaut;

import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class HelloWorldService {

  private static final Logger LOG = LoggerFactory.getLogger(HelloWorldService.class);

  @Property(name = "hello.service.greeting", defaultValue = "default value")
  private String greeting;

  @EventListener
  public void onStartup(StartupEvent startupEvent) {
    LOG.debug("Startup: {}", HelloWorldService.class.getSimpleName());
  }

  public String sayHi() {
    return greeting;
  }
}
