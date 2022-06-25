package com.lenovo.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserIdpRegisteredEvent extends Event {
  private String id;
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private String realmName;
  private Set<String> userRoles;
}
