package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {

    private AppCompatButton button2048;
    private AppCompatButton buttonLightsOut;
    private AppCompatButton buttonAll;
    private RecyclerView mRecyclerView;
    private DataBase db;

    /**
     * Overrided onCreate method, create all the necessary things to start the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        button2048 = (AppCompatButton) findViewById(R.id.records_2048_button);
        buttonLightsOut = (AppCompatButton) findViewById(R.id.records_lights_button);
        buttonAll = (AppCompatButton) findViewById(R.id.records_all_button);
        mRecyclerView = (RecyclerView) findViewById(R.id.records_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DataBase(this);

        button2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DataBase(RecordsActivity.this);
                RecordsListAdapter adapter = new RecordsListAdapter(RecordsActivity.this,db.getScores("2048"));
                mRecyclerView.setAdapter(adapter);
            }
        });
        buttonLightsOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DataBase(RecordsActivity.this);
                RecordsListAdapter adapter = new RecordsListAdapter(RecordsActivity.this,db.getScores("lights_out"));
                mRecyclerView.setAdapter(adapter);
            }
        });
        buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DataBase(RecordsActivity.this);
                RecordsListAdapter adapter = new RecordsListAdapter(RecordsActivity.this,db.getScores("all"));
                mRecyclerView.setAdapter(adapter);
            }
        });
    }
}