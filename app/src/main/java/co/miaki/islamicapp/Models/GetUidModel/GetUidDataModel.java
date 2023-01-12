package co.miaki.islamicapp.Models.GetUidModel;

import com.google.gson.annotations.SerializedName;

public class GetUidDataModel {

    @SerializedName("u_id")
    private String uId;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }


}
