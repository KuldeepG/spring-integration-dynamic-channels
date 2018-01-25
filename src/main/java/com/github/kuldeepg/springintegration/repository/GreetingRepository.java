package com.github.kuldeepg.springintegration.repository;

import com.github.kuldeepg.springintegration.domain.Greeting;
import org.springframework.data.repository.Repository;

public interface GreetingRepository extends Repository<Greeting, Long>{
  void save(Greeting greeting);
}
