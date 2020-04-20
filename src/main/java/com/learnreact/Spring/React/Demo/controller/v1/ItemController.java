package com.learnreact.Spring.React.Demo.controller.v1;

import com.learnreact.Spring.React.Demo.constants.ItemConstants;
import com.learnreact.Spring.React.Demo.document.Item;
import com.learnreact.Spring.React.Demo.repository.ItemReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/** @author a0r00rf */
@RestController
@Slf4j
public class ItemController {

  @Autowired ItemReactiveRepository itemReactiveRepository;

  @GetMapping(ItemConstants.ITEM_END_POINT_V1)
  public Flux<Item> getAllItems() {

    return itemReactiveRepository.findAll();
  }
}