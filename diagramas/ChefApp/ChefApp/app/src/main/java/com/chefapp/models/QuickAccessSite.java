package com.chefapp.models;

public class QuickAccessSite {
    private String name;
    private String url;
    private boolean active;

    public QuickAccessSite(String name, String url, boolean active) {
        this.name = name;
        this.url = url;
        this.active = active;
    }

    public String getName() { return name; }
    public String getUrl() { return url; }
    public boolean isActive() { return active; }
}
