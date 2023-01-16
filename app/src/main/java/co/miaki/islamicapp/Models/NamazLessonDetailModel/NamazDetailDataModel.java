package co.miaki.islamicapp.Models.NamazLessonDetailModel;

import com.google.gson.annotations.SerializedName;

public class NamazDetailDataModel {


    //    @SerializedName("topic")
//    private String topic;

    @SerializedName("topic")
    private String topic;

    @SerializedName("detail")
    private String detail;

//    public String getTopic() {
//        return topic;
//    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
