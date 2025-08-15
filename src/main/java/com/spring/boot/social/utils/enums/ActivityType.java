package com.spring.boot.social.utils.enums;

public enum ActivityType {
    POST_CREATED("POST_CREATED"),
    COMMENT_CREATED("COMMENT_CREATED"),
    REACTION_ADDED("REACTION_ADDED"),
    FRIENDSHIP_REQUEST_SENT("FRIENDSHIP_REQUEST_SENT"),
    FRIENDSHIP_ACCEPTED("FRIENDSHIP_ACCEPTED"),
    FOLLOW_STARTED("FOLLOW_STARTED"),
    FOLLOW_STOPPED("FOLLOW_STOPPED"),
    MESSAGE_SENT("MESSAGE_SENT");

    private final String activity;

    ActivityType(String activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return this.activity;
    }
}
