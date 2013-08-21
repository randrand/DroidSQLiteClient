package com.example.weatherjingjing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class Weather extends AsyncTask<String, Integer, Long> {
	private String temp, date;
	private View localview;
	private DataBaseHand ldb;
	public Weather( View view, DataBaseHand db ) {localview=view;ldb=db;}
    protected Long doInBackground(String... zipcode) {
    	
    	HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        urlgetserver="?zipcode=" +zipcode[0];
            response = httpclient.execute(new HttpGet(urlzip));
            StatusLine statusLine = response.getStatusLine();
            
            
            
    	//from zip code to url
    	String urlzip = "http://xml.weather.yahoo.com/forecastrss?p="+ zipcode[0];
    	
    	HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(urlzip));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        
        //parse response string
     // ---------- Parse the xml file -------------
        Document dest = null;
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        //DocumentBuilder parser; // java xml parser
        try {
        	// You can only instantiate DocumentBuilder from the factory
        	DocumentBuilder parser = dbFactory.newDocumentBuilder();
            dest = parser.parse(new ByteArrayInputStream(responseString.getBytes()));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

        //  read an XML file via DOM XML parser
        // http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
        
        // read temperature value
        Node temperatureNode = dest.getElementsByTagName("yweather:condition").item(0);
        temp = temperatureNode.getAttributes().getNamedItem("temp").getNodeValue().toString();
        
        // read unit
        Node tempUnitNode = dest.getElementsByTagName("yweather:units").item(0);
        // Is it 'C or 'F?
        String unit = tempUnitNode.getAttributes().getNamedItem("temperature").getNodeValue().toString();
        temp = temp + unit;

        // read date
        Node dateNode = dest.getElementsByTagName("pubDate").item(0);
        date = dateNode.getTextContent();
        
        // Inserting Contacts
        Log.d("Insert: ", "Inserting .."); 
        ldb.addEntry( zipcode[0], date, temp );        
    //    TableEntry contacts = ldb.getWeather(zipcode[0]); 
         
 
       
    	return (long)1;
    }

    protected void onProgressUpdate(Integer... progress) {
      //  setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
    	 
        TextView dateText = (TextView) localview.findViewById(R.id.editText2);
    	dateText.setText("Date: "+date);
    	
    	
    	TextView tempText = (TextView) localview.findViewById(R.id.editText3);
    	tempText.setText("Temperature: "+temp);
    }
}
