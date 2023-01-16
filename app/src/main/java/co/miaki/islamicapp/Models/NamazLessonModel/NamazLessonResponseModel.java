package co.miaki.islamicapp.Models.NamazLessonModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import co.miaki.islamicapp.Models.DuaModel.DuaDataModel;

public class NamazLessonResponseModel {


    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("detail")
    private ArrayList<NamazDataModel> detail;

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

    public ArrayList<NamazDataModel> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<NamazDataModel> detail) {
        this.detail = detail;
    }
}