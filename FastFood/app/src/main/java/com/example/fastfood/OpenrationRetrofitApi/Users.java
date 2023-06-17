package com.example.fastfood.OpenrationRetrofitApi;

import com.google.gson.annotations.SerializedName;

public class Users {

    @SerializedName("user_id")
    private String UserId;
    @SerializedName("email")
    private String Response;

    public Users(String userId, String response) {
        UserId = userId;
        Response = response;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
