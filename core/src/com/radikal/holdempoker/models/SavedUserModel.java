package com.radikal.holdempoker.models;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class SavedUserModel {

    private Boolean isLoggedIn;
    private String username;
    private String password;

    public SavedUserModel(Boolean isLoggedIn, String username, String password) {
        this.isLoggedIn = isLoggedIn;
        this.username = username;
        this.password = password;
    }

    public Boolean getLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
