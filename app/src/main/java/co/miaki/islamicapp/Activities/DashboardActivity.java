package co.miaki.islamicapp.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
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

    //SliderView sliderLayout;
    SliderView sliderView;
    ArrayList<SliderItem> sliderDataArrayList = new ArrayList<>();



    HashMap<String, Integer> HashMapForLocalRes;


    DatabaseHelper db;


    // ---------------------- Global Variables --------------------
    private int calcMethod; // caculation method
    private int asrJuristic; // Juristic method for Asr
    private int dhuhrMinutes; // minutes after mid-day for Dhuhr
    private int adjustHighLats; // adjusting method for higher latitudes
    private int timeFormat; // time format
    private double lat; // latitude
    private double lng; // longitude
    private double timeZone; // time-zone
    private double JDate; // Julian date
    // ------------------------------------------------------------
    // Calculation Methods
    private int Jafari; // Ithna Ashari
    private int Karachi; // University of Islamic Sciences, Karachi
    private int ISNA; // Islamic Society of North America (ISNA)
    private int MWL; // Muslim World League (MWL)
    private int Makkah; // Umm al-Qura, Makkah
    private int Egypt; // Egyptian General Authority of Survey
    private int Custom; // Custom Setting
    private int Tehran; // Institute of Geophysics, University of Tehran
    // Juristic Methods
    private int Hanafi; // Hanafi
    // Adjusting Methods for Higher Latitudes
    private int None; // No adjustment
    private int MidNight; // middle of night
    private int OneSeventh; // a/7th of night
    private int AngleBased; // angle/60th of night
    // Time Formats
    private int Time12; // 12-hour format
    private int Time12NS; // 12-hour format with no suffix
    private int Floating; // floating point number
    // Time Names
    private ArrayList<String> timeNames;
    private String InvalidTime; // The string used for invalid times
    // --------------------- Technical Settings --------------------
    private int numIterations; // number of iterations needed to compute times
    // ------------------- Calc Method Parameters --------------------
    private HashMap<Integer, double[]> methodParams;

    static TextView fajrTime, dhuhrTime, asrTime, magribTime, ishaTime;


    private double[] prayerTimesCurrent;
    private int[] offsets;


    //private FeatureCoverFlow coverFlow;
    // private CoverFlowAdapter adapter;
    //private ArrayList<DataModelSlider> games;

    String replacedFajr, replacedDhusor, replacedAsr, replacedMagrib, replacedIsha;

    boolean doubleBackToExitPressedOnce = false;

    ApiInterface apiInterface;
    TextView arbiDate;

    String url1 = "https://www.geeksforgeeks.org/wp-content/uploads/gfg_200X200-1.png";
    String url2 = "https://qphs.fs.quoracdn.net/main-qimg-8e203d34a6a56345f86f1a92570557ba.webp";
    String url3 = "https://bizzbucket.co/wp-content/uploads/2020/08/Life-in-The-Metro-Blog-Title-22.png";

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

        if (subStatus.matches("1")) {

            MenuItem sub = menu.findItem(R.id.nav_sub);
            sub.setVisible(false);

            MenuItem unsub = menu.findItem(R.id.nav_unsub);
            unsub.setVisible(true);

        } else if (subStatus.matches("2")) {

            MenuItem sub = menu.findItem(R.id.nav_sub);
            sub.setVisible(true);

            MenuItem unsub = menu.findItem(R.id.nav_unsub);
            unsub.setVisible(false);
        }


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

        main();


    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
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


    public DashboardActivity() {
        // Initialize vars

        this.setCalcMethod(0);
        this.setAsrJuristic(0);
        this.setDhuhrMinutes(0);
        this.setAdjustHighLats(1);
        this.setTimeFormat(0);

        // Calculation Methods
        this.setKarachi(1); // University of Islamic Sciences, Karachi

        // Juristic Methods
        this.setHanafi(1); // Hanafi

        // Adjusting Methods for Higher Latitudes
        this.setNone(0); // No adjustment
        this.setMidNight(1); // middle of night
        this.setOneSeventh(2); // a/7th of night
        this.setAngleBased(3); // angle/60th of night

        // Time Formats
        this.setTime12(1); // 12-hour format
        this.setTime12NS(2); // 12-hour format with no suffix
        this.setFloating(3); // floating point number

        // Time Names
        timeNames = new ArrayList<String>();
        timeNames.add("Fajr");
        timeNames.add("Sunrise");
        timeNames.add("Dhuhr");
        timeNames.add("Asr");
        timeNames.add("Sunset");
        timeNames.add("Maghrib");
        timeNames.add("Isha");

        InvalidTime = "-----"; // The string used for invalid times

        // --------------------- Technical Settings --------------------

        this.setNumIterations(1); // number of iterations needed to compute
        // times

        // ------------------- Calc Method Parameters --------------------

        // Tuning offsets {fajr, sunrise, dhuhr, asr, sunset, maghrib, isha}
        offsets = new int[7];
        offsets[0] = 0;
        offsets[1] = 0;
        offsets[2] = 0;
        offsets[3] = 0;
        offsets[4] = 0;
        offsets[5] = 0;
        offsets[6] = 0;

        /*
         *
         * fa : fajr angle ms : maghrib selector (0 = angle; a = minutes after
         * sunset) mv : maghrib parameter value (in angle or minutes) is : isha
         * selector (0 = angle; a = minutes after maghrib) iv : isha parameter
         * value (in angle or minutes)
         */
        methodParams = new HashMap<Integer, double[]>();

        // Karachi
        double[] Kvalues = {18, 1, 0, 0, 18};
        methodParams.put(Integer.valueOf(this.getKarachi()), Kvalues);

    }

    // ---------------------- Trigonometric Functions -----------------------
    // range reduce angle in degrees.
    private double fixangle(double a) {

        a = a - (360 * (Math.floor(a / 360.0)));

        a = a < 0 ? (a + 360) : a;

        return a;
    }

    // range reduce hours to 0..23
    private double fixhour(double a) {
        a = a - 24.0 * Math.floor(a / 24.0);
        a = a < 0 ? (a + 24) : a;
        return a;
    }

    // radian to degree
    private double radiansToDegrees(double alpha) {
        return ((alpha * 180.0) / Math.PI);
    }

    // deree to radian
    private double DegreesToRadians(double alpha) {
        return ((alpha * Math.PI) / 180.0);
    }

    // degree sin
    private double dsin(double d) {
        return (Math.sin(DegreesToRadians(d)));
    }

    // degree cos
    private double dcos(double d) {
        return (Math.cos(DegreesToRadians(d)));
    }

    // degree tan
    private double dtan(double d) {
        return (Math.tan(DegreesToRadians(d)));
    }

    // degree arcsin
    private double darcsin(double x) {
        double val = Math.asin(x);
        return radiansToDegrees(val);
    }

    // degree arccos
    private double darccos(double x) {
        double val = Math.acos(x);
        return radiansToDegrees(val);
    }


    // degree arctan2
    private double darctan2(double y, double x) {
        double val = Math.atan2(y, x);
        return radiansToDegrees(val);
    }

    // degree arccot
    private double darccot(double x) {
        double val = Math.atan2(1.0, x);
        return radiansToDegrees(val);
    }


    // ---------------------- Julian Date Functions -----------------------
    // calculate julian date from a calendar date
    private double julianDate(int year, int month, int day) {

        if (month <= 2) {
            year -= 1;
            month += 12;
        }
        double A = Math.floor(year / 100.0);

        double B = 2 - A + Math.floor(A / 4.0);

        double JD = Math.floor(365.25 * (year + 4716))
                + Math.floor(30.6001 * (month + 1)) + day + B - 1524.5;

        return JD;
    }


    // ---------------------- Calculation Functions -----------------------
    // References:
    // http://www.ummah.net/astronomy/saltime
    // http://aa.usno.navy.mil/faq/docs/SunApprox.html
    // compute declination angle of sun and equation of time
    private double[] sunPosition(double jd) {

        double D = jd - 2451545;
        double g = fixangle(357.529 + 0.98560028 * D);
        double q = fixangle(280.459 + 0.98564736 * D);
        double L = fixangle(q + (1.915 * dsin(g)) + (0.020 * dsin(2 * g)));

        // double R = a.00014 - 0.01671 * [self dcos:g] - 0.00014 * [self dcos:
        // (b*g)];
        double e = 23.439 - (0.00000036 * D);
        double d = darcsin(dsin(e) * dsin(L));
        double RA = (darctan2((dcos(e) * dsin(L)), (dcos(L)))) / 15.0;
        RA = fixhour(RA);
        double EqT = q / 15.0 - RA;
        double[] sPosition = new double[2];
        sPosition[0] = d;
        sPosition[1] = EqT;

        return sPosition;
    }

    // compute equation of time
    private double equationOfTime(double jd) {
        double eq = sunPosition(jd)[1];
        return eq;
    }

    // compute declination angle of sun
    private double sunDeclination(double jd) {
        double d = sunPosition(jd)[0];
        return d;
    }

    // compute mid-day (Dhuhr, Zawal) time
    private double computeMidDay(double t) {
        double T = equationOfTime(this.getJDate() + t);
        double Z = fixhour(12 - T);
        return Z;
    }

    // compute time for a given angle G
    private double computeTime(double G, double t) {

        double D = sunDeclination(this.getJDate() + t);
        double Z = computeMidDay(t);
        double Beg = -dsin(G) - dsin(D) * dsin(this.getLat());
        double Mid = dcos(D) * dcos(this.getLat());
        double V = darccos(Beg / Mid) / 15.0;

        return Z + (G > 90 ? -V : V);
    }

    // compute the time of Asr
    // Shafii: step=a, Hanafi: step=b
    private double computeAsr(double step, double t) {
        double D = sunDeclination(this.getJDate() + t);
        double G = -darccot(step + dtan(Math.abs(this.getLat() - D)));
        return computeTime(G, t);
    }

    // ---------------------- Misc Functions -----------------------
    // compute the difference between two times
    private double timeDiff(double time1, double time2) {
        return fixhour(time2 - time1);
    }

    // -------------------- Interface Functions --------------------
    // return prayer times for a given date
    private ArrayList<String> getDatePrayerTimes(int year, int month, int day,
                                                 double latitude, double longitude, double tZone) {
        this.setLat(latitude);
        this.setLng(longitude);
        this.setTimeZone(tZone);
        this.setJDate(julianDate(year, month, day));
        double lonDiff = longitude / (15.0 * 24.0);
        this.setJDate(this.getJDate() - lonDiff);
        return computeDayTimes();
    }

    // return prayer times for a given date
    private ArrayList<String> getPrayerTimes(Calendar date, double latitude,
                                             double longitude, double tZone) {

        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DATE);

        return getDatePrayerTimes(year, month + 1, day, latitude, longitude, tZone);
    }

    // set custom values for calculation parameters
    private void setCustomParams(double[] params) {

        for (int i = 0; i < 5; i++) {
            if (params[i] == -1) {
                params[i] = methodParams.get(this.getCalcMethod())[i];
                methodParams.put(this.getCustom(), params);
            } else {
                methodParams.get(this.getCustom())[i] = params[i];
            }
        }
        this.setCalcMethod(this.getCustom());
    }


    // convert double hours to 12h format
    public String floatToTime12(double time, boolean noSuffix) {

        if (Double.isNaN(time)) {
            return InvalidTime;
        }

        time = fixhour(time + 0.5 / 60); // add 0.5 minutes to round
        int hours = (int) Math.floor(time);
        double minutes = Math.floor((time - hours) * 60);
        String suffix, result;
        if (hours >= 12) {
            suffix = "pm";
        } else {
            suffix = "am";
        }
        hours = ((((hours + 12) - 1) % (12)) + 1);
        /*hours = (hours + 12) - a;
        int hrs = (int) hours % 12;
        hrs += a;*/
        if (noSuffix == false) {
            if ((hours >= 0 && hours <= 9) && (minutes >= 0 && minutes <= 9)) {
                result = "0" + hours + ":0" + Math.round(minutes) + " "
                        + suffix;
            } else if ((hours >= 0 && hours <= 9)) {
                result = "0" + hours + ":" + Math.round(minutes) + " " + suffix;
            } else if ((minutes >= 0 && minutes <= 9)) {
                result = hours + ":0" + Math.round(minutes) + " " + suffix;
            } else {
                result = hours + ":" + Math.round(minutes) + " " + suffix;
            }

        } else {
            if ((hours >= 0 && hours <= 9) && (minutes >= 0 && minutes <= 9)) {
                result = "0" + hours + ":0" + Math.round(minutes);
            } else if ((hours >= 0 && hours <= 9)) {
                result = "0" + hours + ":" + Math.round(minutes);
            } else if ((minutes >= 0 && minutes <= 9)) {
                result = hours + ":0" + Math.round(minutes);
            } else {
                result = hours + ":" + Math.round(minutes);
            }
        }
        return result;

    }

    // ---------------------- Compute Prayer Times -----------------------
    // compute prayer times at given julian date
    private double[] computeTimes(double[] times) {

        double[] t = dayPortion(times);

        double Fajr = this.computeTime(
                180 - methodParams.get(this.getCalcMethod())[0], t[0]);

        double Sunrise = this.computeTime(180 - 0.833, t[1]);

        double Dhuhr = this.computeMidDay(t[2]);
        double Asr = this.computeAsr(1 + this.getAsrJuristic(), t[3]);
        double Sunset = this.computeTime(0.833, t[4]);

        double Maghrib = this.computeTime(
                methodParams.get(this.getCalcMethod())[2], t[5]);
        double Isha = this.computeTime(
                methodParams.get(this.getCalcMethod())[4], t[6]);

        double[] CTimes = {Fajr, Sunrise, Dhuhr, Asr, Sunset, Maghrib, Isha};

        return CTimes;

    }

    // compute prayer times at given julian date
    private ArrayList<String> computeDayTimes() {
        double[] times = {5, 6, 12, 13, 18, 18, 18}; // default times

        for (int i = 1; i <= this.getNumIterations(); i++) {
            times = computeTimes(times);
        }

        times = adjustTimes(times);
        times = tuneTimes(times);

        return adjustTimesFormat(times);
    }

    // adjust times in a prayer time array
    private double[] adjustTimes(double[] times) {
        for (int i = 0; i < times.length; i++) {
            times[i] += this.getTimeZone() - this.getLng() / 15;
        }

        times[2] += this.getDhuhrMinutes() / 60; // Dhuhr
        if (methodParams.get(this.getCalcMethod())[1] == 1) // Maghrib
        {
            times[5] = times[4] + methodParams.get(this.getCalcMethod())[2] / 60;
        }
        if (methodParams.get(this.getCalcMethod())[3] == 1) // Isha
        {
            times[6] = times[5] + methodParams.get(this.getCalcMethod())[4] / 60;
        }

        if (this.getAdjustHighLats() != this.getNone()) {
            times = adjustHighLatTimes(times);
        }

        return times;
    }

    // convert times array to given time format
    private ArrayList<String> adjustTimesFormat(double[] times) {

        ArrayList<String> result = new ArrayList<String>();

        if (this.getTimeFormat() == this.getFloating()) {
            for (double time : times) {
                result.add(String.valueOf(time));
            }
            return result;
        }

        for (int i = 0; i < 7; i++) {
            if (this.getTimeFormat() == this.getTime12()) {
                result.add(floatToTime12(times[i], false));
            } else if (this.getTimeFormat() == this.getTime12NS()) {
                result.add(floatToTime12(times[i], true));
            }

        }
        return result;
    }

    // adjust Fajr, Isha and Maghrib for locations in higher latitudes
    private double[] adjustHighLatTimes(double[] times) {
        double nightTime = timeDiff(times[4], times[1]); // sunset to sunrise

        // Adjust Fajr
        double FajrDiff = nightPortion(methodParams.get(this.getCalcMethod())[0]) * nightTime;

        if (Double.isNaN(times[0]) || timeDiff(times[0], times[1]) > FajrDiff) {
            times[0] = times[1] - FajrDiff;
        }

        // Adjust Isha
        double IshaAngle = (methodParams.get(this.getCalcMethod())[3] == 0) ? methodParams.get(this.getCalcMethod())[4] : 18;
        double IshaDiff = this.nightPortion(IshaAngle) * nightTime;
        if (Double.isNaN(times[6]) || this.timeDiff(times[4], times[6]) > IshaDiff) {
            times[6] = times[4] + IshaDiff;
        }

        // Adjust Maghrib
        double MaghribAngle = (methodParams.get(this.getCalcMethod())[1] == 0) ? methodParams.get(this.getCalcMethod())[2] : 4;
        double MaghribDiff = nightPortion(MaghribAngle) * nightTime;
        if (Double.isNaN(times[5]) || this.timeDiff(times[4], times[5]) > MaghribDiff) {
            times[5] = times[4] + MaghribDiff;
        }

        return times;
    }

    // the night portion used for adjusting times in higher latitudes
    private double nightPortion(double angle) {
        double calc = 0;

        if (adjustHighLats == AngleBased)
            calc = (angle) / 60.0;
        else if (adjustHighLats == MidNight)
            calc = 0.5;
        else if (adjustHighLats == OneSeventh)
            calc = 0.14286;

        return calc;
    }

    // convert hours to day portions
    private double[] dayPortion(double[] times) {
        for (int i = 0; i < 7; i++) {
            times[i] /= 24;
        }
        return times;
    }

    // Tune timings for adjustments
    // Set time offsets
    public void tune(int[] offsetTimes) {

        for (int i = 0; i < offsetTimes.length; i++) { // offsetTimes length

            this.offsets[i] = offsetTimes[i];
        }
    }

    private double[] tuneTimes(double[] times) {
        for (int i = 0; i < times.length; i++) {
            times[i] = times[i] + this.offsets[i] / 60.0;
        }

        return times;
    }


    public int getCalcMethod() {
        return calcMethod;
    }

    public void setCalcMethod(int calcMethod) {
        this.calcMethod = calcMethod;
    }

    public int getAsrJuristic() {
        return asrJuristic;
    }

    public void setAsrJuristic(int asrJuristic) {
        this.asrJuristic = asrJuristic;
    }

    public int getDhuhrMinutes() {
        return dhuhrMinutes;
    }

    public void setDhuhrMinutes(int dhuhrMinutes) {
        this.dhuhrMinutes = dhuhrMinutes;
    }

    public int getAdjustHighLats() {
        return adjustHighLats;
    }

    public void setAdjustHighLats(int adjustHighLats) {
        this.adjustHighLats = adjustHighLats;
    }

    public int getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(int timeFormat) {
        this.timeFormat = timeFormat;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(double timeZone) {
        this.timeZone = timeZone;
    }

    public double getJDate() {
        return JDate;
    }

    public void setJDate(double jDate) {
        JDate = jDate;
    }


    private int getKarachi() {
        return Karachi;
    }

    private void setKarachi(int karachi) {
        Karachi = karachi;
    }


    private int getCustom() {
        return Custom;
    }

    private void setHanafi(int hanafi) {
        Hanafi = hanafi;
    }

    private int getNone() {
        return None;
    }

    private void setNone(int none) {
        None = none;
    }

    private void setMidNight(int midNight) {
        MidNight = midNight;
    }

    private void setOneSeventh(int oneSeventh) {
        OneSeventh = oneSeventh;
    }


    private void setAngleBased(int angleBased) {
        AngleBased = angleBased;
    }

    private int getTime12() {
        return Time12;
    }

    private void setTime12(int time12) {
        Time12 = time12;
    }

    private int getTime12NS() {
        return Time12NS;
    }

    private void setTime12NS(int time12ns) {
        Time12NS = time12ns;
    }

    private int getFloating() {
        return Floating;
    }

    private void setFloating(int floating) {
        Floating = floating;
    }

    private int getNumIterations() {
        return numIterations;
    }

    private void setNumIterations(int numIterations) {
        this.numIterations = numIterations;
    }

    public ArrayList<String> getTimeNames() {
        return timeNames;
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

//
//            Intent topic = new Intent(DashboardActivity.this, IslamicTopicActivity.class);
//            startActivity(topic);


        } else if (id == R.id.nav_hadis) {

            Intent hadis = new Intent(DashboardActivity.this, HadisActivity.class);
            startActivity(hadis);


        } else if (id == R.id.nav_dua) {

            Intent dua = new Intent(DashboardActivity.this, DuaActivity.class);
            startActivity(dua);

        } else if (id == R.id.nav_namaz_shikkha) {

//            Intent namazlesson = new Intent(DashboardActivity.this, NamazLessonActivity.class);
//            startActivity(namazlesson);


        } else if (id == R.id.nav_quran) {

            Intent quran = new Intent(DashboardActivity.this, QuranActivity.class);
            startActivity(quran);

        } else if (id == R.id.nav_tasbih) {

//            Intent tasbih = new Intent(DashboardActivity.this, TasbihActivity.class);
//            startActivity(tasbih);

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
        dialog.setTitle("you have to subscribe first! subscription charge 2.55tk/per day.Only for robi & airtel users");

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

                        NavigationView navigationView = findViewById(R.id.nav_view);
                        Menu menu = navigationView.getMenu();

                        MenuItem sub = menu.findItem(R.id.nav_sub);
                        sub.setVisible(false);

                        MenuItem unsub = menu.findItem(R.id.nav_unsub);
                        unsub.setVisible(true);


                    } else {

                        callSubApi();

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

    public void gotoRamadan(View view) {

        // startActivity(new Intent(DashboardActivity.this, RamadanTimingActivity.class));
    }

    /**
     * @param //args
     */
    public void main() {

        double latitude = 23.777176;
        double longitude = 90.399452;
        double timezone = 6;
        // Test Prayer times here
        DashboardActivity prayers = new DashboardActivity();


        prayers.setTimeFormat(prayers.Time12);
        prayers.setCalcMethod(prayers.Karachi);
        prayers.setAsrJuristic(prayers.Hanafi);
        prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
                latitude, longitude, timezone);
        ArrayList<String> prayerNames = prayers.getTimeNames();


        String replacedFajr = prayerTimes.get(0).replaceAll("0", "০").replaceAll("1", "১").
                replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").
                replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").
                replaceAll("8", "৮").replaceAll("9", "৯").replaceAll("am", "")
                .replace("pm", "");

        String replacedDhuhor = prayerTimes.get(2).replaceAll("0", "০").replaceAll("1", "১").
                replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").
                replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").
                replaceAll("8", "৮").replaceAll("9", "৯").replace("am", "").
                replace("pm", "");

        String replacedAsr = prayerTimes.get(3).replaceAll("0", "০").replaceAll("1", "১").
                replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").
                replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").
                replaceAll("8", "৮").replaceAll("9", "৯").replace("pm", "").
                replace("am", "");


        String replacedMagrib = prayerTimes.get(4).replaceAll("0", "০").replaceAll("1", "১").
                replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").
                replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").
                replaceAll("8", "৮").replaceAll("9", "৯").replace("pm", "");

        String replacedIsha = prayerTimes.get(6).replaceAll("0", "০").replaceAll("1", "১").
                replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").
                replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").
                replaceAll("8", "৮").replaceAll("9", "৯").replace("pm", "");


        fajrTime.setText("ভোর " + replacedFajr);
        dhuhrTime.setText("দুপুর " + replacedDhuhor);
        asrTime.setText("বিকাল " + replacedAsr);
        magribTime.setText("সন্ধ্যা " + replacedMagrib);
        ishaTime.setText("রাত " + replacedMagrib);


        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        String time = df.format(Calendar.getInstance().getTime());


        // date format
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        try {

            d2 = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


}
