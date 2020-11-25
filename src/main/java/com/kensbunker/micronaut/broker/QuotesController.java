package com.kensbunker.micronaut.broker;

import com.kensbunker.micronaut.broker.error.CustomError;
import com.kensbunker.micronaut.broker.model.Quote;
import com.kensbunker.micronaut.broker.persistence.jpa.QuotesRepository;
import com.kensbunker.micronaut.broker.persistence.model.QuoteDTO;
import com.kensbunker.micronaut.broker.persistence.model.QuoteEntity;
import com.kensbunker.micronaut.broker.persistence.model.SymbolEntity;
import com.kensbunker.micronaut.broker.store.InMemoryStore;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/quotes")
public class QuotesController {

  private final InMemoryStore store;
  private final QuotesRepository quotes;

  public QuotesController(InMemoryStore store,
      QuotesRepository quotes) {
    this.store = store;
    this.quotes = quotes;
  }

  @Operation(summary = "Returns a quote for the given symbol.")
  @ApiResponse(
      content = @Content(mediaType = MediaType.APPLICATION_JSON)
  )
  @ApiResponse(responseCode = "400", description = "Invalid symbol specified")
  @Tag(name = "quotes")
  @Get("/{symbol}")
  public HttpResponse getQuote(String symbol) {
    Optional<Quote> maybeQuote = store.fetchQuote(symbol);
    if (!maybeQuote.isPresent()) {
      final CustomError notFound = CustomError.builder().status(HttpStatus.NOT_FOUND.getCode())
          .error(HttpStatus.NOT_FOUND.name())
          .message("quote for symbol not available")
          .path("/quotes/" + symbol)
          .build();
      return HttpResponse.notFound(notFound);
    }
    return HttpResponse.ok(maybeQuote.get());
  }

  @Get("/jpa")
  public List<QuoteEntity> getAllQuotesViaJPA() {
    return quotes.findAll();
  }

  @Operation(summary = "Returns a quote for the given symbol. Fetched from the database via JPA")
  @ApiResponse(
      content = @Content(mediaType = MediaType.APPLICATION_JSON)
  )
  @ApiResponse(responseCode = "400", description = "Invalid symbol specified")
  @Tag(name = "quotes")
  @Get("/{symbol}/jpa")
  public HttpResponse getQuoteViaJPA(String symbol) {
    Optional<QuoteEntity> maybeQuote = quotes.findBySymbol(new SymbolEntity(symbol));
    if (!maybeQuote.isPresent()) {
      final CustomError notFound = CustomError.builder().status(HttpStatus.NOT_FOUND.getCode())
          .error(HttpStatus.NOT_FOUND.name())
          .message("quote for symbol not available in db.")
          .path("/quotes/" + symbol + "/jpa")
          .build();
      return HttpResponse.notFound(notFound);
    }
    return HttpResponse.ok(maybeQuote.get());
  }

  @Get("/jpa/ordered/desc")
  public List<QuoteDTO> orderedDesc() {
    return quotes.listOrderByVolumeDesc();
  }

  @Get("/jpa/ordered/asc")
  public List<QuoteDTO> orderedAsc() {
    return quotes.listOrderByVolumeAsc();
  }

  @Get("/jpa/volume/{volume}")
  public List<QuoteDTO> volumeFilter(@PathVariable BigDecimal volume) {
    return quotes.findByVolumeGreaterThanOrderByVolumeAsc(volume);
  }

  @Get("/jpa/pagination{?page,volume}")
  public List<QuoteDTO> volumeFilterPagination(@QueryValue Optional<Integer> page, @QueryValue Optional<BigDecimal> volume) {
    int myPage = page.isPresent() ? page.get() : 0;
    BigDecimal myVolume = volume.isPresent() ? volume.get() : BigDecimal.ZERO;
    return quotes.findByVolumeGreaterThan(myVolume, Pageable.from(myPage, 2));
  }


  @Get("/jpa/pagination/{page}")
  public List<QuoteDTO> allWithPagination(@PathVariable int page) {
    return quotes.list(Pageable.from(page, 5)).getContent();
  }


}
