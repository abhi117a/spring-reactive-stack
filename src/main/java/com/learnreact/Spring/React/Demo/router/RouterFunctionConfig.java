package com.learnreact.Spring.React.Demo.router;

import com.learnreact.Spring.React.Demo.handler.SampleHandlerFUnction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/** @author a0r00rf */
@Configuration
public class RouterFunctionConfig {

  @Bean
  public RouterFunction<ServerResponse> route(SampleHandlerFUnction handlerFUnction) {
    return RouterFunctions.route(
            GET("/functional/flux").and(accept(MediaType.APPLICATION_STREAM_JSON)),
            handlerFUnction::flux)
        .andRoute(
            GET("/functional/mono").and(accept(MediaType.APPLICATION_STREAM_JSON)),
            handlerFUnction::mono);
  }
}
