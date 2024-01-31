package com.tima.platform.model.constant;

/**
 * @Author: Josiah Adetayo
 * @Email: josleke@gmail.com, josiah.adetayo@meld-tech.com
 * @Date: 1/30/24
 */
public enum AlertStatus {
    NEW("NEW"),
    VIEWED("VIEWED");

    private final String type;

    AlertStatus(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
