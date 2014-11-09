package ch.falksolutions.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import ch.falksolutions.todo.R;

public class SingleEventActivity  extends Activity {
	
	private static String url = MainActivity.getUrl();
	
	// JSON node keys
	private static final String TAG_NAME = "name";
	private static final String TAG_ID = "_id";
	private static final String TAG_DATE = "date";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String name = in.getStringExtra(TAG_NAME);
        String id = in.getStringExtra(TAG_ID);
        String date = in.getStringExtra(TAG_DATE);
        Log.d("SingleEvent AC", "tagid= " + id);
        
        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name_label);
        TextView lblEmail = (TextView) findViewById(R.id.email_label);
        TextView lblMobile = (TextView) findViewById(R.id.mobile_label);
        
        lblName.setText(name);
        lblEmail.setText(id);
        lblMobile.setText(date);
    }
	
	public void onDelete(View v) {
		Intent in = getIntent();
		String id = in.getStringExtra(TAG_ID);
		removeData(url, id);
	}
	public void removeData(String url, String id) {
		url = MainActivity.getUrl();
		url += "rmv?id="  + id;
		MainActivity.setUrl(url);
		Log.d("SingleEvent AC", "rmvid= " +id);
		
		Intent goToMainActivity = new Intent
				(SingleEventActivity.this,MainActivity.class);
				goToMainActivity.putExtra("url",url);
		startActivity(goToMainActivity);
		
		
	}
}
