package co.miaki.islamicapp.Models.DuaDetailModel;

import com.google.gson.annotations.SerializedName;

public class DuaDetailDataModel {


    @SerializedName("title")
    private String title;

    @SerializedName("dua")
    private String dua;

    @SerializedName("transliteration")
    private String transliteration;

    @SerializedName("meaning")
    private String meaning;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDua() {
        return dua;
    }

    public void setDua(String dua) {
        this.dua = dua;
    }

    public String getTransliteration() {
        return transliteration;
    }

    public void setTransliteration(String transliteration) {
        this.transliteration = transliteration;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}

