package co.miaki.islamicapp.Models.SubscriptionApi;

import com.google.gson.annotations.SerializedName;

public class SubscriptionDataModel {

    @SerializedName("u_id")
    private String uId;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }
}

