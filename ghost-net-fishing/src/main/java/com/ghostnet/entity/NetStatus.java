package com.ghostnet.entity;

public enum NetStatus {
    REPORTED("Gemeldet"),
    RECOVERY_PENDING("Bergung bevorstehend"),
    RECOVERED("Geborgen"),
    LOST("Verschollen");

    private final String displayName;

    NetStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}