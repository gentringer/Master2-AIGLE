package com.mtpAdvisor.services;


import java.util.ArrayList;
import java.util.List;

import com.mtpAdvisor.activites.Activity_Fragments;
import com.mtpAdvisor.classes.Interest;
import com.mtpAdvisor.database.InterestDbHelper;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

public class ServiceAddToLocalDataBase extends IntentService{
	public static String category = Activity_Fragments.inputString;


	InterestDbHelper db = new InterestDbHelper(this);


	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();


	public ServiceAddToLocalDataBase() {
		super("ServiceGetBar");
		// TODO Auto-generated constructor stub
	}



	@Override
	protected void onHandleIntent(Intent intent) {

		// TODO Auto-generated method stub
		StrictMode.setThreadPolicy(policy);
		System.out.println("start service add to database");
		ArrayList<Interest> listinterest = new ArrayList<Interest>();
		Bundle extras = intent.getExtras(); 
		if(extras == null)
			Log.d("intent","null");
		else{
			listinterest = intent.getParcelableArrayListExtra("newinterest");
		}
		
		/*if(category.equals("Bars/Pubs")){
			category="bars";
		}
		if(category.equals("Hôtels")){
			category="hotels";
		}
		if(category.equals("restaurant")){
			category="restaurant";
		}*/
		
//		existinginterest= db.getAllInsterests(category);
//		ArrayList<String> list = new ArrayList<String>();
//		for(Interest in : existinginterest){
//			list.add(in.nameInterest);
//		}
		//db.droptables();

		for(Interest newi : listinterest){
			if(newi.getCategory().equals("Bars/Pubs")){
				newi.category="bars";
			}
			if(newi.getCategory().equals("Hôtels") || newi.getCategory().equals("Hotels")){
				newi.category="hotels";
			}
			if(newi.getCategory().equals("restaurant")){
				newi.category="restaurant";
			}
		}
		
		for(Interest newi : listinterest){
			if(!db.ExistsInterest(newi.getNameInterest(), newi.category)){
				db.addInterest(newi);
			}
		}

//		for(Interest newi : listinterest){
//			if(!list.contains(newi.nameInterest)){
//				if(newi.getNameInterest()!=null&&!newi.getNameInterest().equals("null")){
//					db.addInterest(newi);
//				}
//			}
//		}
		this.stopSelf();
		
		

	}

}
