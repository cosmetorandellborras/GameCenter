package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class IntroActivity extends AppCompatActivity {

    private ImageView logoImage;
    private ImageView textImage;

    /**
     * Overrided onCreate method, create all the necessary things to start the activity.
     * Inside animate the intro animation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();

        logoImage = (ImageView) findViewById (R.id.logoImage);
        textImage = (ImageView) findViewById(R.id.gameTitleImage);

        Animation logo = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.custom_intro_anim);
        logo.setFillAfter(true);
        Animation text = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_intro);
        logoImage.startAnimation(logo);
        textImage.startAnimation(text);
        logo.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(getApplicationContext(),LogInActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}