package com.radikal.holdempoker.models;

import com.google.gson.annotations.SerializedName;

public class GameRoom {
    @SerializedName("id")
    public int gameId;
    @SerializedName("game_started")
    public int isGameStarted;
    @SerializedName("game_stage")
    public String gameStage;
    @SerializedName("winning_user_id")
    public int winnerUserId;
    @SerializedName("current_user_id")
    public int currentUserId;
    @SerializedName("game_start_time")
    public String gameStartTime;
    @SerializedName("game_end_time")
    public String gameEndTime;
    @SerializedName("min_bet_amount")
    public String minBetAmount;
    @SerializedName("num_players")
    public int numberOfPlayers;
    @SerializedName("image_url")
    public String imageUrl;
    @SerializedName("room_channel")
    public String roomChannel;

    public GameRoom(int gameId, int isGameStarted, String gameStage, int winnerUserId,
                    int currentUserId, String gameStartTime, String gameEndTime,
                    String minBetAmount, int numberOfPlayers, String imageUrl, String roomChannel) {
        this.gameId = gameId;
        this.isGameStarted = isGameStarted;
        this.gameStage = gameStage;
        this.winnerUserId = winnerUserId;
        this.currentUserId = currentUserId;
        this.gameStartTime = gameStartTime;
        this.gameEndTime = gameEndTime;
        this.minBetAmount = minBetAmount;
        this.numberOfPlayers = numberOfPlayers;
        this.imageUrl = imageUrl;
        this.roomChannel = roomChannel;
    }
}
