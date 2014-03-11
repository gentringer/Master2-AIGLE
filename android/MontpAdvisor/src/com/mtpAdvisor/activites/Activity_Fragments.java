// Activité qui gère le lancement des fragments / l'affichage des framgements 
// Lancement des services

package com.mtpAdvisor.activites;

import com.example.projet.*;

import com.mtpAdvisor.adapters_listeners.TabsPagerAdapter;
import com.mtpAdvisor.fragments.Fragment_InterestList;
import com.mtpAdvisor.fragments.Fragment_Map;

import android.support.v4.app.*;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;


public class Activity_Fragments extends FragmentActivity  {

	//Correspond à la catégorie sélectionnée par l'utilisateur
	public static String inputString;
	private static ViewPager viewPager;
	private TabsPagerAdapter mAdapter;

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ActionBar actionBar = getActionBar();
		Bundle extras = getIntent().getExtras();
		inputString = extras.getString("category");
		//Modification des String (pour meilleure gestion dans la BDD)
		if(inputString.equals("Restaurants")){
			Activity_Fragments.inputString="restaurant";
		}
		if(inputString.equals("Parkings")){
			Activity_Fragments.inputString="parking";
		}
		if(inputString.equals("Monuments")){
			Activity_Fragments.inputString="monument";
		}
		if(inputString.equals("Bars/Pub")){
			Activity_Fragments.inputString="bars";
		}
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		setContentView(R.layout.activity_main);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		//Adapteur qui gère le swipe
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);

		Tab tabA = actionBar.newTab();
		tabA.setText(getString(R.string.fragmentlist)+" "+inputString);
		tabA.setTabListener(new TabListener<Fragment_InterestList>(this, "Tag A", Fragment_InterestList.class));

		Tab tabC = actionBar.newTab();
		tabC.setText(getString(R.string.map));
		tabC.setTabListener(new TabListener<Fragment_Map>(this, "Tag C", Fragment_Map.class));

		actionBar.addTab(tabA);
		actionBar.addTab(tabC);
		actionBar.setHomeButtonEnabled(true);

		if (savedInstanceState != null) {
			int savedIndex = savedInstanceState.getInt("SAVED_INDEX");
			getActionBar().setSelectedNavigationItem(savedIndex);
		}

		//Swipe entre 2 fragments
		viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When swiping between different app sections, select the corresponding tab.
				// We can also use ActionBar.Tab#select() to do this if we have a reference to the
				// Tab.
				actionBar.setSelectedNavigationItem(position);

			}
		});

	}
	
	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt("SAVED_INDEX", getActionBar().getSelectedNavigationIndex());
	}

	//Définir les actions qui se passant lorsque une tab est sélectionné / désélectionné etc.
	// Callback interface
	public static class TabListener<T extends Fragment> 
	implements ActionBar.TabListener{

		private Fragment mFragment;
		private final FragmentActivity mActivity;
		private final String mTag;
		private final Class<T> mClass;
		public Bundle mArgs;
		private FragmentTransaction fft;

		public TabListener(FragmentActivity activity, String tag, Class<T> clz) {
			this(activity, tag, clz, null);
		}

		public TabListener(FragmentActivity activity, String tag, Class<T> clz,
				Bundle args) {

			mActivity = activity;
			mTag = tag;
			mClass = clz;
			mArgs = args;
			// TODO Auto-generated constructor stub

			mFragment = mActivity.getSupportFragmentManager()
					.findFragmentByTag(mTag);
			
		}


		@Override
		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
			//Do nothing


		}

		//Lorsqu'on sélectionne une tab
		@Override
		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		
			fft = mActivity.getSupportFragmentManager().beginTransaction();

			if (mFragment == null) {
				// Le pager se met sur la tab sélectionné
				viewPager.setCurrentItem(tab.getPosition());

			} else {
				viewPager.setCurrentItem(tab.getPosition());
			}
			mActivity.getSupportFragmentManager()
			.executePendingTransactions();
		}

		@Override
		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
			viewPager.setCurrentItem(tab.getPosition());
			mActivity.getSupportFragmentManager()
			.executePendingTransactions();
		}

	}
	
	
}
