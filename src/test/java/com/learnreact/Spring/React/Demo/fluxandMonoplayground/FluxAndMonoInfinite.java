package com.learnreact.Spring.React.Demo.fluxandMonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

/** @author a0r00rf */
public class FluxAndMonoInfinite {

  @Test
  public void infiniteSeq() throws InterruptedException {
    Flux<Long> infinite = Flux.interval(Duration.ofMillis(200)).log();

    infinite.subscribe(
        e ->
            System.out.println(
                e)); // Async and Non blocking so it gets bloacked without thread sleep

    Thread.sleep(3000);
  }
}
