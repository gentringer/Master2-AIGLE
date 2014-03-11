package com.mtpAdvisor.adapters_listeners;


import com.mtpAdvisor.fragments.Fragment_InterestList;
import com.mtpAdvisor.fragments.Fragment_Map;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter 
{
	public TabsPagerAdapter(FragmentManager fm) 
	{
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		
		switch (index) {
		case 0:
			// list interest fragment activity
			return new Fragment_InterestList();
	
		case 1:
			// interest on map fragment activity
			return new Fragment_Map();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}
}