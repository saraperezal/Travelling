package es.travelworld.travelling;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        forgotPasswordTextView.setOnClickListener(v -> {
            Snackbar.make(
                    forgotPasswordTextView,
                    "This feature will be implemented soon",
                    BaseTransientBottomBar.LENGTH_SHORT
            ).show();
        });

        TextView forgotPassword = findViewById(R.id.forgotPasswordTextView);
        forgotPassword.setText(Html.fromHtml("Forgot password <b>Get new</b>"));

        TextView createAccount = findViewById(R.id.createAccountTextView);
        createAccount.setText(Html.fromHtml("Do you have account? <b>Create now</b>"));
    }


}