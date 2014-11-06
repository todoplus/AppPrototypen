package ch.falksolutions.layouttest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteData extends SQLiteOpenHelper {
	
	private static final String TAG = SQLiteData.class.getSimpleName();
	
	private static final String DATABASE_NAME = "savedtodos.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String TABLE_NAME = "data";
	
	private static final String TABLE_DATA_CREATE = "CREATE TABLE" +
		TABLE_NAME	 + "( " + "INTEGER PRIMARY KEY AUTOINCREMENT" + ID + "STRING" + NAME + "STRING"
		+ DESCRIPTION + "STRING);";
	
	SQLiteData(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("SQLiteData", "onCreate() ausgefuehrt");
		db.execSQL(TABLE_DATA_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrade from last version" + oldVersion + "to" + newVersion);
		db.execSQL(TABLE_DATA_CREATE);
		onCreate(db);
		
	}
	
	public void addData(String id, String name, String description) {
		Log.d("SQLiteData", "addData() ausgefuehrt");
		long rowID = 1;
		
		try {
			SQLiteDatabase db = getWritableDatabase();
			ContentValues values = new ContentValues();
			
			values.put(ID, id);
			values.put(NAME, name);
			values.put(DESCRIPTION, description);
			
			rowID = db.insert(TABLE_NAME, null, values);	
		} catch (SQLiteException e) {
			Log.e(TAG, "addData(); rowID=" + rowID);
		} finally {
			Log.d(TAG, "addData(): rowID=" + rowID);
		}
		
		
		 {
			
		}
	}
	

}
