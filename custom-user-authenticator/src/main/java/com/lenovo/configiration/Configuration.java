package com.lenovo.configiration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class Configuration {

  private String kafkaServerUrl;
}
