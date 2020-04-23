package com.learnreact.Spring.React.Demo.handler;

import com.learnreact.Spring.React.Demo.document.Item;
import com.learnreact.Spring.React.Demo.repository.ItemReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/** @author a0r00rf */
@Component
public class ItemHandler {

  @Autowired ItemReactiveRepository itemReactiveRepository;

  public Mono<ServerResponse> getAllItems(ServerRequest serverRequest) {

    return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(itemReactiveRepository.findAll(), Item.class);
  }
}
