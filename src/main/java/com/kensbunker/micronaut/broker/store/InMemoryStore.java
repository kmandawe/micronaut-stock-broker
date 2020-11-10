package com.kensbunker.micronaut.broker.store;

import com.kensbunker.micronaut.broker.model.Quote;
import com.kensbunker.micronaut.broker.model.Symbol;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Singleton;

@Singleton
public class InMemoryStore {

  private final List<Symbol> symbols;
  private final Map<String, Quote> cachedQuotes = new HashMap<>();
  private ThreadLocalRandom current = ThreadLocalRandom.current();

  public InMemoryStore() {
    symbols =
        Stream.of("AAPL", "AMZN", "FB", "GOOG", "MSFT", "NFLX", "TSLA")
            .map(Symbol::new)
            .collect(Collectors.toList());
    symbols.forEach(symbol -> {
      cachedQuotes.put(symbol.getValue(), randomQuote(symbol));
    });
  }

  private Quote randomQuote(final Symbol symbol) {
    return Quote.builder()
        .symbol(symbol)
        .bid(randomValue())
        .ask(randomValue())
        .lastPrice(randomValue())
        .volume(randomValue())
        .build();
  }

  public List<Symbol> getAllSymbols() {

    return symbols;
  }

  public Optional<Quote> fetchQuote(final String symbol) {
    return Optional.ofNullable(cachedQuotes.get(symbol));
  }

  private BigDecimal randomValue() {
    return BigDecimal.valueOf(current.nextDouble(1, 100));
  }

  public void update(final Quote quote) {
    cachedQuotes.put(quote.getSymbol().getValue(), quote);
  }
}
