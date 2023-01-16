package co.miaki.islamicapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;

import java.util.ArrayList;

import co.miaki.islamicapp.Adapter.QuranDetailAdapter;
import co.miaki.islamicapp.GlobalMethods;
import co.miaki.islamicapp.Interfaces.ApiInterface;
import co.miaki.islamicapp.Models.QuranDetailModel.QuranDetailDataModel;
import co.miaki.islamicapp.Models.QuranDetailModel.QuranDetailResponseModel;
import co.miaki.islamicapp.R;
import co.miaki.islamicapp.Retrofit.RetrofitApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuranDetailActivity extends AppCompatActivity {

    JcPlayerView jcPlayerView;

    private ArrayList<QuranDetailDataModel> nameList;

    private RecyclerView listView;
    private QuranDetailAdapter quranAdapter;

    private ApiInterface apiInterface;
    AlertDialog.Builder builder;

    ProgressBar progressBar;

    int itemId;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_detail);

        String surahName = getIntent().getStringExtra("suraName");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);
        toolbar.setTitle(surahName);


        //Setting the Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        listView = findViewById(R.id.quranAyatList);

        final LinearLayoutManager lmQuran = new GridLayoutManager(getApplicationContext(), 1);

        listView.setLayoutManager(lmQuran);

        if (GlobalMethods.isConnected(this)) {

            callQuranDetailAPI();

        } else {

            builder = new AlertDialog.Builder(QuranDetailActivity.this);
            builder.setTitle("Internet is not connected!!")
                    .setCancelable(false)
                    .setIcon(R.mipmap.icon)
                    .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            startActivity(new Intent(Settings.ACTION_SETTINGS));
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            finish();
                        }
                    })
                    .show();
        }


    }

    private void callQuranDetailAPI() {

        Call<QuranDetailResponseModel> call = apiInterface.getQuranDetail(getIntent().getIntExtra("itemId",1));
        call.enqueue(new Callback<QuranDetailResponseModel>() {
            @Override
            public void onResponse(Call<QuranDetailResponseModel> call, Response<QuranDetailResponseModel> response) {


                assert response.body() != null;
                if (response.body().getStatus() == 1) {

                    jcPlayerView = findViewById(R.id.jcplayer);

                    ArrayList<JcAudio> jcAudios = new ArrayList<>();
                    jcAudios.add(JcAudio.createFromURL(response.body().getSuraName(),response.body().getAudiopath()));


                    jcPlayerView.initPlaylist(jcAudios, null);

                    progressBar = findViewById(R.id.pBarQuranDetail);
                    progressBar.setVisibility(View.GONE);

                    if (quranAdapter != null) {
                        nameList.addAll(response.body().getDetail());
                        quranAdapter.notifyDataSetChanged();
                    } else {
                        nameList = response.body().getDetail();
                        quranAdapter = new QuranDetailAdapter(getApplicationContext(), nameList);
                        listView.setAdapter(quranAdapter);
                    }

                }


            }

            @Override
            public void onFailure(Call<QuranDetailResponseModel> call, Throwable t) {

                Toast.makeText(QuranDetailActivity.this, "API Failed!", Toast.LENGTH_SHORT).show();

            }


        });

    }

    @Override
    public void onPause() {
        super.onPause();

        jcPlayerView.kill();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        jcPlayerView.kill();
    }
}