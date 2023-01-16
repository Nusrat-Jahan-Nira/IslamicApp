package co.miaki.islamicapp.Models.HadisModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HadisResponseModel {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("detail")
    private HadisDataModel detail;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public HadisDataModel getDetail() {
        return detail;
    }
}
