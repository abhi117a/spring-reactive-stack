package com.learnreact.Spring.React.Demo.fluxandMonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

/** @author a0r00rf */
public class FluxAndMonoTransforTest {

  List<String> names = new ArrayList<>();

  @Test
  public void TransFormTest() {

    names.add("ana");
    names.add("adam");
    names.add("jenny");
    names.add("jack");

    Flux<Integer> integerFlux = Flux.fromIterable(names).map(a -> a.length()).log();
    StepVerifier.create(integerFlux).expectNext(3, 4, 5, 4).verifyComplete();
  }

  @Test
  public void TransFormMono() {

    names.add("ana");
    names.add("adam");
    names.add("jenny");
    names.add("jack");

    Mono<Integer> integerMono =
        Flux.fromIterable(names).map(a -> a.length()).reduce((a, b) -> a + b).log();
    StepVerifier.create(integerMono).expectNext(16).verifyComplete();
  }
}
