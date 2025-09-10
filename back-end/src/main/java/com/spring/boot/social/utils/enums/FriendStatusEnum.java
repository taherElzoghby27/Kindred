package com.spring.boot.social.utils.enums;

public enum FriendStatusEnum {
    PENDING("PENDING"), ACCEPTED("ACCEPTED"), REJECTED("REJECTED"), BLOCKED("BLOCKED");
    private final String status;

    FriendStatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
