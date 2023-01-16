package co.miaki.islamicapp.Models.DuaDetailModel;

import com.google.gson.annotations.SerializedName;

public class DuaDetailResponseModel {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("detail")
    private DuaDetailDataModel detail;

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

    public DuaDetailDataModel getDetail() {
        return detail;
    }

    public void setDetail(DuaDetailDataModel detail) {
        this.detail = detail;
    }
}