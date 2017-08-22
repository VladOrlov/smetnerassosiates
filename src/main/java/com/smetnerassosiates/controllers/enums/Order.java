package com.smetnerassosiates.controllers.enums;

import lombok.Getter;

@Getter
public enum Order {
  ENGINE("RENDERING_ENGINE");

  private String fieldName;

  Order(String fieldName) {
    this.fieldName = fieldName;
  }
}
