package com.radikal.holdempoker.api;

import com.radikal.holdempoker.models.Json_Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    //PayTm Txn Status
    /*@FormUrlEncoded
    @POST("checkstatus_api.php")
    Call<String> getPayTmTxnStatus(@Field("MID") String MID, @Field("ORDERID") String ORDER_ID,
                                   @Field("CHECKSUMHASH") String CHECKSUM_HASH);*/

    //Login
    @FormUrlEncoded
    @POST("login")
    Call<Json_Response> login(  @Field("username") String email,
                                @Field("password") String password  );

    //Get All Rooms
    @POST("get_rooms")
    Call<Json_Response> getRooms();

    //Get GameConfig
    /*@POST("get_config")
    Call<Json_Response> getGameConfig();*/

    //Get GameConfig
    /*@POST("get_about_us")
    Call<Json_Response> getAboutUs();*/

    //Logout
    /*@POST("logout")
    Call<Json_Response> logout();*/

    //Login
    /*@FormUrlEncoded
    @POST("register")
    Call<Json_Response> register(@Field("email") String email,
                                 @Field("password") String password,
                                 @Field("display_name") String displayName);*/

    //Update FCM Token
    /*@FormUrlEncoded
    @POST("update_fcm_token")
    Call<Json_Response> updateFcmToken(@Field("fcm_token") String token);*/

    //Get total participation
    /*@POST("get_total_participation")
    Call<Json_Response> getTotalParticipation();*/

    //Get Current Server time
    /*@POST("get_current_server_time")
    Call<Json_Response> getCurrentServerTime();*/

    //Get Session Amount
    //@POST("get_session_amount")
    //Call<Json_Response> getSessionAmount();

    //Get Session Amount
    /*@POST("get_session_details")
    Call<Json_Response> getSessionDetail();*/

    //Get Score
    /*@POST("get_score")
    Call<Json_Response> getScore();*/

    //Update score
    /*@FormUrlEncoded
    @POST("update_score")
    Call<Json_Response> updateScore(@Field("score") int score);*/

    //User has paid
    /*@POST("user_has_paid")
    Call<Json_Response> userHasPaid();*/

    //Participate in session
    /*@POST("participate_in_session")
    Call<Json_Response> participateInSession();*/

    //Participate in session
    /*@POST("make_me_inactive")
    Call<Json_Response> outFromSession();*/

    //Get New Access and Refresh Token
    /*@FormUrlEncoded
    @POST("refresh")
    Call<Json_Response> getNewToken(@Field("refresh_token") String token);*/

    //Get New Access and Refresh Token
    /*@FormUrlEncoded
    @POST("generate_checksum")
    Call<Json_Response> getPayTmChecksumKey(@Field("MID") String MID, @Field("ORDER_ID") String ORDER_ID,
                                            @Field("CUST_ID") String CUST_ID, @Field("INDUSTRY_TYPE_ID") String INDUSTRY_TYPE_ID,
                                            @Field("CHANNEL_ID") String CHANNEL_ID, @Field("TXN_AMOUNT") String TXN_AMOUNT,
                                            @Field("MOBILE_NO") String MOBILE_NO, @Field("EMAIL") String EMAIL,
                                            @Field("WEBSITE") String WEBSITE, @Field("CALLBACK_URL") String CALLBACK_URL);*/

    //Get LeaderBoard
    /*@FormUrlEncoded
    @POST("get_leaderboard")
    Call<Json_Response> getLeaderboard(@Field("top") int top);*/

    //Get Winners
    /*@FormUrlEncoded
    @POST("get_winners")
    Call<Json_Response> getWinners(@Field("event_type") String eventType);*/

    //Get Current LeaderBoard
    /*@POST("get_current_leaderboard")
    Call<Json_Response> getCurrentLeaderboard();*/

    //Get Gift Coupons
    /*@POST("get_paytm_coupon")
    Call<Json_Response> getGiftCoupons();*/

    //Get Event Coupons
    /*@POST("get_events_paytm_coupon")
    Call<Json_Response> getEventCoupons();*/
}