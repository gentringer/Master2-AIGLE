package com.mtpAdvisor.userMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.projet.R;
import com.example.projet.R.layout;
import com.example.projet.R.menu;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.mtpAdvisor.Facebook.Fragment_Facebook.SkipLoginCallback;
import com.mtpAdvisor.activites.Activity_Fragments;
import com.mtpAdvisor.activites.ListCateogry;
import com.mtpAdvisor.adapters_listeners.UserAdapter;
import com.mtpAdvisor.classes.Interest;
import com.mtpAdvisor.database.InterestDbHelper;
import com.mtpAdvisor.fragments.Fragment_InterestList;

import android.os.Bundle;
import android.app.Activity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MenuActivity extends Activity {

	private Button skipLoginButton;
	private SkipLoginCallback skipLoginCallback;

	public interface SkipLoginCallback {
		void onSkipLoginPressed();
	}
	public void setSkipLoginCallback(SkipLoginCallback callback) {
		skipLoginCallback = callback;
	}
	
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String category = Activity_Fragments.inputString;

		if(category.equals("Bars/Pubs")){
			category="bars";
		}
		if(category.equals("Hôtels")){
			category="hotels";
		}
		if(category.equals("restaurant")){
			category="restaurant";
		}
		setContentView(R.layout.menu_activity);
		
		InterestDbHelper db = new InterestDbHelper(this);
		
		LoginButton authButton = (LoginButton) findViewById(R.id.menu_login_button);
		authButton.setReadPermissions(Arrays.asList("user_location", "user_birthday", "read_friendlists",  "user_likes"));
		
		
		
		ProfilePictureView profilePictureView = (ProfilePictureView) findViewById(R.id.menu_selection_profile_pic);
		profilePictureView.setProfileId(Fragment_InterestList.currentUser.getUserid());
		profilePictureView.setCropped(true);
		TextView userNameView = (TextView) findViewById(R.id.menu_selection_user_name);
		
		ArrayList<Interest> interestlikes = new ArrayList<Interest>();
		List<String> likes = new ArrayList<String>();


		//bars = intent.getParcelableArrayListExtra("listebar");
		likes=db.getAllLikesByCategory(Fragment_InterestList.currentUser.getUserid(),category);

		
		for(int i=0;i< ListCateogry.inter.size();i++ ){
			for(String s : likes){
				if(s.equals(ListCateogry.inter.get(i).getNameInterest())){
					interestlikes.add(ListCateogry.inter.get(i));
					
				}
			}

		}



		ListView listView = (ListView) this.findViewById(android.R.id.list);  



		final UserAdapter adapter = new UserAdapter(MenuActivity.this, interestlikes, db);

		listView.setAdapter(adapter);



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
