package com.learnreact.Spring.React.Demo.controller.v1;

import com.learnreact.Spring.React.Demo.constants.ItemConstants;
import com.learnreact.Spring.React.Demo.document.Item;
import com.learnreact.Spring.React.Demo.repository.ItemReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** @author a0r00rf */
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class ItemControllerTest {

  @Autowired WebTestClient webTestClient;

  @Autowired ItemReactiveRepository itemReactiveRepository;

  public List<Item> data() {
    return Arrays.asList(
        new Item(null, "Apple Phone", 950.0),
        new Item(null, "Apple Ipad", 700.0),
        new Item(null, "Apple macbook", 1000.0),
        new Item(null, "Apple watch", 200.0),
        new Item("ABC", "Apple HomePods", 10001.0));
  }

  @Before
  public void setUp() {

    itemReactiveRepository
        .deleteAll()
        .thenMany(Flux.fromIterable(data()))
        .flatMap(item -> itemReactiveRepository.save(item))
        .doOnNext(item -> System.out.println("Values "))
        .blockLast();
  }

  @Test
  public void getAllItems() {

    webTestClient
        .get()
        .uri(ItemConstants.ITEM_END_POINT_V1)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(Item.class)
        .hasSize(5);
  }

  @Test
  public void getAllItemsApproach2() {

    webTestClient
        .get()
        .uri(ItemConstants.ITEM_END_POINT_V1)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(Item.class)
        .hasSize(5)
        .consumeWith(
            response -> {
              List<Item> res = response.getResponseBody();
              res.forEach(
                  item -> {
                    assertTrue(item.getId() != null);
                  });
            });
  }

  @Test
  public void getAllItemsApproach3() {

    Flux<Item> itemFlux =
        webTestClient
            .get()
            .uri(ItemConstants.ITEM_END_POINT_V1)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .returnResult(Item.class)
            .getResponseBody();
    StepVerifier.create(itemFlux).expectSubscription().expectNextCount(5).verifyComplete();
  }
}
