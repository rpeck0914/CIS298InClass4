package edu.kvcc.cis298.inclass3.inclass3.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import edu.kvcc.cis298.inclass3.inclass3.Crime;
import edu.kvcc.cis298.inclass3.inclass3.database.CrimeDbSchema.CrimeTable;

/**
 * Created by rpeck0914 on 11/30/2015.
 */
public class CrimeCursorWrapper extends CursorWrapper {

    public  CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    //This method will get the data fro a crime out from the database in it's raw form. For the UUID
    //and Title, that means a string. For the date that means a long that is the number of seconds
    //since Epoch. For the solved, that means either a zero or a 1. Zero for false, 1 for true.
    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

        //Create a new Crime using the constructor that we just added that takes a single parameter
        //which is the UUID.
        Crime crime = new Crime(UUID.fromString(uuidString));
        //Set the remaining properties on the Crime model.
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);

        //return the finished crime model
        return crime;
    }
}
