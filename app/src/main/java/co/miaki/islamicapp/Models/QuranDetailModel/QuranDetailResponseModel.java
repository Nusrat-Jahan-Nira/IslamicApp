package co.miaki.islamicapp.Models.QuranDetailModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuranDetailResponseModel {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("sura_name")
    private String suraName;

    @SerializedName("audio_path")
    private String audiopath;

    @SerializedName("detail")
    private ArrayList<QuranDetailDataModel> detail;

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

    public String getSuraName() {
        return suraName;
    }

    public void setSuraName(String suraName) {
        this.suraName = suraName;
    }

    public String getAudiopath() {
        return audiopath;
    }

    public void setAudiopath(String audiopath) {
        this.audiopath = audiopath;
    }

    public ArrayList<QuranDetailDataModel> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<QuranDetailDataModel> detail) {
        this.detail = detail;
    }
}

