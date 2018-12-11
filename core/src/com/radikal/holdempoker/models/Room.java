package com.radikal.holdempoker.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SubrataMondal on 12/12/17.
 */

public class Room {
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("display_img")
    public String display_img;

    public Room(int id, String title, String display_img) {
        this.id = id;
        this.title = title;
        this.display_img = display_img;
    }
}