package com.lenovo;

import org.apache.log4j.BasicConfigurator;
import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.access.AllowAccessAuthenticatorFactory;
import org.keycloak.models.KeycloakSession;

public class CustomAllowAccessAuthenticatorFactory extends AllowAccessAuthenticatorFactory {
  private static final Logger LOGGER = Logger.getLogger(CustomAllowAccessAuthenticatorFactory.class);
  private static final CustomAllowAccessAuthenticator SINGLETON = new CustomAllowAccessAuthenticator();
  private static final String PROVIDER_ID = "custom-allow-access-authenticator";

  public CustomAllowAccessAuthenticatorFactory() {
    BasicConfigurator.configure();
  }

  @Override
  public String getId() {
    return PROVIDER_ID;
  }

  @Override
  public String getDisplayType() {
    return "Custom allow access authenticator";
  }

  @Override
  public Authenticator create(KeycloakSession session) {
    return SINGLETON;
  }

  @Override
  public void init(Config.Scope config) {
    LOGGER.info("INIT CONFIG");
    final var internalLcpUserConfig = Config.scope("allow-access-authenticator-extension", PROVIDER_ID);
    System.out.println(internalLcpUserConfig);
    System.out.println("init");
  }

  @Override
  public String getHelpText() {
    return "Validates a password from login form. Username may be already known from identity provider authentication";
  }
}
