FROM quay.io/keycloak/keycloak:latest

COPY  custom-allow-access-authenticator/module.xml custom-allow-access-authenticator/target/custom-allow-access-authenticator-*.jar \
     /opt/jboss/keycloak/modules/com/lenovo/custom-allow-access-authenticator/main/

COPY  custom-user-authenticator/module.xml custom-user-authenticator/target/custom-user-authenticator-*.jar \
     /opt/jboss/keycloak/modules/com/lenovo/custom-user-authenticator/main/

COPY  identity-provider-authenticator/module.xml identity-provider-authenticator/target/identity-provider-authenticator-*.jar \
     /opt/jboss/keycloak/modules/com/lenovo/identity-provider-authenticator/main/

COPY themes/socket /opt/jboss/keycloak/themes/socket/

COPY standalone.xml standalone-ha.xml /opt/jboss/keycloak/standalone/configuration/

CMD ["-b", "0.0.0.0"]
