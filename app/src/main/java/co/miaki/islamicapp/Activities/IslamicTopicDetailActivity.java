package co.miaki.islamicapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;

import java.util.ArrayList;

import co.miaki.islamicapp.R;

public class IslamicTopicDetailActivity extends AppCompatActivity {


    JcPlayerView jcPlayerView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_islamic_topic_detail);

        String audioIslamicTopic = getIntent().getStringExtra("audioItem");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back_btn);

        //Setting the Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        jcPlayerView = findViewById(R.id.jcplayer);

        ArrayList<JcAudio> jcAudios = new ArrayList<>();
        jcAudios.add(JcAudio.createFromURL(audioIslamicTopic));

        jcPlayerView.initWithTitlePlaylist(jcAudios,"");
       // jcPlayerView.initPlaylist(jcAudios, null);



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