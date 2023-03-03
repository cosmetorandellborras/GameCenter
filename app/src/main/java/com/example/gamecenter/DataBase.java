package com.example.gamecenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    private final static String DB_NAME = "gameCenterDataBase";
    private final static int DB_VERSION = 1;

    private SQLiteDatabase dbWrittable;
    private SQLiteDatabase dbReadable;

    public DataBase(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    /**
     * Overrided method, creates the tables of the database if not exists
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (user_name TEXT PRIMARY KEY, user_password TEXT NOT NULL);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS scores (score INTEGER, game TEXT, mode TEXT," +
                " user_name TEXT, _id INTEGER PRIMARY KEY AUTOINCREMENT, FOREIGN KEY (user_name) REFERENCES " +
                "user (user_name));");
    }

    /**
     * Overrided method
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS scores");
        onCreate(sqLiteDatabase);
    }

    /**
     * This method add a new user into the database
     * @param name
     * @param password
     * @return
     */
    public boolean addUser(String name, String password){
        boolean inserted = false;
        if(dbWrittable == null){
            dbWrittable = getWritableDatabase();
        }
        try{
            ContentValues content = new ContentValues();
            content.put("user_name",name.toLowerCase());
            content.put("user_password",password);
            dbWrittable.insertOrThrow("users",null,content);
            inserted = true;
            dbWrittable.close();
        }catch (Exception e){
            inserted = false;
        }
        return  inserted;
    }

    /**
     * This method get a user from the database
     * @param name
     * @param password
     * @return if found
     */
    public boolean getUser(String name, String password){
        boolean found = false;
        if(dbReadable == null){
            dbReadable = getReadableDatabase();
        }

        Cursor c = dbReadable.rawQuery("SELECT user_name, user_password FROM users WHERE user_name = ? " +
                "and user_password = ?", new String[] {name, password});

        if(c != null){
            if(c.moveToFirst()){
                if(name.equals(c.getString(0))){
                    found = true;
                }
            }
        }
        c.close();
        return found;
    }

    /**
     * Add new score into the database
     * @param score
     * @param game
     * @param mode
     * @param user_name
     */
    public void addScore(int score, String game, String mode, String user_name){
        if(dbWrittable == null){
            dbWrittable = getWritableDatabase();
        }
        try{
            ContentValues content = new ContentValues();
            content.put("score",score);
            content.put("game",game);
            content.put("mode",mode);
            content.put("user_name",user_name);
            dbWrittable.insertOrThrow("scores",null,content);
            dbWrittable.close();
        }catch (Exception e){

        }
    }

    /**
     * Get max score from a game
     * @param game
     * @param game_mode
     * @return
     */
    public int getMaxScore(String game, String game_mode){
        int score = 0;
        if(dbReadable == null){
            dbReadable = getReadableDatabase();
        }
        Cursor c = dbReadable.rawQuery("SELECT MAX(score) FROM scores WHERE game = "+"'"+game+"' AND mode = '"+game_mode+"'",null);
        if (c != null){
            if(c.moveToFirst()){
                score = c.getInt(0);
            }
        }
        c.close();
        return score;
    }

    /**
     * Get min score from a game
     * @param game
     * @param game_mode
     * @return
     */
    public int getMinScore(String game, String game_mode){
        int score = 0;
        if(dbReadable == null){
            dbReadable = getReadableDatabase();
        }
        Cursor c = dbReadable.rawQuery("SELECT MIN(score) FROM scores WHERE game = "+"'"+game+"' AND mode = '"+game_mode+"'",null);
        if (c != null){
            if(c.moveToFirst()){
                score = c.getInt(0);
            }
        }
        c.close();
        return score;
    }

    /**
     * Gets all scores from the database
     * @param game
     * @return
     */
    public ArrayList<Score> getScores(String game){
        ArrayList<Score> scores = new ArrayList<>();
        Cursor cursor = null;
        try{
            if(dbReadable == null){
                dbReadable = getReadableDatabase();
            }
            if(game.equals("2048")){
                cursor = dbReadable.rawQuery("SELECT user_name,game,mode,score FROM scores WHERE game = "+"'"+game+"' ORDER BY 4 DESC",null);
                if(cursor.moveToFirst()){
                    do{
                        Score score = new Score(cursor.getString(0),cursor.getString(1),cursor.getString(2), cursor.getInt(3) );
                        scores.add(score);

                    }while(cursor.moveToNext());
                }
            }
            else if(game.equals("lights_out")){
                cursor = dbReadable.rawQuery("SELECT user_name,game,mode,score FROM scores WHERE game = "+"'"+game+"' ORDER BY 4",null);
                if(cursor.moveToFirst()){
                    do{
                        Score score = new Score(cursor.getString(0),cursor.getString(1),cursor.getString(2), cursor.getInt(3) );
                        scores.add(score);

                    }while(cursor.moveToNext());
                }
            }
            else{
                cursor = dbReadable.rawQuery("SELECT user_name,game,mode,score FROM scores order by 4",null);
                if(cursor.moveToFirst()){
                    do{
                        Score score = new Score(cursor.getString(0),cursor.getString(1),cursor.getString(2), cursor.getInt(3) );
                        scores.add(score);

                    }while(cursor.moveToNext());
                }
            }
        }catch (Exception e){

        }finally {
            return scores;
        }
    }
}
