package com.lenovo;

import java.util.Objects;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.broker.IdpCreateUserIfUniqueAuthenticator;
import org.keycloak.authentication.authenticators.broker.util.SerializedBrokeredIdentityContext;
import org.keycloak.broker.provider.BrokeredIdentityContext;
import org.keycloak.models.UserModel;

public class CustomIdentityProviderAuthenticator extends IdpCreateUserIfUniqueAuthenticator {

  private static final Logger LOGGER = Logger.getLogger(CustomIdentityProviderAuthenticator.class);

  @Override
  protected void userRegisteredSuccess(
      AuthenticationFlowContext context,
      UserModel registeredUser,
      SerializedBrokeredIdentityContext serializedCtx,
      BrokeredIdentityContext brokerContext) {
    if (Objects.isNull(registeredUser)) {
      LOGGER.info("NO USER");
    } else {
      LOGGER.info("Registered user info => " + registeredUser.getUsername() + " " + registeredUser.getEmail() + " " + registeredUser.getId() + " " + registeredUser.getFirstName());
    }
  }

}
