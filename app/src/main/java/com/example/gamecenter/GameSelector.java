package com.example.gamecenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class GameSelector extends AppCompatActivity {

    private ImageButton game2048Button;
    private ImageButton lightsOutButton;
    private ImageButton preview;
    private ImageButton left_arrow;
    private ImageButton right_arrow;

    /**
     * Overrided onCreate method, create all the necessary things to start the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selector);

        game2048Button = (ImageButton) findViewById(R.id.icon_2048);
        lightsOutButton = (ImageButton) findViewById(R.id.icon_lights_out);

        game2048Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] gameMode = {"3x3","4x4","5x5"};
                new GameModeChooserDialog(gameMode).show(getSupportFragmentManager(), "chooser");
            }
        });

        lightsOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityLightsOut.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.action_records:
                Intent intent = new Intent(getBaseContext(),RecordsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}