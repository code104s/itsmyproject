package com.nothing.itsmyproject.service;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public interface UserService {

  Collection<? extends GrantedAuthority> getAuthorities();

  String getPassword();

  String getUsername();

  boolean isAccountNonExpired();

  boolean isAccountNonLocked();

  boolean isCredentialsNonExpired();

  boolean isEnabled();
}
