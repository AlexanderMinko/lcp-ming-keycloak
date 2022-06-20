package com.lenovo.spi;

import com.lenovo.configiration.Configuration;

import org.apache.log4j.BasicConfigurator;
import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.browser.UsernamePasswordFormFactory;
import org.keycloak.models.KeycloakSession;

public class UserAuthenticatorFactory extends UsernamePasswordFormFactory {
  private static final Logger LOGGER = Logger.getLogger(UserAuthenticatorFactory.class);
  private static final UserAuthenticator SINGLETON = new UserAuthenticator();
  private static final String PROVIDER_ID = "custom-user-authenticator";

  private static final String EXTENSION = "custom-user-authenticator-extension";
  private static final String KAFKA_SERVER_URL = "kafkaServerUrl";

  public UserAuthenticatorFactory() {
    BasicConfigurator.configure();
  }

  @Override
  public String getId() {
    return PROVIDER_ID;
  }

  @Override
  public String getDisplayType() {
    return "Custom user authenticator";
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
}
