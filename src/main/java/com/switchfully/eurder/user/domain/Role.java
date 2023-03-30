package com.switchfully.eurder.user.domain;

public enum Role {

    ADMINISTRATOR ("administrator"),
    CUSTOMER ("customer");

    private String label;

    Role(String label) {
        this.label = label;
    }
}
