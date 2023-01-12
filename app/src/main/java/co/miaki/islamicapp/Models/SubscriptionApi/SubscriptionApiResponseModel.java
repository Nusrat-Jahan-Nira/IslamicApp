package co.miaki.islamicapp.Models.SubscriptionApi;

import com.google.gson.annotations.SerializedName;

public class SubscriptionApiResponseModel {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private SubscriptionDataModel results;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SubscriptionDataModel getResults() {
        return results;
    }

    public void setResults(SubscriptionDataModel results) {
        this.results = results;
    }
}

