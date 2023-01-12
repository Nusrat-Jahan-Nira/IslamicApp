package co.miaki.islamicapp.Models.DuaModel;

import com.google.gson.annotations.SerializedName;

public class DuaDataModel {


    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
