package co.miaki.islamicapp.Models.NamazTimingModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import co.miaki.islamicapp.Models.NamazLessonModel.NamazDataModel;

public class NamazTimingApiResponseModel {


    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("detail")
    private NamazTimingDataModel detail;

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

    public NamazTimingDataModel getDetail() {
        return detail;
    }

    public void setDetail(NamazTimingDataModel detail) {
        this.detail = detail;
    }
}