package es.travelworld.travelling;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import es.travelworld.travelling.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private String NAME = "";
    private String SURNAME = "";

    ActivityResultLauncher<Intent> someActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    NAME = data.getStringExtra(RegisterActivity.NAME_KEY);
                    SURNAME = data.getStringExtra(RegisterActivity.SURNAME_KEY);
                }
            });

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentLoginBinding.bind(view);

        binding.loginEditText.addTextChangedListener(getTextWatcher());
        binding.passwordEditText.addTextChangedListener(getTextWatcher());

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
            ((NewLoginActivity) requireActivity()).showRegisterFragment();
        });

        binding.loginButton.setOnClickListener(v -> {
            if (binding.loginEditText.getText().toString().equals(NAME) &&
                    binding.passwordEditText.getText().toString().equals(SURNAME)
            ) {
                Intent intent = new Intent(requireContext(), HomeActivity.class);
                startActivity(intent);
            } else {
                new AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.login_dialog_title))
                        .setMessage(getString(R.string.login_dialog_description))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.login_dialog_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });


        //setUpActionBar();
    }

//    private void setUpActionBar() {
//        getSupportActionBar().setTitle(R.string.loginTitle);
//        //getSupportActionBar().setHomeEnabled(true); // for burger icon
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // burger icon related
//        getSupportActionBar().setDisplayShowCustomEnabled(true); // CRUCIAL - for displaying your custom actionbar
//        getSupportActionBar().setDisplayShowTitleEnabled(true); // false for hiding the title from actoinBar
//
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                break;
//        }
//        return true;
//    }

    //   private void runAfterTextChanged(String text, TextInputLayout textInputLayout) {

    private TextWatcher getTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginButton();
            }
        };
    }

    private void checkLoginButton() {
        String userNameText = String.valueOf(binding.loginEditText.getText());
        String userPasswordText = String.valueOf(binding.passwordEditText.getText());

        boolean isNameTextValid = !userNameText.isEmpty();
        boolean isPasswordTextValid = !userPasswordText.isEmpty();
        boolean isButtonEnabled = isNameTextValid && isPasswordTextValid;

        binding.loginButton.setEnabled(isButtonEnabled);
    }
}

