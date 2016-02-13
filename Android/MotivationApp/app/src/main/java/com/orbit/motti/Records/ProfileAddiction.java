package com.orbit.motti.Records;

import android.database.Cursor;

import com.orbit.motti.IdentifierColumn;
import com.orbit.motti.Record;

import java.io.InvalidClassException;

/**
 * Created by Vader on 13/02/2016.
 */
public class ProfileAddiction extends Record {
    private Profile profile;
    private Addiction addiction;
    private int amount;

    @Override
    public String getTableName() {
        return "Profile_Addiction";
    }

    @Override
    protected void createFromDatabase(Cursor data) {
        int profileNameIndex = data.getColumnIndex("profile");
        int addictionNameIndex = data.getColumnIndex("addiction");

        this.profile = new Profile(data.getString(profileNameIndex));
        this.addiction = new Addiction();
        addiction.setName(data.getString(addictionNameIndex));

        int amountIndex = data.getColumnIndex("amount");
        this.amount = data.getInt(amountIndex);
        try {
            profile.loadFromDatabase();
        }catch (InvalidClassException ex){
            //0 records found
        }
        try {
            addiction.loadFromDatabase();
        }catch (InvalidClassException ex){
            //0 records found
        }
    }

    @Override
    protected void copyFrom(Record data) {
        ProfileAddiction copy = (ProfileAddiction)data;
        this.profile = copy.profile;
        this.addiction = copy.addiction;
        this.amount = copy.amount;
    }

    @Override
    protected IdentifierColumn setIdentifierColumn() {
        return new IdentifierColumn(this, "profile_addition") {
            @Override
            public Object getValue() {
                //keyvaluepair
                return  null;//not implemented;
            }

            @Override
            public void setValue(Object value) {
                //keyvalue pair
                //not implemented;
            }

            @Override
            public String forQuery() {
                //todo using both values
                return super.forQuery();
            }
        };
    }

    public Addiction getAddiction() {
        return this.addiction;
    }
}
