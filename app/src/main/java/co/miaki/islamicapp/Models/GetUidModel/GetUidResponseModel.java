package co.miaki.islamicapp.Models.GetUidModel;

import com.google.gson.annotations.SerializedName;

public class GetUidResponseModel {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("results")
    private GetUidDataModel results;

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

    public GetUidDataModel getResults() {
        return results;
    }

    public void setResults(GetUidDataModel results) {
        this.results = results;
    }
}

