package com.learnreact.Spring.React.Demo.controller.v1;

import com.learnreact.Spring.React.Demo.constants.ItemConstants;
import com.learnreact.Spring.React.Demo.document.ItemCapped;
import com.learnreact.Spring.React.Demo.repository.ItemReactiveCappedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/** @author a0r00rf */
@RestController
public class ItemStreamController {

  @Autowired ItemReactiveCappedRepository itemReactiveCappedRepository;

  @GetMapping(
      value = ItemConstants.ITEM_STREAM_END_POINT_V1,
      produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<ItemCapped> getItemSTream() {

    return itemReactiveCappedRepository.findItemCappedBy();
  }
}
