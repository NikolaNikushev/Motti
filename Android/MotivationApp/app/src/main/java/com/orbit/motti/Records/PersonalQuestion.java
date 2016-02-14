package com.orbit.motti.Records;

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

        //todo read answers answers
    }
}
