package com.example.smartAir.domain;

public enum UserRole {
    CHILD, PARENT, PROVIDER;

    public static UserRole extract (String s) {
        if (s.toLowerCase().contains("child")) {
            return CHILD;
        } else if (s.toLowerCase().contains("parent")) {
            return PARENT;
        } else if (s.toLowerCase().contains("provider")) {
            return PROVIDER;
        } else {
            return null;
        }
    }
}
