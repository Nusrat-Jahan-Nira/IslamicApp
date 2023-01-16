package co.miaki.islamicapp.Models.HadisModel;

import com.google.gson.annotations.SerializedName;

public class HadisDataModel {


//    @SerializedName("topic")
//    private String topic;

    @SerializedName("meaning")
    private String hadisMeaning;

//    public String getTopic() {
//        return topic;
//    }

    public String getHadisMeaning() {
        return hadisMeaning;
    }
}

