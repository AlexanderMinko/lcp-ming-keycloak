package com.lenovo.spi;

import java.util.Objects;

import com.lenovo.configuration.Configuration;
import com.lenovo.service.EventService;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.broker.IdpCreateUserIfUniqueAuthenticator;
import org.keycloak.authentication.authenticators.broker.util.SerializedBrokeredIdentityContext;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.models.UserModel;

public class CustomIdentityProviderAuthenticator extends IdpCreateUserIfUniqueAuthenticator {

  private static final Logger LOGGER = Logger.getLogger(CustomIdentityProviderAuthenticator.class);

  private Configuration configurations;
  private final EventService eventService = EventService.getInstance();

  @Override
  protected void userRegisteredSuccess(
      AuthenticationFlowContext context,
      UserModel registeredUser,
      SerializedBrokeredIdentityContext serializedCtx,
      BrokeredIdentityContext brokerContext) {
    if (Objects.nonNull(registeredUser)) {
      var realmName = context.getRealm().getName();
      var lcpCustomerRole = context.getRealm().getRole("lcp_customer");
      registeredUser.grantRole(lcpCustomerRole);
      eventService.publishUserIpdRegisteredEvent(registeredUser, realmName);
    } else {
      LOGGER.info("User didn't successfully register via Identity Provider");
    }
  }

  public void setConfigurations(final Configuration configurations) {
    this.configurations = configurations;
    this.eventService.configure(configurations.getKafkaServerUrl());
  }

}
