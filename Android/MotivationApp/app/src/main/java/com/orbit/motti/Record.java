package com.orbit.motti;

import android.database.Cursor;

import java.io.InvalidClassException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vader on 13/02/2016.
 */
public abstract class Record {
    public abstract String getTableName();
    protected abstract void createFromDatabase(Cursor data);
    protected abstract void copyFrom(Record data);
    private  boolean isCreated = false;
    public  boolean getIsCreated(){return this.isCreated;}

    public  Record(){
        this.identifierColumn = setIdentifierColumn();
    }

    private IdentifierColumn identifierColumn;
    protected abstract IdentifierColumn setIdentifierColumn();
    private static ArrayList<Record> cache = new ArrayList<Record>();

    public void loadFromDatabase() throws  InvalidClassException
    {
        loadFromDatabase(false);
    }

    public void loadFromDatabase(boolean forceFetch) throws  InvalidClassException
    {
        if(!forceFetch) {
            for (int i = 0; i < cache.size(); i++) {
                if (cache.get(i).getClass() == this.getClass())
                    if (cache.get(i).getIdentifierColumn().getValue() == this.getIdentifierColumn().getValue()) {
                        copyFrom(cache.get(i));
                        return;
                    }
            }
        }
        Cursor data = Database.get(this,this.identifierColumn.forQuery());
        if(!data.moveToFirst())
            throw new InvalidClassException("No data found for " + this.getClass().getName() + " with "+this.identifierColumn.getName()+" = " + this.setIdentifierColumn().getName());
        createFromDatabase(data);
        isCreated = true;
    }

    public IdentifierColumn getIdentifierColumn() {
        return identifierColumn;
    }
}
