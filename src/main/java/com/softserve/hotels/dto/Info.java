package com.softserve.hotels.dto;

public class Info {

    private String tab;

    private String event;

    private String description;

    public Info(String tab, String event, String description) {
        this.tab = tab;
        this.event = event;
        this.description = description;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
