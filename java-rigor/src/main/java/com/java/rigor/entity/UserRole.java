package com.java.rigor.entity;

/**
 * Created by sanandasena on 1/20/2016.
 */
public enum UserRole {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER"),
    ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN");

    private String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
