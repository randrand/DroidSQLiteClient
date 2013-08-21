package com.example.weatherjingjing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHand  extends SQLiteOpenHelper {
		 
	    // All Static variables
	    // Database Version
	    private static final int DATABASE_VERSION = 1;
	 
	    // Database Name
	    private static final String DATABASE_NAME = "weather";
	 
	    // Contacts table name
	    private static final String TABLE_WEATHER = "weatherquery";
	 
	    // Contacts Table Columns names
	    private static final String KEY_ID = "zipcode";
	    private static final String KEY_NAME = "date";
	    private static final String KEY_PH_NO = "temperature";
	 
	    public DataBaseHand(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	 
	    // Creating Tables
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WEATHER + "("
	                + KEY_ID + " TEXT," + KEY_NAME + " TEXT,"
	                + KEY_PH_NO + " TEXT" + ")";
	        db.execSQL(CREATE_CONTACTS_TABLE);
	    }
	 
	    // Upgrading database
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // Drop older table if existed
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);
	 
	        // Create tables again
	        onCreate(db);
	    }
	    
	    // Adding new contact
	    void addEntry(String a, String b, String c) {
	        SQLiteDatabase db = this.getWritableDatabase();
	 
	        ContentValues values = new ContentValues();
	        values.put(KEY_ID, a); // Contact Name
	        values.put(KEY_NAME, b); // Contact Phone
	        values.put(KEY_PH_NO, c); 
	 
	        // Inserting Row
	        db.insert(TABLE_WEATHER, null, values);
	      
	        db.close(); // Closing database connection
	    }
	 
	 // Getting weather for a zipcode
	    public TableEntry getWeather( String zipcode) {
	        //List<TableEntry> contactList = new ArrayList<TableEntry>();
	        // Select All Query
	        String selectQuery = "SELECT  * FROM " + TABLE_WEATHER +" where " + KEY_ID+"="+zipcode+ " LIMIT 1";
	        //String selectQuery = "SELECT  * FROM " + TABLE_WEATHER + " LIMIT 1";
	        SQLiteDatabase db = this.getWritableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	 
	        // looping through all rows and adding to list
	       // if (cursor.moveToFirst()) {
	         //   do {
	        TableEntry entry=null;
	        cursor.moveToFirst();
	       
	            	String zip = cursor.getString(cursor.getColumnIndex(KEY_ID));
	           // 	if(zip==zipcode) {
	            		String dateq=cursor.getString(cursor.getColumnIndex(KEY_NAME));
	            		String tempq=cursor.getString(cursor.getColumnIndex(KEY_PH_NO));
	            		entry=new TableEntry(zip, dateq, tempq);
	        
	      
	            //} while (cursor.moveToNext());
	        //}
	
	        return entry;
	    }
	    
	  
}
