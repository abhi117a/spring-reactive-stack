package com.learnreact.Spring.React.Demo.controller.v1;

import com.learnreact.Spring.React.Demo.constants.ItemConstants;
import com.learnreact.Spring.React.Demo.document.Item;
import com.learnreact.Spring.React.Demo.repository.ItemReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** @author a0r00rf */
@RestController
@Slf4j
public class ItemController {

  @Autowired ItemReactiveRepository itemReactiveRepository;

  @GetMapping(ItemConstants.ITEM_END_POINT_V1)
  public Flux<Item> getAllItems() {

    return itemReactiveRepository.findAll();
  }

  @GetMapping(ItemConstants.ITEM_END_POINT_V1 + "/{id}")
  public Mono<ResponseEntity<Item>> getItem(@PathVariable String id) {
    return itemReactiveRepository
        .findById(id)
        .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NO_CONTENT));
  }

  @PostMapping(ItemConstants.ITEM_END_POINT_V1)
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Item> createItem(@RequestBody Item item) {
    return itemReactiveRepository.save(item);
  }

  @DeleteMapping(ItemConstants.ITEM_END_POINT_V1 + "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<Void> deleteItem(@PathVariable String id) {
    return itemReactiveRepository.deleteById(id);
  }

  @PutMapping(ItemConstants.ITEM_END_POINT_V1 + "/{id}")
  public Mono<ResponseEntity<Item>> updateItem(@PathVariable String id, @RequestBody Item item) {
    return itemReactiveRepository
        .findById(id)
        .flatMap(
            item1 -> {
              item1.setPrice(item.getPrice());
              item1.setDescription(item.getDescription());
              return itemReactiveRepository.save(item1);
            })
        .map(updatedItem -> new ResponseEntity<>(updatedItem, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
