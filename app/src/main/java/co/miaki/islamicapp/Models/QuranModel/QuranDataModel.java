package co.miaki.islamicapp.Models.QuranModel;

import com.google.gson.annotations.SerializedName;

public class QuranDataModel {



    @SerializedName("id")
    private int id;

    @SerializedName("sura_name")
    private String suraName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSuraName() {
        return suraName;
    }

    public void setSuraName(String suraName) {
        this.suraName = suraName;
    }
}
