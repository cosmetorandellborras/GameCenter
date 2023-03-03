package com.example.gamecenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Activity_2048 extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private GestureDetectorCompat detector;
    private String selectedMode;
    private Button ngButton;
    private Button undoButton;
    private Integer[][] table;
    private Integer[][] previousTable;
    private Integer[][] tempTable;
    private GridLayout gridLayout;
    private ConstraintLayout mainLayout;
    private TextView actualScoreTextView;
    private TextView actualBestScore;
    private int actual_score;
    private int gameMode;

    /**
     * Overrided onCreate method, create all the necessary things to start the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedMode = getIntent().getStringExtra("gameMode");

        if (selectedMode.equals("3x3")){
            setContentView(R.layout.activity_2048_3x3);
            mainLayout = (ConstraintLayout)  findViewById(R.id.mainLayout2048);
            actualScoreTextView = findViewById(R.id.actual_score);
            actualBestScore = findViewById(R.id.actual_counter);
            getMaxScore();
            actual_score = 0;
            table = new Integer[3][3];
            previousTable = new Integer[3][3];
            tempTable = new Integer[3][3];
            initiateTable(table);
            insertRandomNumArray(table);
            paintTable(table);
            gameMode = 3;
        }
        else if(selectedMode.equals("4x4")){
            setContentView(R.layout.activity_activity20484x4);
            mainLayout = (ConstraintLayout)  findViewById(R.id.mainLayout2048);
            actualScoreTextView = findViewById(R.id.actual_score);
            actualBestScore = findViewById(R.id.actual_counter);
            getMaxScore();
            actual_score = 0;
            table = new Integer[4][4];
            previousTable = new Integer[4][4];
            tempTable = new Integer[4][4];
            initiateTable(table);
            insertRandomNumArray(table);
            paintTable(table);
            gameMode = 4;
        }
        else{
            setContentView(R.layout.activity_2048_5x5);
            mainLayout = (ConstraintLayout)  findViewById(R.id.mainLayout2048);
            actualScoreTextView = findViewById(R.id.actual_score);
            actualBestScore = findViewById(R.id.actual_counter);
            getMaxScore();
            actual_score = 0;
            table = new Integer[5][5];
            previousTable = new Integer[5][5];
            tempTable = new Integer[5][5];
            initiateTable(table);
            insertRandomNumArray(table);
            paintTable(table);
            gameMode = 5;
        }
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        detector = new GestureDetectorCompat(this,this);
        detector.setOnDoubleTapListener(this);
        ngButton = findViewById(R.id.new_game_button);
        ngButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame(table);
            }
        });
        undoButton = findViewById(R.id.undo_button);
        undoButton.setEnabled(false);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("UNDO","click");
                undoGame();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.detector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    /**
     * Overrided method onFling, captures the fling motion and call the correspondent onSwpie method
     * @param me1
     * @param me2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        boolean result = false;
        try {
            float diffY = me2.getY() - me1.getY();
            float diffX = me2.getX() - me1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
                result = true;
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeDown();
                } else {
                    onSwipeUp();
                }
            }
            result = true;

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    /**
     * Method onSwipeRight
     * Copy the original matrix, call moveNumbersRight function and if there are changes, set undoButton enabled true and copy the matrix
     */
    public void onSwipeRight(){
        matrixCopy(this.table,this.tempTable);
        Boolean changes = moveNumbersRight(table);
        if(changes){
            undoButton.setEnabled(true);
            matrixCopy(this.tempTable,this.previousTable);

        }
    }
    /**
     * Method onSwipeLeft
     * Copy the original matrix, call moveNumbersLeft function and if there are changes, set undoButton enabled true and copy the matrix
     */
    public void onSwipeLeft(){
        matrixCopy(this.table,this.tempTable);
        Boolean changes = moveNumbersLeft(table);
        if(changes){
            undoButton.setEnabled(true);
            matrixCopy(this.tempTable,this.previousTable);
        }
    }
    /**
     * Method onSwipeUp
     * Copy the original matrix, call moveNumbersUp function and if there are changes, set undoButton enabled true and copy the matrix
     */
    public void onSwipeUp(){
        matrixCopy(this.table,this.tempTable);
        Boolean changes = moveNumbersUP(table);
        if(changes){
            undoButton.setEnabled(true);
            matrixCopy(this.tempTable,this.previousTable);
        }
    }
    /**
     * Method onSwipeDown
     * Copy the original matrix, call moveNumbersDown function and if there are changes, set undoButton enabled true and copy the matrix
     */
    public void onSwipeDown(){
        matrixCopy(this.table,this.tempTable);
        Boolean changes = moveNumbersDown(table);
        if (changes){
            undoButton.setEnabled(true);
            matrixCopy(this.tempTable,this.previousTable);
        }
    }

    /**
     * Initialize the main table
     * @param table
     */
    public void initiateTable(Integer[][] table){
        for (int i=0;i<table.length;i++){
            for (int j=0;j<table[i].length;j++){
                table[i][j] = 0;
            }
        }
    }

    /**
     * Restore the last movement
     */
    private void undoGame(){
        matrixCopy(this.previousTable,this.table);
        undoButton.setEnabled(false);
        paintTable(this.table);
    }

    /**
     * Inserts a new number into the main matrix
     * @param table
     */
    public void insertRandomNumArray(Integer[][] table){
        Random rand = new Random();
        int y = rand.nextInt(table.length);
        int x = rand.nextInt(table.length);
        while(table[y][x] != 0){
            y = rand.nextInt(table.length);
            x = rand.nextInt(table.length);
        }
        table[y][x] = 2;
        int id = this.getResources().getIdentifier("p"+y+x,"id",this.getPackageName());
        TextView cell = findViewById(id);
        AlphaAnimation anim = new AlphaAnimation(0.0f,1.0f);
        anim.setDuration(500);
        cell.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                paintTable(table);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    /**
     * Move numbers to UP and if there are changes do the animation
     * @param table
     * @return
     */
    public boolean moveNumbersUP(Integer[][] table){
        int numberOfAnimations = checkNumberOfAnimationsUp();
        Log.i("NROANIMS",String.valueOf(numberOfAnimations));
        int animationCounter = 0;
        String movmentDirection = "up";
        int nroMovments = 0;
        boolean joined = false;
        boolean movment = false;
        for(int j=0;j<table.length;j++){
            for(int i=1;i<table.length;i++){
                if(table[i][j]!=0){
                    int k=i;
                    while(k>0){
                        if(table[k-1][j]==0){
                            table[k-1][j] = table[k][j];
                            table[k][j] = 0;
                            movment = true;
                            nroMovments++;
                            k--;
                        }
                        else if(table[k][j]==table[k-1][j]){
                            table[k-1][j]= table[k-1][j]+table[k][j];
                            actual_score = actual_score + table[k-1][j];
                            updateScore();
                            table[k][j]=0;
                            movment = true;
                            joined = true;
                            nroMovments++;
                            k = 0;
                        }
                        else{
                            k = 0;
                        }
                    }
                    if(nroMovments!=0){

                        animationCounter = animationCounter + 1;
                        Log.i("NROANIMS", "Counter: "+String.valueOf(animationCounter));
                        if(animationCounter == numberOfAnimations){
                            Log.i("NROANIMS", "Last anim");
                            int id = this.getResources().getIdentifier("p"+i+j,"id",this.getPackageName());
                            TextView cellOriginal = findViewById(id);
                            int id2 = this.getResources().getIdentifier("p"+(i-nroMovments)+j,"id",this.getPackageName());
                            TextView cellObjective = findViewById(id2);
                            animateLastCell(cellOriginal,cellObjective,joined,movmentDirection);
                            nroMovments = 0;
                            joined = false;

                        }
                        else{
                            Log.i("NROANIMS", "Normal anim");
                            int id = this.getResources().getIdentifier("p"+i+j,"id",this.getPackageName());
                            TextView cellOriginal = findViewById(id);
                            int id2 = this.getResources().getIdentifier("p"+(i-nroMovments)+j,"id",this.getPackageName());
                            TextView cellObjective = findViewById(id2);
                            animateCells(cellOriginal,cellObjective,joined,movmentDirection);
                            nroMovments = 0;
                            joined = false;
                        }
                    }
                }
            }
        }
        return movment;
    }
    /**
     * Move numbers to Down and if there are changes do the animation
     * @param table
     * @return
     */
    public boolean moveNumbersDown(Integer[][] table){
        int numberOfAnimations = checkNumberOfAnimationsDown();
        Log.i("NROANIMS",String.valueOf(numberOfAnimations));
        int animationCounter = 0;
        String movmentDirection = "down";
        int nroMovments = 0;
        boolean joined = false;
        boolean movment = false;
        for(int j=0;j<table.length;j++){
            for(int i=table.length-2;i>=0;i--){
                if(table[i][j]!=0){
                    int k=i;
                    while(k<table.length-1){
                        if(table[k+1][j]==0){
                            table[k+1][j] = table[k][j];
                            table[k][j] = 0;
                            movment = true;
                            nroMovments++;
                            k++;
                        }
                        else if(table[k][j]==table[k+1][j]){
                            table[k+1][j]=table[k+1][j]+table[k][j];
                            actual_score = actual_score + table[k+1][j];
                            updateScore();
                            table[k][j]=0;
                            joined = true;
                            movment = true;
                            nroMovments++;
                            k = table.length-1;

                        }
                        else{
                            k = table.length-1;
                        }
                    }
                    if(nroMovments!=0){

                        animationCounter = animationCounter + 1;
                        Log.i("NROANIMS", "Counter: "+String.valueOf(animationCounter));
                        if(animationCounter == numberOfAnimations){
                            Log.i("NROANIMS", "Last anim");
                            int id = this.getResources().getIdentifier("p"+i+j,"id",this.getPackageName());
                            TextView cellOriginal = findViewById(id);
                            int id2 = this.getResources().getIdentifier("p"+(i+nroMovments)+j,"id",this.getPackageName());
                            TextView cellObjective = findViewById(id2);
                            animateLastCell(cellOriginal,cellObjective,joined,movmentDirection);
                            nroMovments = 0;
                            joined = false;

                        }
                        else{
                            Log.i("NROANIMS", "Normal anim");
                            int id = this.getResources().getIdentifier("p"+i+j,"id",this.getPackageName());
                            TextView cellOriginal = findViewById(id);
                            int id2 = this.getResources().getIdentifier("p"+(i+nroMovments)+j,"id",this.getPackageName());
                            TextView cellObjective = findViewById(id2);
                            animateCells(cellOriginal,cellObjective,joined,movmentDirection);
                            nroMovments = 0;
                            joined = false;
                        }
                    }
                }
            }
        }
        return movment;
    }
    /**
     * Move numbers to left and if there are changes do the animation
     * @param table
     * @return
     */
    public boolean moveNumbersLeft(Integer[][] table){
        int numberOfAnimations = checkNumberOfAnimationsLeft();
        Log.i("NROANIMS",String.valueOf(numberOfAnimations));
        int animationCounter = 0;
        String movmentDirection = "left";
        boolean movment = false;
        int nroMovments = 0;
        boolean joined = false;
        for(int i=0;i<table.length;i++){
            for(int j=1;j< table.length;j++){
                if(table[i][j]!=0){
                    int k = j;
                    while(k>0){
                        if(table[i][k-1] == 0){
                            table[i][k-1] = table[i][k];
                            table[i][k] = 0;
                            movment = true;
                            nroMovments++;
                            k--;
                        }
                        else if(table[i][k-1] == table[i][k]){
                            table[i][k-1] = table[i][k-1]+table[i][k];
                            actual_score = actual_score + table[i][k-1];
                            updateScore();
                            table[i][k]=0;
                            joined = true;
                            movment = true;
                            nroMovments++;
                            k = 0;
                        }
                        else{
                            k = 0;
                        }
                    }
                    if(nroMovments!=0){

                        animationCounter = animationCounter + 1;
                        Log.i("NROANIMS", "Counter: "+String.valueOf(animationCounter));
                        if(animationCounter == numberOfAnimations){
                            Log.i("NROANIMS", "Last anim");
                            int id = this.getResources().getIdentifier("p"+i+j,"id",this.getPackageName());
                            TextView cellOriginal = findViewById(id);
                            int id2 = this.getResources().getIdentifier("p"+i+(j-nroMovments),"id",this.getPackageName());
                            TextView cellObjective = findViewById(id2);
                            animateLastCell(cellOriginal,cellObjective,joined,movmentDirection);
                            nroMovments = 0;
                            joined = false;

                        }
                        else{
                            Log.i("NROANIMS", "Normal anim");
                            int id = this.getResources().getIdentifier("p"+i+j,"id",this.getPackageName());
                            TextView cellOriginal = findViewById(id);
                            int id2 = this.getResources().getIdentifier("p"+i+(j-nroMovments),"id",this.getPackageName());
                            TextView cellObjective = findViewById(id2);
                            animateCells(cellOriginal,cellObjective,joined,movmentDirection);
                            nroMovments = 0;
                            joined = false;
                        }
                    }
                }
            }
        }
        return movment;
    }
    /**
     * Move numbers to right and if there are changes do the animation
     * @param table
     * @return
     */
    public boolean moveNumbersRight(Integer[][] table){
        int numberOfAnimations = checkNumberOfAnimationsRight();
        Log.i("NROANIMS",String.valueOf(numberOfAnimations));
        int animationCounter = 0;
        String movmentDirection = "right";
        boolean movment = false;
        int nroMovments = 0;
        boolean joined = false;
        for(int i=0;i<table.length;i++){
            for(int j=table.length-2;j>=0;j--){
                if(table[i][j]!=0){
                    int k = j;
                    while(k<table.length-1){
                        if(table[i][k+1] == 0){
                            table[i][k+1] = table[i][k];
                            table[i][k] = 0;
                            movment = true;
                            nroMovments++;
                            k++;
                        }
                        else if (table[i][k]==table[i][k+1]){
                            table[i][k+1] = table[i][k]+table[i][k+1];
                            actual_score = actual_score + table[i][k+1];
                            updateScore();
                            table[i][k]=0;
                            joined = true;
                            movment = true;
                            nroMovments++;
                            k = table.length-1;
                        }
                        else{
                            k = table.length-1;
                        }
                    }
                    if(nroMovments!=0){

                        animationCounter = animationCounter + 1;
                        Log.i("NROANIMS", "Counter: "+String.valueOf(animationCounter));
                        if(animationCounter == numberOfAnimations){
                            Log.i("NROANIMS", "Last anim");
                            int id = this.getResources().getIdentifier("p"+i+j,"id",this.getPackageName());
                            TextView cellOriginal = findViewById(id);
                            int id2 = this.getResources().getIdentifier("p"+i+(j+nroMovments),"id",this.getPackageName());
                            TextView cellObjective = findViewById(id2);
                            animateLastCell(cellOriginal,cellObjective,joined,movmentDirection);
                            nroMovments = 0;
                            joined = false;

                        }
                        else{
                            Log.i("NROANIMS", "Normal anim");
                            int id = this.getResources().getIdentifier("p"+i+j,"id",this.getPackageName());
                            TextView cellOriginal = findViewById(id);
                            int id2 = this.getResources().getIdentifier("p"+i+(j+nroMovments),"id",this.getPackageName());
                            TextView cellObjective = findViewById(id2);
                            animateCells(cellOriginal,cellObjective,joined,movmentDirection);
                            nroMovments = 0;
                            joined = false;
                        }

                    }

                }
            }
        }
        return movment;
    }

    /**
     * Restart the game and all the variables
     * @param table
     */
    public void restartGame(Integer[][] table){
        actual_score = 0;
        updateScore();
        initiateTable(table);
        insertRandomNumArray(table);
        paintTable(table);
    }

    /**
     * Paint the table and changes the color and size of the cells
     * @param table
     */
    private void paintTable(Integer[][] table){
        for(int i=0;i<table.length;i++){
            for(int j=0;j<table[i].length;j++){
                int id = this.getResources().getIdentifier("p"+i+j,"id",this.getPackageName());
                TextView grid = findViewById(id);
                if(table[i][j] == 0){
                    grid.setText("");
                    grid.setBackgroundResource(R.color.colorPrimary);
                }else if(table[i][j] == 2){
                    grid.setBackgroundResource(R.color.c2);
                    grid.setTextColor(ContextCompat.getColor(this,R.color.c24Text));
                    grid.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.getResources().getDimension(R.dimen.smallNum));
                    grid.setText(String.valueOf(table[i][j]));
                }else if(table[i][j] == 4){
                    grid.setBackgroundResource(R.color.c4);
                    grid.setTextColor(ContextCompat.getColor(this,R.color.c24Text));
                    grid.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.getResources().getDimension(R.dimen.smallNum));
                    grid.setText(String.valueOf(table[i][j]));
                }else if(table[i][j] == 8){
                    grid.setBackgroundResource(R.color.c8);
                    grid.setTextColor(ContextCompat.getColor(this, R.color.cTextRest));
                    grid.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.getResources().getDimension(R.dimen.smallNum));
                    grid.setText(String.valueOf(table[i][j]));
                }else if(table[i][j] == 16){
                    grid.setBackgroundResource(R.color.c16);
                    grid.setTextColor(ContextCompat.getColor(this, R.color.cTextRest));
                    grid.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.getResources().getDimension(R.dimen.smallNum));
                    grid.setText(String.valueOf(table[i][j]));
                }else if(table[i][j] == 32){
                    grid.setBackgroundResource(R.color.c32);
                    grid.setTextColor(ContextCompat.getColor(this, R.color.cTextRest));
                    grid.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.getResources().getDimension(R.dimen.smallNum));
                    grid.setText(String.valueOf(table[i][j]));
                }else if(table[i][j] == 64){
                    grid.setBackgroundResource(R.color.c64);
                    grid.setTextColor(ContextCompat.getColor(this, R.color.cTextRest));
                    grid.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.getResources().getDimension(R.dimen.smallNum));
                    grid.setText(String.valueOf(table[i][j]));
                }else if (table[i][j] == 128){
                    grid.setBackgroundResource(R.color.c128);
                    grid.setTextColor(ContextCompat.getColor(this, R.color.cTextRest));
                    grid.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.getResources().getDimension(R.dimen.mediumNum));
                    grid.setText(String.valueOf(table[i][j]));
                }else if(table[i][j] == 256){
                    grid.setBackgroundResource(R.color.c256);
                    grid.setTextColor(ContextCompat.getColor(this, R.color.cTextRest));
                    grid.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.getResources().getDimension(R.dimen.mediumNum));
                    grid.setText(String.valueOf(table[i][j]));
                }else if(table[i][j] == 512){
                    grid.setBackgroundResource(R.color.c512);
                    grid.setTextColor(ContextCompat.getColor(this, R.color.cTextRest));
                    grid.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.getResources().getDimension(R.dimen.mediumNum));
                    grid.setText(String.valueOf(table[i][j]));
                }else if(table[i][j] == 1024){
                    grid.setBackgroundResource(R.color.c1024);
                    grid.setTextColor(ContextCompat.getColor(this, R.color.cTextRest));
                    grid.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.getResources().getDimension(R.dimen.mediumNum));
                    grid.setText(String.valueOf(table[i][j]));
                }else if (table[i][j] == 2048){
                    grid.setBackgroundResource(R.color.c2048);
                    grid.setTextColor(ContextCompat.getColor(this, R.color.cTextRest));
                    grid.setTextSize(TypedValue.COMPLEX_UNIT_PX,this.getResources().getDimension(R.dimen.mediumNum));
                    grid.setText(String.valueOf(table[i][j]));
                }
            }
        }
    }

    /**
     * Get the distance between cells for the animation
     * @param view1
     * @param view2
     * @param movmentDirection
     * @return
     */
    public int getDistanceBetweenViews(View view1,View view2,String movmentDirection){
        RectF firstRect = calculateRectOnScreen(view1);
        RectF secondRect = calculateRectOnScreen(view2);
        float distance = 0;
        if (movmentDirection.equals("right")){
            distance = Math.abs(firstRect.left-secondRect.left);
        } else if(movmentDirection.equals("left")){
            distance = (Math.abs(firstRect.left-secondRect.left))*-1;
        }else if(movmentDirection.equals("up")){
            distance = (Math.abs(firstRect.top-secondRect.top))*-1;
        }else if(movmentDirection.equals("down")){
            distance = Math.abs(firstRect.top-secondRect.top);
        }
        return (int) distance;
    }

    /**
     * Calculate the distance between views
     * @param view
     * @return
     */
    public static RectF calculateRectOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getMeasuredWidth(), location[1] + view.getMeasuredHeight());
    }

    /**
     * Copy the original matrix to another matrix for backup
     * @param originMatrix
     * @param targetMatrix
     */
    private void matrixCopy(Integer[][] originMatrix, Integer[][] targetMatrix){
        for(int i=0;i<originMatrix.length;i++){
            for(int j=0; j<originMatrix[i].length;j++){
                targetMatrix[i][j]=originMatrix[i][j];
            }
        }
    }

    /**
     * Animate cells, made translate animation and if the cell is joined made a zoom animation
     * @param cellOriginal
     * @param cellObjective
     * @param joined
     * @param movmentDirection
     */
    private void animateCells(TextView cellOriginal, TextView cellObjective, boolean joined, String movmentDirection){
        int horizontalDistance = 0;
        int verticalDistance = 0;
        String axis = "";
        if(movmentDirection.equals("right")){
            horizontalDistance = getDistanceBetweenViews(cellOriginal,cellObjective,movmentDirection);
            verticalDistance = 0;
        }else if(movmentDirection.equals("left")){
            horizontalDistance = getDistanceBetweenViews(cellOriginal,cellObjective,movmentDirection);
            verticalDistance = 0;
        }else if(movmentDirection.equals("up")){
            horizontalDistance = 0;
            verticalDistance = getDistanceBetweenViews(cellOriginal,cellObjective,movmentDirection);
        }else if (movmentDirection.equals("down")){
            horizontalDistance = 0;
            verticalDistance = getDistanceBetweenViews(cellOriginal,cellObjective,movmentDirection);
        }

        cellOriginal.setElevation(10);

        TranslateAnimation translateAnimation = new TranslateAnimation(0,horizontalDistance,0,verticalDistance);
        translateAnimation.setDuration(200);
        translateAnimation.setZAdjustment(Animation.ZORDER_TOP);
        cellOriginal.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                paintTable(table);
                cellOriginal.setElevation(0);
                if(joined){

                    Animation zoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_cell_2048);
                    Animation zoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out_cell_2048);
                    AnimationSet zoomAnim = new AnimationSet(false);
                    zoomAnim.addAnimation(zoomIn);
                    zoomAnim.addAnimation(zoomOut);
                    cellObjective.startAnimation(zoomAnim);

                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * Animate cells, but this method is called at the last animation, at the end calls insertRandomNumArray function
     * @param cellOriginal
     * @param cellObjective
     * @param joined
     * @param movmentDirection
     */
    private void animateLastCell(TextView cellOriginal, TextView cellObjective, boolean joined, String movmentDirection){
        int horizontalDistance = 0;
        int verticalDistance = 0;
        String axis = "";
        if(movmentDirection.equals("right")){
            horizontalDistance = getDistanceBetweenViews(cellOriginal,cellObjective,movmentDirection);
            verticalDistance = 0;
        }else if(movmentDirection.equals("left")){
            horizontalDistance = getDistanceBetweenViews(cellOriginal,cellObjective,movmentDirection);
            verticalDistance = 0;
        }else if(movmentDirection.equals("up")){
            horizontalDistance = 0;
            verticalDistance = getDistanceBetweenViews(cellOriginal,cellObjective,movmentDirection);
        }else if (movmentDirection.equals("down")){
            horizontalDistance = 0;
            verticalDistance = getDistanceBetweenViews(cellOriginal,cellObjective,movmentDirection);
        }

        cellOriginal.setElevation(10);

        TranslateAnimation translateAnimation = new TranslateAnimation(0,horizontalDistance,0,verticalDistance);
        translateAnimation.setDuration(200);
        translateAnimation.setZAdjustment(Animation.ZORDER_TOP);
        cellOriginal.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                paintTable(table);
                cellOriginal.setElevation(0);
                if(joined){

                    Animation zoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_cell_2048);
                    Animation zoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out_cell_2048);
                    AnimationSet zoomAnim = new AnimationSet(false);
                    zoomAnim.addAnimation(zoomIn);
                    zoomAnim.addAnimation(zoomOut);
                    cellObjective.startAnimation(zoomAnim);
                    zoomAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            insertRandomNumArray(table);
                            Log.i("ENDGAME","if before enter end game");
                            if(checkEndGame()){
                                Log.i("ENDGAME","if enter end game");
                                Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                }else{
                    insertRandomNumArray(table);
                    Log.i("ENDGAME","else before enter end game");
                    if(checkEndGame()){
                        Log.i("ENDGAME","else enter end game");
                        Toast.makeText(getApplicationContext(),"GAME ENDED",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * Check if there are any up movments
     * @return
     */
    private boolean checkMovmentsUp(){
        boolean movment = false;
        for(int j=0;j<table.length;j++){
            for(int i=1;i<table.length;i++){
                if(table[i][j]!=0){
                    int k=i;
                    while(k>0){
                        if(table[k-1][j]==0){
                            movment = true;
                            k--;
                        }
                        else if(table[k][j]==table[k-1][j]){
                            movment = true;
                            k = 0;
                        }
                        else{
                            k = 0;
                        }
                    }
                }
            }
        }
        return movment;
    }

    /**
     * Check if there are any down movments
     * @return
     */
    private boolean checkMovmentsDown(){
        boolean movment = false;
        for(int j=0;j<table.length;j++){
            for(int i=table.length-2;i>=0;i--){
                if(table[i][j]!=0){
                    int k=i;
                    while(k<table.length-1){
                        if(table[k+1][j]==0){
                            movment = true;
                            k++;
                        }
                        else if(table[k][j]==table[k+1][j]){
                            movment = true;
                            k = table.length-1;

                        }
                        else{
                            k = table.length-1;
                        }
                    }
                }
            }
        }
        return movment;
    }
    /**
     * Check if there are any left movments
     * @return
     */
    private boolean checkMovmentsLeft(){
        boolean movment = false;
        for(int i=0;i<table.length;i++){
            for(int j=1;j< table.length;j++){
                if(table[i][j]!=0){
                    int k = j;
                    while(k>0){
                        if(table[i][k-1] == 0){
                            movment = true;
                            k--;
                        }
                        else if(table[i][k-1] == table[i][k]){
                            movment = true;
                            k = 0;
                        }
                        else{
                            k = 0;
                        }
                    }
                }
            }
        }
        return movment;
    }
    /**
     * Check if there are any right movments
     * @return
     */
    private boolean checkMovmentsRight(){
        boolean movment = false;
        for(int i=0;i<table.length;i++){
            for(int j=table.length-2;j>=0;j--){
                if(table[i][j]!=0){
                    int k = j;
                    while(k<table.length-1){
                        if(table[i][k+1] == 0){
                            movment = true;
                            k++;
                        }
                        else if (table[i][k]==table[i][k+1]){
                            movment = true;
                            k = table.length-1;
                        }
                        else{
                            k = table.length-1;
                        }
                    }
                }
            }
        }
        return movment;
    }

    /**
     * Calls all check movments functions and check if there are any movment, if no movment finish the game and save score
     * @return
     */
    private boolean checkEndGame(){
        boolean endGame = false;
        int num = 0;
        boolean down = checkMovmentsDown();
        Log.i("END","down "+String.valueOf(down));
        boolean up = checkMovmentsUp();
        Log.i("END","up "+String.valueOf(up));
        boolean right = checkMovmentsRight();
        Log.i("END","right "+String.valueOf(right));
        boolean left = checkMovmentsLeft();
        Log.i("END","left "+String.valueOf(left));
        if(down == false && up == false && left == false && right == false){
            undoButton.setEnabled(false);
            Toast.makeText(getApplicationContext(),"GAME ENDED! SCORE: "+String.valueOf(actual_score),Toast.LENGTH_SHORT).show();
            saveScore();
            getMaxScore();
            endGame = true;
        }
        return endGame;
    }

    /**
     * Update the text view of the score
     */
    private void updateScore(){
        if(actual_score<100){
            actualScoreTextView.setText(String.valueOf(actual_score));
        }
        else if(actual_score<1000){
            actualScoreTextView.setTextSize(50);
            actualScoreTextView.setText(String.valueOf(actual_score));
        }
        else if(actual_score<10000){
            actualScoreTextView.setTextSize(40);
            actualScoreTextView.setText(String.valueOf(actual_score));
        }
    }

    /**
     * Check the number of animations made when the method is called
     * @return number of animations
     */
    private int checkNumberOfAnimationsRight(){
        int number = 0;
        for(int i=0;i<table.length;i++){
            for(int j=table.length-2;j>=0;j--){
                if(table[i][j]!=0){
                    int k = j;
                    while(k<table.length-1){
                        if(table[i][k+1] == 0 || table[i][k]==table[i][k+1]){
                            number = number+1;
                            k = table.length-1;
                        }
                        k++;
                    }
                }
            }
        }
        return number;
    }
    /**
     * Check the number of animations made when the method is called
     * @return number of animations
     */
    private int checkNumberOfAnimationsUp(){
        int number = 0;
        for(int j=0;j<table.length;j++){
            for(int i=1;i<table.length;i++){
                if(table[i][j]!=0){
                    int k=i;
                    while(k>0){
                        if(table[k-1][j]==0 || table[k][j]==table[k-1][j]){
                            number = number+1;
                            k = 0;
                        }
                        k--;
                    }
                }
            }
        }
        return number;
    }
    /**
     * Check the number of animations made when the method is called
     * @return number of animations
     */
    private int checkNumberOfAnimationsDown(){
        int number = 0;
        for(int j=0;j<table.length;j++){
            for(int i=table.length-2;i>=0;i--){
                if(table[i][j]!=0){
                    int k=i;
                    while(k<table.length-1){
                        if(table[k+1][j]==0 || table[k][j]==table[k+1][j] ){
                            number = number+1;
                            k = table.length-1;
                        }
                        k++;
                    }
                }
            }
        }
        return number;
    }
    /**
     * Check the number of animations made when the method is called
     * @return number of animations
     */
    private int checkNumberOfAnimationsLeft(){
        int number = 0;
        for(int i=0;i<table.length;i++){
            for(int j=1;j< table.length;j++){
                if(table[i][j]!=0){
                    int k = j;
                    while(k>0){
                        if(table[i][k-1] == 0 || table[i][k-1] == table[i][k]){
                            number = number+1;
                            k = 0;
                        }
                        k--;
                    }
                }
            }
        }
        return number;
    }

    /**
     * Saves the score into the database
     */
    private void saveScore(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user_name = preferences.getString("user_name",null);
        DataBase db = new DataBase(this);
        db.addScore(actual_score,"2048",selectedMode,user_name);
    }

    /**
     * Get the max score from the database and put the score into the best score textview
     */
    private void getMaxScore(){
        int maxScore = 0;
        DataBase db = new DataBase(this);
        maxScore = db.getMaxScore("2048",selectedMode);
        if(maxScore<100){
            actualBestScore.setText(String.valueOf(maxScore));
        }
        else if(maxScore<1000){
            actualBestScore.setTextSize(50);
            actualBestScore.setText(String.valueOf(maxScore));
        }
        else if(maxScore<10000){
            actualBestScore.setTextSize(40);
            actualBestScore.setText(String.valueOf(maxScore));
        }
        else if(maxScore<100000){
            actualBestScore.setTextSize(30);
            actualBestScore.setText(String.valueOf(maxScore));
        }
    }
}