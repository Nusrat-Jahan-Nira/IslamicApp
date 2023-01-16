package co.miaki.islamicapp.Models.NamazLessonDetailModel;

import com.google.gson.annotations.SerializedName;

import co.miaki.islamicapp.Models.DuaDetailModel.DuaDetailDataModel;


public class NamazLessonDetailResponseModel {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("detail")
    private NamazDetailDataModel detail;

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

    public NamazDetailDataModel getDetail() {
        return detail;
    }

    public void setDetail(NamazDetailDataModel detail) {
        this.detail = detail;
    }
}