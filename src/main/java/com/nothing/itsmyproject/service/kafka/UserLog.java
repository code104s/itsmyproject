package com.nothing.itsmyproject.service.kafka;

public class UserLog {

  private String action;
  private Long userId;
  private String userName;
  private String email;
  private String roles;

  public UserLog(String action, Long userId, String userName, String email, String roles) {
    this.action = action;
    this.userId = userId;
    this.userName = userName;
    this.email = email;
    this.roles = roles;
  }

  // Getters and Setters
  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }
}