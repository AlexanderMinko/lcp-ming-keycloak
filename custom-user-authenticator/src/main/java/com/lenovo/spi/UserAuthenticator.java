package com.lenovo.spi;

import javax.ws.rs.core.MultivaluedMap;

import com.lenovo.configiration.Configuration;
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
    final MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
    if (formData.containsKey("cancel")) {
      context.cancelLogin();
    } else if (validateForm(context, formData)) {
      try {
        final var userModel = context.getUser();
        final var realmName = context.getRealm().getName();
        eventService.publishUserAuthenticatedEvent(userModel, realmName);
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
