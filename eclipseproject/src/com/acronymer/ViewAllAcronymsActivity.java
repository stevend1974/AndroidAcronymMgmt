package com.acronymer;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.acronymer.contentprovider.ContentProviderDB;
import com.acronymer.database.AcronymDB.AcronymEntry;


public class ViewAllAcronymsActivity extends ListActivity implements
LoaderManager.LoaderCallbacks<Cursor>{


	  // The loader's unique id. Loader ids are specific to the Activity or
	  // Fragment in which they reside.
	  private static final int LOADER_ID = 1;

	  // The callbacks through which we will interact with the LoaderManager.
	  private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

	  // The adapter that binds our data to the ListView
	  private SimpleCursorAdapter mAdapter;
	  
	  
	  // project for the columns to be returned
	  private  static final String[] PROJECTION = {
		  		AcronymEntry._ID,
				AcronymEntry.COLUMN_NAME_ABBREVIATION,
				AcronymEntry.COLUMN_NAME_FULLFORM };
	  
	  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_view_all_acronyms);
			    
	    String[] dataColumns = new String[] { AcronymEntry.COLUMN_NAME_ABBREVIATION ,AcronymEntry.COLUMN_NAME_FULLFORM};
	    int[] viewIDs = new int[] { R.id.acronymabberviationlabel, R.id.acronymfullformlabel };
	    // Initialize the adapter. Note that we pass a 'null' Cursor as the
	    // third argument. We will pass the adapter a Cursor only when the
	    // data has finished loading for the first time (i.e. when the
	    // LoaderManager delivers the data to onLoadFinished). Also note
	    // that we have passed the '0' flag as the last argument. This
	    // prevents the adapter from registering a ContentObserver for the
	    // Cursor (the CursorLoader will do this for us!).
	    mAdapter = new SimpleCursorAdapter(this, R.layout.acronym_row, null, dataColumns, viewIDs, 0);
	    // Associate the (now empty) adapter with the ListView.
	    setListAdapter(mAdapter);

	    // The Activity (which implements the LoaderCallbacks<Cursor>
	    // interface) is the callbacks object through which we will interact
	    // with the LoaderManager. The LoaderManager uses this object to
	    // instantiate the Loader and to notify the client when data is made
	    // available/unavailable.
	    mCallbacks = this;

	    // Initialize the Loader with id '1' and callbacks 'mCallbacks'.
	    // If the loader doesn't already exist, one is created. Otherwise,
	    // the already created Loader is reused. In either case, the
	    // LoaderManager will manage the Loader across the Activity/Fragment
	    // lifecycle, will receive any new loads once they have completed,
	    // and will report this new data back to the 'mCallbacks' object.
	    LoaderManager lm = getLoaderManager();
	    lm.initLoader(LOADER_ID, null, mCallbacks);
				
	}

	
	  @Override
	  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
	    // Create a new CursorLoader with the following query parameters.
	    return new CursorLoader(ViewAllAcronymsActivity.this, ContentProviderDB.TABLE_URI,PROJECTION, null, null, null);
	  }

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		 // A switch-case is useful when dealing with multiple Loaders/IDs
	    switch (loader.getId()) {
	      case LOADER_ID:
	        // The asynchronous load is complete and the data
	        // is now available for use. Only now can we associate
	        // the queried Cursor with the SimpleCursorAdapter.
	        mAdapter.swapCursor(cursor);
	        break;
	    }
	    // The listview now displays the queried data.
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// For whatever reason, the Loader's data is now unavailable.
	    // Remove any references to the old data by replacing it with
	    // a null Cursor.
	    mAdapter.swapCursor(null);
		
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

	  // Opens the second activity if a list item entry is clicked
	  @Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.d(this.getClass().getName()," click on position "+position+" and id "+id);
		super.onListItemClick(l, v, position, id);
	    Intent intent = new Intent(this, ViewAcronymActivity.class);
	    Uri acronymUri = Uri.parse(ContentProviderDB.TABLE_URI + "/" + id);
	    Log.d(this.getClass().getName()," passing the uri "+acronymUri);
	    intent.putExtra(ContentProviderDB.CONTENT_ITEM_TYPE, acronymUri);
	    startActivity(intent);
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
