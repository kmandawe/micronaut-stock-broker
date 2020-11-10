package com.kensbunker.micronaut.broker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
class MarketsControllerTest {

  @Inject
  EmbeddedApplication application;

  @Inject
  @Client("/")
  RxHttpClient client;

  @Test
  void returnsListOfMarkets() {
    final List<LinkedHashMap<String, String>> result = client.toBlocking().retrieve("/markets", List.class);
    assertEquals(7, result.size());
    assertThat(result)
        .extracting(entry -> entry.get("value"))
        .containsExactlyInAnyOrder("AAPL", "AMZN", "FB", "GOOG", "MSFT", "NFLX", "TSLA");
  }

}