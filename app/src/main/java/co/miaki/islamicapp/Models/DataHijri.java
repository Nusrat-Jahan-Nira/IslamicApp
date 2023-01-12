package co.miaki.islamicapp.Models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class DataHijri {

    @Nullable
    @SerializedName("status")
    private String status;

    @Nullable
    @SerializedName("message")
    private String message;

    @SerializedName("date")
    private String date;

    @Nullable
    @SerializedName("ramadan")
    private Ramadan ramadan;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Nullable
    public Ramadan getRamadan() {
        return ramadan;
    }

    public void setRamadan(@Nullable Ramadan ramadan) {
        this.ramadan = ramadan;
    }
}

