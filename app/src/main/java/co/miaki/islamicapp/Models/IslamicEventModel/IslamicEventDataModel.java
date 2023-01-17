package co.miaki.islamicapp.Models.IslamicEventModel;

import com.google.gson.annotations.SerializedName;

public class IslamicEventDataModel {

    @SerializedName("id")
    private int id;

    @SerializedName("image_path")
    private String image_path;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
