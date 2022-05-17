package es.travelworld.travelling;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
    public static String NAME_KEY = "name";
    public static String SURNAME_KEY = "surname";

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
                boolean containsInvalidChars = containsInvalidChars(text);
                checkSignButton();

                if (containsInvalidChars) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(getString(R.string.name_surname_error));
                } else {
                    textInputLayout.setErrorEnabled(false);
                }
            }
        };

    }

    private boolean containsInvalidChars(String text) {
        return text.contains("@") || text.contains("!");
    }


    private void checkSignButton() {
        String nameText = String.valueOf(binding.mainNameEditText.getText());
        String surnameText = String.valueOf(binding.mainSurnameEditText.getText());

        boolean isNameValid = !nameText.isEmpty() && !containsInvalidChars(nameText);
        boolean isSurnameValid = !surnameText.isEmpty() && !containsInvalidChars(surnameText);
        boolean isButtonEnabled = isNameValid && isSurnameValid;

        binding.signInButton.setEnabled(isButtonEnabled);


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
        @SuppressLint("ResourceType") ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, R.array.register_array);

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.register_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.ageAutoComplete.setAdapter(adapter);
    }

    private void setUpListeners() {
        binding.userImageView.setOnClickListener(v -> dispatchTakePictureIntent());
        binding.mainNameEditText.addTextChangedListener(getTextWatcher(binding.mainNameInputLayout));
        binding.mainSurnameEditText.addTextChangedListener(getTextWatcher(binding.mainSurnameInputLayout));

        /*val items = listOf("Material", "Design", "Components", "Android")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)*/

        binding.ageAutoComplete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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

        binding.signInButton.setOnClickListener(v -> {
            String nameText = String.valueOf(binding.mainNameEditText.getText());
            String surnameText = String.valueOf(binding.mainSurnameEditText.getText());
            Intent resultIntent = new Intent();

            resultIntent.putExtra(NAME_KEY, nameText);
            resultIntent.putExtra(SURNAME_KEY, surnameText);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
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