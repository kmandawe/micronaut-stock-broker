package com.kensbunker.micronaut.broker.store;

import com.kensbunker.micronaut.broker.model.Symbol;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Singleton;

@Singleton
public class InMemoryStore {

  private final List<Symbol> symbols;

  public InMemoryStore() {
    symbols =
        Stream.of("AAPL", "AMZN", "FB", "GOOG", "MSFT", "NFLX", "TSLA")
            .map(Symbol::new)
            .collect(Collectors.toList());
  }

  public List<Symbol> getAllSymbols() {

    return symbols;
  }
}
