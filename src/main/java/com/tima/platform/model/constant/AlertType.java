package com.tima.platform.model.constant;

/**
 * @Author: Josiah Adetayo
 * @Email: josleke@gmail.com, josiah.adetayo@meld-tech.com
 * @Date: 1/30/24
 */
public enum AlertType {
    PAYMENT("PAYMENT"),
    CAMPAIGN("CAMPAIGN");

    private final String type;

    AlertType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
