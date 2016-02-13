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
        
        //Profiles
        CreateTable("Profile","CREATE TABLE profile (\n" +
                "\tusername VARCHAR(256) NOT NULL,\n" +
                "\tage_range VARCHAR(100),\n" +
                "\tcredits INT4,\n" +
                "\tCONSTRAINT profile_pk PRIMARY KEY (username),\n" +
                "\tCONSTRAINT FK_profile_age_group FOREIGN KEY (age_range) REFERENCES age_group(name) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");" );

        // 12-15; 15-17 etc
        CreateTable("from Age_Group", "CREATE TABLE age_group (\n" +
                "\tname VARCHAR(100) NOT NULL,\n" +
                "\tCONSTRAINT age_group_pk PRIMARY KEY (name)\n" +
                ");\n" +
                "\n" +
                "CREATE INDEX age_group_pk ON age_group (name);\n" +
                "\n" +
                "CREATE INDEX sqlite_autoindex_age_group_1 ON age_group (name);");

        // goals in the application
        CreateTable("Goal", "CREATE TABLE Goal (\n" +
                "\tTitle VARCHAR(256),\n" +
                "\tDescription TEXT(2000000000),\n" +
                "\tdate_from DATE(2000000000),\n" +
                "\tDate_to DATE(2000000000),\n" +
                "\treminder_frequency INTEGER,\n" +
                "\tparent_goal INTEGER,\n" +
                "\tID INTEGER,\n" +
                "\tdate_finished DATE(2000000000),\n" +
                "\tAddiction_type VARCHAR(256),\n" +
                "\tCONSTRAINT goal_pk PRIMARY KEY (ID)\n" +
                ");\n");

        // smoking; gambling
        CreateTable("Addiction_Type", "CREATE TABLE addiction_type (\n" +
                "\tname VARCHAR(256) NOT NULL,\n" +
                "\tCONSTRAINT addiction_type_pk PRIMARY KEY (name)\n" +
                ");\n" +
                "\n" +
                "CREATE INDEX addiction_type_pk ON addiction_type (name);\n" +
                "\n" +
                "CREATE INDEX sqlite_autoindex_addiction_type_1 ON addiction_type (name);");

        // did you know; an apple a day keeps the doctor away
        CreateTable("Motivation", "CREATE TABLE motivation (\n" +
                "\tdescription TEXT(2147483647) NOT NULL,\n" +
                "\taddiction VARCHAR(256),\n" +
                "\tis_positive BOOL,\n" +
                "\timage VARCHAR(256),\n" +
                "\tCONSTRAINT motivation_pk PRIMARY KEY (description),\n" +
                "\tCONSTRAINT FK_motivation_addiction_type FOREIGN KEY (addiction) REFERENCES addiction_type(name) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");\n" +
                "\n" +
                "CREATE INDEX motivation_pk ON motivation (description);\n" +
                "\n" +
                "CREATE INDEX sqlite_autoindex_motivation_1 ON motivation (description);");

        // did you know; an apple a day keeps the doctor away
        CreateTable("Profile_Addiction", "CREATE TABLE profile_addiction (\n" +
                "\tprofile VARCHAR(256) NOT NULL,\n" +
                "\taddiction VARCHAR(256) NOT NULL,\n" +
                "\tamount INT4,\n" +
                "\tCONSTRAINT profile_addiction_pk PRIMARY KEY (profile,addiction),\n" +
                "\tCONSTRAINT FK_profile_addiction_addiction_type FOREIGN KEY (addiction) REFERENCES addiction_type(name) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tCONSTRAINT FK_profile_addiction_profile FOREIGN KEY (profile) REFERENCES profile(username)\n" +
                ");\n" +
                "\n" +
                "CREATE INDEX profile_addiction_pk ON profile_addiction (profile,addiction);\n" +
                "\n" +
                "CREATE INDEX sqlite_autoindex_profile_addiction_1 ON profile_addiction (profile,addiction);");
    }

    static  void CreateTable(String name, String sqlIfNotExisting){
        try {
            database.rawQuery("Select * from " + name, null);
        }
        catch (Exception ex){
            database.execSQL("Drop table if exists " + name);
            database.execSQL(sqlIfNotExisting);
        }
    }

    public static Cursor get(Record record, String whereStatement) throws IndexOutOfBoundsException{

        String sql = "Select * from " + record.getTableName() + " Where " + whereStatement;
        return executeWithResult(sql);
    }

    public static Cursor executeWithResult(String sql) {
        return database.rawQuery(sql, null);
    }
}
