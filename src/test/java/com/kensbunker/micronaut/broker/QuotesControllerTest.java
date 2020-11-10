package com.kensbunker.micronaut.broker;

import static io.micronaut.http.HttpRequest.GET;
import static org.assertj.core.api.Assertions.assertThat;

import com.kensbunker.micronaut.broker.model.Quote;
import com.kensbunker.micronaut.broker.model.Symbol;
import com.kensbunker.micronaut.broker.store.InMemoryStore;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MicronautTest
class QuotesControllerTest {

  private static final Logger LOG = LoggerFactory.getLogger(QuotesControllerTest.class);

  @Inject EmbeddedApplication application;

  @Inject
  @Client("/")
  RxHttpClient client;

  @Inject InMemoryStore store;

  @Test
  void returnsQuotePerSymbol() {
    final Quote apple = initRandom("APPL");
    store.update(apple);

    final Quote amazon = initRandom("AMZN");
    store.update(amazon);

    final Quote appleResult = client.toBlocking().retrieve(GET("/quotes/APPL"), Quote.class);
    LOG.debug("Result: {}", appleResult);
    assertThat(apple).isEqualToComparingFieldByField(appleResult);

    final Quote amazonResult = client.toBlocking().retrieve(GET("/quotes/AMZN"), Quote.class);
    LOG.debug("Result: {}", amazonResult);
    assertThat(amazon).isEqualToComparingFieldByField(amazonResult);
  }

  private Quote initRandom(String symbol) {
    return Quote.builder()
            .symbol(new Symbol(symbol))
            .bid(randomValue())
            .ask(randomValue())
            .lastPrice(randomValue())
            .volume(randomValue())
    .build();
  }

  private BigDecimal randomValue() {
    return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1, 100));
  }
}
