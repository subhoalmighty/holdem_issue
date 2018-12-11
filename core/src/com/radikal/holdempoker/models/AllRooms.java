package com.radikal.holdempoker.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SubrataMondal on 12/12/17.
 */

public class AllRooms {
    @SerializedName("rooms")
    public Room[] rooms;
    @SerializedName("version")
    public String version;

    public AllRooms(Room[] rooms) {
        this.rooms = rooms;
    }
}