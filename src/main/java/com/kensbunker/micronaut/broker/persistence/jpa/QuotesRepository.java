package com.kensbunker.micronaut.broker.persistence.jpa;

import com.kensbunker.micronaut.broker.model.Quote;
import com.kensbunker.micronaut.broker.persistence.model.QuoteEntity;
import com.kensbunker.micronaut.broker.persistence.model.SymbolEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuotesRepository extends CrudRepository<QuoteEntity, Integer> {

  @Override
  List<QuoteEntity> findAll();

  Optional<QuoteEntity> findBySymbol(SymbolEntity entity);
}
