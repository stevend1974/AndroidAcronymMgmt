package com.acronymer.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.acronymer.database.AcronymDB.AcronymEntry;
import com.acronymer.database.AcronymDBHelper;

public class ContentProviderDB extends ContentProvider{
		AcronymDBHelper dbHelper ;
		public static final String AUTHORITY = "com.acronymer.content.provider.ContentProviderAuthorities";//specific for our our app, will be specified in maninfed 
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
		public static final Uri TABLE_URI = Uri.parse("content://" + AUTHORITY+"/"+AcronymEntry.TABLE_NAME);
	 
		public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+AcronymEntry.TABLE_NAME;
		
		
		private static final int ACRONYM = 10;
		private static final int ACRONYM_ID = 20;

		  private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		  static {
		    sURIMatcher.addURI(AUTHORITY, AcronymEntry.TABLE_NAME, ACRONYM);
		    sURIMatcher.addURI(AUTHORITY, AcronymEntry.TABLE_NAME + "/#", ACRONYM_ID);
		  }
		
		  
	 @Override
	 public boolean onCreate() {
		 
	  dbHelper = new AcronymDBHelper(getContext());
	  
	  Log.d(this.getClass().getName()," new databasehelper instantiated");
	  
	  return true;
	 }

	 @Override
	 public int delete(Uri uri, String where, String[] args) {
		 String table = getTableName(uri);
	     SQLiteDatabase dataBase=dbHelper.getWritableDatabase();
	     
	     /*
	      * check which uri pattern we have, if it has an acronymid -> only delete this id and not all records ;-)
	      */
	     int rowsDeleted = 0;
	     switch (sURIMatcher.match(uri)) {
	     case ACRONYM:
	       rowsDeleted = dataBase.delete(table, where, args);
	       break;
	     case ACRONYM_ID:
	       String id = uri.getLastPathSegment();
	       if (TextUtils.isEmpty(where)) {
	         rowsDeleted = dataBase.delete(table,AcronymEntry._ID + "=" + id, null);
	       } else {
	         rowsDeleted = dataBase.delete(table,AcronymEntry._ID + "=" + id + " and " + where,args);
	       }
	       break;
	     default:
	       throw new IllegalArgumentException("Unknown URI: " + uri);
	     }
	     getContext().getContentResolver().notifyChange(uri, null);
	     return rowsDeleted;	     
	     
	 }

	 @Override
	 public String getType(Uri arg0) {
	  // TODO Auto-generated method stub
	  return null;
	 }

	 
	 @Override
	 public Uri insert(Uri uri, ContentValues initialValues) {
	  String table = getTableName(uri);
	  SQLiteDatabase database = dbHelper.getWritableDatabase();
	  Log.d(this.getClass().getName()," writable database instantiated");
	  long value = database.insert(table, null, initialValues);
	  Log.d(this.getClass().getName()," id "+ value);
	  return Uri.parse(CONTENT_URI+ "/" + String.valueOf(value));
	 }

	 @Override
	 public Cursor query(Uri uri, String[] projection, String selection,
	      String[] selectionArgs, String sortOrder) {
	  
		 String table =AcronymEntry.TABLE_NAME;
		 Log.d(this.getClass().getName()," readable database instantiated");
		 	// Uisng SQLiteQueryBuilder instead of query() method
		    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		    // Set the table
		    queryBuilder.setTables(table);

		    int uriType = sURIMatcher.match(uri);
			    switch (uriType) {
			    case ACRONYM:
			      break;
			    case ACRONYM_ID:
			      // adding the ID to the original query
			      queryBuilder.appendWhere(AcronymEntry._ID + "="+ uri.getLastPathSegment());
			      break;
			    default:
			      throw new IllegalArgumentException("Unknown URI: " + uri);
			    }

			    SQLiteDatabase database = dbHelper.getReadableDatabase();
			    Cursor cursor = queryBuilder.query(database,  projection, selection, selectionArgs, null, null, sortOrder);
		    // make sure that potential listeners are getting notified
		    cursor.setNotificationUri(getContext().getContentResolver(), uri);

		    return cursor;
		 
	 }  

	 @Override
	 public int update(Uri uri, ContentValues values, String whereClause,
	      String[] whereArgs) {
		  String table = getTableName(uri);
		  SQLiteDatabase database = dbHelper.getWritableDatabase();  
	  
	     int rowsUpdated = 0;
	     switch (sURIMatcher.match(uri)) {
	     case ACRONYM:
	    	 rowsUpdated = database.update(table, values, whereClause, whereArgs);
	       break;
	     case ACRONYM_ID:
	       String id = uri.getLastPathSegment();
	       if (TextUtils.isEmpty(whereClause)) {
	    	   rowsUpdated = database.update(table,values, AcronymEntry._ID + "=" + id, null);
	       } else {
	    	   rowsUpdated = database.update(table,values,AcronymEntry._ID + "=" + id + " and " + whereClause,whereArgs);
	       }
	       break;
	     default:
	       throw new IllegalArgumentException("Unknown URI: " + uri);
	     }
	  
	  return rowsUpdated;
	 }
	 
	 public static String getTableName(Uri uri){
	  String value = AcronymEntry.TABLE_NAME;//uri.getPath();
	  value = value.replace("/", "");//we need to remove '/'
	  return value;
	 }
	}
