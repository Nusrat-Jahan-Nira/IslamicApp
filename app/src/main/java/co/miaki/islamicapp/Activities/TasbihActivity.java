package co.miaki.islamicapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import co.miaki.islamicapp.R;

public class TasbihActivity extends AppCompatActivity {

    TextView counterButton,pressButton,tryAgainButton;
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasbih);


        counterButton = findViewById(R.id.counterButton);
        pressButton = findViewById(R.id.pressButton);
        tryAgainButton = findViewById(R.id.tryAgainButton);

        pressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                counterButton.setText(String.valueOf(counter));
            }
        });

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 0;
                counterButton.setText(String.valueOf(counter));
            }
        });

    }
}