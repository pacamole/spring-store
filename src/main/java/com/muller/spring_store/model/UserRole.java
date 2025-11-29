package com.muller.spring_store.model;

public enum UserRole {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    final String userType;

    UserRole(String userType) {
        this.userType = userType;
    }
}
