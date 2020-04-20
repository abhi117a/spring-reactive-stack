package com.learnreact.Spring.React.Demo.initialize;

import com.learnreact.Spring.React.Demo.document.Item;
import com.learnreact.Spring.React.Demo.repository.ItemReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

/** @author a0r00rf */
@Component
public class ItemDataInitializer implements CommandLineRunner {

  @Autowired ItemReactiveRepository itemReactiveRepository;

  @Override
  public void run(String... args) throws Exception {

    initialDataSetUp();
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
}
