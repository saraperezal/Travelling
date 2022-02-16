package es.travelworld.travelling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OnboardingActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.titleTextView);
        title.setOnClickListener(v -> {
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
        });

    }
}