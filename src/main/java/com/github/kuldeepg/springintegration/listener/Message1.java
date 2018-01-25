package com.github.kuldeepg.springintegration.listener;

import com.github.kuldeepg.springintegration.annotation.EventHandler;
import com.github.kuldeepg.springintegration.model.MessageCreatedEvent;
import com.github.kuldeepg.springintegration.model.MessageUpdatedEvent;
import com.github.kuldeepg.springintegration.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.util.Arrays.asList;

@Component
public class Message1 {
  private static final String FILE_NAME =
      System.getProperty("user.dir")+"/output.txt";

  public Message1(GreetingRepository repository) {
    this.repository = repository;
  }

  @Autowired
  private GreetingRepository repository;

  @EventHandler
  public void writeToFile(MessageCreatedEvent messageCreatedEvent) throws IOException {
    Path file = Paths.get(FILE_NAME);
    Files.write(file, asList(messageCreatedEvent.getMessage()),
        Charset.forName("UTF-8"), StandardOpenOption.CREATE);
  }

  @EventHandler
  public void addToFile(MessageUpdatedEvent messageUpdatedEvent) throws IOException {
    Path file = Paths.get(FILE_NAME);
    Files.write(file, asList(messageUpdatedEvent.getMessage()),
        Charset.forName("UTF-8"), StandardOpenOption.APPEND);
  }
}
