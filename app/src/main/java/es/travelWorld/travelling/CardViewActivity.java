package es.travelWorld.travelling;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CardViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        //TextView title = findViewById(R.id.main_activity_title);


        String[] peliculas = getResources().getStringArray(R.array.movies);

        int [] iconos = getResources().getIntArray(R.array.status_icons);
        //iconos[0]

    }
}