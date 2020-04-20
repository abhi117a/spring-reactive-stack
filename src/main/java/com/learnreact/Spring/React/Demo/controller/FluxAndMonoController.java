package com.learnreact.Spring.React.Demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/** @author a0r00rf */
@RestController
public class FluxAndMonoController {

  @GetMapping("/flux")
  public Flux<Integer> getFlux() {

    return Flux.just(1, 2, 3, 4).delayElements(Duration.ofMillis(1000)).log();
  }

  @GetMapping(value = "/fluxstream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Integer> getFluxStream() {

    return Flux.just(1, 2, 3, 4).delayElements(Duration.ofMillis(1000)).log();
  }

  @GetMapping(value = "/fluxstreaminfinite", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Long> getFluxStreamInfinite() {

    return Flux.interval(Duration.ofMillis(1000)).log();
  }

  @GetMapping(value = "/mono")
  public Mono<Integer> getMono() {

    return Mono.just(1).log();
  }
}
