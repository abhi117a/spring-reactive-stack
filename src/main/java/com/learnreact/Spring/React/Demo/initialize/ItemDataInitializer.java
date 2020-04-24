package com.learnreact.Spring.React.Demo.initialize;

import com.learnreact.Spring.React.Demo.document.Item;
import com.learnreact.Spring.React.Demo.document.ItemCapped;
import com.learnreact.Spring.React.Demo.repository.ItemReactiveCappedRepository;
import com.learnreact.Spring.React.Demo.repository.ItemReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/** @author a0r00rf */
@Component
@Profile("!test")
@Slf4j
public class ItemDataInitializer implements CommandLineRunner {

  @Autowired ItemReactiveRepository itemReactiveRepository;

  @Autowired MongoOperations mongoOperations;

  @Autowired ItemReactiveCappedRepository itemReactiveCappedRepository;

  @Override
  public void run(String... args) throws Exception {

    initialDataSetUp();
    createCappedCollections();
    intitialDatasetupCappedCollection();
  }

  private void createCappedCollections() {

    mongoOperations.dropCollection(ItemCapped.class);
    mongoOperations.createCollection(
        ItemCapped.class, CollectionOptions.empty().maxDocuments(20).size(5000).capped());
  }

  public List<Item> data() {
    return Arrays.asList(
        new Item(null, "Apple Phone", 950.0),
        new Item(null, "Apple Ipad", 700.0),
        new Item(null, "Apple macbook", 1000.0),
        new Item(null, "Apple watch", 200.0),
        new Item("ABC", "Apple HomePods", 10001.0));
  }

  private void initialDataSetUp() {

    itemReactiveRepository
        .deleteAll()
        .thenMany(Flux.fromIterable(data()))
        .flatMap(item -> itemReactiveRepository.save(item))
        .thenMany(itemReactiveRepository.findAll())
        .subscribe(
            item -> {
              System.out.println("Item  inserted from Command Line Runner");
            });
  }

  private void intitialDatasetupCappedCollection() {
    Flux<ItemCapped> itemCappedFlux =
        Flux.interval(Duration.ofMillis(1000))
            .map(i -> new ItemCapped(null, "RandomItem " + i, (100.00 + i)));

    itemReactiveCappedRepository
        .insert(itemCappedFlux)
        .subscribe(
            itemCapped -> {
              log.info("Item Inserted " + itemCapped);
            });
  }
}
