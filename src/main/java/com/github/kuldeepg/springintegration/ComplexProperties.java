package com.github.kuldeepg.springintegration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ConfigurationProperties(prefix = "complex")
public class ComplexProperties {
  private String name;

  private List<ComplexObject> objects = new ArrayList<>();

  public Map<String, String> getLocations() {
    return locations;
  }

  public void setLocations(Map<String, String> locations) {
    this.locations = locations;
  }

  private Map<String, String> locations = new HashMap<>();

  public void setName(String name) {
    this.name = name;
  }

  public void setObjects(List<ComplexObject> objects) {
    this.objects = objects;
  }

  public String getName() {
    return name;
  }

  public Map<String, String> getObjectsMap() {
    return objects.stream().collect(Collectors.toMap(ComplexObject::getName, ComplexObject::getValue));
  }

  public List<ComplexObject> getObjects() {
    return objects;
  }
}
