package ch.falksolutions.layouttest;

import java.io.FileInputStream;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;


public class MainActivity extends ListActivity {
	String vName;
	String vBeschreibung;	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.d("MainAc","onCreate ausgefuehrt");
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
       
        // Laden der xml Ressourcen in ein Array
        String[] added_events=  getResources().getStringArray(R.array.added_events);
        
        // Initialisieren des Adapters
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, added_events);
         
        // Einfuegen in die ListView
       setListAdapter(adapter);
              
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	Log.d("MainAc","onResume ausgefuehrt");
    	
    	 
    	  
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
    
    public void readData() {
    	//FileInputStream fileIn = openFileInput(AddEvent.datei);
    }
   
}
