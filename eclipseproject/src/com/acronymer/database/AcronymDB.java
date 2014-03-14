package com.acronymer.database;

import android.provider.BaseColumns;

public final class AcronymDB {

	// To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public AcronymDB() {}

    /* Inner class that defines the table contents */
    public static abstract class AcronymEntry implements BaseColumns {
        public static final String TABLE_NAME = "acronym";
        public static final String COLUMN_NAME_ABBREVIATION = "abbreviation";
        public static final String COLUMN_NAME_FULLFORM = "fullform";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }
}
