package co.miaki.islamicapp.Models.QuranModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuranResponseModel {


    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("detail")
    private ArrayList<QuranDataModel> detail;

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

    public ArrayList<QuranDataModel> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<QuranDataModel> detail) {
        this.detail = detail;
    }
}

