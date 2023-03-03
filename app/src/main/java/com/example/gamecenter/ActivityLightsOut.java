package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.Random;

public class ActivityLightsOut extends AppCompatActivity {

    private Button new_game;
    private ToggleButton solve;
    private TextView counterTextView;
    private TextView bestTextView;
    private int actualCounter = 0;
    private Integer[][] tableAux;
    private Integer[][] table;

    private TextView p00;
    private TextView p01;
    private TextView p02;
    private TextView p03;
    private TextView p04;
    private TextView p10;
    private TextView p11;
    private TextView p12;
    private TextView p13;
    private TextView p14;
    private TextView p20;
    private TextView p21;
    private TextView p22;
    private TextView p23;
    private TextView p24;
    private TextView p30;
    private TextView p31;
    private TextView p32;
    private TextView p33;
    private TextView p34;
    private TextView p40;
    private TextView p41;
    private TextView p42;
    private TextView p43;
    private TextView p44;

    /**
     * Overrided onCreate method, create all the necessary things to start the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights_out);

        counterTextView = (TextView) findViewById(R.id.actual_counter);
        bestTextView = (TextView) findViewById(R.id.best_counter);
        getMinScore();
        tableAux = new Integer[5][5];
        table = new Integer[5][5];
        initiateTableAux(tableAux);
        initiateTable(tableAux,table);
        paintTable(table);

        new_game = (Button) findViewById(R.id.new_game_button);
        new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableAux = new Integer[5][5];
                table = new Integer[5][5];
                if(solve.isChecked()){
                    solve.setChecked(false);
                    setClicableEnabled();
                }
                setClicableEnabled();
                counterTextView.setText("0");
                actualCounter = 0;
                initiateTableAux(tableAux);
                initiateTable(tableAux,table);
                paintTable(table);
            }
        });
        solve = (ToggleButton) findViewById(R.id.solution_button);
        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(solve.isChecked()){
                    paintSolutionTable(tableAux);
                    setClickableDisabled();
                }else{
                    paintTable(table);
                    setClicableEnabled();
                }
            }
        });

        p00 = (TextView) findViewById(R.id.p00);
        p00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });

        p01 = (TextView) findViewById(R.id.p01);
        p01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });

        p02 = (TextView) findViewById(R.id.p02);
        p02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p03 = (TextView) findViewById(R.id.p03);
        p03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p04 = (TextView) findViewById(R.id.p04);
        p04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p10 = (TextView) findViewById(R.id.p10);
        p10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p11 = (TextView) findViewById(R.id.p11);
        p11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p12 = (TextView) findViewById(R.id.p12);
        p12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p13 = (TextView) findViewById(R.id.p13);
        p13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p14 = (TextView) findViewById(R.id.p14);
        p14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p20 = (TextView) findViewById(R.id.p20);
        p20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p21 = (TextView) findViewById(R.id.p21);
        p21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p22 = (TextView) findViewById(R.id.p22);
        p22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p23 = (TextView) findViewById(R.id.p23);
        p23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p24 = (TextView) findViewById(R.id.p24);
        p24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p30 = (TextView) findViewById(R.id.p30);
        p30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p31 = (TextView) findViewById(R.id.p31);
        p31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p32 = (TextView) findViewById(R.id.p32);
        p32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p33 = (TextView) findViewById(R.id.p33);
        p33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p34 = (TextView) findViewById(R.id.p34);
        p34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p40 = (TextView) findViewById(R.id.p40);
        p40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p41 = (TextView) findViewById(R.id.p41);
        p41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p42 = (TextView) findViewById(R.id.p42);
        p42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p43 = (TextView) findViewById(R.id.p43);
        p43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });
        p44 = (TextView) findViewById(R.id.p44);
        p44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(view, table);
                paintTable(table);
                actualCounter++;
                refreshCounter(counterTextView);
                updateSolution(view, tableAux);
                if(checkEndGame()){
                    Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    setClickableDisabled();
                    saveScore();
                    getMinScore();
                }
            }
        });

    }

    /**
     * When a light button is pressed, changes the state (number) of the numbers around the button pressed
     * @param view
     * @param table
     */
    public void changeState(View view,Integer[][] table){
        String getID = view.getResources().getResourceEntryName(view.getId());
        int[] coordenates = getCoordenates(getID);
        int i = coordenates[0];
        int j = coordenates[1];
        //propio
        if(table[i][j] == 0){
            table[i][j] = 1;
        }
        else {
            table[i][j] = 0;
        }
        //ariba
        if(i-1>=0 && table[i-1][j] ==0){
            table[i-1][j] = 1;
        } else if(i-1 >= 0){
            table[i-1][j] = 0;
        }
        //abajo
        if(i+1<table.length && table[i+1][j]==0){
            table[i+1][j]=1;
        }else if(i+1<table.length){
            table[i+1][j]=0;
        }
        //derecha
        if(j+1<table.length && table[i][j+1] == 0){
            table[i][j+1]=1;
        }else if(j+1< table.length){
            table[i][j+1]=0;
        }
        //izquierda
        if(j-1>=0 && table[i][j-1]==0){
            table[i][j-1] = 1;
        }else if(j-1>=0){
            table[i][j-1]=0;
        }
    }

