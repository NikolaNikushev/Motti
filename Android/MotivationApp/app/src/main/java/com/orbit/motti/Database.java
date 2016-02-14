package com.orbit.motti; /**
 * Created by Vader on 13/02/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.orbit.motti.Records.ExtendedCursor;

import java.util.ArrayList;
import java.util.Objects;

public class Database {
    public  static  Database Instance;

    public  Database(Context context){
        if(Instance == null) {
            Instance = this;
        }
        database = context.openOrCreateDatabase("database.sqlite", SQLiteDatabase.CREATE_IF_NECESSARY, null);
    }

    protected static SQLiteDatabase database;

    public static void dropTables(){
        executeSQL("Drop table profile");
        executeSQL("Drop table motivation");
        executeSQL("Drop table motivation_type");
        executeSQL("Drop table Goal");
        executeSQL("Drop table addiction_type");
        executeSQL("Drop table personal_question_answer");
        executeSQL("Drop table Sub_Goal");
    }

    public static void connectToDatabase(){
        //Profiles
        CreateTable("Profile","CREATE TABLE Profile (\n" +
                "\tusername VARCHAR(256),\n" +
                "\tcredits INTEGER,\n" +
                "\tCONSTRAINT profile_pk PRIMARY KEY (username)\n" +
                ");" );

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
                "\tprofile VARCHAR(256),\n" +
                "\tCONSTRAINT goal_pk PRIMARY KEY (ID)\n" +
                ");");

        // smoking; gambling
        CreateTable("Addiction_Type", "CREATE TABLE addiction_type (\n" +
                "\tname VARCHAR(256) NOT NULL,\n" +
                "\tCONSTRAINT addiction_type_pk PRIMARY KEY (name)\n" +
                ");");

        // an apple a day keeps the doctor away
        CreateTable("Motivation", "CREATE TABLE Motivation (\n" +
                "\tid INTEGER,\n" +
                "\tdescription VARCHAR(256),\n" +
                "\tis_positive BOOL,\n" +
                "\timage VARCHAR(256),\n" +
                "\tmotivation_type VARCHAR(256),\n" +
                "\taddiction VARCHAR(256),\n" +
                "\tCONSTRAINT motivation_pk PRIMARY KEY (id),\n" +
                "\tCONSTRAINT FK_Motivation_Motivation_Type FOREIGN KEY (motivation_type) REFERENCES Motivation_Type(Name) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                "\tCONSTRAINT FK_Motivation_addiction_type FOREIGN KEY (addiction) REFERENCES addiction(name) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");\n");

        // did you know; question; personal action
        CreateTable("Motivation_Type", "CREATE TABLE Motivation_Type (\n" +
                "\tName VARCHAR(256),\n" +
                "\tCONSTRAINT motivation_type_pk PRIMARY KEY (Name)\n" +
                ");");

        //do you need assistance : yes/no
        CreateTable("Personal_Question_Answer", "CREATE TABLE Personal_Question_Answer (\n" +
                "\tmotivation_id INTEGER,\n" +
                "\tanswer VARCHAR(256),\n" +
                "\tCONSTRAINT personal_question_answer_pk PRIMARY KEY (motivation_id,answer),\n" +
                "\tCONSTRAINT FK_Personal_Question_Answer_Motivation FOREIGN KEY (motivation_id) REFERENCES Motivation(id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");\n" +
                "\n" +
                "CREATE INDEX sqlite_autoindex_Personal_Question_Answer_1 ON Personal_Question_Answer (motivation_id,answer);\n");

        CreateTable("Sub_Goal", "CREATE TABLE Sub_Goal (\n" +
                "\tID INTEGER,\n" +
                "\tTitle VARCHAR(256),\n" +
                "\tparent_goal INTEGER,\n" +
                "\tis_finished BOOL,\n" +
                "\ttime_period INTEGER,\n" +
                "\tCONSTRAINT sub_goal_pk PRIMARY KEY (ID),\n" +
                "\tCONSTRAINT FK_Sub_Goal_Goal FOREIGN KEY (parent_goal) REFERENCES Goal(ID) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");\n");
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

    public static ExtendedCursor get(Record record, String whereStatement) throws IndexOutOfBoundsException{
        String sql = "Select * from " + record.getTableName() + " Where " + whereStatement;
        return executeWithResult(sql);
    }

    public static void executeSQL(String sql){
        database.execSQL(sql);
    }

    public static ExtendedCursor executeWithResult(String sql) {
        return new ExtendedCursor(database.rawQuery(sql, null));
    }
}
