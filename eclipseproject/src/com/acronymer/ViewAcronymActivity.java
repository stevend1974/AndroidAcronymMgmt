package com.acronymer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.acronymer.contentprovider.ContentProviderDB;
import com.acronymer.database.AcronymDB.AcronymEntry;

public class ViewAcronymActivity extends Activity {

	private EditText editTextAbbreviation;
	private EditText editTextFullform;
	private EditText editTextDescription;
	
	private Uri acronymUri;

	
	 private  static final String[] PROJECTION = {
			AcronymEntry.COLUMN_NAME_ABBREVIATION,
			AcronymEntry.COLUMN_NAME_FULLFORM,
			AcronymEntry.COLUMN_NAME_DESCRIPTION};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_acronym);
		
		editTextAbbreviation = (EditText) findViewById(R.id.view_acronym_abbreviation);
		editTextFullform = (EditText) findViewById(R.id.view_acronym_fullform);
		editTextDescription = (EditText) findViewById(R.id.view_acronym_description);
	    
		Button confirmButton = (Button) findViewById(R.id.view_acronym_confirm_button);
		Button deleteButton = (Button) findViewById(R.id.view_acronym_delete_button);
		
		Bundle extras = getIntent().getExtras();
		
		
		if (extras != null) {
			acronymUri = extras.getParcelable(ContentProviderDB.CONTENT_ITEM_TYPE);
			Log.d(this.getClass().getName()," acronymUri obtained is "+acronymUri);
			
			Cursor cursor = getContentResolver().query(acronymUri, PROJECTION, null, null,null);
			
				if (cursor.moveToFirst()) {
					
					// found it
					editTextAbbreviation.setText(cursor.getString(cursor.getColumnIndexOrThrow(AcronymEntry.COLUMN_NAME_ABBREVIATION)));
					editTextFullform.setText(cursor.getString(cursor.getColumnIndexOrThrow(AcronymEntry.COLUMN_NAME_FULLFORM)));
					editTextDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow(AcronymEntry.COLUMN_NAME_DESCRIPTION)));
					
					
					// always close the cursor
				    cursor.close();
				}
			} // end of etra's obtained

		

		// instantiate the on click listener...when ok -> go back to previous page
		 deleteButton.setOnClickListener(
				 new View.OnClickListener() {
					 	public void onClick(View view) {
					 			Log.d(this.getClass().getName()," id to be deleted is "+acronymUri.getLastPathSegment());
					 		    getContentResolver().delete(acronymUri, null, null);
					 			setResult(RESULT_OK);
					 			finish();
					 	}
				});

		
		
	}
	
    /** Called when the user clicks the Creation confirmation button */
    public void modifyAcronym(View view) {
        // Do something in response to button
    	if (TextUtils.isEmpty(editTextAbbreviation.getText().toString())) {
				makeToast();
		} else {
    	Intent intent = new Intent(this, ViewAllAcronymsActivity.class);
    	startActivity(intent);
		}  
    }    


	private void makeToast() {
		Toast.makeText(ViewAcronymActivity.this, R.string.view_acronym_toast_message,Toast.LENGTH_LONG).show();
	}	      
		      
		      
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    saveAcronymState();
	    outState.putParcelable(ContentProviderDB.CONTENT_ITEM_TYPE, acronymUri);
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    saveAcronymState();
	  }

	  private void saveAcronymState() {

		    String abbreviation = editTextAbbreviation.getText().toString();
		    String fullform = editTextFullform.getText().toString();
		    String description = editTextDescription.getText().toString();

		    // only save if abbreviation or fullform is available
		    if (abbreviation.length() == 0 && fullform.length() == 0) {
		      return;
		    }

		    ContentValues values = new ContentValues();
		    values.put(AcronymEntry.COLUMN_NAME_ABBREVIATION, abbreviation);
		    values.put(AcronymEntry.COLUMN_NAME_FULLFORM, fullform);
		    values.put(AcronymEntry.COLUMN_NAME_DESCRIPTION, description);

		    Log.d(this.getClass().getName()," updating acronym uri "+acronymUri+" with values "+abbreviation+"-"+fullform+"-"+description);
		    // Update acronym
		    getContentResolver().update(acronymUri, values, null, null);

		  }

}
