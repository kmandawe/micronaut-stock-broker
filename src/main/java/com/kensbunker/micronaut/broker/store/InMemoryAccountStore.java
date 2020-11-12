package com.kensbunker.micronaut.broker.store;

import com.kensbunker.micronaut.broker.model.WatchList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.inject.Singleton;

@Singleton
public class InMemoryAccountStore {

  private final Map<UUID, WatchList> watchListPerAccount = new HashMap<>();

  public WatchList getWatchList(UUID accountId) {
    return watchListPerAccount.getOrDefault(accountId, new WatchList());
  }

  public WatchList updateWatchList(UUID accountId, WatchList watchList) {
    watchListPerAccount.put(accountId, watchList);
    return getWatchList(accountId);
  }

  public void deleteWatchList(UUID accountId) {
    watchListPerAccount.remove(accountId);
  }
}
