package com.example.gamecenter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class LogInActivity extends AppCompatActivity {

    private AppCompatButton buttonLogin;
    private AppCompatButton buttonSignUp;
    private TextInputEditText userName;
    private TextInputEditText password;
    private DataBase db;


    /**
     * Overrided onCreate method, create all the necessary things to start the activity.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        buttonLogin = (AppCompatButton) findViewById(R.id.buttonLogin);
        buttonSignUp = (AppCompatButton) findViewById(R.id.buttonSignUp);
        userName = (TextInputEditText) findViewById(R.id.log_in_user_name_edit_text);
        password = (TextInputEditText) findViewById(R.id.log_in_password_edit_text);
        db = new DataBase(this);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userName.getText().toString().toLowerCase();
                String pass = password.getText().toString();
                if(user.equals("")||pass.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
                    builder.setTitle("MISSING CREDENTIALS");
                    builder.setMessage("User name or password are empty");
                    builder.create().show();
                }
                else {
                    boolean found = db.getUser(user,pass);
                    if(found){
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("user_name",userName.getText().toString());
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(),GameSelector.class);
                        startActivity(intent);
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
                        builder.setTitle("USER NOT FOUND");
                        builder.setMessage("User not found, please try again");
                        builder.create().show();
                    }
                }
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}