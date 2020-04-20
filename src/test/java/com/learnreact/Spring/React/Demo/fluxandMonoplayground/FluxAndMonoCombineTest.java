package com.learnreact.Spring.React.Demo.fluxandMonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/** @author a0r00rf */
public class FluxAndMonoCombineTest {

  @Test
  public void combineUsignMerge() {

    Flux<String> flux1 = Flux.just("abc", "cbn", "hji");
    Flux<String> flux2 = Flux.just("as", "bc");
    Flux<String> fluxMerge = Flux.merge(flux1, flux2);

    StepVerifier.create(fluxMerge).expectNext("abc", "cbn", "hji", "as", "bc").verifyComplete();
  }
}
