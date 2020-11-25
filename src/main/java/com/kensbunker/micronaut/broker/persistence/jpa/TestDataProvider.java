package com.kensbunker.micronaut.broker.persistence.jpa;

import com.kensbunker.micronaut.broker.persistence.model.SymbolEntity;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import java.util.stream.Stream;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to insert data into db on startup
 */
@Singleton
@Requires(notEnv = Environment.TEST)
public class TestDataProvider {

  private static final Logger LOG = LoggerFactory.getLogger(TestDataProvider.class);
  private final SymbolsRepository symbols;

  public TestDataProvider(SymbolsRepository symbols) {
    this.symbols = symbols;
  }

  @EventListener
  public void init(StartupEvent event) {
    if (symbols.findAll().isEmpty()) {
      LOG.info("Adding test data as empty database was found!");
      Stream.of("AAPL", "AMZN", "FB", "TSLA")
          .map(SymbolEntity::new)
          .forEach(symbols::save);
    }
  }
}
