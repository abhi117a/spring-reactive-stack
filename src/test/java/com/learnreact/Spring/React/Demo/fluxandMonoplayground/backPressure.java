package com.learnreact.Spring.React.Demo.fluxandMonoplayground;

import org.junit.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/** @author a0r00rf */
public class backPressure {

  @Test
  public void testbackPressure() {

    Flux<Integer> finiteFlux = Flux.range(1, 10).log();

    StepVerifier.create(finiteFlux)
        .expectSubscription()
        .thenRequest(1)
        .expectNext(1)
        .thenRequest(2)
        .expectNext(2)
        .expectNext(3)
        .thenCancel()
        .verify();
  }

  @Test
  public void testOnHook() {

    Flux<Integer> finiteFlux = Flux.range(1, 10).log();
    finiteFlux.subscribe(
        new BaseSubscriber<Integer>() {
          @Override
          protected void hookOnNext(Integer value) {
            request(1);
            System.out.println(value);
            if (value == 4) {
              cancel();
            }
          }
        });
  }
}
