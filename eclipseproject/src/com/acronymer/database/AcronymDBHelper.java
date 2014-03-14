package com.acronymer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.acronymer.database.AcronymDB.AcronymEntry;

public class AcronymDBHelper extends SQLiteOpenHelper {
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ACRONYMS =
	    "CREATE TABLE " + AcronymEntry.TABLE_NAME + " ( " +
	    		"_id INTEGER PRIMARY KEY AUTOINCREMENT " + COMMA_SEP +
	    		AcronymEntry.COLUMN_NAME_ABBREVIATION + TEXT_TYPE + COMMA_SEP +
	    		AcronymEntry.COLUMN_NAME_FULLFORM + TEXT_TYPE + COMMA_SEP +
	    		AcronymEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE +
	    " );";
	
	private static final String SQL_INSERT_EXAMPLE = "INSERT INTO "+ AcronymEntry.TABLE_NAME+" VALUES (null,'TLA','Three Letter Acronym','An example of an acronym.');";

	private static final String SQL_DELETE_ACRONYMS =
	    "DROP TABLE IF EXISTS " + AcronymEntry.TABLE_NAME;
	
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Acronyms.db";

    public AcronymDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ACRONYMS);
        Log.d(this.getClass().getName(),"database created");
        db.execSQL(SQL_INSERT_EXAMPLE);
        Log.d(this.getClass().getName(),"sample record entered");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ACRONYMS);
        Log.d(this.getClass().getName()," all records purged.");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
	

}
