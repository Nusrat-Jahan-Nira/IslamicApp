package co.miaki.islamicapp.Models;

import com.google.gson.annotations.SerializedName;

public class Ramadan {

    @SerializedName("today_sehri_time")
    private String todaySehriTime;

    @SerializedName("today_iftar_time")
    private String todayIftarTime;

    @SerializedName("tomorrow_sehri_time")
    private String tomorrowSehriTime;

    @SerializedName("tomorrow_iftar_time")
    private String tomorrowIftarTime;

    public String getTodaySehriTime() {
        return todaySehriTime;
    }

    public void setTodaySehriTime(String todaySehriTime) {
        this.todaySehriTime = todaySehriTime;
    }

    public String getTodayIftarTime() {
        return todayIftarTime;
    }

    public void setTodayIftarTime(String todayIftarTime) {
        this.todayIftarTime = todayIftarTime;
    }

    public String getTomorrowSehriTime() {
        return tomorrowSehriTime;
    }

    public void setTomorrowSehriTime(String tomorrowSehriTime) {
        this.tomorrowSehriTime = tomorrowSehriTime;
    }

    public String getTomorrowIftarTime() {
        return tomorrowIftarTime;
    }

    public void setTomorrowIftarTime(String tomorrowIftarTime) {
        this.tomorrowIftarTime = tomorrowIftarTime;
    }
}

