package com.mtpAdvisor.activites;

import com.example.projet.R;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;


//Gère la sélection d'une catégroie dans la liste des catégories (Bars, Restaurants etc.)
public class ChooseCategory extends FragmentActivity {

	@Override
	protected void onCreate(Bundle bundle) {

		super.onCreate(bundle);
		setContentView(R.layout.choose_category);
		Bundle extras = getIntent().getExtras();
		String inputString = extras.getString("category");

		if(inputString!=null){
			// Lancement de l'activité qui créé les deux fragments et lance les services
			Intent newin = new Intent(ChooseCategory.this,Activity_Fragments.class);
			newin.putExtra("category", inputString);
			startActivity(newin);
			this.finish();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_category, menu);
		return true;
	}

}
