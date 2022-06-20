package com.lenovo.spi;

import javax.ws.rs.core.MultivaluedMap;

import com.lenovo.configiration.Configuration;
import com.lenovo.model.KeycloakUserData;
import com.lenovo.service.EventService;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.browser.UsernamePasswordForm;

public class UserAuthenticator extends UsernamePasswordForm {
  private static final Logger LOGGER = Logger.getLogger(UserAuthenticator.class);

  private Configuration configurations;
  private final EventService eventService = EventService.getInstance();

  @Override
  public void action(AuthenticationFlowContext context) {
    LOGGER.info("ACTION");
    final MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
    if (formData.containsKey("cancel")) {
      context.cancelLogin();
    } else if (validateForm(context, formData)) {
      try {
        LOGGER.info("IN TRY");
        final var user = context.getUser();
        final var realmName = context.getRealm().getName();
        final var keycloakUserData = new KeycloakUserData();
        keycloakUserData.setUsername(user.getUsername());
        keycloakUserData.setEmail(user.getEmail());
        keycloakUserData.setFirstName(user.getFirstName());
        keycloakUserData.setLastName(user.getLastName());
        eventService.publishUserAuthenticatedEvent(keycloakUserData, realmName);
      } catch (RuntimeException e) {
        LOGGER.error(e.getMessage());
      }
      context.success();
    }
  }

  public void setConfigurations(final Configuration configurations) {
    this.configurations = configurations;
    this.eventService.configure(configurations.getKafkaServerUrl());
  }

}
