package com.acronymer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.acronymer.contentprovider.ContentProviderDB;
import com.acronymer.database.AcronymDB.AcronymEntry;

public class SaveAcronymActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_acronym);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		String abbreviation = intent.getStringExtra(AddAcronymActivity.ADD_ACRONYM_ABBREVIATION);
		String fullform = intent.getStringExtra(AddAcronymActivity.ADD_ACRONYM_FULLFORM);
		String description = intent.getStringExtra(AddAcronymActivity.ADD_ACRONYM_DESCRIPTION);
		

		Log.d(SaveAcronymActivity.class.getName()," database connection opened ");
		
		
		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(AcronymEntry.COLUMN_NAME_ABBREVIATION, abbreviation);
		values.put(AcronymEntry.COLUMN_NAME_FULLFORM, fullform);
		values.put(AcronymEntry.COLUMN_NAME_DESCRIPTION, description);

		// Insert the new row, returning the primary key value of the new row
		Uri contentUri = Uri.withAppendedPath(ContentProviderDB.CONTENT_URI, AcronymEntry.TABLE_NAME);
		Uri resultUri = this.getContentResolver().insert(contentUri, values);
		
		 // Create the text view to confirm has been added to database
		 Resources res = getResources();
		 String text = String.format(res.getString(R.string.acronym_added_message), abbreviation, fullform, description);
		 TextView textView = new TextView(this);
		 textView.setText(text);

		 // Set the text view as the activity layout
		 setContentView(textView);
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
        	addAcronym();
            return true;
        case R.id.action_viewall:
            viewAll();
            return true;
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void addAcronym() {
	    Intent i = new Intent(this, AddAcronymActivity.class);
	    startActivity(i);
	  }

	private void viewAll() {
	    Intent i = new Intent(this, ViewAllAcronymsActivity.class);
	    startActivity(i);
	  }
	
}
