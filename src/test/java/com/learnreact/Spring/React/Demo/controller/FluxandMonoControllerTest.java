package com.learnreact.Spring.React.Demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** @author a0r00rf */
@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxandMonoControllerTest {

  @Autowired WebTestClient webTestClient;

  @Test
  public void fluxApproach1() {

    Flux<Integer> integerFlux =
        webTestClient
            .get()
            .uri("/flux")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(Integer.class)
            .getResponseBody();

    StepVerifier.create(integerFlux)
        .expectSubscription()
        .expectNext(1)
        .expectNext(2)
        .expectNext(3)
        .expectNext(4);
  }

  @Test
  public void fluxApproach2() {

    webTestClient
        .get()
        .uri("/flux")
        .accept(MediaType.ALL)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(Integer.class)
        .hasSize(4);
  }

  @Test
  public void fluxApproach3() {

    List<Integer> expectedList = Arrays.asList(1, 2, 3, 4);

    EntityExchangeResult<List<Integer>> entityExchangeResult =
        webTestClient
            .get()
            .uri("/flux")
            .accept(MediaType.ALL)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(Integer.class)
            .returnResult();
    assertEquals(expectedList, entityExchangeResult.getResponseBody());
  }

  @Test
  public void fluxApproach4() {

    List<Integer> expectedList = Arrays.asList(1, 2, 3, 4);

    EntityExchangeResult<List<Integer>> entityExchangeResult =
        webTestClient
            .get()
            .uri("/flux")
            .accept(MediaType.ALL)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(Integer.class)
            .consumeWith((response) -> assertEquals(expectedList, response.getResponseBody()));
  }

  // Test for Infinite stream

  @Test
  public void fluxApproach1Infinite() {
    Flux<Long> longFlux =
        webTestClient
            .get()
            .uri("/fluxstreaminfinite")
            .accept(
                MediaType.valueOf(MediaType.APPLICATION_STREAM_JSON_VALUE)) // The application type
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(Long.class)
            .getResponseBody();

    StepVerifier.create(longFlux)
        .expectNext(0l)
        .expectNext(1l)
        .expectNext(2l)
        .expectNext(3l)
        .thenCancel()
        .verify();
  }

  @Test
  public void mono() {

    Integer exoectedValue = 1;

    webTestClient
        .get()
        .uri("/mono")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Integer.class)
        .consumeWith(response -> assertEquals(exoectedValue, response.getResponseBody()));
  }
}
