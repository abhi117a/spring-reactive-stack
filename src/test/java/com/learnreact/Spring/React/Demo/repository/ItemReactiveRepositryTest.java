package com.learnreact.Spring.React.Demo.repository;

import com.learnreact.Spring.React.Demo.document.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

/** @author a0r00rf */
@DataMongoTest
@RunWith(SpringRunner.class)
@DirtiesContext
public class ItemReactiveRepositryTest {

  @Autowired ItemReactiveRepository itemReactiveRepository;

  List<Item> itemList =
      Arrays.asList(
          new Item(null, "Apple Phone", 950.0),
          new Item(null, "Apple Ipad", 700.0),
          new Item(null, "Apple macbook", 1000.0),
          new Item(null, "Apple watch", 200.0),
          new Item("ABC", "Apple HomePods", 10001.0));

  @Before
  public void testSetUp() {

    itemReactiveRepository
        .deleteAll()
        .thenMany(Flux.fromIterable(itemList))
        .flatMap(itemReactiveRepository::save)
        .doOnNext((item -> System.out.println(item)))
        .blockLast();
  }

  @Test
  public void getAllItems() {
    StepVerifier.create(itemReactiveRepository.findAll())
        .expectSubscription()
        .expectNextCount(5l)
        .verifyComplete();
  }

  @Test
  public void getItemById() {

    StepVerifier.create(itemReactiveRepository.findById("ABC"))
        .expectSubscription()
        .expectNextMatches(item -> item.getDescription().equals("Apple HomePods"))
        .verifyComplete();
  }

  @Test
  public void getItemByDescription() {

    StepVerifier.create(
            itemReactiveRepository.findByDescription("Apple HomePods").log("findItem: "))
        .expectSubscription()
        .expectNextCount(1)
        .verifyComplete();
  }

  @Test
  public void saveItem() {

    Item item = new Item(null, "Google Pixel", 350.55);
    Mono<Item> testItem = itemReactiveRepository.save(item);
    StepVerifier.create(testItem.log("ItemSaved: "))
        .expectSubscription()
        .expectNextMatches(
            item1 -> item1.getId() != null && item1.getDescription().equals("Google Pixel"))
        .verifyComplete();
  }

  @Test
  public void updateItem() {

    Mono<Item> updatedItem =
        itemReactiveRepository
            .findByDescription("Apple HomePods") // Fetching a selected value
            .map(
                item -> {
                  item.setPrice(5000.01); // Setting a new Price to the item
                  return item;
                })
            .flatMap(
                item -> {
                  return itemReactiveRepository.save(item); // Saving again to the database
                });

    StepVerifier.create(updatedItem)
        .expectSubscription()
        .expectNextMatches(item -> item.getPrice().equals(5000.01))
        .verifyComplete();
  }

  @Test
  public void deleteById() {

    Mono<Void> deletetedItem =
        itemReactiveRepository
            .findById("ABC")
            .map(item -> item.getId())
            .flatMap(id -> itemReactiveRepository.deleteById(id));

    StepVerifier.create(deletetedItem.log()).expectSubscription().verifyComplete();
    StepVerifier.create(itemReactiveRepository.findAll())
        .expectSubscription()
        .expectNextCount(4)
        .verifyComplete();
  }

  @Test
  public void deleteItem() {

    Mono<Void> deletetedItem =
        itemReactiveRepository
            .findByDescription("Apple HomePods")
            .flatMap(
                item -> {
                  return itemReactiveRepository.delete(item);
                });

    StepVerifier.create(deletetedItem.log()).expectSubscription().verifyComplete();
    StepVerifier.create(itemReactiveRepository.findAll())
        .expectSubscription()
        .expectNextCount(4)
        .verifyComplete();
  }
}
