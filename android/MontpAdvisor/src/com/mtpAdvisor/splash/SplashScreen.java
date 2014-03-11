package com.mtpAdvisor.splash;

import java.lang.reflect.Field;

import com.example.projet.R;
import com.example.projet.R.layout;
import com.example.projet.R.menu;
import com.mtpAdvisor.Facebook.FacebookActivity;
import com.mtpAdvisor.activites.ListCateogry;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.ViewConfiguration;

public class SplashScreen extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 2500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try { ViewConfiguration config = ViewConfiguration.get(this); 

		Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

		if(menuKeyField != null) 
		{ menuKeyField.setAccessible(true); menuKeyField.setBoolean(config, false); } 
		}
		catch (Exception ex) { // Ignore }
		}


		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				// Démarrage de l'activité Facebook
				Intent i = new Intent(SplashScreen.this, FacebookActivity.class);
				startActivity(i);

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}


}
