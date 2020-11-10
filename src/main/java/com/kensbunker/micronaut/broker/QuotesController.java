package com.kensbunker.micronaut.broker;

import com.kensbunker.micronaut.broker.model.Quote;
import com.kensbunker.micronaut.broker.store.InMemoryStore;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.util.Optional;

@Controller("/quotes")
public class QuotesController {

  private final InMemoryStore store;

  public QuotesController(InMemoryStore store) {
    this.store = store;
  }

  @Get("/{symbol}")
  public HttpResponse getQuote(String symbol) {
    Optional<Quote> maybeQuote = store.fetchQuote(symbol);
    return HttpResponse.ok(maybeQuote.get());
  }
}
