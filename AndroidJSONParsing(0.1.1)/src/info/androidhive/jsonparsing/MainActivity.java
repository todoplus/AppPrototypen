package info.androidhive.jsonparsing;

import info.androidhive.jsonparsing.R;
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

	// URL to get contacts JSON
	private static String url = "http://127.0.0.1";

	// JSON Node names
	private static final String TAG_ID = "id";
	private static final String TAG_DATE = "date";
	private static final String TAG_USER = "user";
	private static final String TAG_CONTENT = "name";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_DEVICE = "device";
	

	// contacts JSONArray
	JSONArray content = null;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> eventList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		eventList = new ArrayList<HashMap<String, String>>();

		ListView lv = getListView();

		// Listview on item click listener
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.name))
						.getText().toString();
				String cost = ((TextView) view.findViewById(R.id.email))
						.getText().toString();
				String description = ((TextView) view.findViewById(R.id.mobile))
						.getText().toString();

				// Starting single contact activity
				Intent in = new Intent(getApplicationContext(),
						SingleContactActivity.class);
				in.putExtra(TAG_CONTENT, name);
				in.putExtra(TAG_DESCRIPTION, description);
				in.putExtra(TAG_ID, id);
				startActivity(in);

			}
		});

		// Calling async task to get json
		new GetContacts().execute();
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetContacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					
					// Getting JSON Array node
					content = jsonObj.getJSONArray(TAG_CONTENT);

					// looping through All Contacts
					for (int i = 0; i < content.length(); i++) {
						JSONObject c = content.getJSONObject(i);
						
						String id = c.getString(TAG_ID);
						String name = c.getString(TAG_CONTENT);
						String date = c.getString(TAG_DATE);
						String content = c.getString(TAG_DEVICE);
						String description = c.getString(TAG_DESCRIPTION);

						/*// Phone node is JSON Object
						JSONObject phone = c.getJSONObject(TAG_PHONE);
						String mobile = phone.getString(TAG_PHONE_MOBILE);
						String home = phone.getString(TAG_PHONE_HOME);
						String office = phone.getString(TAG_PHONE_OFFICE);*/

						// tmp hashmap for single contact
						HashMap<String, String> singleEvent = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						singleEvent.put(TAG_ID, id);
						singleEvent.put(TAG_DATE, date);
						singleEvent.put(TAG_CONTENT, content);
						singleEvent.put(TAG_DESCRIPTION, description);

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
					R.layout.list_item, new String[] { TAG_CONTENT, TAG_DATE,
							TAG_DESCRIPTION }, new int[] { R.id.name,
							R.id.email, R.id.mobile });

			setListAdapter(adapter);
		}

	}

}