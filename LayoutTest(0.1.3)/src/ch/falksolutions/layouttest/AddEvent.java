package ch.falksolutions.layouttest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class AddEvent extends ActionBarActivity {
	EditText inputName;
	EditText inputBeschreibung;
	
	private final String DATEINAME = "addedbyuser.txt";
	private final String VERZEICHNISNAME = "events"; 
	
	public static File verzeichnis;
	public static File datei;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("AddEvent Activity", "Started");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addevent);
		
		inputName = (EditText) findViewById(R.id.editText1);
		inputBeschreibung = (EditText) findViewById(R.id.editText2);
		
		verzeichnis = getDir(VERZEICHNISNAME, MODE_PRIVATE);
		datei = new File(verzeichnis, DATEINAME);
	
		
		
		
	}
	public void onButtonFinishClick(View view) {
		Log.d("AddEventAc", "Finish Button betaetigt");
		
		try {
			FileOutputStream fileOut = new FileOutputStream(datei);
			dateiSpeichern(fileOut);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SQLiteData openHandler = new SQLiteData(this);
		openHandler.addData("abc",inputName.getText().toString() ,inputBeschreibung.getText().toString());
	
		
		
		Intent goToMainActivity = new Intent
				(AddEvent.this,MainActivity.class);
				goToMainActivity.putExtra("Name", inputName.getText().toString());
				goToMainActivity.putExtra("Beschreibung", inputBeschreibung.getText().toString());
				Log.d("AddE AC",inputName.getText()+"."+ inputBeschreibung.getText());
		startActivity(goToMainActivity);
		
		
	}
	private void dateiSpeichern(FileOutputStream fileOut) {
		
		OutputStreamWriter writer = new OutputStreamWriter(fileOut);
		String text = inputName.getText().toString();
		try {
			writer.write(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) { 
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	

}
