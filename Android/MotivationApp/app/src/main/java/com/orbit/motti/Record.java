package com.orbit.motti;

import android.database.Cursor;

import java.io.InvalidClassException;

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

    public void loadFromDatabase() throws  InvalidClassException
    {
        Cursor data = Database.get(this, this.identifierColumn.getName() + " = " + this.identifierColumn.forQuery());
        if(!data.moveToFirst())
            throw new InvalidClassException("No data found for " + this.getClass().getName() + " with "+this.identifierColumn.getName()+" = " + this.setIdentifierColumn().getName());
        createFromDatabase(data);
        isCreated = true;
    }

    public IdentifierColumn getIdentifierColumn() {
        return identifierColumn;
    }
}