    /**
     * This method is called before start the game, add number at every cell for after obtains the main matrix for the game
     * @param table
     * @param i
     * @param j
     */
    public void changeStateConstructorMainTable(Integer[][] table,int i,int j){
        table[i][j] = table[i][j]+1;
        //ariba
        if(i-1>=0) {
            table[i-1][j] = table[i-1][j] + 1;
        }
        //abajo
        if(i+1<table.length){
            table[i+1][j]=table[i+1][j]+1;
        }
        //derecha
        if(j+1<table.length){
            table[i][j+1]=table[i][j+1]+1;
        }
        //izquierda
        if(j-1>=0){
            table[i][j-1] = table[i][j-1]+1;
        }
    }

    /**
     * Get the coordenates of a button from the id string
     * @param text
     * @return
     */
    private int[] getCoordenates(String text){
        int[] coordenates = new int[2];
        coordenates[0] = Integer.valueOf(Character.toString(text.charAt(1)));
        coordenates[1] = Integer.valueOf(Character.toString(text.charAt(2)));
        return coordenates;
    }

    /**
     * Initialize aux table with random number between 0 and 1
     * @param table
     */
    private void initiateTableAux(Integer[][] table){
        Random rand = new Random();
        for(int i=0;i<table.length;i++){
            for(int j=0;j<table[i].length;j++){
                table[i][j] = rand.nextInt(2);
            }
        }
    }

    /**
     * Inititate main table with 0 value, then calls the changeStateConstructorMainTable for add numbers when pressed, and finally obtains the final table for the game
     * @param tableAux
     * @param mainTable
     */
    private void initiateTable(Integer[][] tableAux,Integer[][] mainTable){
        for(int i=0;i<mainTable.length;i++){
            for(int j=0;j<mainTable[i].length;j++){
                table[i][j] = 0;
            }
        }
        for(int i=0;i< tableAux.length;i++){
            for(int j=0;j<tableAux[i].length;j++){
                if(tableAux[i][j]==1){
                    changeStateConstructorMainTable(mainTable,i,j);
                }
            }
        }
        for(int i=0;i<mainTable.length;i++){
            for(int j=0;j<mainTable[i].length;j++){
                if(mainTable[i][j]%2 == 0){
                    mainTable[i][j] = 0;
                }else{
                    mainTable[i][j] = 1;
                }
            }
        }
    }

