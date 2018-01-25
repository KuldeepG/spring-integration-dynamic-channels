package com.github.kuldeepg.springintegration.listener;

import com.github.kuldeepg.springintegration.annotation.EventHandler;
import com.github.kuldeepg.springintegration.domain.Greeting;
import com.github.kuldeepg.springintegration.model.MessageCreatedEvent;
import com.github.kuldeepg.springintegration.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class Message2 {
  public Message2(GreetingRepository repository) {
    this.repository = repository;
  }

  @Autowired
  private GreetingRepository repository;

  @EventHandler
  @Order(1)
  public void writeToDb2(MessageCreatedEvent messageCreatedEvent) {
    Greeting greeting = new Greeting(messageCreatedEvent.getMessage());
    repository.save(greeting);
  }
}
