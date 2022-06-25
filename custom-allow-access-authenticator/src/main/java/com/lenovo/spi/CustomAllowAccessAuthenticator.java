package com.lenovo.spi;

import com.lenovo.configuration.Configuration;
import com.lenovo.service.EventService;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.access.AllowAccessAuthenticator;

public class CustomAllowAccessAuthenticator extends AllowAccessAuthenticator {

  private static final Logger LOGGER = Logger.getLogger(CustomAllowAccessAuthenticator.class);

  private Configuration configurations;
  private final EventService eventService = EventService.getInstance();

  @Override
  public void authenticate(AuthenticationFlowContext context) {
    var user = context.getUser();
    var realmName = context.getRealm().getName();
    eventService.publishUserIdpAuthenticatedEvent(user, realmName);
    context.success();
  }

  public void setConfigurations(final Configuration configurations) {
    this.configurations = configurations;
    this.eventService.configure(configurations.getKafkaServerUrl());
  }
}
