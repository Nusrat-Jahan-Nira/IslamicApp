package co.miaki.islamicapp.Interfaces;

import co.miaki.islamicapp.Models.CheckSub_unsub_model.CheckSub_unsub_dataResModel;
import co.miaki.islamicapp.Models.CheckSub_unsub_model.CheckSub_unsub_dataparam;
import co.miaki.islamicapp.Models.DataHijri;
import co.miaki.islamicapp.Models.DuaModel.DuaResponseModel;
import co.miaki.islamicapp.Models.GetUidModel.GetUidParamModel;
import co.miaki.islamicapp.Models.GetUidModel.GetUidResponseModel;
import co.miaki.islamicapp.Models.SubscriptionApi.SubscriptionApiResponseModel;
import co.miaki.islamicapp.Models.SubscriptionApi.SubscriptionParamModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("/api/hijri")
    Call<DataHijri> getHijriDate();

    @POST("http://103.9.185.88/bdapps/islamer_alo/public/get_uid")
    Call<GetUidResponseModel>getUid(@Body GetUidParamModel getUidParamModel);

    @POST("http://103.9.185.88/bdapps/islamer_alo/public/check_subscription")
    Call<CheckSub_unsub_dataResModel> checkSub(@Body CheckSub_unsub_dataparam checkSub_unsub_dataparam);

    @POST("http://103.9.185.88/bdapps/islamer_alo/public/subscribe")
    Call<SubscriptionApiResponseModel>getSubResponse(@Body SubscriptionParamModel subscriptionParamModel);

    @GET("/api/dua/list")
    Call<DuaResponseModel> getDua();




}
