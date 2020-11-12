package com.kensbunker.micronaut.broker;

import com.kensbunker.micronaut.broker.model.Symbol;
import com.kensbunker.micronaut.broker.store.InMemoryStore;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Controller("/markets")
public class MarketsController {

  private final InMemoryStore store;

  public MarketsController(final InMemoryStore store) {
    this.store = store;
  }

  @Operation(summary = "Returns all available markets")
  @ApiResponse(
      content = @Content(mediaType = MediaType.APPLICATION_JSON)
  )
  @Tag(name = "markets")
  @Get("/")
  public List<Symbol> all() {
    return store.getAllSymbols();
  }
}
