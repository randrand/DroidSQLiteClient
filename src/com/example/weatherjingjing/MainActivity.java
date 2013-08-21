package com.example.weatherjingjing;

//import android.R;
import com.example.weatherjingjing.R;
//import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {
private DataBaseHand db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.activity_list_item);
		setContentView(R.layout.activity_main);
		db = new DataBaseHand(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 public void sendMessage(View view) {
	        // Do something in response to button
	    	EditText editText = (EditText) findViewById(R.id.editText1);
	    	String zipcode = editText.getText().toString();
	    	Weather weather = new Weather( editText.getRootView(), db );
	    	weather.execute(zipcode);
	    
	    }
	 public void queryWeather(View view) {
	        // Do something in response to button
	    	EditText editText = (EditText) findViewById(R.id.queryText);
	    	String zipcode = editText.getText().toString();
	    	WeatherQuery weather = new WeatherQuery( editText.getRootView(), db );
	    	weather.execute(zipcode);    
	    }

}
