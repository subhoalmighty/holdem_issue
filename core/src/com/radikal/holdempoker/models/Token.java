package com.radikal.holdempoker.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Saptarshi Biswas on 29/11/18.
 */

public class Token implements Serializable {
    @SerializedName("token_type")
    public String tokenType;
    @SerializedName("expires_in")
    public String expiresIn;
    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("refresh_token")
    public String refreshToken;
}