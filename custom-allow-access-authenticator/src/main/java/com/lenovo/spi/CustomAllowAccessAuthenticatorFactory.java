package com.lenovo.spi;

import com.lenovo.configuration.Configuration;

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

  private static final String EXTENSION = "custom-allow-access-extension";
  private static final String KAFKA_SERVER_URL = "kafkaServerUrl";

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
    final var internalLcpUserConfig = Config.scope(EXTENSION, PROVIDER_ID);
    final var configurations = new Configuration(internalLcpUserConfig.get(KAFKA_SERVER_URL));
    SINGLETON.setConfigurations(configurations);
    LOGGER.info("Configurations " + configurations);
  }

  @Override
  public String getHelpText() {
    return "Custom allow access authenticator. Help to get access to user authenticates via identity providers";
  }
}
