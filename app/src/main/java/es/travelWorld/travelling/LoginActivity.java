package es.travelworld.travelling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import es.travelworld.travelling.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = this;

        binding.forgotPasswordTextView.setText(Html.fromHtml(getString(R.string.forgotPassword), Html.FROM_HTML_MODE_LEGACY));
        binding.forgotPasswordTextView.setOnClickListener(v -> {
            Snackbar.make(
                    binding.forgotPasswordTextView,
                    "This feature will be implemented soon",
                    BaseTransientBottomBar.LENGTH_SHORT
            ).show();
        });

        binding.createAccountTextView.setText(Html.fromHtml(getString(R.string.createAccount), Html.FROM_HTML_MODE_LEGACY));
        binding.createAccountTextView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RegisterActivity.class);
            startActivity(intent);
        });
        binding.loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
        });


        //setUpActionBar();
    }

    private void setUpActionBar() {
        getSupportActionBar().setTitle(R.string.loginTitle);
        //getSupportActionBar().setHomeEnabled(true); // for burger icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // burger icon related
        getSupportActionBar().setDisplayShowCustomEnabled(true); // CRUCIAL - for displaying your custom actionbar
        getSupportActionBar().setDisplayShowTitleEnabled(true); // false for hiding the title from actoinBar

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }


}