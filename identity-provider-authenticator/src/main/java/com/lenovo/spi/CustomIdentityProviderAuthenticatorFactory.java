package com.lenovo.spi;

import com.lenovo.configuration.Configuration;

import org.apache.log4j.BasicConfigurator;
import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.broker.IdpCreateUserIfUniqueAuthenticatorFactory;
import org.keycloak.models.KeycloakSession;

public class CustomIdentityProviderAuthenticatorFactory extends IdpCreateUserIfUniqueAuthenticatorFactory {
  private static final Logger LOGGER = Logger.getLogger(CustomIdentityProviderAuthenticatorFactory.class);
  private static final CustomIdentityProviderAuthenticator SINGLETON = new CustomIdentityProviderAuthenticator();
  private static final String PROVIDER_ID = "identity-provider-authenticator";

  private static final String EXTENSION = "identity-provider-extension";
  private static final String KAFKA_SERVER_URL = "kafkaServerUrl";

  public CustomIdentityProviderAuthenticatorFactory() {
    BasicConfigurator.configure();
  }

  @Override
  public String getId() {
    return PROVIDER_ID;
  }

  @Override
  public String getDisplayType() {
    return "Custom identity provider authenticator";
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
    return "Validates a password from login form. Username may be already known from identity provider authentication";
  }
}
