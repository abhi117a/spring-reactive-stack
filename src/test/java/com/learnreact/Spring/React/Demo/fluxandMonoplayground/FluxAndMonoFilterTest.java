package com.learnreact.Spring.React.Demo.fluxandMonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

/** @author a0r00rf */
public class FluxAndMonoFilterTest {

  List<String> names = new ArrayList<>();

  @Test
  public void filterTest() {

    names.add("ana");
    names.add("abhi");
    names.add("jay");

    Flux<String> stringFlux = Flux.fromIterable(names).filter((a) -> a.length() > 3).log();
    StepVerifier.create(stringFlux).expectNext("abhi").verifyComplete();
  }
}
