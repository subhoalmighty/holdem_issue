package com.radikal.holdempoker.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SubrataMondal on 12/12/17.
 */

public class User {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String Name;
    @SerializedName("email")
    public String email;
    @SerializedName("nick_name")
    public String nickName;
    @SerializedName("phone")
    public String phone;
    @SerializedName("address_1")
    public String addressOne;
    @SerializedName("address_2")
    public String addressTwo;
    @SerializedName("is_verified")
    public int isVerified;
    @SerializedName("image_url")
    public String imageUrl;
}
