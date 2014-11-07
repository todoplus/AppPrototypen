package ch.falksolutions.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class AddEventActivity extends ActionBarActivity {
	EditText inputName;
	EditText inputBeschreibung;

	private static String url = "http://192.168.178.162:8080/";
	private static String user = "testuser";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("AddEvent Activity", "Started");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addevent);
		
		inputName = (EditText) findViewById(R.id.editText1);
		inputBeschreibung = (EditText) findViewById(R.id.editText2);
		
		
		
		
	}
	public void onButtonFinishClick(View view) {
		Log.d("AddEventAc", "Finish Button betaetigt");
		ServiceHandler sh = new ServiceHandler();
		sh.putData(url,user,inputName.getText().toString());
	
		
		
		Intent goToMainActivity = new Intent
				(AddEventActivity.this,MainActivity.class);
		startActivity(goToMainActivity);
		
		
	}
	

}
