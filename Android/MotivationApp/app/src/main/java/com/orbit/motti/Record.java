package com.orbit.motti;

import android.database.Cursor;

/**
 * Created by Vader on 13/02/2016.
 */
public abstract class Record {
    public abstract String getTableName();
    protected abstract void createFromDatabase(Cursor data);
    private  boolean isCreated = false;
    public  boolean getIsCreated(){return this.isCreated;}

    public  Record(){
        this.identifierColumn = setIdentifierColumn();
    }

    private IdentifierColumn identifierColumn;
    protected abstract IdentifierColumn setIdentifierColumn();

    public void loadFromDatabase()
    {
        Cursor data = Database.get(this, this.identifierColumn.getName() + " = '" + this.identifierColumn.getValue()+"'");
        //if(data.length == 0) => no element to create
        createFromDatabase(data);
        isCreated = true;
    }
}
