package com.mtpAdvisor.Facebook;

import com.example.projet.R;
import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.mtpAdvisor.activites.ListCateogry;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuItem;


import android.support.v4.app.FragmentActivity;


public class FacebookActivity extends FragmentActivity {

	private static final String USER_SKIPPED_LOGIN_KEY = "user_skipped_login";

	private static final int SPLASH = 0;
	private static final int FRAGMENT_COUNT = SPLASH +1;
	public static boolean menuint = false;
	private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
	private MenuItem settings;
	private boolean isResumed = false;
	private boolean userSkippedLogin = false;
	private UiLifecycleHelper uiHelper;


	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			userSkippedLogin = savedInstanceState.getBoolean(USER_SKIPPED_LOGIN_KEY);
		}
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);

		setContentView(R.layout.activity_facebook);


		FragmentManager fm = getSupportFragmentManager();
		Fragment_Facebook fragment_Facebook = (Fragment_Facebook) fm.findFragmentById(R.id.splashFragment);
		ListFragment disconnect_fragment = (ListFragment) fm.findFragmentById(R.id.userSettingsFragment);

		fragments[SPLASH] = fragment_Facebook;

		FragmentTransaction transaction = fm.beginTransaction();
		for(int i = 0; i < fragments.length; i++) {
			transaction.hide(fragments[i]);
		}
		
		transaction.commit();

		fragment_Facebook.setSkipLoginCallback(new Fragment_Facebook.SkipLoginCallback() {
			@Override
			public void onSkipLoginPressed() {
				// Si l'utilisateur ne veut pas se connecter
				userSkippedLogin = true;
				Intent i = new Intent(FacebookActivity.this, ListCateogry.class);     
				startActivity(i);
				finish(); //should use the finish if y
			}
		});


	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		isResumed = true;


		// Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
		// the onResume methods of the primary Activities that an app may be launched into.
		AppEventsLogger.activateApp(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
		isResumed = false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);

		outState.putBoolean(USER_SKIPPED_LOGIN_KEY, userSkippedLogin);
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		Session session = Session.getActiveSession();
		if(menuint){
			
		}
		else if (session != null && session.isOpened()) {
			// if the session is already open, try to show the selection fragment
			Intent i = new Intent(FacebookActivity.this, ListCateogry.class);     
			startActivity(i);
			finish(); //should use the finish if y
		} else if (userSkippedLogin) {
			Intent i = new Intent(FacebookActivity.this, ListCateogry.class);     
			startActivity(i);
			finish(); //should use the finish if y
		} 
		else {
			// otherwise present the splash screen and ask the user to login, unless the user explicitly skipped.
			showFragment(SPLASH, false);

		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// only add the menu when the selection fragment is showing
		if (fragments[SPLASH].isVisible()) {
			if (menu.size() == 0) {
			}
			return true;
		} else {
			menu.clear();
			settings = null;
		}
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.equals(settings)) {
			return true;
		}
		return false;
	}



	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (isResumed) {
			FragmentManager manager = getSupportFragmentManager();
			int backStackSize = manager.getBackStackEntryCount();
			for (int i = 0; i < backStackSize; i++) {
				manager.popBackStack();
			}
			// check for the OPENED state instead of session.isOpened() since for the
			// OPENED_TOKEN_UPDATED state, the selection fragment should already be showing.
			if (state.equals(SessionState.OPENED)) {
				Intent i = new Intent(FacebookActivity.this, ListCateogry.class);     
				startActivity(i);
				finish(); //should use the finish if y
			} else if (state.isClosed()) {
				showFragment(SPLASH, false);
			}
		}
	}

	private void showFragment(int fragmentIndex, boolean addToBackStack) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		for (int i = 0; i < fragments.length; i++) {
			if (i == fragmentIndex) {
				transaction.show(fragments[i]);
			} else {
				transaction.hide(fragments[i]);
			}
		}
		if (addToBackStack) {
			transaction.addToBackStack(null);
		}
		transaction.commit();
	}

}
