package com.lenovo.service;

import java.util.HashMap;

import com.lenovo.model.Event;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jboss.logging.Logger;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class EventProducer {
  private static final Logger LOGGER = Logger.getLogger(EventService.class);
  private KafkaTemplate<String, Event> kafkaTemplate;
  private boolean initialized;

  public void init(String kafkaServerUrl) {
    var config = new HashMap<String, Object>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    var producerFactory = new DefaultKafkaProducerFactory<String, Event>(config);
    kafkaTemplate = new KafkaTemplate<>(producerFactory);
    initialized = true;
    LOGGER.info(EventProducer.class.getName() + " initialized! " + " URL => " + kafkaServerUrl);
  }

  public void sendEvent(Event event) {
    kafkaTemplate.send(event.getTopicName(), event);
    LOGGER.info("Event send: " + event);
  }

  public boolean isInitialized() {
    return initialized;
  }

}
