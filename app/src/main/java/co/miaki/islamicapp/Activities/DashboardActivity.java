package co.miaki.islamicapp.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import co.miaki.islamicapp.Adapter.SliderAdapter;
import co.miaki.islamicapp.Database.DatabaseHelper;
import co.miaki.islamicapp.Interfaces.ApiInterface;
import co.miaki.islamicapp.Models.CheckSub_unsub_model.CheckSub_unsub_dataResModel;
import co.miaki.islamicapp.Models.CheckSub_unsub_model.CheckSub_unsub_dataparam;
import co.miaki.islamicapp.Models.DataHijri;
import co.miaki.islamicapp.Models.GetUidModel.GetUidParamModel;
import co.miaki.islamicapp.Models.GetUidModel.GetUidResponseModel;
import co.miaki.islamicapp.Models.NamazTimingModel.NamazTimingApiResponseModel;
import co.miaki.islamicapp.Models.SliderItem;
import co.miaki.islamicapp.Models.SubscriptionApi.SubscriptionApiResponseModel;
import co.miaki.islamicapp.Models.SubscriptionApi.SubscriptionParamModel;
import co.miaki.islamicapp.R;
import co.miaki.islamicapp.Retrofit.RetrofitApiClient;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener

{


    SubscriptionParamModel subscriptionParamModel;
    CheckSub_unsub_dataparam checkSubUnsubDataparam;
    GetUidParamModel getUidParamModel;
    String msisdn, uniqueId, phoneNo, userId;
    SliderView sliderView;
    ArrayList<SliderItem> sliderDataArrayList = new ArrayList<>();

    ProgressBar progressBar;
    DatabaseHelper db;


    static TextView fajrTime, dhuhrTime, asrTime, magribTime, ishaTime;


    private double[] prayerTimesCurrent;
    private int[] offsets;

    LinearLayout linearKiblaCampus,linearHadish,linearDua,linearKuran,linearNamaz,linearIslamicTopic,linearTasbih;


    boolean doubleBackToExitPressedOnce = false;

    ApiInterface apiInterface;
    TextView arbiDate;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences prefs = getSharedPreferences("SUB_STATUS_PREF", MODE_PRIVATE);
        String subStatus = prefs.getString("subStatus", "No role defined");

        arbiDate = findViewById(R.id.arbiDateTxt);

        apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        getHijriDate();

        Menu menu = navigationView.getMenu();
        MenuItem sub = menu.findItem(R.id.nav_sub);
        MenuItem unsub = menu.findItem(R.id.nav_unsub);

        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        String aUserID = sh.getString("userId", "");

        if(aUserID.equals("")){
            sub.setVisible(true);
            unsub.setVisible(false);
        }
        else{
            sub.setVisible(false);
            unsub.setVisible(true);
        }

//        if (subStatus.matches("1")) {
//
//            //MenuItem sub = menu.findItem(R.id.nav_sub);
//            sub.setVisible(false);
//
//            //MenuItem unsub = menu.findItem(R.id.nav_unsub);
//            unsub.setVisible(true);
//
//        }
//        else if (subStatus.matches("2")) {
//
//            //MenuItem sub = menu.findItem(R.id.nav_sub);
//            sub.setVisible(true);
//
//            //MenuItem unsub = menu.findItem(R.id.nav_unsub);
//            unsub.setVisible(false);
//        }


        apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        fajrTime = findViewById(R.id.fajarTimeTxt);
        dhuhrTime = findViewById(R.id.johorTimeTxt);
        asrTime = findViewById(R.id.asarTimeTxt);
        magribTime = findViewById(R.id.magribTimeTxt);
        ishaTime = findViewById(R.id.eshaTimeTxt);


        // slider
        sliderView = findViewById(R.id.slider);
        AddImageUrlFormLocalRes();
        SliderAdapter adapter = new SliderAdapter(this,sliderDataArrayList);

        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.BLUE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        calander();


        callNamazTimingApi();


        linearKiblaCampus = findViewById(R.id.linearKiblaCampus);
        linearKiblaCampus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compass = new Intent(DashboardActivity.this, KiblaCompassActivity.class);
                startActivity(compass);
            }
        });

        linearHadish = findViewById(R.id.linearHadish);
        linearHadish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compass = new Intent(DashboardActivity.this, HadisActivity.class);
                startActivity(compass);
            }
        });

        linearDua = findViewById(R.id.linearDua);
        linearDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compass = new Intent(DashboardActivity.this, DuaActivity.class);
                startActivity(compass);
            }
        });

        linearKuran = findViewById(R.id.linearKuran);
        linearKuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compass = new Intent(DashboardActivity.this, QuranActivity.class);
                startActivity(compass);
            }
        });

        linearNamaz = findViewById(R.id.linearNamaz);
        linearNamaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compass = new Intent(DashboardActivity.this, NamazLessonActivity.class);
                startActivity(compass);
            }
        });
        linearIslamicTopic = findViewById(R.id.linearIslamicTopic);
        linearIslamicTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compass = new Intent(DashboardActivity.this, IslamicTopicActivity.class);
                startActivity(compass);
            }
        });
        linearTasbih = findViewById(R.id.linearTasbih);
        linearTasbih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent compass = new Intent(DashboardActivity.this, TasbihActivity.class);
                startActivity(compass);
            }
        });

    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }

    private void calander(){
        Date c = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("hh:mm");
        String time = df.format(c);

        String[] s1 = {"০", "১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯"};
        String[] s2 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        for (int i = 0; i < s2.length; i++) {
            time = time.replace(s2[i], s1[i]);
        }


        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dff = new SimpleDateFormat("dd MMM yyyy");
        String date = dff.format(c);

        String[] date1 = {"০", "১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯", "জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল",
                "মে", "জুন", "জুলাই", "আগস্ট", "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর"};


        String[] date2 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Jan", "Feb", "Mar", "Apr",
                "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};

        for (int i = 0; i < date2.length; i++) {
            date = date.replace(date2[i], date1[i]);
        }


        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dfff = new SimpleDateFormat("EEE");
        String dayOfWeek = dfff.format(c);

        String[] week1 = {"শনিবার", "রবিবার", "সোমবার", "মঙ্গলবার", "বুধবার", "বৃহস্পতিবার", "শুক্রবার"};


        String[] week2 = {"Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri"};

        for (int i = 0; i < week2.length; i++) {
            dayOfWeek = dayOfWeek.replace(week2[i], week1[i]);
        }

        TextView tvNameOfDate = findViewById(R.id.nameOfDateTxt);
        tvNameOfDate.setText(dayOfWeek + "," + date);
    }
    private void getHijriDate() {

        final TextView tvTodayIftar, tvTodaySehri, tvNextSehri, tvNextIftar;

        tvTodayIftar = findViewById(R.id.tvTodayIftar);
        tvTodaySehri = findViewById(R.id.tvTodaySehri);


        Call<DataHijri> call = apiInterface.getHijriDate();
        call.enqueue(new Callback<DataHijri>() {
            @Override
            public void onResponse(Call<DataHijri> call, Response<DataHijri> response) {


                assert response.body() != null;
                assert response.body().getStatus() != null;
                if (response.body().getStatus().matches("1")) {

                    arbiDate.setText(response.body().getDate());

                    if(response.body().getRamadan()!=null){
                        tvTodaySehri.setText(response.body().getRamadan().getTodaySehriTime());
                        tvTodayIftar.setText(response.body().getRamadan().getTodayIftarTime());
                    }
                }
            }

            @Override
            public void onFailure(Call<DataHijri> call, Throwable t) {

            }


        });

    }


    @Override
    protected void onStop() {
       // sliderLayout.stopAutoCycle();

        super.onStop();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (doubleBackToExitPressedOnce) {


            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_event) {

//            Intent event = new Intent(DashboardActivity.this, IslamicEventActivity.class);
//            startActivity(event);


        } else if (id == R.id.nav_topic) {


            Intent topic = new Intent(DashboardActivity.this, IslamicTopicActivity.class);
            startActivity(topic);


        } else if (id == R.id.nav_hadis) {

            Intent hadis = new Intent(DashboardActivity.this, HadisActivity.class);
            startActivity(hadis);


        } else if (id == R.id.nav_dua) {

            Intent dua = new Intent(DashboardActivity.this, DuaActivity.class);
            startActivity(dua);

        } else if (id == R.id.nav_namaz_shikkha) {

            Intent namazlesson = new Intent(DashboardActivity.this, NamazLessonActivity.class);
            startActivity(namazlesson);


        } else if (id == R.id.nav_quran) {

            Intent quran = new Intent(DashboardActivity.this, QuranActivity.class);
            startActivity(quran);

        } else if (id == R.id.nav_tasbih) {

            Intent tasbih = new Intent(DashboardActivity.this, TasbihActivity.class);
            startActivity(tasbih);

        } else if (id == R.id.nav_names) {

//            Intent asmaulHusna = new Intent(DashboardActivity.this, AsmaulHusnaActivity.class);
//            startActivity(asmaulHusna);

        } else if (id == R.id.nav_kibla) {

            Intent compass = new Intent(DashboardActivity.this, KiblaCompassActivity.class);
            startActivity(compass);

        } else if (id == R.id.nav_sub) {

            sub();

        } else if (id == R.id.nav_unsub) {


            showAlert();


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAlert() {

        final Dialog dialog = new Dialog(DashboardActivity.this);
        dialog.setContentView(R.layout.show_dialog_layout);
        dialog.setCancelable(true);
        dialog.setTitle("User Alert!");

        final Button okButton = dialog.findViewById(R.id.okBtn);

        // if button is clicked, close the custom dialog
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();


            }
        });


        dialog.show();
    }


    @SuppressLint("Range")
    private void sub() {

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

    }



    public void AddImageUrlFormLocalRes() {
        sliderDataArrayList.add(new SliderItem(getURLForResource(R.drawable.ab),""));
        sliderDataArrayList.add(new SliderItem(getURLForResource(R.drawable.ab2),""));
        sliderDataArrayList.add(new SliderItem(getURLForResource(R.drawable.ab3),""));
        sliderDataArrayList.add(new SliderItem(getURLForResource(R.drawable.ab4),""));
        sliderDataArrayList.add(new SliderItem(getURLForResource(R.drawable.ab5),""));

    }


    private void inputMsisdn() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_msisdn_layout);
        dialog.setCancelable(true);
        dialog.setTitle("you have to subscribe first! subscription charge 2.67tk/per day.Only for robi & airtel users");

        final Button addButton = dialog.findViewById(R.id.addMsisdnBtn);

        // if button is clicked, close the custom dialog
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etMsisdn = dialog.findViewById(R.id.etMsisdn);
                phoneNo = etMsisdn.getText().toString();
                getUidParamModel.setMsisdn(phoneNo);

                if (phoneNo.matches("")) {

                    Toast.makeText(DashboardActivity.this, "field can't left blank!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(DashboardActivity.this, "number has set!", Toast.LENGTH_SHORT).show();


                getUidApi();

                dialog.dismiss();


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

                        myEdit.commit();

                        boolean isInserted = db.insertData(phoneNo, userId);
                        if (isInserted) {

                            checkSubUnsubDataparam = new CheckSub_unsub_dataparam();
                            checkSubUnsubDataparam.setuId(uniqueId);

                            callSubCheckApi();
                            Toast.makeText(DashboardActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();

                        } else
                            Toast.makeText(DashboardActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                        //Toast.makeText(VideoPlayerActivity.this, "Hoise!", Toast.LENGTH_SHORT).show();

                        //callPlayVideoApi();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetUidResponseModel> call, Throwable t) {

                Toast.makeText(DashboardActivity.this, "API Error!", Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(DashboardActivity.this, "Already subscribe!", Toast.LENGTH_SHORT).show();

                        NavigationView navigationView = findViewById(R.id.nav_view);
                        Menu menu = navigationView.getMenu();

                        MenuItem sub = menu.findItem(R.id.nav_sub);
                        sub.setVisible(false);

                        MenuItem unsub = menu.findItem(R.id.nav_unsub);
                        unsub.setVisible(true);


                    } else {
                        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        String aUserID = sh.getString("userId", "");

                        subscriptionParamModel.setuId(aUserID);

                        if(aUserID== null){
                            callSubApi();
                        }
                        else{

                            NavigationView navigationView = findViewById(R.id.nav_view);
                            Menu menu = navigationView.getMenu();

                            MenuItem sub = menu.findItem(R.id.nav_sub);
                            sub.setVisible(false);

                            MenuItem unsub = menu.findItem(R.id.nav_unsub);
                            unsub.setVisible(true);
                        }
//
//
//                        builder = new AlertDialog.Builder(DashboardActivity.this);
//                        builder.setTitle("Subscribe first!Subscription charge 2.67tk/per day!")
//                                .setCancelable(false)
//                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                                        //inputMsisdn();
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
//                                            NavigationView navigationView = findViewById(R.id.nav_view);
//                                            Menu menu = navigationView.getMenu();
//
//                                            MenuItem sub = menu.findItem(R.id.nav_sub);
//                                            sub.setVisible(false);
//
//                                            MenuItem unsub = menu.findItem(R.id.nav_unsub);
//                                            unsub.setVisible(true);
//                                        }
//
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

                        callSubCheckApi();
                        Toast.makeText(DashboardActivity.this, "Subscription in process!!", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(DashboardActivity.this, "API Error!", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SubscriptionApiResponseModel> call, Throwable t) {

            }
        });

    }

    private void callNamazTimingApi() {

        Call<NamazTimingApiResponseModel> call = apiInterface.getNamazTiming();
        call.enqueue(new Callback<NamazTimingApiResponseModel>() {
            @Override
            public void onResponse(Call<NamazTimingApiResponseModel> call, Response<NamazTimingApiResponseModel> response) {
                try {

                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {

                        progressBar = findViewById(R.id.pBarDua);
                        progressBar.setVisibility(View.GONE);

                         fajrTime.setText("ভোর " + response.body().getDetail().getFajr_start());
                         dhuhrTime.setText("দুপুর " + response.body().getDetail().getDhuhr_start());
                         asrTime.setText("বিকাল " + response.body().getDetail().getAsr_start());
                         magribTime.setText("সন্ধ্যা " + response.body().getDetail().getMaghrib_start());
                         ishaTime.setText("রাত " + response.body().getDetail().getIsha_start());

                    } else
                        Toast.makeText(DashboardActivity.this, "API Error!", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<NamazTimingApiResponseModel> call, Throwable t) {

            }
        });

    }

    public void gotoRamadan(View view) {
    }


}