    /**
     * Paint the buttons of the game table
     * @param table
     */
    private void paintTable(Integer[][] table){
        for(int i=0;i<table.length;i++){
            for(int j=0;j<table[i].length;j++){
                int id = this.getResources().getIdentifier("p"+i+j,"id",this.getPackageName());
                TextView grid = findViewById(id);
                if(table[i][j]==0){
                    grid.setBackgroundResource(R.drawable.blacklight);
                }
                if(table[i][j]==1){
                    grid.setBackgroundResource(R.drawable.redlight);
                }
            }
        }
    }

    /**
     * Paint the solutions buttons of the table
     * @param table
     */
    private void paintSolutionTable(Integer[][] table){
        for(int i=0;i<table.length;i++){
            for(int j=0;j<table[i].length;j++){
                int id = this.getResources().getIdentifier("p"+i+j,"id",this.getPackageName());
                TextView grid = findViewById(id);
                if(table[i][j]==0){
                    grid.setBackgroundResource(R.drawable.blacklight);
                }
                if(table[i][j]==1){
                    grid.setBackgroundResource(R.drawable.greenlight);
                }
            }
        }
    }

    /**
     * Set all buttons of the table game disabled
     */
    private void setClickableDisabled(){
        for(int i=0;i<table.length;i++){
            for(int j=0;j<table[i].length;j++){
                int id = this.getResources().getIdentifier("p"+i+j,"id",this.getPackageName());
                TextView grid = findViewById(id);
                grid.setClickable(false);
            }
        }
    }

    /**
     * Set all buttons of the table game enabled
     */
    private void setClicableEnabled(){
        for(int i=0;i<table.length;i++){
            for(int j=0;j<table[i].length;j++){
                int id = this.getResources().getIdentifier("p"+i+j,"id",this.getPackageName());
                TextView grid = findViewById(id);
                grid.setClickable(true);
            }
        }
    }

    /**
     * Paint the actual movment counter
     * @param text
     */
    private void refreshCounter(TextView text){
        if(actualCounter<100){
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.getResources().getDimension(R.dimen.lightsOut099));
            text.setText(String.valueOf(actualCounter));
        }else{
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.getResources().getDimension(R.dimen.lightsOut100));
            text.setText(String.valueOf(actualCounter));
        }
    }

    /**
     * Updates the solution matrix when light button is pressed
     * @param view
     * @param table
     */
    private void updateSolution(View view,Integer[][]table){
        String getID = view.getResources().getResourceEntryName(view.getId());
        int[] coordenates = getCoordenates(getID);
        int i = coordenates[0];
        int j = coordenates[1];
        if(table[i][j] == 1){
            table[i][j] = 0;
        }
        else {
            table[i][j] = 1;
        }
    }

    /**
     * Check if all the light buttons are off
     * @return
     */
    private boolean checkEndGame(){
        boolean endGame = true;
        for(int i=0;i<table.length;i++){
            for(int j=0;j<table[i].length;j++){
                if(table[i][j]==1){
                    endGame = false;
                }
            }
        }
        return endGame;
    }

    /**
     * Gets the min score from the database and paints the score into the record text view
     */
    private void getMinScore(){
        int maxScore = 0;
        DataBase db = new DataBase(this);
        maxScore = db.getMinScore("lights_out","5x5");
        if(maxScore<100){
            bestTextView.setText(String.valueOf(maxScore));
        }
        else if(maxScore<1000){
            bestTextView.setTextSize(50);
            bestTextView.setText(String.valueOf(maxScore));
        }
        else if(maxScore<10000){
            bestTextView.setTextSize(40);
            bestTextView.setText(String.valueOf(maxScore));
        }
        else if(maxScore<100000){
            bestTextView.setTextSize(30);
            bestTextView.setText(String.valueOf(maxScore));
        }
    }

    /**
     * Saves the score when end game
     */
    private void saveScore(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user_name = preferences.getString("user_name",null);
        DataBase db = new DataBase(this);
        db.addScore(actualCounter,"lights_out","5x5",user_name);
    }
}