package com.kensbunker.micronaut.broker.account;

import com.kensbunker.micronaut.broker.model.WatchList;
import com.kensbunker.micronaut.broker.store.InMemoryAccountStore;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import java.util.UUID;

@Controller("/account/watchlist")
public class WatchListController {

  static final UUID ACCOUNT_ID = UUID.randomUUID();
  private final InMemoryAccountStore store;

  public WatchListController(InMemoryAccountStore store) {
    this.store = store;
  }

  @Get(produces = MediaType.APPLICATION_JSON)
  public WatchList get() {
    return store.getWatchList(ACCOUNT_ID);
  }

  @Put(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
  public WatchList update(@Body WatchList watchList) {
    return store.updateWatchList(ACCOUNT_ID, watchList);
  }

  @Delete(
      value = "/{accountId}",
      consumes = MediaType.APPLICATION_JSON,
      produces = MediaType.APPLICATION_JSON)
  public void delete(@PathVariable UUID accountId) {
    store.deleteWatchList(accountId);
  }
}
