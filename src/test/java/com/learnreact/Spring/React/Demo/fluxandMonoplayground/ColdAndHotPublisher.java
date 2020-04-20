package com.learnreact.Spring.React.Demo.fluxandMonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

/** @author a0r00rf */
public class ColdAndHotPublisher {

  @Test
  public void coldPublisherer() throws InterruptedException {

    Flux<String> flux = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(1000)).log();

    flux.subscribe(e -> System.out.println(e));
    Thread.sleep(2000);

    flux.subscribe(e -> System.out.println(e));
    Thread.sleep(4000);
  }
}
