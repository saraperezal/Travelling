package es.travelworld.travelling;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;

import es.travelworld.travelling.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    Context context;
    TextView policyTextView;
    ImageView userPhoto;

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context = this;
        binding.policyTextView.setText(Html.fromHtml(getString(R.string.privacyPolicy), Html.FROM_HTML_MODE_LEGACY));
        binding.policyTextView.setOnClickListener(v -> {
            gotoUrl("https://developers.google.com/ml-kit/terms");
        });


        setUpSpinner();
        setUpActionBar();
        setUpListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.castleIcon:
                gotoUrl("https://www.disneylandparis.com/es-es/");
                break;
            case R.id.carIcon:
                Intent intent = new Intent(context, FragmentActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    private TextWatcher getTextWatcher(TextInputLayout textInputLayout) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                //runAfterTextChanged(text, textInputLayout);

//                boolean containsInvalidChars = text.contains("@") || text.contains("!");
//                textInputLayout.setErrorEnabled(containsInvalidChars);
//                textInputLayout.setError(getString(R.string.name_surname_error));


                if (text.contains("@") || text.contains("!")) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.name_surname_error));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        };

    }

    private void runAfterTextChanged(String text, TextInputLayout textInputLayout) {
        if (text.contains("@") || text.contains("!")) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(getString(R.string.name_surname_error));
        } else {
            textInputLayout.setErrorEnabled(false);
        }
    }

    private void gotoUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void setUpSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.register_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.ageSpinner.setAdapter(adapter);
    }

    private void setUpListeners() {
        binding.userImageView.setOnClickListener(v -> dispatchTakePictureIntent());
        binding.mainNameEditText.addTextChangedListener(getTextWatcher(binding.mainNameInputLayout));
        binding.mainSurnameEditText.addTextChangedListener(getTextWatcher(binding.mainSurnameInputLayout));

        binding.ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = binding.ageSpinner.getSelectedItem().toString();
                //Log.e("XXX", "position=" + position + " id=" + id + "Selected item : " + itemValue);

                if (position < 3) {
                    binding.ageInputLayout.setErrorEnabled(true);
                    binding.ageInputLayout.setError(getString(R.string.age_error));
                } else {
                    binding.ageInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setUpActionBar() {
        getSupportActionBar().setTitle(R.string.register);
        //getSupportActionBar().setHomeEnabled(true); // for burger icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // burger icon related
        getSupportActionBar().setDisplayShowCustomEnabled(true); // CRUCIAL - for displaying your custom actionbar
        getSupportActionBar().setDisplayShowTitleEnabled(true); // false for hiding the title from actoinBar
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int MY_CAMERA_REQUEST_CODE = 69;

    private void dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            //}
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

}