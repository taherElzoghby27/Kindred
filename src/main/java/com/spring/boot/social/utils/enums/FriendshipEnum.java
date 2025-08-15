package com.spring.boot.social.utils.enums;

public enum FriendshipEnum {
    PENDING("PENDING"), ACCEPTED("ACCEPTED"), BLOCKED("BLOCKED");
    private final String status;

    FriendshipEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
