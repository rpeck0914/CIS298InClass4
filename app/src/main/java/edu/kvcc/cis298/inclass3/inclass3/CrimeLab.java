package edu.kvcc.cis298.inclass3.inclass3;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.kvcc.cis298.inclass3.inclass3.database.CrimeBaseHelper;
import edu.kvcc.cis298.inclass3.inclass3.database.CrimeDbSchema;
import edu.kvcc.cis298.inclass3.inclass3.database.CrimeDbSchema.CrimeTable;

/**
 * Created by dbarnes on 10/28/2015.
 */
public class CrimeLab {

    //Static variable to hold the instance of CrimeLab
    //Rather than returning a new instance of CrimeLab,
    //we will return this variable that holds our instance.
    private static CrimeLab sCrimeLab;

    private Context mContext;

    private SQLiteDatabase mDatabase;

    //This is the method that will be used to get an instance of
    //CrimeLab. It will check to see if the current instance in the
    //variable is null, and if it is, it will create a new one using
    //the private constructor. If it is NOT null, it will just return
    //the instance that exists.
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    //This is the constructor. It is private rather than public.
    //It is private because we don't want people to be able to
    //create a new instance from outside classes. If they want
    //to make an instance, we want them to use the get method
    //declared right above here.
    private CrimeLab(Context context) {

        //Assign the passed in context a class level one.
        mContext = context.getApplicationContext();

        //Use the context to conjunction with the CrimeBaseHelper class that we wrote to get the
        //writable database. We didn't write the getWritableDatabase function that is being called
        //It came from the parent class that CrimeBaseHelper inherits from.
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    //Method to add a new crime to the database. This method will get called when a user clicks
    //on the add button of the toolbar.
    public void addCrime(Crime c) {
        //Get the content values that we would like to stick into the database by sending it the
        //crime that needs to be translated
        ContentValues values = getContentValues(c);
        //Call the insert method of our class level version of the CrimeBaseHelper class. We did not
        //write the insert method. It came from the parent class of CrimeBaseHelper. We are just using it.
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void updateCrime(Crime crime) {
        //Get the UUID out of the crime as a string. This will be used in the WHERE clause of the
        //SQL to find the row we want to update
        String uuidString = crime.getId().toString();
        //Create the content values that will be used to do the update of the model
        ContentValues values = getContentValues(crime);

        //Update a specific crime with the values from the content values for the crime that has the UUID
        //of The one in uuidString.
        mDatabase.update(CrimeTable.NAME, values,
                         CrimeTable.Cols.UUID + " = ?",
                         new String[] { uuidString });
    }

    //Getter to get the crimes
    public List<Crime> getCrimes() {
        return new ArrayList<>();
    }

    //Method to get a specific crime based on the
    //UUID that is passed in.
    public Crime getCrime(UUID id) {

        //no match, return null.
        return null;
    }

    //static method to do the work of talking in a crime and creating a contentValues object that can
    //be used to insert the crime into the database. The ContentValues class operates as a hash table,
    //or "key => value" array. The key refers to the column name of the database and the value refers
    //to the value we would like to put into the database.
    private static ContentValues getContentValues(Crime crime) {
        //Make new ContentValues object
        ContentValues values = new ContentValues();
        //Puts all the values in theirs spots with their key
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

        //Returns the ContentValue object
        return values;
    }
}
