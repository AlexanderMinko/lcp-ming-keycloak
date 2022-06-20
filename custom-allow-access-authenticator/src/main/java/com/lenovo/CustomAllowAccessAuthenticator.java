package com.lenovo;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.access.AllowAccessAuthenticator;

public class CustomAllowAccessAuthenticator extends AllowAccessAuthenticator {

  private static final Logger LOGGER = Logger.getLogger(CustomAllowAccessAuthenticator.class);

  @Override
  public void authenticate(AuthenticationFlowContext context) {
    var user = context.getUser();
    LOGGER.info("USER => " + user.getId() + " " + user.getEmail());
    context.success();
  }

}
