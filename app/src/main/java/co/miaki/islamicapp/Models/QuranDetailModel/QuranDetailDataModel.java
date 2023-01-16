package co.miaki.islamicapp.Models.QuranDetailModel;

import com.google.gson.annotations.SerializedName;

public class QuranDetailDataModel {

    @SerializedName("ayat_no")
    private int ayatno;

    @SerializedName("ayat")
    private String ayat;

    @SerializedName("meaning")
    private String meaning;

    public int getAyatno() {
        return ayatno;
    }

    public void setAyatno(int ayatno) {
        this.ayatno = ayatno;
    }

    public String getAyat() {
        return ayat;
    }

    public void setAyat(String ayat) {
        this.ayat = ayat;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
