package com.orbit.motti;

import android.database.Cursor;

/**
 * Created by Vader on 13/02/2016.
 */
public class ProfileExample extends Record {
    @Override
    protected IdentifierColumn setIdentifierColumn() {
        return new IdentifierColumn(this, "username") {
            @Override
            public Object getValue() {
                return ((ProfileExample)this.getRecord()).getUsername();
            }
        };
    }

    public ProfileExample(String username){
        this.userName = username;
    }

    private String userName = null;
    public String getUsername(){return  this.userName;}

    @Override
    protected void createFromDatabase(Cursor data) {
        data.moveToNext();
        this.userName = data.getString(0) + " From Database";
    }

    @Override
    public String getTableName() {
        return "Profile";
    }
}
