package com.lenovo.service;

import java.time.Instant;
import java.util.stream.Collectors;

import com.lenovo.model.UserAuthenticatedEvent;
import com.lenovo.model.UserIdpAuthenticatedEvent;
import com.lenovo.model.UserIdpRegisteredEvent;

import org.jboss.logging.Logger;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;

public class EventService {
  private static final Logger LOGGER = Logger.getLogger(EventService.class);
  private static EventService eventService;
  private final EventProducer eventProducer = new EventProducer();

  private static final String USER_AUTHENTICATED_TOPIC = "lcp-ming-user-authenticated-event";
  private static final String USER_IDP_REGISTERED_TOPIC = "lcp-ming-user-idp-registered-event";
  private static final String USER_IDP_AUTHENTICATED_TOPIC = "lcp-ming-user-idp-authenticated-event";

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

  public void publishUserAuthenticatedEvent(UserModel userModel, String realmName) {
    final var event = new UserAuthenticatedEvent();
    event.setTopicName(USER_AUTHENTICATED_TOPIC);
    event.setRealmName(realmName);
    event.setCreatedDate(Instant.now());
    event.setId(userModel.getId());
    event.setUsername(userModel.getUsername());
    event.setEmail(userModel.getEmail());
    event.setFirstName(userModel.getFirstName());
    event.setLastName(userModel.getLastName());
    eventProducer.send(event);
  }

  public void publishUserIpdRegisteredEvent(UserModel userModel, String realmName) {
    var userRoles = userModel.getRoleMappingsStream().map(RoleModel::getName).collect(Collectors.toSet());
    final var event = new UserIdpRegisteredEvent();
    event.setTopicName(USER_IDP_REGISTERED_TOPIC);
    event.setRealmName(realmName);
    event.setCreatedDate(Instant.now());
    event.setId(userModel.getId());
    event.setUsername(userModel.getUsername());
    event.setEmail(userModel.getEmail());
    event.setFirstName(userModel.getFirstName());
    event.setLastName(userModel.getLastName());
    event.setUserRoles(userRoles);
    eventProducer.send(event);
  }

  public void publishUserIdpAuthenticatedEvent(UserModel userModel, String realmName) {
    final var event = new UserIdpAuthenticatedEvent();
    event.setTopicName(USER_IDP_AUTHENTICATED_TOPIC);
    event.setRealmName(realmName);
    event.setCreatedDate(Instant.now());
    event.setId(userModel.getId());
    eventProducer.send(event);
  }
}
