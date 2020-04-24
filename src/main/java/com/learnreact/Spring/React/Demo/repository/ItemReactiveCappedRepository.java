package com.learnreact.Spring.React.Demo.repository;

import com.learnreact.Spring.React.Demo.document.ItemCapped;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

/** @author a0r00rf */
public interface ItemReactiveCappedRepository extends ReactiveMongoRepository<ItemCapped, String> {

  @Tailable
  Flux<ItemCapped> findItemCappedBy();
}
