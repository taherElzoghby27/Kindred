package com.spring.boot.social.utils.enums;

public enum ReactionType {
    //'like', 'love', 'haha', 'sad', 'angry'
    LIKE("LIKE"), LOVE("LOVE"), HAHA("HAHA"), SAD("SAD"), ANGRY("ANGRY");
    private final String status;

    ReactionType(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
