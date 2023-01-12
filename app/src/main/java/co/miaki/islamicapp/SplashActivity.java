package co.miaki.islamicapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import co.miaki.islamicapp.Activities.DashboardActivity;
import co.miaki.islamicapp.Interfaces.ApiInterface;
import co.miaki.islamicapp.Retrofit.RetrofitApiClient;

public class SplashActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    ApiInterface apiInterface;
    String appUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        checkVersion();

    }

    private void checkVersion() {

//        Call<VersionCheckResponseModel> call = apiInterface.getVersionCode("ialo","2.3");
//        call.enqueue(new Callback<VersionCheckResponseModel>() {
//            @Override
//            public void onResponse(Call<VersionCheckResponseModel> call, Response<VersionCheckResponseModel> response) {
//                try {
//
//                    assert response.body() != null;
//                    if (response.body().getStatus().matches("1")) {

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                                finish();
                            }
                        }, 3000);


//                        Toast.makeText(SplashActivity.this, "Version is updated!!", Toast.LENGTH_SHORT).show();
//
//                    } else if(response.body().getStatus().matches("2")){
//
//                        Toast.makeText(SplashActivity.this, "App is under maintenance", Toast.LENGTH_SHORT).show();
//                    }else {
//
//                        appUrl = response.body().getDownloadUrl();
//
//                        updateApp();
//                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

//            private void updateApp() {
//
//                builder = new AlertDialog.Builder(SplashActivity.this);
//                builder.setTitle("Please update your app!!")
//                        .setCancelable(false)
//                        .setIcon(R.mipmap.icon)
//                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//                                        Uri.parse(appUrl));
//                                startActivity(browserIntent);
//                            }
//                        })
//                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                dialog.dismiss();
//                                finish();
//                            }
//                        })
//                        .show();
//            }
//
//            @Override
//            public void onFailure(Call<VersionCheckResponseModel> call, Throwable t) {
//
//            }
//        });
    }

}