package com.radikal.holdempoker.models;

public class Json_Response {

    private int code;
    private String data;
    private String msg;
    private UserGroup userGroup;
    private GameRoom[] gameRooms;

    public GameRoom[] getGameRooms() {
        return gameRooms;
    }

    public void setGameRooms(GameRoom[] gameRooms) {
        this.gameRooms = gameRooms;
    }

    public Json_Response() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
}
