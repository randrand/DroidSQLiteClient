package com.example.weatherjingjing;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class WeatherQuery extends AsyncTask<String, Integer, Long> {
	private String temp, date;
	private View localview;
	private DataBaseHand ldb;
	public WeatherQuery( View view, DataBaseHand db ) {localview=view;ldb=db;}
    protected Long doInBackground(String... zipcode) {
    	
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts.."); 
        TableEntry contacts = ldb.getWeather(zipcode[0]); 
        date = contacts.getDate();
        temp = contacts.getTemp();
  
       
    	return (long)1;
    }

    protected void onProgressUpdate(Integer... progress) {
      //  setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        TextView dateText = (TextView) localview.findViewById(R.id.queryOutput);
    	dateText.setText("Date: "+date + "Temperature: "+temp);
    }
}
