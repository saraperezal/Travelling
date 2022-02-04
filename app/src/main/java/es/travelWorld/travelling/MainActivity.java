package es.travelWorld.travelling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.titleTextView);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CardViewActivity.class);
                startActivity(intent);
            }

            private Context getContext() {
                return null;
            }
        });


        String[] peliculas = getResources().getStringArray(R.array.movies);

        int [] iconos = getResources().getIntArray(R.array.status_icons);
        //iconos[0]

    }
}