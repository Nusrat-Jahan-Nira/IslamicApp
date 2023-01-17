package co.miaki.islamicapp.Interfaces;

import co.miaki.islamicapp.Models.CheckSub_unsub_model.CheckSub_unsub_dataResModel;
import co.miaki.islamicapp.Models.CheckSub_unsub_model.CheckSub_unsub_dataparam;
import co.miaki.islamicapp.Models.DataHijri;
import co.miaki.islamicapp.Models.DuaDetailModel.DuaDetailResponseModel;
import co.miaki.islamicapp.Models.DuaModel.DuaResponseModel;
import co.miaki.islamicapp.Models.GetUidModel.GetUidParamModel;
import co.miaki.islamicapp.Models.GetUidModel.GetUidResponseModel;
import co.miaki.islamicapp.Models.HadisModel.HadisResponseModel;
import co.miaki.islamicapp.Models.IslamicEventModel.IslamicEventResponseModel;
import co.miaki.islamicapp.Models.IslamicTopicModel.IslamicTopicResponseModel;
import co.miaki.islamicapp.Models.NamazLessonDetailModel.NamazLessonDetailResponseModel;
import co.miaki.islamicapp.Models.NamazLessonModel.NamazLessonResponseModel;
import co.miaki.islamicapp.Models.NamazTimingModel.NamazTimingApiResponseModel;
import co.miaki.islamicapp.Models.QuranDetailModel.QuranDetailResponseModel;
import co.miaki.islamicapp.Models.QuranModel.QuranResponseModel;
import co.miaki.islamicapp.Models.SubscriptionApi.SubscriptionApiResponseModel;
import co.miaki.islamicapp.Models.SubscriptionApi.SubscriptionParamModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @GET("/api/dua/view")
    Call<DuaDetailResponseModel> getDuaDetail(@Query("id") String id);

    @GET("/api/hadith")
    Call<HadisResponseModel> gethadisResponse();

    @GET("/api/sura/list")
    Call<QuranResponseModel> getSuraName();

    @GET("/api/sura/view")
    Call<QuranDetailResponseModel> getQuranDetail(@Query("id") int id);

    @GET("/api/namaz/shikkha/list")
    Call<NamazLessonResponseModel> getNamazList();

    @GET("/api/namaz/shikkha/view?")
    Call<NamazLessonDetailResponseModel> getNamazDetail(@Query("id") int id);


    @GET("/api/islamic/topics")
    Call<IslamicTopicResponseModel> getTopic();

    @GET("/api/namaz/timing")
    Call<NamazTimingApiResponseModel> getNamazTiming();


    @GET("/api/islamic/event/all")
    Call<IslamicEventResponseModel> getIslamicEventAllList();



}
