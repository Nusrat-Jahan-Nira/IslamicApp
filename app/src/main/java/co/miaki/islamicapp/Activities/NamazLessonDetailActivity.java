package co.miaki.islamicapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import co.miaki.islamicapp.GlobalMethods;
import co.miaki.islamicapp.Interfaces.ApiInterface;
import co.miaki.islamicapp.Models.DuaDetailModel.DuaDetailResponseModel;
import co.miaki.islamicapp.Models.NamazLessonDetailModel.NamazLessonDetailResponseModel;
import co.miaki.islamicapp.R;
import co.miaki.islamicapp.Retrofit.RetrofitApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NamazLessonDetailActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    AlertDialog.Builder builder;

    TextView tvTitle, tvArbi, tvBanglaMeaning, tvPron;

    int itemId;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namaz_lesson_detail);


        itemId = getIntent().getIntExtra("itemId", 1);

        tvTitle = findViewById(R.id.namazHeadlinetxt);
//        tvArbi = findViewById(R.id.namaztxtArbi);
        tvBanglaMeaning = findViewById(R.id.namaztxtBng);
        //tvPron = findViewById(R.id.namazProtxtBng);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        if (GlobalMethods.isConnected(this)) {

            callNamazDetailListApi(itemId);
        } else {

            builder = new AlertDialog.Builder(this);
            builder.setTitle("Internet is not connected!!")
                    .setCancelable(true)
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("Connect", (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_SETTINGS)))
                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                    .show();
        }

    }


    private void callNamazDetailListApi(int itemId) {

        Call<NamazLessonDetailResponseModel> call = apiInterface.getNamazDetail(itemId);
        call.enqueue(new Callback<NamazLessonDetailResponseModel>() {
            @Override
            public void onResponse(Call<NamazLessonDetailResponseModel> call, Response<NamazLessonDetailResponseModel> response) {


                assert response.body() != null;
                if (response.body().getStatus() == 1) {


                    if(response.body().getDetail()!= null){
                        tvTitle.setText(response.body().getDetail().getTopic());
                        //tvArbi.setText(response.body().getDetail().getDua());
                        tvBanglaMeaning.setText(response.body().getDetail().getDetail());
                        //tvPron.setText(response.body().getDetail().getTransliteration());
                    }



                }

            }

            @Override
            public void onFailure(Call<NamazLessonDetailResponseModel> call, Throwable t) {

            }


        });

    }
}