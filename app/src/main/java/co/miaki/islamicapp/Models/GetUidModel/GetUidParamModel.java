package co.miaki.islamicapp.Models.GetUidModel;

import com.google.gson.annotations.SerializedName;

public class GetUidParamModel {

    @SerializedName("msisdn")
    private String msisdn;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }


}
