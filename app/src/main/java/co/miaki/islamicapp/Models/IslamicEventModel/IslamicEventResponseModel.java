package co.miaki.islamicapp.Models.IslamicEventModel;

import com.google.gson.annotations.SerializedName;

import co.miaki.islamicapp.Models.DuaDetailModel.DuaDetailDataModel;

public class IslamicEventResponseModel {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("detail")
    private IslamicEventDataModel detail;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IslamicEventDataModel getDetail() {
        return detail;
    }

    public void setDetail(IslamicEventDataModel detail) {
        this.detail = detail;
    }
}