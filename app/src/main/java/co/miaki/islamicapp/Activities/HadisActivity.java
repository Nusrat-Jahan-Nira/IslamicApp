package co.miaki.islamicapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.miaki.islamicapp.Database.DatabaseHelper;
import co.miaki.islamicapp.GlobalMethods;
import co.miaki.islamicapp.Interfaces.ApiInterface;
import co.miaki.islamicapp.Models.CheckSub_unsub_model.CheckSub_unsub_dataResModel;
import co.miaki.islamicapp.Models.CheckSub_unsub_model.CheckSub_unsub_dataparam;
import co.miaki.islamicapp.Models.GetUidModel.GetUidParamModel;
import co.miaki.islamicapp.Models.GetUidModel.GetUidResponseModel;
import co.miaki.islamicapp.Models.HadisModel.HadisResponseModel;
import co.miaki.islamicapp.Models.SubscriptionApi.SubscriptionApiResponseModel;
import co.miaki.islamicapp.Models.SubscriptionApi.SubscriptionParamModel;
import co.miaki.islamicapp.R;
import co.miaki.islamicapp.Retrofit.RetrofitApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HadisActivity extends AppCompatActivity {

    String hadisText, hadisMeaning;
    TextView tvMeaning, tvMore;

    ApiInterface apiInterface;

    AlertDialog.Builder builder;

    Toolbar toolbar;

    SubscriptionParamModel subscriptionParamModel;
    CheckSub_unsub_dataparam checkSubUnsubDataparam;

    GetUidParamModel getUidParamModel;

    DatabaseHelper db;
    String msisdn, uniqueId, phoneNo, userId;


    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_hadis);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);

        //Setting the Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        //apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);


        tvMeaning = findViewById(R.id.hadistxtBng);
        tvMore = findViewById(R.id.tvMore);


        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                builder = new AlertDialog.Builder(HadisActivity.this);
                builder.setTitle("More Service!!")

                        .setCancelable(false)
                        .setIcon(R.mipmap.icon)
                        .setMessage("প্রতিদিন আরও হাদিস পেতে *২১৩*১১৮# ডায়াল করুন")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(isPermissionGranted()){

                                    call_action();

                                }



                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });



        if (GlobalMethods.isConnected(this)) {

            subscriptionParamModel = new SubscriptionParamModel();


            getUidParamModel = new GetUidParamModel();

            db = new DatabaseHelper(this);

            Cursor cursor = db.getPhoneData();

            Cursor res = db.getPhoneData();
            if (res.getCount() == 0) {

                inputMsisdn();

            } else {

                if (cursor.moveToFirst()) {

                    msisdn = cursor.getString(cursor.getColumnIndex("PHONE"));
                    uniqueId = cursor.getString(cursor.getColumnIndex("USER_ID"));
                }

                checkSubUnsubDataparam = new CheckSub_unsub_dataparam();
                checkSubUnsubDataparam.setuId(uniqueId);
                subscriptionParamModel.setuId(uniqueId);

                callSubCheckApi();
            }

        } else {

            builder = new AlertDialog.Builder(HadisActivity.this);
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

    private void callHadisApi() {

        Call<HadisResponseModel> call = apiInterface.gethadisResponse();
        call.enqueue(new Callback<HadisResponseModel>() {
            @Override
            public void onResponse(Call<HadisResponseModel> call, Response<HadisResponseModel> response) {


                assert response.body() != null;
                if (response.body().getStatus() == 1) {

                    tvMeaning.setText(response.body().getDetail().getHadisMeaning().toString());

                }


            }

            @Override
            public void onFailure(Call<HadisResponseModel> call, Throwable t) {

            }


        });


    }

    private void inputMsisdn() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_msisdn_layout);
        dialog.setCancelable(false);
        dialog.setTitle("you have to subscribe first! subscription charge 2.67tk/per day.Only for robi & airtel users");

        final Button addButton = dialog.findViewById(R.id.addMsisdnBtn);
        final Button cancelButton = dialog.findViewById(R.id.cancelMsisdnBtn);

        // if button is clicked, close the custom dialog
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etMsisdn = dialog.findViewById(R.id.etMsisdn);
                phoneNo = etMsisdn.getText().toString();
                getUidParamModel.setMsisdn(phoneNo);

                if (phoneNo.matches("")) {

                    Toast.makeText(HadisActivity.this, "field can't left blank!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(HadisActivity.this, "number has set!", Toast.LENGTH_SHORT).show();


                getUidApi();

                dialog.dismiss();


            }

        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.cancel();
                finish();

            }
        });


        dialog.show();


    }

    private void getUidApi() {

        Call<GetUidResponseModel> call = apiInterface.getUid(getUidParamModel);
        call.enqueue(new Callback<GetUidResponseModel>() {
            @Override
            public void onResponse(Call<GetUidResponseModel> call, Response<GetUidResponseModel> response) {
                try {

                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        userId = response.body().getResults().getuId();

                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("userId", userId);

                        boolean isInserted = db.insertData(phoneNo, userId);
                        if (isInserted) {

                            checkSubUnsubDataparam = new CheckSub_unsub_dataparam();
                            checkSubUnsubDataparam.setuId(userId);

                            callSubCheckApi();
                            builder = new AlertDialog.Builder(HadisActivity.this);
                            builder.setTitle("Subscribe first!Subscription charge 2.67tk/per day!")
                                    .setCancelable(false)
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            Toast.makeText(HadisActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();


                                        }
                                    })
                                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                            finish();
                                        }
                                    })
                                    .show();


                        } else
                            Toast.makeText(HadisActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                        //Toast.makeText(VideoPlayerActivity.this, "Hoise!", Toast.LENGTH_SHORT).show();

                        //callPlayVideoApi();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetUidResponseModel> call, Throwable t) {

                Toast.makeText(HadisActivity.this, "API Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callSubCheckApi() {

        Call<CheckSub_unsub_dataResModel> call = apiInterface.checkSub(checkSubUnsubDataparam);
        call.enqueue(new Callback<CheckSub_unsub_dataResModel>() {
            @Override
            public void onResponse(Call<CheckSub_unsub_dataResModel> call, Response<CheckSub_unsub_dataResModel> response) {
                try {

                    assert response.body() != null;
                    if (response.body().getResults().matches("REGISTERED")) {



                        SharedPreferences.Editor editor = getSharedPreferences("SUB_STATUS_PREF", MODE_PRIVATE).edit();
                        editor.putString("subStatus", "1");
                        editor.apply();

                        callHadisApi();


                    } else {



                        subscriptionParamModel = new SubscriptionParamModel();

                        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        String aUserID = sh.getString("userId", "");

                        subscriptionParamModel.setuId(aUserID);

                        if(aUserID== null){
                            callSubApi();
                        }
                        else{

                            callHadisApi();
                        }

//                        SharedPreferences.Editor editor = getSharedPreferences("SUB_STATUS_PREF", MODE_PRIVATE).edit();
//                        editor.putString("subStatus", "2");
//                        editor.apply();
//
//                        builder = new AlertDialog.Builder(HadisActivity.this);
//                        builder.setTitle("Subscribe first!Subscription charge 2.67tk/per day!")
//                                .setCancelable(false)
//                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//
////                                        subscriptionParamModel = new SubscriptionParamModel();
////
////                                        subscriptionParamModel.setuId(userId);
//
//
//
//                                        subscriptionParamModel = new SubscriptionParamModel();
//
//                                        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
//                                        String aUserID = sh.getString("userId", "");
//
//                                        subscriptionParamModel.setuId(aUserID);
//
//                                        if(aUserID== null){
//                                            callSubApi();
//                                        }
//                                        else{
//
//                                            callHadisApi();
//                                        }
//
//                                        //callSubApi();
//
//                                    }
//                                })
//                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                        dialog.dismiss();
//                                        finish();
//                                    }
//                                })
//                                .show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CheckSub_unsub_dataResModel> call, Throwable t) {

            }
        });

    }

    private void callSubApi() {

        Call<SubscriptionApiResponseModel> call = apiInterface.getSubResponse(subscriptionParamModel);
        call.enqueue(new Callback<SubscriptionApiResponseModel>() {
            @Override
            public void onResponse(Call<SubscriptionApiResponseModel> call, Response<SubscriptionApiResponseModel> response) {
                try {

                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callSubCheckApi();
                            }
                        }, 5000);

                        Toast.makeText(HadisActivity.this, "Subscription in process!!", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(HadisActivity.this, "API Error!", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SubscriptionApiResponseModel> call, Throwable t) {

            }
        });

    }

    private void call_action() {

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + "*213*118#"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Calling.....", Toast.LENGTH_SHORT).show();
                    call_action();

                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }
}