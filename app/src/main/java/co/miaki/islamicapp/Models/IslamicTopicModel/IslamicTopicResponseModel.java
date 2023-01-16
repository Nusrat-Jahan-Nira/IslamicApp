package co.miaki.islamicapp.Models.IslamicTopicModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import co.miaki.islamicapp.Models.DuaModel.DuaDataModel;
import co.miaki.islamicapp.Models.HadisModel.HadisDataModel;

public class IslamicTopicResponseModel {


    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("detail")
    private ArrayList<IslamicTopicDataModel> detail;

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

    public ArrayList<IslamicTopicDataModel> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<IslamicTopicDataModel> detail) {
        this.detail = detail;
    }
}