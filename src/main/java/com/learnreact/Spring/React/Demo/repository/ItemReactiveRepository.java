package com.learnreact.Spring.React.Demo.repository;

import com.learnreact.Spring.React.Demo.document.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/** @author a0r00rf */
public interface ItemReactiveRepository extends ReactiveMongoRepository<Item, String> {

  Mono<Item> findByDescription(String description);
}
