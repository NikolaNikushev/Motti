package com.orbit.motti.Records;

import com.orbit.motti.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vader on 14/02/2016.
 */
public class PersonalQuestion extends Motivation {
    List<String> answers = new ArrayList<String>();
    @Override
    public void loadFromCursor(ExtendedCursor data) {
        super.loadFromCursor(data);

        ExtendedCursor answerRecords = Database.Instance.executeWithResult("Select * from Personal_Question_Answer where motivation_id = " + this.getID());

        answers.clear();
        while(answerRecords.moveToNext())
        {
            answers.add(answerRecords.getString("answer"));
        }
    }
}
