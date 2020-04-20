package com.learnreact.Spring.React.Demo.fluxandMonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

/** @author a0r00rf */
public class FluxAndMonoFactoryMethods {

  List<String> names = new ArrayList<>();

  @Test
  public void fluxIterable() {

    names.add("ana");
    names.add("adam");
    names.add("jenny");
    names.add("jack");

    Flux<String> namesFlux = Flux.fromIterable(names);

    StepVerifier.create(namesFlux).expectNext("ana", "adam", "jenny", "jack").verifyComplete();
  }
}
