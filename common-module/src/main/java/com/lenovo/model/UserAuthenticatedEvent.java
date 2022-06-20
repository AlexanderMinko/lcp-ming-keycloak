package com.lenovo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticatedEvent extends Event {
  private KeycloakUserData keycloakUserData;
  private String realmName;
}
