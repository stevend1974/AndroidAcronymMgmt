package com.acronymer;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddAcronymActivity extends Activity {

	 public static final String ADD_ACRONYM_ABBREVIATION = "com.acronymer.ADD_ACRONYM_ABBREVIATION";
	 public static final String ADD_ACRONYM_FULLFORM = "com.acronymer.ADD_ACRONYM_FULLFORM";
	 public static final String ADD_ACRONYM_DESCRIPTION = "com.acronymer.ADD_ACRONYM_DESCRIPTION";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_acronym);
		// Show the Up button in the action bar.
		setupActionBar();
		
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
	
	
	   /** Called when the user clicks the Cancel button */
    public void cancelAddAcronym(View view) {
        // Do something in response to cancel button -> go to home
    	NavUtils.navigateUpFromSameTask(this);
    }
    
    /** Called when the user clicks the Creation confirmation button */
    public void confirmAddAcronym(View view) {
        // Do something in response to button
    	Intent intent = new Intent(this, SaveAcronymActivity.class);
    	EditText editTextAbbreviation = (EditText) findViewById(R.id.add_acronym_abbreviation);
    	EditText editTextFullform = (EditText) findViewById(R.id.add_acronym_fullform);
    	EditText editTextDescription = (EditText) findViewById(R.id.add_acronym_description);
    	intent.putExtra(ADD_ACRONYM_ABBREVIATION, editTextAbbreviation.getText().toString());
    	intent.putExtra(ADD_ACRONYM_FULLFORM, editTextFullform.getText().toString());
    	intent.putExtra(ADD_ACRONYM_DESCRIPTION, editTextDescription.getText().toString());
    	startActivity(intent);
    }    


}
