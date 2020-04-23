package com.learnreact.Spring.React.Demo.router;

import com.learnreact.Spring.React.Demo.constants.ItemConstants;
import com.learnreact.Spring.React.Demo.handler.ItemHandler;
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
public class ItemRouter {

  @Bean
  public RouterFunction<ServerResponse> itemRoute(ItemHandler itemHandler) {
    return RouterFunctions.route(
        GET(ItemConstants.ITEM_END_POINT_V1_FUNCTIONAL).and(accept(MediaType.APPLICATION_JSON)),
        itemHandler::getAllItems);
  }
}
