package com.lenovo;

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
    LOGGER.info("INIT CONFIG");
    final var internalLcpUserConfig = Config.scope("identity-provider-extension", PROVIDER_ID);
    System.out.println(internalLcpUserConfig);
    System.out.println("init");
  }

  @Override
  public String getHelpText() {
    return "Validates a password from login form. Username may be already known from identity provider authentication";
  }
}
