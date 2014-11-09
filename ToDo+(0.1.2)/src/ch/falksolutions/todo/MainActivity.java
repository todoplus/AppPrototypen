package ch.falksolutions.todo;

import ch.falksolutions.todo.R;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	private ProgressDialog pDialog;

	// Parsing URL
	private static String StanUrl = "http://192.168.178.162:8080/";
	private static String url = StanUrl;
	
	
	public static String getUrl() {
		return StanUrl;
	}

	public static void setUrl(String pUrl) {
		MainActivity.url = pUrl;
	}

	// JSON Node names
	private static final String TAG_ID = "_id";
	private static final String TAG_DATE = "Date";
	private static final String TAG_USER = "user";
	private static final String TAG_NAME = "name";
	private static final String TAG_DESCRIPTION = "description";
	//private static final String TAG_DEVICE = "device";
	

	// content JSONArray
	JSONArray content = null;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> eventList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		eventList = new ArrayList<HashMap<String, String>>();

		ListView lv = getListView();
		
		Intent in = getIntent();
		Boolean autoSync = in.getBooleanExtra("SYNC", false);
		Log.d("Main AC", "autoSync B= " + autoSync);
		
		if (autoSync == true) {
			Log.d("MainAC","processRemove= " + url);
			new GetContent().execute();
			
		}
		
		
		
		// Listview on item click listener
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("MAIN AC", "ListID" + id);
				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.name))
						.getText().toString();
				String description = ((TextView) view.findViewById(R.id.description))
						.getText().toString();
				String _id = ((TextView) view.findViewById(R.id.id))
						.getText().toString();

				// Starting single contact activity
				Intent in = new Intent(getApplicationContext(),
						SingleEventActivity.class);
				in.putExtra(TAG_NAME, name);
				//in.putExtra(TAG_DESCRIPTION, description);
				in.putExtra(TAG_ID, _id);
				startActivity(in);

			
		}
    });
		// Calling async task to get json
		//new GetContent().execute();
	}

	public void onButtonPlusClick(View view) {

		Intent in = new Intent(MainActivity.this, AddEventActivity.class);
		startActivity(in);
	}
	
	public void onGET(View view) {
		String pUrl = StanUrl;
		pUrl += "get?usr=testuser";
	
		setUrl(pUrl);
		new GetContent().execute();
	}
	public void autoGET() {
		String pUrl = StanUrl;
		pUrl += "get?usr=testuser";
		Log.d("Main AC", "autoGet= " + pUrl);
		setUrl(pUrl);
		new GetContent().execute();
	}
	
	/**
	 * Async task class to get json by making HTTP call
	 * */
	public class GetContent extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);
			
			if (jsonStr == null) {
				finish();
			}

			if (jsonStr != null) {
				try {
					JSONArray content = new JSONArray(jsonStr);
					

					// looping through All Contacts
					for (int i = 0; i < content.length(); i++) {
						JSONObject c = content.getJSONObject(i);
						
						String _id = c.getString(TAG_ID);
						String name = c.getString(TAG_NAME);
						String date = c.getString(TAG_DATE);
						//String device = c.getString(TAG_DEVICE);
						//String description = c.getString(TAG_DESCRIPTION);
						

						// tmp hashmap for single contact
						HashMap<String, String> singleEvent = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						singleEvent.put(TAG_NAME, name);
						singleEvent.put(TAG_ID, _id);
						singleEvent.put(TAG_DATE, date);
						//singleEvent.put(TAG_DESCRIPTION, description);

						// adding contact to contact list
						eventList.add(singleEvent);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			ListAdapter adapter = new SimpleAdapter(
					MainActivity.this, eventList,
					R.layout.list_item, new String[] { TAG_NAME, TAG_DATE,
							TAG_ID }, new int[] { R.id.name,
							R.id.description, R.id.id });

			setListAdapter(adapter);
		}

	}

}