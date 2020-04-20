package com.learnreact.Spring.React.Demo.fluxandMonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/** @author a0r00rf */
public class FluxAndMonoTest {

  @Test
  public void fluxTest() {

    Flux<String> fluxString = Flux.just("Spring", "Spring Boot", "Reactive Spring");
    //  .concatWith(Flux.error(new RuntimeException()));

    fluxString.subscribe(
        System.out::println,
        (e) -> {
          System.out.println("Error occured" + e);
        },
        () -> System.out.println(""));
  }

  @Test
  public void fluxTestElementes_WithoutError() {
    Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "React").log();
    StepVerifier.create(stringFlux)
        .expectNext("Spring")
        .expectNext("Spring boot")
        .expectNext("React")
        .verifyComplete();
  }

  @Test
  public void fluxTestElementes_WithError() {
    Flux<String> stringFlux =
        Flux.just("Spring", "Spring boot", "React")
            .concatWith(Flux.error(new RuntimeException("Exception")))
            .log();
    StepVerifier.create(stringFlux)
        .expectNext("Spring")
        .expectNext("Spring boot")
        .expectNext("React")
        .expectError(RuntimeException.class);
  }

  @Test
  public void monoTest() {

    Mono<String> stringMono = Mono.just("Spring Boot");
    StepVerifier.create(stringMono).expectNext("Spring Boot").verifyComplete();
  }

  @Test
  public void monoTestWithError() {

    StepVerifier.create(Mono.error(new RuntimeException("Error")))
        .expectError(RuntimeException.class)
        .verify();
  }
}
