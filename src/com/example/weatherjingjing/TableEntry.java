package com.example.weatherjingjing;

public class TableEntry {
	
	     
	    //private variables
	    String zipcode;
	    String date;
	    String temp;
	     
	    // Empty constructor
	    public TableEntry(){
	         
	    }
	    public TableEntry(String zipcode, String date, String temp){
	    this.zipcode=zipcode;
	    this.date=date;
	    this.temp=temp;
	    }   
	    public String getDate(){
		   return this.date;
		   
		    }   
	    
	    public String getTemp(){
			   return this.temp;
			   
			    }   
}
