package com.example.gamecenter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private AppCompatButton buttonSignUp;
    private TextInputEditText userName;
    private TextInputEditText password;
    private TextInputLayout passwordLayout;
    private DataBase db;

    /**
     * Overrided onCreate method, create all the necessary things to start the activity.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonSignUp = (AppCompatButton) findViewById(R.id.sign_up_layout_button);
        userName = (TextInputEditText) findViewById(R.id.sign_in_user_name_edit_text);
        password = (TextInputEditText) findViewById(R.id.sign_in_password_edit_text);
        passwordLayout = (TextInputLayout) findViewById(R.id.sign_in_password_layout);
        db = new DataBase(this);

        buttonSignUp.setEnabled(false);


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = charSequence.toString();
                if(password.length()>=8){
                    Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
                    Matcher matcher = pattern.matcher(password);
                    boolean isPwdContainsSpeChar = matcher.find();
                    if(isPwdContainsSpeChar){
                        passwordLayout.setHelperText("Strong Password");
                        buttonSignUp.setEnabled(true);
                    }
                    else{
                        passwordLayout.setHelperText("");
                        passwordLayout.setError("Weak Password. Include minimun 1 special char");
                        buttonSignUp.setEnabled(false);
                    }
                }else{
                    passwordLayout.setHelperText("Enter minimum 8 characters");
                    passwordLayout.setError("");
                    buttonSignUp.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userName.getText().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setTitle("MISSING USER NAME");
                    builder.setMessage("User name required");
                    builder.create().show();
                }
                else{
                    String user = userName.getText().toString().toLowerCase();
                    String pass = password.getText().toString();
                    boolean inserted = db.addUser(user,pass);
                    if(inserted){
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("user_name",userName.getText().toString());
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(),GameSelector.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}