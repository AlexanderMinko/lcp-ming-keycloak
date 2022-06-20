package com.lenovo.service;

import java.time.Instant;

import com.lenovo.model.KeycloakUserData;
import com.lenovo.model.UserAuthenticatedEvent;

import org.jboss.logging.Logger;

public class EventService {
  private static final Logger LOGGER = Logger.getLogger(EventService.class);
  private static EventService eventService;
  private final EventProducer eventProducer = new EventProducer();

  private static final String USER_AUTHENTICATED_TOPIC = "lcp-ming-user-authenticated-event";

  private EventService() {
  }

  public static synchronized EventService getInstance() {
    if (eventService == null) {
      eventService = new EventService();
    }
    return eventService;
  }

  public void configure(String kafkaServerUrl) {
    if (!eventProducer.isInitialized()) {
      eventProducer.init(kafkaServerUrl);
    }
  }

  public void publishUserAuthenticatedEvent(KeycloakUserData keycloakUserData, String realmName) {
    final var event = new UserAuthenticatedEvent();
    event.setKeycloakUserData(keycloakUserData);
    event.setRealmName(realmName);
    event.setCreatedDate(Instant.now());
    event.setTopicName(USER_AUTHENTICATED_TOPIC);
    eventProducer.sendEvent(event);
  }

}
