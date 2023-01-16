package co.miaki.islamicapp.Models.IslamicTopicModel;

import com.google.gson.annotations.SerializedName;

public class IslamicTopicDataModel {
    @SerializedName("title")
    private String title;

    @SerializedName("audio_path")
    private String audio_path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudio_path() {
        return audio_path;
    }

    public void setAudio_path(String audio_path) {
        this.audio_path = audio_path;
    }
}
