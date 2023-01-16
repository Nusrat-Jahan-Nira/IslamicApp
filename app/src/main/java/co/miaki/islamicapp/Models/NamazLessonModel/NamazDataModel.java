package co.miaki.islamicapp.Models.NamazLessonModel;

import com.google.gson.annotations.SerializedName;

public class NamazDataModel {


    @SerializedName("id")
    private int id;

    @SerializedName("topic")
    private String topic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}