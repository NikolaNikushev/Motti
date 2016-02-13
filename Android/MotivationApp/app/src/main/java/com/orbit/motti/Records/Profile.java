package com.orbit.motti.Records;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.orbit.motti.Database;
import com.orbit.motti.IdentifierColumn;
import com.orbit.motti.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Vader on 13/02/2016.
 */
public class Profile extends Record {
    private String username = null;

    public String getUsername() {
        return this.username;
    }

    private AgeGroup ageGroup = null;

    public AgeGroup getAgeGroup() {
        return this.ageGroup;
    }

    private int credits = -1;

    public int getCredits() {
        return this.credits;
    }

    public ArrayList<Addiction> addictions = new ArrayList<Addiction>();

    public Profile(String name) {
        this.username = name;
    }

    @Override
    public String getTableName() {
        return "Profile";
    }

    @Override
    protected void createFromDatabase(Cursor data) {
        //todo check if case sensitive
        int nameIndex = data.getColumnIndex("username");
        this.username = data.getString(nameIndex);

        int creditsIndex = data.getColumnIndex("credits");
        this.credits = data.getInt(creditsIndex);

        int ageGroupIndex = data.getColumnIndex("age_range");
        this.ageGroup = new AgeGroup(data.getString(ageGroupIndex));

        this.addictions.clear();
        Addiction a = new Addiction();
        Cursor records = Database.Instance.executeWithResult("Select * from " + a.getTableName() + " where " + a.getIdentifierColumn().forQuery());

        while (records.moveToNext()) {
            a = new Addiction();
            nameIndex = records.getColumnIndex(a.getIdentifierColumn().getName());
            String name = records.getString(nameIndex);
            a.getIdentifierColumn().setValue(name);
            try {
                a.loadFromDatabase();
            } catch (Exception ex) {//todo unable to load}
            }
        }
    }

    @Override
    protected IdentifierColumn setIdentifierColumn() {
        return new IdentifierColumn(this, "username") {
            @Override
            public Object getValue() {
                return ((Profile) this.getRecord()).getUsername();
            }

            @Override
            public void setValue(Object value) {
                ((Profile) this.getRecord()).setUsername((String) value);
            }
        };
    }

    public void setUsername(String username) {
        this.username = username;
    }
}