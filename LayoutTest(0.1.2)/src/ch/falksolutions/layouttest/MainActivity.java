package ch.falksolutions.layouttest;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class MainActivity<T> extends ActionBarActivity {
	String vName;
	String vBeschreibung;
	private ListView exampleListView;

	//List<String> eventList = (List<String>) new ArrayList<String>();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.d("MainAc","onCreate ausgefuehrt");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
              
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	Log.d("MainAc","onResume ausgefuehrt");
    	
    	Intent getData = getIntent();
    	String vName = getData.getStringExtra("Name");
    	String vBeschreibung = getData.getStringExtra("Beschreibung");
    	Log.d("Main AC", vName + "." + vBeschreibung);
    	
    	if (vName == null) {
    		Log.d("Main AC", "Name leer");
    	}
    	else if (vName != null) {
    		
    		List<String> eventList = (List<String>) new ArrayList<String>();
    		exampleListView = (ListView) findViewById(R.id.listView1);
    	
    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    	        android.R.layout.simple_list_item_1, eventList);
    		
    		eventList.add(vName);
    		eventList.add(vBeschreibung);
        	Log.d("Main AC","Daten zu EventList hinzugefuegt");
        	exampleListView.setAdapter(adapter);
        	
    	}
    	  
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void onButtonPlusClick(View view) {
    	Log.d("MainAc","onButtonPlusClick ausgefuehrt");
    	Intent goToAddEvent = new Intent
				(
					MainActivity.this,
					AddEvent.class
						);
		startActivity(goToAddEvent);
    }
   
}
