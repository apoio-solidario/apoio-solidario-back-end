package com.github.apoioSolidario.enums;

public enum UserRole {
        ADMIN("admin"),
        ONG("ong");

        private String role;

        UserRole(String role) {
            this.role = role;
        }
}
