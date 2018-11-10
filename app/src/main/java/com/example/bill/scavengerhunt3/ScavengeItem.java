package com.example.bill.scavengerhunt3;

public class ScavengeItem {
    private String name;
    private Boolean found;

    public ScavengeItem(String name) {
        this.name = name;
        this.found = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }
}