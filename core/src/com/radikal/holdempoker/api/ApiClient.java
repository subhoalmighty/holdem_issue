package com.radikal.holdempoker.api;


import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.radikal.holdempoker.Utils;
import com.radikal.holdempoker.models.GameRoom;
import com.radikal.holdempoker.models.Json_Response;
import com.radikal.holdempoker.models.UserGroup;

import java.io.IOException;
import java.lang.reflect.Type;

import jdk.nashorn.internal.runtime.logging.DebugLogger;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //public static final String PAYTM_BASE_URL = "http://54.204.60.179/check_paytm/";
    //private static Retrofit payTmRetrofit = null;

    /*public static Retrofit GetPayTmClient() {
        if (payTmRetrofit == null) {
            payTmRetrofit = new Retrofit.Builder()
                    .baseUrl(PAYTM_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return payTmRetrofit;
    }*/

    public static final String BASE_URL = "http://192.168.1.17/HoldemPoker/public/api/";
    //public static final String BASE_URL = "http://54.204.60.179/api/";

    //http://54.204.60.179/check_paytm/checkstatus_api.php

    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder httpClient = null;
    private static OkHttpClient session_client = null;

    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Json_Response.class, new ArrayObjectDualityDeserializer())
            .create();

    public static Retrofit GetClient() {
        if (retrofit == null || Utils.ACCESS_TOKEN.equals("")) {
            httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new SessionOkHttpInterceptor());
            session_client = httpClient.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(session_client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        Gdx.app.log("savedAccessToken", Utils.ACCESS_TOKEN + "");
        Gdx.app.log("savedRefreshToken", Utils.REFRESH_TOKEN + "");
        return retrofit;
    }

    public static class SessionOkHttpInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer " + Utils.ACCESS_TOKEN);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }

    public static class ArrayObjectDualityDeserializer implements JsonDeserializer<Json_Response> {

        public Json_Response deserialize(JsonElement json, Type typeOfT,
                                         JsonDeserializationContext context) throws JsonParseException {
            Gdx.app.log("ApiClient Response", json.toString());
            Json_Response response = new Json_Response();
            JsonObject object = json.getAsJsonObject();
            response.setCode(object.get("code").getAsInt());
            response.setMsg(object.get("message").getAsString());

            if(object.get("data").isJsonArray()) {
                try {
                    GameRoom[] gameRooms = gson.fromJson(object.get("data"), GameRoom[].class);
                    response.setGameRooms(gameRooms);
                } catch (JsonSyntaxException e) {

                }
            } else if(object.get("data").isJsonObject()) {
                try {
                    UserGroup userGroup = gson.fromJson(object.get("data"), UserGroup.class);
                    response.setUserGroup(userGroup);
                } catch (JsonSyntaxException e) {

                }
            } else if (object.get("data").isJsonNull()) {

            } else {
                response.setData(object.get("data").getAsString());
            }

            return response;
        }
    }
}