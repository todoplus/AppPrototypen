package ch.falksolutions.layouttest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddEvent extends MainActivity {
	EditText inputName;
	EditText inputBeschreibung;
	

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
		Intent goToMainActivity = new Intent
				(AddEvent.this,MainActivity.class);
				goToMainActivity.putExtra("Name", inputName.getText().toString());
				goToMainActivity.putExtra("Beschreibung", inputBeschreibung.getText().toString());
				Log.d("AddE AC",inputName.getText()+"."+ inputBeschreibung.getText());
		startActivity(goToMainActivity);
		
		
	}
	

}
