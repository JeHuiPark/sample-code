package com.example.jehuipark.builder;

import java.util.HashSet;
import java.util.Set;

class UrlRole {

  private final String url;
  private final Set<String> role;
  private final Set<Method> method;

  private UrlRole(String url, Set<String> role, Set<Method> method) {
    this.url = url;
    this.role = role;
    this.method = method;
  }

  static UrlRoleBuilder url(String url, Method... methods) {
    return new UrlRoleBuilder(url, methods);
  }

  @Override
  public String toString() {
    return "UrlRole{" +
        "url='" + url + '\'' +
        ", role=" + role +
        ", method=" + method +
        '}';
  }

  @SuppressWarnings({"UseBulkOperation", "ManualArrayToCollectionCopy"})
  public static class UrlRoleBuilder {
    private String url;
    private Set<String> roles;
    private Set<Method> methods;

    UrlRoleBuilder(String url, Method... methods) {
      this.url = url;
      this.roles = new HashSet<>();
      this.methods = new HashSet<>();
      for (Method each : methods) {
        this.methods.add(each);
      }
    }

    public UrlRoleBuilder hasAnyRole(String... role) {
      for (String each : role) {
        roles.add(each);
      }
      return this;
    }

    public UrlRole build() {
      validate();
      if (methods.isEmpty()) {
        for (Method each : Method.values()) {
          methods.add(each);
        }
      }
      return new UrlRole(url, roles, methods);
    }

    private void validate() {
      // do something for validate)
    }
  }
}
