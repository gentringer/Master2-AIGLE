package com.mtpAdvisor.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mtpAdvisor.activites.Activity_Fragments;
import com.mtpAdvisor.classes.Interest;
import com.mtpAdvisor.database.InterestDbHelper;
import com.mtpAdvisor.parsers.JSONParser;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.StrictMode;


public class ServiceGetAllInterestFromServer extends IntentService{

	InterestDbHelper db = new InterestDbHelper(this);

	private String category;

	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();


	public ServiceGetAllInterestFromServer() {
		super("ServiceGetAllInterestFromServer");
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		category= Activity_Fragments.inputString;
		StrictMode.setThreadPolicy(policy);
		System.out.println("start service get all from server database");

		// Creating JSON Parser object
		JSONParser jParser = new JSONParser();

		ArrayList<Interest> interestList = new ArrayList<Interest>();

		String url_all_products ="";
		if(category.equals("Bars/Pubs")){
			url_all_products = "http://gillesentringer.com/php/get_interests.php";
		}
		if(category.equals("Hôtels") || category.equals("Hotels")){
			url_all_products = "http://gillesentringer.com/php/get_hotels.php";
		}
		if(category.equals("restaurant")){
			url_all_products = "http://gillesentringer.com/php/get_restaurants.php";
		}
		if(category.equals("parking")){
			url_all_products = "http://gillesentringer.com/php/get_parkings.php";
		}
		if(category.equals("monument")){
			url_all_products = "http://gillesentringer.com/php/get_monuments.php";
		}
		
		
		// JSON Node names
		final String TAG_SUCCESS = "success";
		final String TAG_INTEREST = "interest";
		final String TAG_CATEGORY = "category";
		final String TAG_NAME = "nameinterest";
		final String TAG_LAT = "lat";
		final String TAG_LON = "lon";

		// products JSONArray
		JSONArray products = null;

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// getting JSON string from URL
		JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);


		try {
			// Checking for SUCCESS TAG
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				// products found
				// Getting Array of Products
				products = json.getJSONArray(TAG_INTEREST);

				// looping through All Products
				for (int i = 0; i < products.length(); i++) {
					JSONObject c = products.getJSONObject(i);


					// Storing each json item in variable
					String category = c.getString(TAG_CATEGORY);
					String name = c.getString(TAG_NAME);
					String lon = c.getString(TAG_LON);
					String lat = c.getString(TAG_LAT);

					Geocoder geocoder = new Geocoder(getBaseContext(),Locale.getDefault());
					List<Address> addresses = new ArrayList<Address>();
					try {
						addresses = geocoder.getFromLocation(Double.parseDouble(lat),Double.parseDouble(lon), 1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String adresseGeocoder = null;
					for(Address adr:addresses){
						adresseGeocoder=adr.getAddressLine(0);
					}
					Interest interest = new Interest(category, lat, lon, name,adresseGeocoder);
					interest.setStatus(0);
					interest.setNote(0);
					// creating new HashMap

					// adding each child node to HashMap key => value
					interestList.add(interest);
				}
				publishResults(interestList);
				this.stopSelf();

			}
			else {
				publishResults(interestList);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		

	}


	private void publishResults(ArrayList<Interest> al2) {
		Intent intent = new Intent("NEW_BARS");
		//		for(Interest b: al2){
		//		}
		intent.putParcelableArrayListExtra("newbars", al2);
		sendBroadcast(intent);

	}


}
