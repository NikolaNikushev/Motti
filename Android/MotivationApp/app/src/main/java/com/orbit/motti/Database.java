package com.orbit.motti; /**
 * Created by Vader on 13/02/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Database {
    public  static  Database Instance;

    public  Database(){
        if(Instance == null)
            Instance = this;
    }

    protected static SQLiteDatabase database;

    public static void connectToDatabase(Context context){
        database = context.openOrCreateDatabase("database.sqlite", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        try {
            database.execSQL("Select * from Profile");
        }
        catch (SQLException ex) {
            try {
                database.execSQL("CREATE TABLE profile (\n" +
                        "\tusername VARCHAR(256) NOT NULL,\n" +
                        "\tage_range VARCHAR(100),\n" +
                        "\tcredits INT4,\n" +
                        "\tCONSTRAINT profile_pk PRIMARY KEY (username),\n" +
                        "\tCONSTRAINT FK_profile_age_group FOREIGN KEY (age_range) REFERENCES age_group(name) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                        ");");
                database.execSQL("Insert into profile(username)values('test')");
            } catch (SQLException exc) {
            }
        }

        database.execSQL("Select * from Age_Group"); // 12-15; 15-17 etc
        database.execSQL("Select * from Goal"); // goals in the application
        database.execSQL("Select * from Addiction_Type"); // smoking; gambling
        database.execSQL("Select * from Motivation"); // did you know; an apple a day keeps the doctor away
        database.execSQL("Select * from Profile_Addiction"); // did you know; an apple a day keeps the doctor away
    }

    public static Cursor get(Record record, String whereStatement) throws IndexOutOfBoundsException{

        String sql = "Select * from " + record.getTableName() + " Where " + whereStatement;
        return executeWithResult(sql);
    }

    public static Cursor executeWithResult(String sql) {
        return database.rawQuery(sql, null);
    }
}
