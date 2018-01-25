package com.github.kuldeepg.springintegration.web;

import com.github.kuldeepg.springintegration.ComplexProperties;
import com.github.kuldeepg.springintegration.domain.Greeting;
import com.github.kuldeepg.springintegration.model.MessageCreatedEvent;
import com.github.kuldeepg.springintegration.model.MessageUpdatedEvent;
import com.github.kuldeepg.springintegration.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

import static java.time.Instant.now;

@RestController
public class GreetingsController {

  @Autowired
  private JmsTemplate jmsTemplate;

  @Autowired
  private GreetingRepository repository;

  @Autowired
  private ComplexProperties properties;

  @PostMapping("greeting")
  public ResponseEntity createGreeting(@RequestBody String message) {
    jmsTemplate.convertAndSend(new MessageCreatedEvent(message, now()));
    return ResponseEntity.created(URI.create("http://localhost:8080")).build();
  }

  @PutMapping("greeting")
  public ResponseEntity updateGreeting(@RequestBody String message) {
    jmsTemplate.convertAndSend(new MessageUpdatedEvent(message, now()));
    return ResponseEntity.created(URI.create("http://localhost:8080")).build();
  }

  @PostMapping("raw_greeting")
  public ResponseEntity rawCreateGreeting(@RequestBody String message) {
    repository.save(new Greeting(message));
    repository.save(new Greeting(message));
    if(message.equals("fail"))
      throw(new RuntimeException());
    return ResponseEntity.created(URI.create("http://localhost:8080")).build();
  }

}
