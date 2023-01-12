package co.miaki.islamicapp.Models.DuaModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DuaResponseModel {


    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("detail")
    private ArrayList<DuaDataModel> detail;

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

    public ArrayList<DuaDataModel> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<DuaDataModel> detail) {
        this.detail = detail;
    }
}
